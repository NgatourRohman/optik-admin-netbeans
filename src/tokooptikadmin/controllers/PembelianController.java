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
import tokooptikadmin.models.ProdukModel;
import tokooptikadmin.models.SupplierModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.List;

public class PembelianController {

    public static boolean simpanPembelian(SupplierModel supplier, List<KeranjangItem> keranjang) {
        double total = keranjang.stream().mapToDouble(KeranjangItem::getSubtotal).sum();

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            String sql = "INSERT INTO pembelian (supplier_id, total) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, supplier.getId());
            stmt.setDouble(2, total);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            int pembelianId = rs.getInt(1);

            for (KeranjangItem item : keranjang) {
                ProdukModel produk = item.getProduk();

                String detailSql = "INSERT INTO pembelian_detail (pembelian_id, produk_id, jumlah, harga, subtotal) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement detailStmt = conn.prepareStatement(detailSql);
                detailStmt.setInt(1, pembelianId);
                detailStmt.setInt(2, produk.getId());
                detailStmt.setInt(3, item.getJumlah());
                detailStmt.setDouble(4, produk.getHargaBeli());
                detailStmt.setDouble(5, item.getSubtotal());
                detailStmt.executeUpdate();

                // update stok
                String updateStok = "UPDATE produk SET stok = stok + ? WHERE id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateStok);
                updateStmt.setInt(1, item.getJumlah());
                updateStmt.setInt(2, produk.getId());
                updateStmt.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
