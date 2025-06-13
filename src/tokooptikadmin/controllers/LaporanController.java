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

public class LaporanController {

    public static List<Map<String, Object>> getLaporanPenjualan() {
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT p.id, p.tanggal, p.pelanggan, p.total, p.bayar, p.kembali FROM penjualan p ORDER BY p.id DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("tanggal", rs.getTimestamp("tanggal"));
                row.put("pelanggan", rs.getString("pelanggan"));
                row.put("total", rs.getDouble("total"));
                row.put("bayar", rs.getDouble("bayar"));
                row.put("kembali", rs.getDouble("kembali"));
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Map<String, Object>> getDetailPenjualan(int penjualanId) {
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = """
                SELECT pd.jumlah, pd.harga, pd.subtotal, pr.nama_produk
                FROM penjualan_detail pd
                JOIN produk pr ON pd.produk_id = pr.id
                WHERE pd.penjualan_id = ?
            """;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, penjualanId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("nama_produk", rs.getString("nama_produk"));
                row.put("jumlah", rs.getInt("jumlah"));
                row.put("harga", rs.getDouble("harga"));
                row.put("subtotal", rs.getDouble("subtotal"));
                list.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
