/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.views;

/**
 *
 * @author ngato
 */
import tokooptikadmin.controllers.ProdukController;
import tokooptikadmin.models.ProdukModel;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;

import java.util.List;

public class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private ProdukPanel parent;
    private int selectedRow;

    public ButtonEditor(JCheckBox checkBox, String label, ProdukPanel parent) {
        super(checkBox);
        this.label = label;
        this.parent = parent;
        button = new JButton(label);
        button.setOpaque(true);
        button.addActionListener(e -> fireEditingStopped());
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        selectedRow = row;
        isPushed = true;
        button.setText(label);
        return button;
    }

    public Object getCellEditorValue() {
        if (isPushed) {
            int modelRow = parent.getTable().convertRowIndexToModel(selectedRow);
            int id = Integer.parseInt(parent.getTable().getModel().getValueAt(modelRow, 0).toString());

            if (label.equals("Edit")) {
                ProdukModel produk = ProdukController.getProdukById(id);
                parent.showEditDialog(produk);
            } else if (label.equals("Hapus")) {
                int confirm = JOptionPane.showConfirmDialog(parent, "Yakin hapus produk ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (ProdukController.hapusProduk(id)) {
                        JOptionPane.showMessageDialog(parent, "Produk berhasil dihapus.");
                        parent.loadData();
                    } else {
                        JOptionPane.showMessageDialog(parent, "Gagal menghapus produk.");
                    }
                }
            }
        }
        isPushed = false;
        return label;
    }

}
