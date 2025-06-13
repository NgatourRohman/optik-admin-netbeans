/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.views;

/**
 *
 * @author ngato
 */
import tokooptikadmin.controllers.PembelianController;
import tokooptikadmin.controllers.ProdukController;
import tokooptikadmin.controllers.SupplierController;
import tokooptikadmin.models.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PembelianPanel extends JPanel {

    private JComboBox<SupplierModel> cbSupplier;
    private JComboBox<ProdukModel> cbProduk;
    private JSpinner spJumlah;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<KeranjangItem> keranjang = new ArrayList<>();

    public PembelianPanel() {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        cbSupplier = new JComboBox<>();
        cbProduk = new JComboBox<>();
        spJumlah = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
        JButton btnTambah = new JButton("Tambah");

        formPanel.setBorder(BorderFactory.createTitledBorder("Form Pembelian"));
        formPanel.add(new JLabel("Supplier"));
        formPanel.add(cbSupplier);
        formPanel.add(new JLabel("Produk"));
        formPanel.add(cbProduk);
        formPanel.add(new JLabel("Jumlah"));
        formPanel.add(spJumlah);
        formPanel.add(new JLabel(""));
        formPanel.add(btnTambah);

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{
            "Kode", "Nama Produk", "Harga Beli", "Jumlah", "Subtotal"
        }, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnSimpan = new JButton("Simpan Pembelian");
        add(btnSimpan, BorderLayout.SOUTH);

        // Load data
        SupplierController.getAll().forEach(cbSupplier::addItem);
        ProdukController.getAllProduk().forEach(cbProduk::addItem);

        // Tambah ke keranjang
        btnTambah.addActionListener(e -> {
            ProdukModel p = (ProdukModel) cbProduk.getSelectedItem();
            int jumlah = (Integer) spJumlah.getValue();
            keranjang.add(new KeranjangItem(p, jumlah));
            tableModel.addRow(new Object[]{
                p.getKodeProduk(), p.getNamaProduk(), p.getHargaBeli(), jumlah, jumlah * p.getHargaBeli()
            });
        });

        btnSimpan.addActionListener(e -> {
            SupplierModel supplier = (SupplierModel) cbSupplier.getSelectedItem();
            if (keranjang.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Keranjang kosong.");
                return;
            }

            if (PembelianController.simpanPembelian(supplier, keranjang)) {
                JOptionPane.showMessageDialog(this, "Pembelian berhasil disimpan.");
                keranjang.clear();
                tableModel.setRowCount(0);
            } else {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan pembelian.");
            }
        });
    }
}
