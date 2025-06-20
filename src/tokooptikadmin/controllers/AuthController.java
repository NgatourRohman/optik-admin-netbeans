/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.controllers;

/**
 *
 * @author ngato
 */
import tokooptikadmin.models.AdminModel;
import tokooptikadmin.models.DBConnection;

import java.sql.*;

public class AuthController {

    public static AdminModel login(String username, String password) {
        String sql = "SELECT * FROM admin WHERE username = ? AND password = MD5(?)";

        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new AdminModel(rs.getInt("id"), rs.getString("username"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean ubahPassword(int adminId, String oldPass, String newPass) {
        String sql = "UPDATE admin SET password = MD5(?) WHERE id = ? AND password = MD5(?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newPass);
            stmt.setInt(2, adminId);
            stmt.setString(3, oldPass);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
