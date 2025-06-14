/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.components;

/**
 *
 * @author ngato
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class PlaceholderPasswordField extends JPasswordField implements FocusListener {

    private final String placeholder;
    private boolean showingPlaceholder;

    public PlaceholderPasswordField(String placeholder) {
        this.placeholder = placeholder;
        this.showingPlaceholder = true;
        setText(placeholder);
        setForeground(Color.GRAY);
        addFocusListener(this);
        setEchoChar((char) 0); // tampilkan teks, bukan bintang
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (showingPlaceholder) {
            setText("");
            setEchoChar('•');
            setForeground(Color.BLACK);
            showingPlaceholder = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (getPassword().length == 0) {
            setText(placeholder);
            setForeground(Color.GRAY);
            setEchoChar((char) 0); // tampilkan teks placeholder
            showingPlaceholder = true;
        }
    }

    public boolean isShowingPlaceholder() {
        return showingPlaceholder;
    }
}
