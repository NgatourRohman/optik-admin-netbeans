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

public class LaporanPembelianPanel extends JPanel {

    public LaporanPembelianPanel() {
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new String[]{
            "ID", "Tanggal", "Supplier", "Total"
        }, 0);
        JTable table = new JTable(model);

        List<Map<String, Object>> list = LaporanController.getLaporanPembelian();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        for (Map<String, Object> row : list) {
            model.addRow(new Object[]{
                row.get("id"),
                sdf.format(row.get("tanggal")),
                row.get("supplier"),
                row.get("total")
            });
        }

        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
