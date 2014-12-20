package com.utstatus.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class SysTray {

    private static SysTray singleton;
    private TrayIcon icon;

    public static SysTray getInstance() {
        if (singleton == null) {
            singleton = new SysTray();
        }
        return singleton;
    }

    private SysTray() {
        icon = new TrayIcon(SysTray.getImage(this),
                "UT Status App", SysTray.createPopupMenu());
    }

    public TrayIcon getIcon() {
        return icon;
    }

    public void setIcon(TrayIcon icon) {
        this.icon = icon;
    }

    public static Image getImage(SysTray s) {

        ImageIcon defaultIcon = new ImageIcon(s.getClass().getResource("/ut_icon.gif"));
        Image img = new BufferedImage(defaultIcon.getIconWidth(),
                defaultIcon.getIconHeight(),
                BufferedImage.TYPE_4BYTE_ABGR);
        defaultIcon.paintIcon(new Panel(), img.getGraphics(), 0, 0);
        return img;

    }

    public static PopupMenu createPopupMenu() throws
            HeadlessException {

        PopupMenu menu = new PopupMenu();

        MenuItem exit = new MenuItem("Exit");

        exit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                removeSysTrayIcon();
                System.exit(0);

            }
        });

        menu.add(exit);

        return menu;

    }

    public static void removeSysTrayIcon() {
        TrayIcon icon = SysTray.getInstance().getIcon();
        SystemTray.getSystemTray().remove(icon);
    }
}
