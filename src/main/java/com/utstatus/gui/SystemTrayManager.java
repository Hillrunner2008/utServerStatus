package com.utstatus.gui;

import static com.utstatus.gui.UrtApp.APPLICATION_NAME;
import java.awt.AWTException;
import static java.awt.EventQueue.invokeLater;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.PopupMenu;
import static java.awt.SystemTray.getSystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import static java.lang.System.exit;
import javax.swing.ImageIcon;
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public final class SystemTrayManager {

    private static final Logger logger = getLogger(SystemTrayManager.class);

    private TrayIcon icon;
    private final UrtApp app;

    public SystemTrayManager(final UrtApp app) {
        this.app = app;
        icon = new TrayIcon(getImage(), APPLICATION_NAME, createPopupMenu());
        setupIconListener();
        addSystemTray();
    }

    private void setupIconListener() {
        icon.addActionListener((ActionEvent e) -> {
            invokeLater(() -> {
                synchronized (app) {
                    try {
                        app.updateTable();
                    } catch (Exception ex) {
                        logger.error("Application error", ex);
                    }
                    app.setVisible(true);
                }
            });
        });
    }

    private void addSystemTray() {
        try {
            getSystemTray().remove(icon); //fixes windows bug (at least mostly
            getSystemTray().add(icon);
        } catch (AWTException ex) {
            logger.error("Error while adding system tray", ex);
        }
    }

    public TrayIcon getIcon() {
        return icon;
    }

    public void setIcon(TrayIcon icon) {
        this.icon = icon;
    }

    private Image getImage() {
        ImageIcon defaultIcon = new ImageIcon(SystemTrayManager.class.getClassLoader().getResource("ut_icon.gif"));
        Image img = new BufferedImage(defaultIcon.getIconWidth(),
                defaultIcon.getIconHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        defaultIcon.paintIcon(new Panel(), img.getGraphics(), 0, 0);
        return img;

    }

    public PopupMenu createPopupMenu() throws HeadlessException {
        PopupMenu menu = new PopupMenu();
        MenuItem exit = new MenuItem("Exit");
        exit.addActionListener((ActionEvent e) -> {
            removeSysTrayIcon();
            exit(0);
        });
        menu.add(exit);
        return menu;
    }

    public void removeSysTrayIcon() {
        getSystemTray().remove(icon);
    }
}
