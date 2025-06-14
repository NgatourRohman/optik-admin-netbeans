/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.controllers;

/**
 *
 * @author ngato
 */
import tokooptikadmin.models.DBConnection;

import java.sql.*;
import java.util.*;

public class StatistikController {

    public static Map<String, Integer> getJumlahPenjualanPerHari() {
        Map<String, Integer> data = new LinkedHashMap<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = """
                SELECT DATE(tanggal) AS tgl, COUNT(*) AS jumlah
                FROM penjualan
                WHERE tanggal >= DATE_SUB(CURDATE(), INTERVAL 7 DAY)
                GROUP BY DATE(tanggal)
                ORDER BY DATE(tanggal)
            """;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                data.put(rs.getString("tgl"), rs.getInt("jumlah"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
