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
import tokooptikadmin.models.SupplierModel;

import java.sql.*;
import java.util.*;

public class SupplierController {

    public static List<SupplierModel> getAll() {
        List<SupplierModel> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM supplier";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                list.add(new SupplierModel(
                        rs.getInt("id"),
                        rs.getString("nama"),
                        rs.getString("no_hp"),
                        rs.getString("alamat")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean tambah(SupplierModel s) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO supplier (nama, no_hp, alamat) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, s.getNama());
            ps.setString(2, s.getNoHp());
            ps.setString(3, s.getAlamat());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
