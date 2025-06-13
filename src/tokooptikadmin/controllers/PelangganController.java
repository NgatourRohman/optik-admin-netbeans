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
import tokooptikadmin.models.PelangganModel;

import java.sql.*;
import java.util.*;

public class PelangganController {

    public static List<PelangganModel> getAll() {
        List<PelangganModel> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM pelanggan";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new PelangganModel(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("no_hp"),
                        rs.getString("alamat"),
                        rs.getString("catatan")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean tambah(PelangganModel p) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO pelanggan (nama, no_hp, alamat, catatan) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getNama());
            ps.setString(2, p.getNoHp());
            ps.setString(3, p.getAlamat());
            ps.setString(4, p.getCatatan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
