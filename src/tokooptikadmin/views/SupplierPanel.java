/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.views;

/**
 *
 * @author ngato
 */
import tokooptikadmin.controllers.SupplierController;
import tokooptikadmin.models.SupplierModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SupplierPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public SupplierPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        JTextField tfNama = new JTextField();
        JTextField tfNoHp = new JTextField();
        JTextField tfAlamat = new JTextField();
        JButton btnSimpan = new JButton("Simpan");

        formPanel.setBorder(BorderFactory.createTitledBorder("Tambah Supplier"));
        formPanel.add(new JLabel("Nama"));
        formPanel.add(tfNama);
        formPanel.add(new JLabel("No. HP"));
        formPanel.add(tfNoHp);
        formPanel.add(new JLabel("Alamat"));
        formPanel.add(tfAlamat);
        formPanel.add(new JLabel());
        formPanel.add(btnSimpan);

        add(formPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "No. HP", "Alamat"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();

        btnSimpan.addActionListener(e -> {
            SupplierModel s = new SupplierModel(0, tfNama.getText(), tfNoHp.getText(), tfAlamat.getText());
            if (SupplierController.tambah(s)) {
                JOptionPane.showMessageDialog(this, "Supplier ditambahkan.");
                tfNama.setText("");
                tfNoHp.setText("");
                tfAlamat.setText("");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan supplier.");
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        for (SupplierModel s : SupplierController.getAll()) {
            tableModel.addRow(new Object[]{s.getId(), s.getNama(), s.getNoHp(), s.getAlamat()});
        }
    }
}
