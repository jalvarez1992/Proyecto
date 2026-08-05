package com.gestionorganizacional.util;

import com.gestionorganizacional.modelo.ReporteData;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/** Generador PDF sencillo y autocontenido para tablas, sin librerías externas. */
public final class ExportadorPDF {
    private static final int ANCHO = 842;
    private static final int ALTO = 595;
    private static final int MARGEN = 36;
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private ExportadorPDF() {}

    public static void exportar(Path archivo, ReporteData reporte) throws IOException {
        Files.write(archivo, generar(reporte));
    }

    public static byte[] generar(ReporteData reporte) throws IOException {
        List<List<String>> paginas = paginar(reporte);
        int totalObjetos = 4 + paginas.size() * 2;
        List<byte[]> objetos = new ArrayList<>(totalObjetos + 1);
        objetos.add(null);
        objetos.add(bytes("<< /Type /Catalog /Pages 2 0 R >>"));

        StringBuilder hijos = new StringBuilder("[");
        for (int i = 0; i < paginas.size(); i++) hijos.append(5 + i * 2).append(" 0 R ");
        hijos.append(']');
        objetos.add(bytes("<< /Type /Pages /Count " + paginas.size() + " /Kids " + hijos + " >>"));
        objetos.add(bytes("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica /Encoding /WinAnsiEncoding >>"));
        objetos.add(bytes("<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica-Bold /Encoding /WinAnsiEncoding >>"));

        for (int i = 0; i < paginas.size(); i++) {
            int paginaId = 5 + i * 2;
            int contenidoId = paginaId + 1;
            String pagina = "<< /Type /Page /Parent 2 0 R /MediaBox [0 0 " + ANCHO + " " + ALTO
                    + "] /Resources << /Font << /F1 3 0 R /F2 4 0 R >> >> /Contents "
                    + contenidoId + " 0 R >>";
            objetos.add(bytes(pagina));
            byte[] contenido = bytes(construirContenido(reporte, paginas.get(i), i + 1, paginas.size()));
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            stream.write(bytes("<< /Length " + contenido.length + " >>\nstream\n"));
            stream.write(contenido);
            stream.write(bytes("\nendstream"));
            objetos.add(stream.toByteArray());
        }

        ByteArrayOutputStream salida = new ByteArrayOutputStream();
        salida.write("%PDF-1.4\n".getBytes(StandardCharsets.US_ASCII));
        salida.write(new byte[]{'%', (byte) 0xE2, (byte) 0xE3, (byte) 0xCF, (byte) 0xD3, '\n'});
        long[] offsets = new long[objetos.size()];
        for (int i = 1; i < objetos.size(); i++) {
            offsets[i] = salida.size();
            salida.write(bytes(i + " 0 obj\n"));
            salida.write(objetos.get(i));
            salida.write(bytes("\nendobj\n"));
        }
        long inicioXref = salida.size();
        salida.write(bytes("xref\n0 " + objetos.size() + "\n"));
        salida.write(bytes("0000000000 65535 f \n"));
        for (int i = 1; i < objetos.size(); i++) {
            salida.write(bytes(String.format("%010d 00000 n \n", offsets[i])));
        }
        salida.write(bytes("trailer\n<< /Size " + objetos.size() + " /Root 1 0 R >>\nstartxref\n"
                + inicioXref + "\n%%EOF\n"));
        return salida.toByteArray();
    }

    private static List<List<String>> paginar(ReporteData reporte) {
        int filasPorPagina = 22;
        List<List<String>> paginas = new ArrayList<>();
        List<String> serializadas = new ArrayList<>();
        for (List<String> fila : reporte.getFilas()) serializadas.add(String.join("\u001F", fila));
        if (serializadas.isEmpty()) paginas.add(new ArrayList<>());
        for (int i = 0; i < serializadas.size(); i += filasPorPagina) {
            paginas.add(new ArrayList<>(serializadas.subList(i, Math.min(i + filasPorPagina, serializadas.size()))));
        }
        return paginas;
    }

    private static String construirContenido(ReporteData reporte, List<String> filas,
            int pagina, int totalPaginas) {
        int columnas = Math.max(1, reporte.getColumnas().size());
        float anchoTabla = ANCHO - MARGEN * 2f;
        float anchoColumna = anchoTabla / columnas;
        float y = ALTO - MARGEN;
        StringBuilder c = new StringBuilder();
        texto(c, "F2", 18, MARGEN, y, reporte.getTitulo());
        y -= 20;
        texto(c, "F1", 9, MARGEN, y, reporte.getSubtitulo());
        texto(c, "F1", 8, ANCHO - 180, y, "Generado: " + LocalDateTime.now().format(FECHA));
        y -= 24;

        c.append("0.94 0.96 0.98 rg ").append(MARGEN).append(' ').append(y - 18)
                .append(' ').append(anchoTabla).append(" 22 re f\n");
        c.append("0.14 0.39 0.92 RG 0.7 w ").append(MARGEN).append(' ').append(y - 18)
                .append(' ').append(anchoTabla).append(" 22 re S\n");
        for (int i = 0; i < columnas; i++) {
            String encabezado = i < reporte.getColumnas().size() ? reporte.getColumnas().get(i) : "";
            texto(c, "F2", 8, MARGEN + i * anchoColumna + 4, y - 5,
                    truncar(encabezado, caracteresPorColumna(anchoColumna, 8)));
        }
        y -= 24;

        for (String filaSerializada : filas) {
            String[] valores = filaSerializada.split("\u001F", -1);
            c.append("0.88 0.91 0.95 RG 0.35 w ").append(MARGEN).append(' ').append(y - 13)
                    .append(" m ").append(MARGEN + anchoTabla).append(' ').append(y - 13).append(" l S\n");
            for (int i = 0; i < columnas; i++) {
                String valor = i < valores.length ? valores[i] : "";
                texto(c, "F1", 7.5f, MARGEN + i * anchoColumna + 4, y,
                        truncar(valor, caracteresPorColumna(anchoColumna, 7.5f)));
            }
            y -= 21;
        }
        texto(c, "F1", 8, MARGEN, 20, "Registros: " + reporte.getTotalFilas());
        texto(c, "F1", 8, ANCHO - 100, 20, "Página " + pagina + " de " + totalPaginas);
        return c.toString();
    }

    private static int caracteresPorColumna(float ancho, float fuente) {
        return Math.max(5, (int) (ancho / (fuente * 0.52f)));
    }

    private static String truncar(String valor, int maximo) {
        String texto = valor == null ? "" : valor.replace('\n', ' ').replace('\r', ' ');
        if (texto.length() <= maximo) return texto;
        return texto.substring(0, Math.max(1, maximo - 1)) + "…";
    }

    private static void texto(StringBuilder c, String fuente, float tamano, float x, float y, String texto) {
        c.append("BT /").append(fuente).append(' ').append(tamano).append(" Tf ")
                .append(x).append(' ').append(y).append(" Td (").append(escapar(texto)).append(") Tj ET\n");
    }

    private static String escapar(String valor) {
        String normalizado = valor == null ? "" : valor;
        normalizado = normalizado.replace('…', '.').replace('–', '-').replace('—', '-');
        return normalizado.replace("\\", "\\\\").replace("(", "\\(").replace(")", "\\)");
    }

    private static byte[] bytes(String texto) {
        return texto.getBytes(StandardCharsets.ISO_8859_1);
    }
}
