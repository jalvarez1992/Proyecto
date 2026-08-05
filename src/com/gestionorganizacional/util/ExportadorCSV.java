package com.gestionorganizacional.util;

import com.gestionorganizacional.modelo.ReporteData;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExportadorCSV {
    private ExportadorCSV() {}

    public static void exportar(Path archivo, ReporteData reporte) throws IOException {
        try (BufferedWriter salida = Files.newBufferedWriter(archivo, StandardCharsets.UTF_8)) {
            salida.write('\ufeff');
            escribirFila(salida, reporte.getColumnas());
            for (var fila : reporte.getFilas()) escribirFila(salida, fila);
        }
    }

    private static void escribirFila(BufferedWriter salida, Iterable<String> valores) throws IOException {
        boolean primero = true;
        for (String valor : valores) {
            if (!primero) salida.write(',');
            salida.write(escapar(valor));
            primero = false;
        }
        salida.newLine();
    }

    private static String escapar(String valor) {
        String texto = valor == null ? "" : valor;
        return '"' + texto.replace("\"", "\"\"") + '"';
    }
}
