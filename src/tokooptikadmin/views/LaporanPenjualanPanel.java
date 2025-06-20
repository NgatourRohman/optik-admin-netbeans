/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.views;

/**
 *
 * @author ngato
 */
import tokooptikadmin.controllers.LaporanController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class LaporanPenjualanPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField tfSearch;

    public LaporanPenjualanPanel() {
        setLayout(new BorderLayout());
        setBackground(new Color(252, 252, 247));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(252, 252, 247));
        JLabel title = new JLabel("  Manajemen Penjualan");
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        title.setForeground(new Color(139, 183, 135));
        title.setIcon(new ImageIcon(getClass().getResource("/assets/ic_chart.png")));
        title.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

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

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightPanel.setBackground(new Color(252, 252, 247));

        JButton btnCetak = new JButton("Cetak Nota");
        JButton btnTambah = new JButton("+ Tambah");
        btnCetak.setBackground(new Color(193, 214, 193));
        btnTambah.setBackground(new Color(139, 183, 135));
        btnTambah.setForeground(Color.WHITE);

        rightPanel.add(btnCetak);
        rightPanel.add(btnTambah);

        topBar.add(filterPanel, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);

        add(topBar, BorderLayout.BEFORE_FIRST_LINE);

        JPanel sectionLabel = new JPanel(new BorderLayout());
        sectionLabel.setBackground(new Color(193, 214, 193));
        sectionLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        JLabel lblSection = new JLabel("  Data Penjualan");
        lblSection.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblSection.setIcon(new ImageIcon(getClass().getResource("/assets/ic_table.png")));
        sectionLabel.add(lblSection, BorderLayout.WEST);

        tableModel = new DefaultTableModel() {
            public boolean isCellEditable(int row, int col) {
                return col == 5;
            }
        };
        tableModel.setColumnIdentifiers(new String[]{
            "No Faktur", "Tanggal Jual", "Nama Pelanggan", "Total Transaksi", "Status", "Aksi"
        });

        table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getColumn("Aksi").setCellRenderer(new ButtonRenderer("/assets/ic_detail.png"));
        table.getColumn("Aksi").setCellEditor(new ButtonEditor(new JCheckBox(), "Rincian", this));

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(Color.WHITE);
        tableWrapper.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        tableWrapper.add(sectionLabel, BorderLayout.NORTH);
        tableWrapper.add(tableScroll, BorderLayout.CENTER);

        add(tableWrapper, BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Map<String, Object>> data = LaporanController.getLaporanPenjualan();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Map<String, Object> row : data) {
            tableModel.addRow(new Object[]{
                "INV/" + row.get("id"),
                sdf.format(row.get("tanggal")),
                row.get("pelanggan"),
                row.get("total"),
                row.get("status"),
                "Rincian"
            });
        }
    }

    public JTable getTable() {
        return table;
    }
}
