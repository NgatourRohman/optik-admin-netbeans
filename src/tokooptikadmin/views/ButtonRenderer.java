/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.views;

/**
 *
 * @author ngato
 */
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ButtonRenderer extends JButton implements TableCellRenderer {

    private final String iconPath;

    public ButtonRenderer() {
        this(null); // default tanpa ikon
    }

    public ButtonRenderer(String iconPath) {
        this.iconPath = iconPath;
        setOpaque(true);
        setForeground(Color.WHITE);
        setFont(new Font("SansSerif", Font.PLAIN, 12));
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value != null ? value.toString() : "Aksi");
        setBackground(new Color(193, 214, 193)); // warna sesuai desain
        setForeground(Color.BLACK);
        setFocusPainted(false);

        if (iconPath != null) {
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
                Image img = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(img));
                setHorizontalTextPosition(SwingConstants.RIGHT);
            } catch (Exception e) {
                setIcon(null);
            }
        } else {
            setIcon(null);
        }

        return this;
    }
}
