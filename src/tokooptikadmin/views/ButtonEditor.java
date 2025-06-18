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
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tokooptikadmin.models.ProdukModel;

public class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean clicked;
    private ProdukPanel produkPanel;
    private int selectedRow;

    public ButtonEditor(JCheckBox checkBox, String label, ProdukPanel produkPanel) {
        super(checkBox);
        this.produkPanel = produkPanel;
        button = new JButton();
        button.setOpaque(true);
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setBackground(new Color(193, 214, 193));
        button.setFocusPainted(false);
        button.setForeground(Color.BLACK);
        button.setIcon(new ImageIcon(getClass().getResource("/assets/ic_detail.png")));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped(); // supaya editor tidak stuck
                ProdukModel selected = getProdukFromTable();
                if (selected != null) {
                    produkPanel.showDetailDialog(selected);
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj,
            boolean isSelected, int row, int col) {
        this.label = obj != null ? obj.toString() : "Rincian";
        button.setText(label);
        this.selectedRow = row;
        this.clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    private ProdukModel getProdukFromTable() {
        JTable table = produkPanel.getTable();
        int modelRow = table.convertRowIndexToModel(selectedRow);

        try {
            return new ProdukModel(
                    0,
                    table.getValueAt(modelRow, 1).toString(),
                    table.getValueAt(modelRow, 2).toString(),
                    table.getValueAt(modelRow, 3).toString(),
                    "", 0,
                    Double.parseDouble(table.getValueAt(modelRow, 4).toString()),
                    Integer.parseInt(table.getValueAt(modelRow, 5).toString())
            );
        } catch (Exception e) {
            return null;
        }
    }
}
