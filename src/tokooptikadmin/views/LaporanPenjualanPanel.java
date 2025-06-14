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
    private JTable detailTable;
    private DefaultTableModel detailModel;

    public LaporanPenjualanPanel() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new String[]{"ID", "Tanggal", "Pelanggan", "Total", "Bayar", "Kembali"}, 0);
        table = new JTable(tableModel);
        loadData();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Daftar Transaksi Penjualan"));

        add(scrollPane, BorderLayout.CENTER);

        // Detail Panel
        detailModel = new DefaultTableModel(new String[]{"Produk", "Jumlah", "Harga", "Subtotal"}, 0);
        detailTable = new JTable(detailModel);
        JScrollPane detailPane = new JScrollPane(detailTable);
        detailPane.setPreferredSize(new Dimension(100, 150));
        detailPane.setBorder(BorderFactory.createTitledBorder("Detail Transaksi"));

        add(detailPane, BorderLayout.SOUTH);

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    int id = (int) table.getValueAt(row, 0);
                    loadDetail(id);
                }
            }
        });
    }

    private void loadData() {
        tableModel.setRowCount(0);
        List<Map<String, Object>> data = LaporanController.getLaporanPenjualan();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (Map<String, Object> row : data) {
            tableModel.addRow(new Object[]{
                row.get("id"),
                sdf.format(row.get("tanggal")),
                row.get("pelanggan"),
                row.get("total"),
                row.get("bayar"),
                row.get("kembali")
            });
        }
    }

    private void loadDetail(int penjualanId) {
        detailModel.setRowCount(0);
        List<Map<String, Object>> data = LaporanController.getDetailPenjualan(penjualanId);

        for (Map<String, Object> row : data) {
            detailModel.addRow(new Object[]{
                row.get("nama_produk"),
                row.get("jumlah"),
                row.get("harga"),
                row.get("subtotal")
            });
        }
    }
}
