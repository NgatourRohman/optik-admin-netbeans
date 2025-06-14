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
import tokooptikadmin.utils.Session;
import tokooptikadmin.utils.SessionStorage;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import tokooptikadmin.components.PlaceholderPasswordField;
import tokooptikadmin.components.PlaceholderTextField;

public class LoginView extends JFrame {

    public LoginView() {
        setTitle("Login Admin");
        setSize(1000, 600);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color green = new Color(88, 144, 74);
        Font fontTitle = new Font("SansSerif", Font.BOLD, 32);
        Font fontLabel = new Font("SansSerif", Font.PLAIN, 16);
        Font fontHint = new Font("SansSerif", Font.PLAIN, 14);

        // Panel kiri (gambar)
        JLabel imgLabel = new JLabel(new ImageIcon(getClass().getResource("/assets/login_image.png")));
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setMinimumSize(new Dimension(350, 400));
        leftPanel.add(imgLabel, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        // Panel kanan (form login)
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;

        JLabel lblTitle = new JLabel("Masuk");
        lblTitle.setFont(fontTitle);
        lblTitle.setForeground(green);
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(lblTitle, gbc);

        // Username Label
        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(fontLabel);
        gbc.gridy++;
        rightPanel.add(lblUsername, gbc);

        // Username Panel
        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBackground(Color.WHITE);
        usernamePanel.setBorder(new RoundedBorder(15, Color.decode("#dcdfe3")));
        JLabel iconUser = new JLabel(new ImageIcon(getClass().getResource("/assets/icon_user.png")));
        PlaceholderTextField tfUsername = new PlaceholderTextField("Masukan username anda");
        tfUsername.setBorder(null);
        tfUsername.setFont(fontHint);
        tfUsername.setForeground(Color.GRAY);
        usernamePanel.add(iconUser, BorderLayout.WEST);
        usernamePanel.add(tfUsername, BorderLayout.CENTER);
        gbc.gridy++;
        rightPanel.add(usernamePanel, gbc);

        // Password Label
        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(fontLabel);
        gbc.gridy++;
        rightPanel.add(lblPassword, gbc);

        // Password Panel
        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setBorder(new RoundedBorder(15, Color.decode("#dcdfe3")));
        JLabel iconPass = new JLabel(new ImageIcon(getClass().getResource("/assets/icon_lock.png")));
        PlaceholderPasswordField pfPassword = new PlaceholderPasswordField("Masukan password anda");
        pfPassword.setBorder(null);
        pfPassword.setFont(fontHint);
        pfPassword.setForeground(Color.GRAY);
        passwordPanel.add(iconPass, BorderLayout.WEST);
        passwordPanel.add(pfPassword, BorderLayout.CENTER);
        gbc.gridy++;
        rightPanel.add(passwordPanel, gbc);

        // Ingat saya
        JCheckBox cbIngatSaya = new JCheckBox("Ingat Saya");
        cbIngatSaya.setBackground(Color.WHITE);
        cbIngatSaya.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridy++;
        rightPanel.add(cbIngatSaya, gbc);

        // Tombol Login (Rounded, tanpa shadow)
        JButton btnLogin = new JButton("Masuk") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
                g2.dispose();
            }

            @Override
            protected void paintBorder(Graphics g) {
                // no border
            }
        };
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnLogin.setBackground(green);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBorder(null);
        btnLogin.setContentAreaFilled(false);
        btnLogin.setOpaque(false);
        btnLogin.setPreferredSize(new Dimension(100, 45));
        gbc.gridy++;
        rightPanel.add(btnLogin, gbc);

        add(rightPanel, BorderLayout.CENTER);

        // Action Login
        btnLogin.addActionListener(e -> {
            String username = tfUsername.getText().trim();
            String password = new String(pfPassword.getPassword()).trim();

            AdminModel admin = AuthController.login(username, password);
            if (admin != null) {
                JOptionPane.showMessageDialog(this, "Login berhasil, selamat datang " + admin.getUsername());
                Session.setAdmin(admin);

                if (cbIngatSaya.isSelected()) {
                    SessionStorage.simpanSession(admin);
                } else {
                    SessionStorage.hapusSession();
                }

                dispose();
                new DashboardView().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password salah", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Render final
        pack();
        setLocationRelativeTo(null);
    }

    // Border Radius Class
    class RoundedBorder extends AbstractBorder {

        private final int radius;
        private final Color color;

        public RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(color);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(8, 8, 8, 8);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = 8;
            return insets;
        }
    }
}
