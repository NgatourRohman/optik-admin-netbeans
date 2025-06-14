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
import java.util.List;
import java.util.Map;

public class LaporanStokPanel extends JPanel {

    public LaporanStokPanel() {
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new String[]{
            "Kode", "Nama", "Jenis", "Merk", "Harga Beli", "Harga Jual", "Stok"
        }, 0);
        JTable table = new JTable(model);

        List<Map<String, Object>> list = LaporanController.getLaporanStokProduk();

        for (Map<String, Object> row : list) {
            model.addRow(new Object[]{
                row.get("kode"),
                row.get("nama"),
                row.get("jenis"),
                row.get("merk"),
                row.get("harga_beli"),
                row.get("harga_jual"),
                row.get("stok")
            });
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
