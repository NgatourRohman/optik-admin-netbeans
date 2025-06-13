/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.views;

/**
 *
 * @author ngato
 */
import javax.swing.*;
import java.awt.*;

public class DashboardView extends JFrame {

    public DashboardView() {
        setTitle("Dashboard Admin Toko Optik");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(createInfoCard("Total Produk", "120"));
        panel.add(createInfoCard("Total Pelanggan", "85"));
        panel.add(createInfoCard("Penjualan Hari Ini", "Rp 1.250.000"));
        panel.add(createInfoCard("Stok Hampir Habis", "5"));

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Dashboard", panel);
        tabs.addTab("Produk", new ProdukPanel());
        tabs.addTab("Penjualan", new PenjualanPanel());
        tabs.addTab("Supplier", new SupplierPanel());
        tabs.addTab("Pelanggan", new PelangganPanel());
        tabs.addTab("Pembelian", new PembelianPanel());
        tabs.addTab("Laporan Penjualan", new LaporanPenjualanPanel());
        tabs.addTab("Laporan Pembelian", new LaporanPembelianPanel());
        tabs.addTab("Laporan Stok", new LaporanStokPanel());

        getContentPane().add(tabs);

    }

    private JPanel createInfoCard(String title, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        card.setBackground(new Color(240, 240, 240));

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 14));

        JLabel lblValue = new JLabel(value, SwingConstants.CENTER);
        lblValue.setFont(new Font("SansSerif", Font.PLAIN, 24));

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);

        return card;
    }
}
