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
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.List;

public class ProdukPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    public ProdukPanel() {
        setLayout(new BorderLayout());

        // INISIALISASI TABEL DENGAN KOLOM + ACTION BUTTON
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
            "ID", "Kode", "Nama", "Jenis", "Merk", "Harga Beli", "Harga Jual", "Stok", "Edit", "Hapus"
        });

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ATUR RENDERER & EDITOR TOMBOL
        table.getColumn("Edit").setCellRenderer(new ButtonRenderer());
        table.getColumn("Edit").setCellEditor(new ButtonEditor(new JCheckBox(), "Edit", this));
        table.getColumn("Hapus").setCellRenderer(new ButtonRenderer());
        table.getColumn("Hapus").setCellEditor(new ButtonEditor(new JCheckBox(), "Hapus", this));

        // FORM INPUT PRODUK
        JPanel formPanel = new JPanel(new GridLayout(3, 5, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Tambah Produk"));

        JTextField tfKode = new JTextField();
        JTextField tfNama = new JTextField();
        JComboBox<String> cbJenis = new JComboBox<>(new String[]{"Frame", "Lensa", "Aksesoris"});
        JTextField tfMerk = new JTextField();
        JTextField tfHargaBeli = new JTextField();
        JTextField tfHargaJual = new JTextField();
        JTextField tfStok = new JTextField();

        formPanel.add(new JLabel("Kode"));
        formPanel.add(new JLabel("Nama"));
        formPanel.add(new JLabel("Jenis"));
        formPanel.add(new JLabel("Merk"));
        formPanel.add(new JLabel("Harga Beli"));

        formPanel.add(tfKode);
        formPanel.add(tfNama);
        formPanel.add(cbJenis);
        formPanel.add(tfMerk);
        formPanel.add(tfHargaBeli);

        formPanel.add(new JLabel("Harga Jual"));
        formPanel.add(new JLabel("Stok"));
        formPanel.add(new JLabel());
        formPanel.add(new JLabel());
        formPanel.add(new JLabel());

        formPanel.add(tfHargaJual);
        formPanel.add(tfStok);
        formPanel.add(new JLabel());
        formPanel.add(new JLabel());

        JButton btnTambah = new JButton("Tambah Produk");
        formPanel.add(btnTambah);

        add(formPanel, BorderLayout.NORTH);

        // AKSI TOMBOL TAMBAH
        btnTambah.addActionListener(e -> {
            try {
                ProdukModel produk = new ProdukModel(
                        0,
                        tfKode.getText(),
                        tfNama.getText(),
                        cbJenis.getSelectedItem().toString(),
                        tfMerk.getText(),
                        Double.parseDouble(tfHargaBeli.getText()),
                        Double.parseDouble(tfHargaJual.getText()),
                        Integer.parseInt(tfStok.getText())
                );

                if (ProdukController.tambahProduk(produk)) {
                    JOptionPane.showMessageDialog(this, "Produk berhasil ditambahkan.");
                    tfKode.setText("");
                    tfNama.setText("");
                    tfMerk.setText("");
                    tfHargaBeli.setText("");
                    tfHargaJual.setText("");
                    tfStok.setText("");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menambahkan produk.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // MUAT DATA PRODUK AWAL
        loadData();
    }

    public void loadData() {
        tableModel.setRowCount(0);
        List<ProdukModel> produkList = ProdukController.getAllProduk();
        for (ProdukModel p : produkList) {
            tableModel.addRow(new Object[]{
                p.getId(), p.getKodeProduk(), p.getNamaProduk(), p.getJenis(),
                p.getMerk(), p.getHargaBeli(), p.getHargaJual(), p.getStok(), "Edit", "Hapus"
            });
        }
    }

    public void showEditDialog(ProdukModel produk) {
        JTextField tfKode = new JTextField(produk.getKodeProduk());
        JTextField tfNama = new JTextField(produk.getNamaProduk());
        JComboBox<String> cbJenis = new JComboBox<>(new String[]{"Frame", "Lensa", "Aksesoris"});
        cbJenis.setSelectedItem(produk.getJenis());
        JTextField tfMerk = new JTextField(produk.getMerk());
        JTextField tfHargaBeli = new JTextField(String.valueOf(produk.getHargaBeli()));
        JTextField tfHargaJual = new JTextField(String.valueOf(produk.getHargaJual()));
        JTextField tfStok = new JTextField(String.valueOf(produk.getStok()));

        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Kode"));
        panel.add(tfKode);
        panel.add(new JLabel("Nama"));
        panel.add(tfNama);
        panel.add(new JLabel("Jenis"));
        panel.add(cbJenis);
        panel.add(new JLabel("Merk"));
        panel.add(tfMerk);
        panel.add(new JLabel("Harga Beli"));
        panel.add(tfHargaBeli);
        panel.add(new JLabel("Harga Jual"));
        panel.add(tfHargaJual);
        panel.add(new JLabel("Stok"));
        panel.add(tfStok);

        int result = JOptionPane.showConfirmDialog(this, panel, "Edit Produk", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            ProdukModel updated = new ProdukModel(
                    produk.getId(),
                    tfKode.getText(),
                    tfNama.getText(),
                    cbJenis.getSelectedItem().toString(),
                    tfMerk.getText(),
                    Double.parseDouble(tfHargaBeli.getText()),
                    Double.parseDouble(tfHargaJual.getText()),
                    Integer.parseInt(tfStok.getText())
            );

            if (ProdukController.updateProduk(updated)) {
                JOptionPane.showMessageDialog(this, "Produk berhasil diupdate.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Gagal update produk.");
            }
        }
    }

    public JTable getTable() {
        return table;
    }
}
