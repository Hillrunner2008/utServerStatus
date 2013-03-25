/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utstatus.utStatusCheck;

import com.utstatus.gui.Setup;
import com.utstatus.gui.SysTray;
import com.utstatus.gui.UrtApp;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author dcnorris
 */
public class Driver {

    private static SysTray sysTray;
    private static UrtApp app;

    public static void main(String[] args) throws Exception {

        Setup setup = new Setup();
        setup.setVisible(true);
        sysTray = SysTray.getInstance();
        app = UrtApp.getInstance();

        TrayIcon icon = sysTray.getIcon();
        icon.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                java.awt.EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        synchronized (app) {
                            try {
                                app.updateTable();
                            } catch (Exception ex) {
                                System.err.println(ex.getMessage());
                            }
                            app.setVisible(true);
                        }
                    }
                });
            }
        });
        SystemTray.getSystemTray().remove(icon); //fixes windows bug (at least mostly
        SystemTray.getSystemTray().add(icon);
    }
}
