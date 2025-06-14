/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.views;

/**
 *
 * @author ngato
 */
import tokooptikadmin.controllers.PelangganController;
import tokooptikadmin.models.PelangganModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PelangganPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public PelangganPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));
        JTextField tfNama = new JTextField();
        JTextField tfNoHp = new JTextField();
        JTextField tfAlamat = new JTextField();
        JTextField tfCatatan = new JTextField();
        JButton btnSimpan = new JButton("Simpan");

        formPanel.setBorder(BorderFactory.createTitledBorder("Tambah Pelanggan"));
        formPanel.add(new JLabel("Nama"));
        formPanel.add(tfNama);
        formPanel.add(new JLabel("No. HP"));
        formPanel.add(tfNoHp);
        formPanel.add(new JLabel("Alamat"));
        formPanel.add(tfAlamat);
        formPanel.add(new JLabel("Catatan"));
        formPanel.add(tfCatatan);
        formPanel.add(new JLabel());
        formPanel.add(btnSimpan);

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "No. HP", "Alamat", "Catatan"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();

        btnSimpan.addActionListener(e -> {
            PelangganModel p = new PelangganModel(0, tfNama.getText(), tfNoHp.getText(), tfAlamat.getText(), tfCatatan.getText());
            if (PelangganController.tambah(p)) {
                JOptionPane.showMessageDialog(this, "Pelanggan ditambahkan.");
                tfNama.setText("");
                tfNoHp.setText("");
                tfAlamat.setText("");
                tfCatatan.setText("");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menambahkan pelanggan.");
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        for (PelangganModel p : PelangganController.getAll()) {
            tableModel.addRow(new Object[]{p.getId(), p.getNama(), p.getNoHp(), p.getAlamat(), p.getCatatan()});
        }
    }
}
