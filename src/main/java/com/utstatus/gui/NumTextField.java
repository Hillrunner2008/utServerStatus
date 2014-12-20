
package com.utstatus.gui;

import java.awt.event.KeyEvent;
import java.util.regex.Pattern;
import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 *
 * @author dcnorris
 */
public class NumTextField extends JTextField {

    private static final long serialVersionUID = 1L;
    private static final Pattern NUMBERS = Pattern.compile("[0-9]");

    public NumTextField(String text) {
        super(text);
    }

    public NumTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    public NumTextField(int columns) {
        super(columns);
    }

    public NumTextField(String text, int columns) {
        super(text, columns);
    }

    @Override
    public void processKeyEvent(KeyEvent ev) {
        char ch = ev.getKeyChar();

        if (NUMBERS.matcher(String.valueOf(ch)).matches()
                || ch == KeyEvent.VK_BACK_SPACE || ch == KeyEvent.VK_DELETE
                || ch == KeyEvent.VK_ENTER) {
            super.processKeyEvent(ev);
            return;
        }

        ev.consume();
    }
}
