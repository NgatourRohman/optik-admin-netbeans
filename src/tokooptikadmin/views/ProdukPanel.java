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

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
            "ID", "Kode", "Nama", "Jenis", "Merk", "Harga Beli", "Harga Jual", "Stok"
        });

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();

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
    }

    private void loadData() {
        tableModel.setRowCount(0); // clear table
        List<ProdukModel> produkList = ProdukController.getAllProduk();
        for (ProdukModel p : produkList) {
            tableModel.addRow(new Object[]{
                p.getId(), p.getKodeProduk(), p.getNamaProduk(), p.getJenis(),
                p.getMerk(), p.getHargaBeli(), p.getHargaJual(), p.getStok()
            });
        }
    }
}
