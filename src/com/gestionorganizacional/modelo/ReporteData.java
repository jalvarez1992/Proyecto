package com.gestionorganizacional.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ReporteData {
    private final String titulo;
    private final String subtitulo;
    private final List<String> columnas;
    private final List<List<String>> filas;

    public ReporteData(String titulo, String subtitulo, List<String> columnas, List<List<String>> filas) {
        this.titulo = titulo == null ? "Reporte" : titulo;
        this.subtitulo = subtitulo == null ? "" : subtitulo;
        this.columnas = Collections.unmodifiableList(new ArrayList<>(columnas));
        List<List<String>> copia = new ArrayList<>();
        for (List<String> fila : filas) copia.add(Collections.unmodifiableList(new ArrayList<>(fila)));
        this.filas = Collections.unmodifiableList(copia);
    }

    public String getTitulo() { return titulo; }
    public String getSubtitulo() { return subtitulo; }
    public List<String> getColumnas() { return columnas; }
    public List<List<String>> getFilas() { return filas; }
    public int getTotalFilas() { return filas.size(); }

    public ReporteData filtrar(String texto) {
        String criterio = texto == null ? "" : texto.trim().toLowerCase();
        if (criterio.isEmpty()) return this;
        List<List<String>> resultado = new ArrayList<>();
        for (List<String> fila : filas) {
            boolean coincide = fila.stream().anyMatch(v -> v != null && v.toLowerCase().contains(criterio));
            if (coincide) resultado.add(fila);
        }
        return new ReporteData(titulo, subtitulo, columnas, resultado);
    }
}
