/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.utils;

/**
 *
 * @author ngato
 */
import tokooptikadmin.models.AdminModel;

public class Session {

    private static AdminModel currentAdmin;

    public static void setAdmin(AdminModel admin) {
        currentAdmin = admin;
    }

    public static AdminModel getAdmin() {
        return currentAdmin;
    }

    public static void clear() {
        currentAdmin = null;
    }
}
