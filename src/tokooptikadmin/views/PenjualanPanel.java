/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.views;

/**
 *
 * @author ngato
 */
import tokooptikadmin.controllers.PenjualanController;
import tokooptikadmin.controllers.ProdukController;
import tokooptikadmin.models.KeranjangItem;
import tokooptikadmin.models.ProdukModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PenjualanPanel extends JPanel {
    private JTextField tfPelanggan, tfBayar;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<ProdukModel> cbProduk;
    private JSpinner spJumlah;

    private List<KeranjangItem> keranjang = new ArrayList<>();

    public PenjualanPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Transaksi Penjualan"));

        tfPelanggan = new JTextField();
        cbProduk = new JComboBox<>();
        spJumlah = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        JButton btnTambah = new JButton("Tambah");

        formPanel.add(new JLabel("Pelanggan"));
        formPanel.add(tfPelanggan);
        formPanel.add(new JLabel("Produk"));
        formPanel.add(cbProduk);
        formPanel.add(new JLabel("Jumlah"));
        formPanel.add(spJumlah);
        formPanel.add(new JLabel());
        formPanel.add(btnTambah);

        add(formPanel, BorderLayout.NORTH);

        // Tabel keranjang
        tableModel = new DefaultTableModel(new String[] {
            "Kode", "Nama", "Harga", "Jumlah", "Subtotal"
        }, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Panel bawah
        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        tfBayar = new JTextField();
        JButton btnSimpan = new JButton("Simpan Transaksi");
        bottomPanel.add(new JLabel("Bayar"));
        bottomPanel.add(tfBayar);
        bottomPanel.add(btnSimpan);
        add(bottomPanel, BorderLayout.SOUTH);

        // Load produk
        ProdukController.getAllProduk().forEach(cbProduk::addItem);

        // Aksi Tambah ke keranjang
        btnTambah.addActionListener(e -> {
            ProdukModel selected = (ProdukModel) cbProduk.getSelectedItem();
            int jumlah = (Integer) spJumlah.getValue();
            keranjang.add(new KeranjangItem(selected, jumlah));
            tableModel.addRow(new Object[]{
                selected.getKodeProduk(),
                selected.getNamaProduk(),
                selected.getHargaJual(),
                jumlah,
                selected.getHargaJual() * jumlah
            });
        });

        // Aksi Simpan Transaksi
        btnSimpan.addActionListener(e -> {
            String pelanggan = tfPelanggan.getText();
            double bayar = Double.parseDouble(tfBayar.getText());

            if (PenjualanController.simpanPenjualan(pelanggan, keranjang, bayar)) {
                JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan.");
                keranjang.clear();
                tableModel.setRowCount(0);
                tfPelanggan.setText("");
                tfBayar.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan transaksi.");
            }
        });
    }
}
