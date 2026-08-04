package com.gestionorganizacional.conexion;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class ConexionDB {
    private static final Path ARCHIVO_CONFIGURACION =
            Paths.get(System.getProperty("user.dir"), "settings.properties");
    private static final Path SCRIPT_SQL =
            Paths.get(System.getProperty("user.dir"), "database", "database.sql");
    private static Properties configuracion;

    private ConexionDB() {}

    public static Connection obtenerConexion() throws SQLException {
        Properties cfg = obtenerConfiguracion();
        return DriverManager.getConnection(urlBaseDatos(cfg),
                cfg.getProperty("db.user"), cfg.getProperty("db.password"));
    }

    public static synchronized void inicializar() throws SQLException {
        Properties cfg = obtenerConfiguracion();
        cargarControlador();
        validarNombreBaseDatos(cfg.getProperty("db.name"));
        crearBaseDatosSiCorresponde(cfg);
        ejecutarScript();
    }

    private static Properties obtenerConfiguracion() throws SQLException {
        if (configuracion != null) return configuracion;
        Properties cfg = new Properties();
        cfg.setProperty("db.host", "localhost");
        cfg.setProperty("db.port", "3306");
        cfg.setProperty("db.name", "sistema_gestion_organizacional");
        cfg.setProperty("db.user", "root");
        cfg.setProperty("db.password", "");
        cfg.setProperty("db.useSSL", "false");
        cfg.setProperty("db.allowPublicKeyRetrieval", "true");
        cfg.setProperty("db.createIfNotExists", "true");

        if (Files.exists(ARCHIVO_CONFIGURACION)) {
            try (Reader entrada = Files.newBufferedReader(
                    ARCHIVO_CONFIGURACION, StandardCharsets.UTF_8)) {
                cfg.load(entrada);
            } catch (IOException ex) {
                throw new SQLException("No se pudo leer settings.properties.", ex);
            }
        }
        configuracion = cfg;
        return configuracion;
    }

    private static void cargarControlador() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new SQLException("No se encontró el controlador MySQL Connector/J.", ex);
        }
    }

    private static void validarNombreBaseDatos(String nombre) throws SQLException {
        if (nombre == null || !nombre.matches("[A-Za-z0-9_]+")) {
            throw new SQLException("db.name solo puede contener letras, números y guion bajo.");
        }
    }

    private static void crearBaseDatosSiCorresponde(Properties cfg) throws SQLException {
        if (!Boolean.parseBoolean(cfg.getProperty("db.createIfNotExists", "true"))) {
            return;
        }
        String sql = "CREATE DATABASE IF NOT EXISTS `" + cfg.getProperty("db.name")
                + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci";
        try (Connection cn = DriverManager.getConnection(urlServidor(cfg),
                cfg.getProperty("db.user"), cfg.getProperty("db.password"));
             Statement st = cn.createStatement()) {
            st.executeUpdate(sql);
        } catch (SQLException exCreacion) {
            // Un usuario sin permiso CREATE puede continuar si el esquema ya existe.
            try (Connection ignorada = DriverManager.getConnection(urlBaseDatos(cfg),
                    cfg.getProperty("db.user"), cfg.getProperty("db.password"))) {
                return;
            } catch (SQLException exConexion) {
                exConexion.addSuppressed(exCreacion);
                throw exConexion;
            }
        }
    }

    private static void ejecutarScript() throws SQLException {
        if (!Files.exists(SCRIPT_SQL)) {
            throw new SQLException("No se encontró el script: " + SCRIPT_SQL.toAbsolutePath());
        }
        try {
            String sql = Files.readString(SCRIPT_SQL, StandardCharsets.UTF_8);
            try (Connection cn = obtenerConexion(); Statement st = cn.createStatement()) {
                for (String sentencia : sql.split(";")) {
                    String limpia = sentencia.trim();
                    if (!limpia.isEmpty()) st.execute(limpia);
                }
            }
        } catch (IOException ex) {
            throw new SQLException("No se pudo leer database.sql.", ex);
        }
    }

    private static String urlServidor(Properties cfg) {
        return "jdbc:mysql://" + cfg.getProperty("db.host") + ":"
                + cfg.getProperty("db.port") + "/?" + parametros(cfg);
    }

    private static String urlBaseDatos(Properties cfg) {
        return "jdbc:mysql://" + cfg.getProperty("db.host") + ":"
                + cfg.getProperty("db.port") + "/" + cfg.getProperty("db.name")
                + "?" + parametros(cfg);
    }

    private static String parametros(Properties cfg) {
        return "useSSL=" + cfg.getProperty("db.useSSL", "false")
                + "&allowPublicKeyRetrieval="
                + cfg.getProperty("db.allowPublicKeyRetrieval", "true")
                + "&serverTimezone=America/Tegucigalpa"
                + "&useUnicode=true&characterEncoding=UTF-8"
                + "&connectTimeout=5000&socketTimeout=10000";
    }
}
