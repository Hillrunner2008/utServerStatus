/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utstatus.utStatusCheck;

import com.utstatus.globals.Constants;
import com.utstatus.gui.Setup;
import com.utstatus.gui.SysTray;
import com.utstatus.gui.UrtApp;
import com.utstatus.sound.SoundPlayer;
import com.utstatus.sound.SoundPlayerService;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author dcnorris
 */
public class Driver {

    private static SysTray sysTray;
    private static UrtApp app;

    @SuppressWarnings("SleepWhileInLoop")
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
                                //do nothing
                            }
                            app.setVisible(true);
                        }
                    }
                });
            }
        });
        SystemTray.getSystemTray().remove(icon); //? bug fix
        SystemTray.getSystemTray().add(icon);



    }
}
