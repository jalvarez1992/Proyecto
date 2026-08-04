package com.gestionorganizacional.principal;

import com.gestionorganizacional.util.EstilosUI;
import com.gestionorganizacional.vista.FrmSplash;
import java.awt.EventQueue;
import javax.swing.UIManager;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {}
        EstilosUI.configurarLookAndFeel();
        EventQueue.invokeLater(() -> new FrmSplash().iniciarCarga());
    }
}
