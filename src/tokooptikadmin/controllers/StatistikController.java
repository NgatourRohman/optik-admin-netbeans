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

    public static Map<String, Integer> getProdukTerlaris() {
        Map<String, Integer> data = new LinkedHashMap<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = """
            SELECT pr.nama_produk, SUM(pd.jumlah) AS total
            FROM penjualan_detail pd
            JOIN produk pr ON pd.produk_id = pr.id
            GROUP BY pr.nama_produk
            ORDER BY total DESC
            LIMIT 5
        """;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                data.put(rs.getString("nama_produk"), rs.getInt("total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

}
