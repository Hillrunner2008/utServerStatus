/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

public class SysTray {

    public static Image getImage(SysTray s) {
     
        ImageIcon defaultIcon = new ImageIcon(s.getClass().getResource("/images/ut_icon.gif"));
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

                System.exit(0);

            }
        });

        menu.add(exit);

        return menu;

    }

}
