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
import tokooptikadmin.models.KeranjangItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;

public class PenjualanController {

    public static boolean simpanPenjualan(String pelanggan, List<KeranjangItem> keranjang, double bayar) {
        double total = keranjang.stream().mapToDouble(KeranjangItem::getSubtotal).sum();
        double kembali = bayar - total;

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            String sql = "INSERT INTO penjualan (pelanggan, total, bayar, kembali) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, pelanggan);
            stmt.setDouble(2, total);
            stmt.setDouble(3, bayar);
            stmt.setDouble(4, kembali);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int penjualanId = rs.getInt(1);

            for (KeranjangItem item : keranjang) {
                String detailSql = "INSERT INTO penjualan_detail (penjualan_id, produk_id, jumlah, harga, subtotal) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement detailStmt = conn.prepareStatement(detailSql);
                detailStmt.setInt(1, penjualanId);
                detailStmt.setInt(2, item.getProduk().getId());
                detailStmt.setInt(3, item.getJumlah());
                detailStmt.setDouble(4, item.getProduk().getHargaJual());
                detailStmt.setDouble(5, item.getSubtotal());
                detailStmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
