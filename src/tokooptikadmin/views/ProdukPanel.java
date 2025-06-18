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
    private JTextField tfSearch;

    public ProdukPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(252, 252, 247)); // warna latar sesuai desain

        // === Header: Judul Halaman ===
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(252, 252, 247));

        JLabel title = new JLabel("  Manajemen Inventaris");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(new Color(139, 183, 135));
        title.setIcon(new ImageIcon(getClass().getResource("/assets/ic_box.png")));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // === Panel Filter dan Tombol ===
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(252, 252, 247));
        topBar.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setBackground(new Color(252, 252, 247));

        JButton btnFilter = new JButton(" Filter");
        btnFilter.setIcon(new ImageIcon(getClass().getResource("/assets/ic_filter.png")));
        btnFilter.setBackground(new Color(223, 232, 223));
        btnFilter.setFocusPainted(false);

        tfSearch = new JTextField(20);
        JButton btnCari = new JButton("Cari");
        btnCari.setBackground(new Color(250, 240, 230));
        btnCari.setFocusPainted(false);

        filterPanel.add(btnFilter);
        filterPanel.add(tfSearch);
        filterPanel.add(btnCari);

        JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightButtons.setBackground(new Color(252, 252, 247));
        JButton btnCetak = new JButton("Cetak");
        btnCetak.setBackground(new Color(193, 214, 193));
        JButton btnTambah = new JButton("+ Tambah");
        btnTambah.setBackground(new Color(139, 183, 135));
        btnTambah.setForeground(Color.WHITE);
        btnTambah.setFocusPainted(false);

        rightButtons.add(btnCetak);
        rightButtons.add(btnTambah);

        topBar.add(filterPanel, BorderLayout.WEST);
        topBar.add(rightButtons, BorderLayout.EAST);

        add(topBar, BorderLayout.BEFORE_FIRST_LINE);

        // === Label Data Barang ===
        JPanel sectionLabel = new JPanel(new BorderLayout());
        sectionLabel.setBackground(new Color(193, 214, 193));
        sectionLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel label = new JLabel("  Data Barang");
        label.setIcon(new ImageIcon(getClass().getResource("/assets/ic_table.png")));
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        sectionLabel.add(label, BorderLayout.WEST);

        // === Tabel Produk ===
        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int col) {
                return col == 6;
            }
        };

        tableModel.setColumnIdentifiers(new String[]{
            "No", "Kode Barang", "Nama Barang", "Kategori", "Harga", "Stok", "Aksi"
        });

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.getColumn("Aksi").setCellRenderer(new ButtonRenderer("/assets/ic_detail.png"));
        table.getColumn("Aksi").setCellEditor(new ButtonEditor(new JCheckBox(), "Rincian", this));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(Color.WHITE);
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        tableWrapper.add(sectionLabel, BorderLayout.NORTH);
        tableWrapper.add(scrollPane, BorderLayout.CENTER);

        add(tableWrapper, BorderLayout.CENTER);

        // === Aksi tombol ===
        btnTambah.addActionListener(e -> showTambahDialog());

        btnCari.addActionListener(e -> {
            String keyword = tfSearch.getText().trim().toLowerCase();
            tableModel.setRowCount(0);
            int no = 1;
            for (ProdukModel p : ProdukController.getAllProduk()) {
                if (p.getKodeProduk().toLowerCase().contains(keyword)
                        || p.getNamaProduk().toLowerCase().contains(keyword)) {
                    tableModel.addRow(new Object[]{
                        no++, p.getKodeProduk(), p.getNamaProduk(), p.getJenis(),
                        p.getHargaJual(), p.getStok(), "Rincian"
                    });
                }
            }
        });

        // Load data awal
        loadData();
    }

    public void loadData() {
        tableModel.setRowCount(0);
        List<ProdukModel> list = ProdukController.getAllProduk();
        int no = 1;
        for (ProdukModel p : list) {
            tableModel.addRow(new Object[]{
                no++, p.getKodeProduk(), p.getNamaProduk(), p.getJenis(),
                p.getHargaJual(), p.getStok(), "Rincian"
            });
        }
    }

    public void showTambahDialog() {
        JTextField tfKode = new JTextField();
        JTextField tfNama = new JTextField();
        JComboBox<String> cbJenis = new JComboBox<>(new String[]{"frame", "lensa"});
        JTextField tfHarga = new JTextField();
        JTextField tfStok = new JTextField();

        JPanel form = new JPanel(new GridLayout(0, 2, 5, 5));
        form.add(new JLabel("Kode Barang"));
        form.add(tfKode);
        form.add(new JLabel("Nama Barang"));
        form.add(tfNama);
        form.add(new JLabel("Kategori"));
        form.add(cbJenis);
        form.add(new JLabel("Harga"));
        form.add(tfHarga);
        form.add(new JLabel("Stok"));
        form.add(tfStok);

        int res = JOptionPane.showConfirmDialog(this, form, "Tambah Barang", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            try {
                ProdukModel produk = new ProdukModel(0, tfKode.getText(), tfNama.getText(),
                        cbJenis.getSelectedItem().toString(), "", 0, Double.parseDouble(tfHarga.getText()), Integer.parseInt(tfStok.getText()));
                if (ProdukController.tambahProduk(produk)) {
                    JOptionPane.showMessageDialog(this, "Produk berhasil ditambahkan.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menambahkan produk.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid.");
            }
        }
    }

    public void showDetailDialog(ProdukModel p) {
        JOptionPane.showMessageDialog(this,
                "Kode: " + p.getKodeProduk()
                + "\nNama: " + p.getNamaProduk()
                + "\nKategori: " + p.getJenis()
                + "\nHarga: " + p.getHargaJual()
                + "\nStok: " + p.getStok(),
                "Rincian Produk", JOptionPane.INFORMATION_MESSAGE);
    }

    public JTable getTable() {
        return table;
    }
}
