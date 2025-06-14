/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tokooptikadmin;

/**
 *
 * @author ngato
 */
import javax.swing.SwingUtilities;
import tokooptikadmin.views.LoginView;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginView().setVisible(true);
        });
    }
}
