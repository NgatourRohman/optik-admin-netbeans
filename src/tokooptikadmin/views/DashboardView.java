/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.views;

/**
 *
 * @author ngato
 */
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import tokooptikadmin.models.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DashboardView extends JFrame {

    public DashboardView() {
        setTitle("Dashboard - Admin Toko Optik");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        JPanel sidebar = buildSidebar();
        JPanel content = buildContent();

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(sidebar, BorderLayout.WEST);
        wrapper.add(scrollPane, BorderLayout.CENTER);

        setContentPane(wrapper);
        setVisible(true);
    }

    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel(new GridLayout(7, 1, 0, 10));
        sidebar.setBackground(new Color(145, 168, 138));
        sidebar.setPreferredSize(new Dimension(100, 800));

        String[] iconFiles = {
            "ic_home.png",
            "ic_box.png",
            "ic_chart.png",
            "ic_user.png",
            "ic_report.png",
            "ic_truck.png"
        };

        for (int i = 0; i < iconFiles.length; i++) {
            String fileName = iconFiles[i];
            JButton btn = new JButton();
            btn.setPreferredSize(new Dimension(50, 50));
            btn.setBackground(new Color(145, 168, 138));
            btn.setBorderPainted(false);
            btn.setFocusPainted(false);

            try {
                ImageIcon icon = new ImageIcon(getClass().getResource("/assets/" + fileName));
                Image scaled = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                btn.setIcon(new ImageIcon(scaled));
            } catch (Exception e) {
                System.err.println("Icon not found: " + fileName);
            }

            final int index = i;
            btn.addActionListener(e -> {
                if (index == 0) {
                    // Tombol Dashboard
                    setMainContent(buildContent());
                } else if (index == 1) {
                    // Tombol Produk (📦)
                    setMainContent(new ProdukPanel());
                }
                // Tambahkan panel lainnya sesuai index jika dibutuhkan
            });

            sidebar.add(btn);
        }
        return sidebar;
    }

    private JPanel buildContent() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        content.setBackground(Color.WHITE);

        content.add(buildChartWithHeader());
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        content.add(buildSection("Penjualan", buildTable("penjualan")));
        content.add(Box.createRigidArea(new Dimension(0, 20)));
        content.add(buildSection("Pembelian", buildTable("pembelian")));

        return content;
    }

    private JPanel buildChartWithHeader() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);

        JPanel left = new JPanel();
        left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
        left.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Grafik Laporan Bulanan");
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(new Color(139, 183, 135));
        left.add(lblTitle);

        JButton btnCetak = new JButton("Cetak");
        btnCetak.setBackground(new Color(145, 168, 138));
        btnCetak.setForeground(Color.WHITE);
        btnCetak.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnCetak.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCetak.setFocusPainted(false);
        btnCetak.setPreferredSize(new Dimension(80, 30));
        btnCetak.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Fitur cetak akan ditambahkan.");
        });

        header.add(left, BorderLayout.WEST);
        header.add(btnCetak, BorderLayout.EAST);

        container.add(header);
        container.add(buildChartPanel());
        container.add(buildChartLegend()); // <- Tambahan legend

        return container;
    }

    private ChartPanel buildChartPanel() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Double> penjualan = getMonthlyTotals("penjualan");
        Map<String, Double> pembelian = getMonthlyTotals("pembelian");

        for (String bulan : penjualan.keySet()) {
            double jual = penjualan.getOrDefault(bulan, 0.0);
            double beli = pembelian.getOrDefault(bulan, 0.0);
            double profit = jual - beli;
            dataset.addValue(jual, "Penjualan", bulan);
            dataset.addValue(beli, "Pembelian", bulan);
            dataset.addValue(profit, "Keuntungan", bulan);
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "", "Bulan", "Jumlah (Rp)", dataset,
                PlotOrientation.VERTICAL, true, true, false
        );
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(139, 183, 135)); // Penjualan
        renderer.setSeriesPaint(1, new Color(165, 146, 200)); // Pembelian
        renderer.setSeriesPaint(2, new Color(189, 153, 132)); // Keuntungan

        return new ChartPanel(chart);
    }

    private JScrollPane buildTable(String type) {
        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.setColumnIdentifiers(new Object[]{"ID", "Tanggal", "Nama Barang", "Kuantitas", "Total Harga", "Status"});

        String sql = "";
        if (type.equals("penjualan")) {
            sql = """
                    SELECT p.id, p.tanggal, pr.nama_produk, d.kuantitas, p.total_harga, p.status
                    FROM penjualan p
                    JOIN penjualan_detail d ON p.id = d.penjualan_id
                    JOIN produk pr ON d.produk_id = pr.id
                    ORDER BY p.tanggal DESC
                    """;
        } else {
            sql = """
                    SELECT b.id, b.tanggal, pr.nama_produk, d.kuantitas, b.total_harga, b.status
                    FROM pembelian b
                    JOIN pembelian_detail d ON b.id = d.pembelian_id
                    JOIN produk pr ON d.produk_id = pr.id
                    ORDER BY b.tanggal DESC
                    """;
        }

        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getString(1),
                    rs.getDate(2),
                    rs.getString(3),
                    rs.getInt(4),
                    rs.getDouble(5),
                    rs.getString(6)
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(1000, 160));
        return scrollPane;
    }

    private JPanel buildSection(String title, JScrollPane tableScroll) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(title);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setOpaque(true);
        label.setBackground(new Color(145, 168, 138));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.setBackground(Color.WHITE);
        panel.add(label, BorderLayout.NORTH);
        panel.add(tableScroll, BorderLayout.CENTER);
        return panel;
    }

    private Map<String, Double> getMonthlyTotals(String table) {
        Map<String, Double> data = new HashMap<>();
        String sql = "SELECT MONTH(tanggal) as bulan, SUM(total_harga) as total FROM " + table + " GROUP BY bulan";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int bulan = rs.getInt("bulan");
                double total = rs.getDouble("total");
                data.put(getMonthName(bulan), total);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    private String getMonthName(int m) {
        String[] nama = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli",
            "Agustus", "September", "Oktober", "November", "Desember"};
        return nama[m - 1];
    }

    private JPanel buildChartLegend() {
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        legendPanel.setBackground(Color.WHITE);

        legendPanel.add(createLegendItem(new Color(139, 183, 135), "Penjualan"));
        legendPanel.add(createLegendItem(new Color(165, 146, 200), "Pembelian"));
        legendPanel.add(createLegendItem(new Color(189, 153, 132), "Keuntungan"));

        return legendPanel;
    }

    private JPanel createLegendItem(Color color, String labelText) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        item.setOpaque(false);

        JLabel colorBox = new JLabel();
        colorBox.setPreferredSize(new Dimension(15, 15));
        colorBox.setOpaque(true);
        colorBox.setBackground(color);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));

        item.add(colorBox);
        item.add(label);
        return item;
    }

    private void setMainContent(JPanel panel) {
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.add(buildSidebar(), BorderLayout.WEST);
        wrapper.add(scrollPane, BorderLayout.CENTER);

        setContentPane(wrapper);
        revalidate();
        repaint();
    }
}
