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
        tableModel.setColumnIdentifiers(new String[] {
            "ID", "Kode", "Nama", "Jenis", "Merk", "Harga Beli", "Harga Jual", "Stok"
        });

        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0); // clear table
        List<ProdukModel> produkList = ProdukController.getAllProduk();
        for (ProdukModel p : produkList) {
            tableModel.addRow(new Object[] {
                p.getId(), p.getKodeProduk(), p.getNamaProduk(), p.getJenis(),
                p.getMerk(), p.getHargaBeli(), p.getHargaJual(), p.getStok()
            });
        }
    }
}
