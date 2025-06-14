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
import tokooptikadmin.utils.Session;

import javax.swing.*;
import java.awt.*;

public class UbahPasswordDialog extends JDialog {

    public UbahPasswordDialog(JFrame parent) {
        super(parent, "Ubah Password", true);
        setSize(350, 200);
        setLocationRelativeTo(parent);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        JPasswordField pfOld = new JPasswordField();
        JPasswordField pfNew = new JPasswordField();
        JPasswordField pfConfirm = new JPasswordField();
        JButton btnSimpan = new JButton("Simpan");

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(new JLabel("Password Lama:"));
        panel.add(pfOld);
        panel.add(new JLabel("Password Baru:"));
        panel.add(pfNew);
        panel.add(new JLabel("Konfirmasi Baru:"));
        panel.add(pfConfirm);
        panel.add(new JLabel());
        panel.add(btnSimpan);

        add(panel);

        btnSimpan.addActionListener(e -> {
            String oldPass = new String(pfOld.getPassword());
            String newPass = new String(pfNew.getPassword());
            String confirm = new String(pfConfirm.getPassword());

            if (!newPass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Konfirmasi tidak cocok", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean sukses = AuthController.ubahPassword(Session.getAdmin().getId(), oldPass, newPass);
            if (sukses) {
                JOptionPane.showMessageDialog(this, "Password berhasil diubah.");
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Password lama salah.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
