/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tokooptikadmin;

/**
 *
 * @author ngato
 */
import tokooptikadmin.utils.SessionStorage;
import tokooptikadmin.utils.Session;
import tokooptikadmin.models.AdminModel;
import tokooptikadmin.views.*;

public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            AdminModel remembered = SessionStorage.loadSession();
            if (remembered != null) {
                Session.setAdmin(remembered);
                new DashboardView().setVisible(true);
            } else {
                new LoginView().setVisible(true);
            }
        });
    }
}
