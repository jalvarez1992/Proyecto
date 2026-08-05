package com.gestionorganizacional.util;

import com.gestionorganizacional.modelo.ReporteData;
import java.awt.Component;
import java.awt.Window;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

public final class ExportacionUtil {
    private ExportacionUtil() {}

    public static void instalarAccionesExportacion(Component padre, JPanel panelAcciones,
            JTable tabla, String titulo) {
        JButton btnPdf = new JButton("Exportar PDF");
        JButton btnCsv = new JButton("Exportar CSV");
        EstilosUI.configurarBoton(btnPdf, IconosUI.Tipo.PDF, EstilosUI.VarianteBoton.SECUNDARIO);
        EstilosUI.configurarBoton(btnCsv, IconosUI.Tipo.CSV, EstilosUI.VarianteBoton.SECUNDARIO);
        btnPdf.addActionListener(e -> exportarPdf(padre, tabla, titulo));
        btnCsv.addActionListener(e -> exportarCsv(padre, tabla, titulo));
        panelAcciones.add(btnPdf);
        panelAcciones.add(btnCsv);
    }

    public static ReporteData desdeTabla(JTable tabla, String titulo) {
        if (tabla == null) throw new IllegalArgumentException("La tabla a exportar no está disponible.");
        List<String> columnas = new ArrayList<>();
        for (int c = 0; c < tabla.getColumnCount(); c++) columnas.add(tabla.getColumnName(c));
        List<List<String>> filas = new ArrayList<>();
        // JTable#getValueAt respeta el orden visible cuando existe un RowSorter.
        for (int r = 0; r < tabla.getRowCount(); r++) {
            List<String> fila = new ArrayList<>();
            for (int c = 0; c < tabla.getColumnCount(); c++) {
                Object valor = tabla.getValueAt(r, c);
                fila.add(valor == null ? "" : String.valueOf(valor));
            }
            filas.add(fila);
        }
        return new ReporteData(titulo, "Datos visibles en el módulo", columnas, filas);
    }

    public static void exportarPdf(Component padre, JTable tabla, String titulo) {
        try {
            Path archivo = seleccionarArchivo(padre, titulo, "pdf", "Documento PDF");
            if (archivo == null) return;
            ExportadorPDF.exportar(archivo, desdeTabla(tabla, titulo));
            validarArchivoGenerado(archivo);
            mostrarExito(padre, archivo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(padre, "No se pudo exportar el PDF:\n" + ex.getMessage(),
                    "Error de exportación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void exportarCsv(Component padre, JTable tabla, String titulo) {
        try {
            Path archivo = seleccionarArchivo(padre, titulo, "csv", "Archivo CSV");
            if (archivo == null) return;
            ExportadorCSV.exportar(archivo, desdeTabla(tabla, titulo));
            validarArchivoGenerado(archivo);
            mostrarExito(padre, archivo);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(padre, "No se pudo exportar el CSV:\n" + ex.getMessage(),
                    "Error de exportación", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Path seleccionarArchivo(Component padre, String titulo, String extension, String descripcion) {
        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Guardar " + descripcion);
        selector.setFileFilter(new FileNameExtensionFilter(descripcion, extension));
        selector.setSelectedFile(new java.io.File(nombreSeguro(titulo) + "." + extension));
        Window ventana = padre == null ? null : SwingUtilities.getWindowAncestor(padre);
        Component propietario = ventana == null ? padre : ventana;
        if (selector.showSaveDialog(propietario) != JFileChooser.APPROVE_OPTION) return null;
        Path ruta = selector.getSelectedFile().toPath().toAbsolutePath().normalize();
        if (!ruta.getFileName().toString().toLowerCase().endsWith("." + extension)) {
            ruta = ruta.resolveSibling(ruta.getFileName() + "." + extension);
        }
        if (java.nio.file.Files.exists(ruta)) {
            int respuesta = JOptionPane.showConfirmDialog(padre, "El archivo ya existe. ¿Desea reemplazarlo?",
                    "Confirmar reemplazo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (respuesta != JOptionPane.YES_OPTION) return null;
        }
        return ruta;
    }

    public static void validarArchivoGenerado(Path archivo) throws IOException {
        if (archivo == null || !java.nio.file.Files.isRegularFile(archivo)
                || java.nio.file.Files.size(archivo) == 0L) {
            throw new IOException("El archivo no pudo generarse o quedó vacío.");
        }
    }

    private static String nombreSeguro(String titulo) {
        String base = titulo == null ? "reporte" : titulo.toLowerCase();
        base = java.text.Normalizer.normalize(base, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "").replaceAll("[^a-z0-9]+", "_")
                .replaceAll("^_+|_+$", "");
        return base.isBlank() ? "reporte" : base;
    }

    private static void mostrarExito(Component padre, Path archivo) {
        JOptionPane.showMessageDialog(padre, "Archivo generado correctamente:\n" + archivo.toAbsolutePath(),
                "Exportación completada", JOptionPane.INFORMATION_MESSAGE);
    }
}
