/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.views;

/**
 *
 * @author ngato
 */
import tokooptikadmin.controllers.AuthController;
import tokooptikadmin.models.AdminModel;

import javax.swing.*;
import java.awt.*;
import tokooptikadmin.utils.Session;

public class LoginView extends JFrame {

    public LoginView() {
        setTitle("Login Admin");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        JTextField tfUsername = new JTextField();
        JPasswordField pfPassword = new JPasswordField();
        JButton btnLogin = new JButton("Login");

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Username:"));
        panel.add(tfUsername);
        panel.add(new JLabel("Password:"));
        panel.add(pfPassword);
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel, BorderLayout.CENTER);

        btnLogin.addActionListener(e -> {
            String username = tfUsername.getText();
            String password = new String(pfPassword.getPassword());

            AdminModel admin = AuthController.login(username, password);
            if (admin != null) {
                JOptionPane.showMessageDialog(this, "Login berhasil, selamat datang " + admin.getUsername());
                Session.setAdmin(admin);
                dispose();
                new DashboardView().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password salah", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
