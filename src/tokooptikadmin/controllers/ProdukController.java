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
import tokooptikadmin.models.ProdukModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdukController {

    public static List<ProdukModel> getAllProduk() {
        List<ProdukModel> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM produk ORDER BY id DESC";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                list.add(new ProdukModel(
                        rs.getInt("id"),
                        rs.getString("kode_produk"),
                        rs.getString("nama_produk"),
                        rs.getString("jenis"),
                        rs.getString("merk"),
                        rs.getDouble("harga_beli"),
                        rs.getDouble("harga_jual"),
                        rs.getInt("stok")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean tambahProduk(ProdukModel produk) {
        String sql = "INSERT INTO produk (kode_produk, nama_produk, jenis, merk, harga_beli, harga_jual, stok) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produk.getKodeProduk());
            stmt.setString(2, produk.getNamaProduk());
            stmt.setString(3, produk.getJenis());
            stmt.setString(4, produk.getMerk());
            stmt.setDouble(5, produk.getHargaBeli());
            stmt.setDouble(6, produk.getHargaJual());
            stmt.setInt(7, produk.getStok());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
