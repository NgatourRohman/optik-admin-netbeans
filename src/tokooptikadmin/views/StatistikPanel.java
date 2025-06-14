/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.views;

/**
 *
 * @author ngato
 */
import tokooptikadmin.controllers.StatistikController;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class StatistikPanel extends JPanel {

    public StatistikPanel() {
        setLayout(new GridLayout(2, 1, 10, 10));

        // === BAR CHART: Penjualan 7 Hari Terakhir ===
        Map<String, Integer> barData = StatistikController.getJumlahPenjualanPerHari();
        DefaultCategoryDataset barDataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : barData.entrySet()) {
            barDataset.addValue(entry.getValue(), "Transaksi", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Grafik Penjualan 7 Hari Terakhir",
                "Tanggal",
                "Jumlah Transaksi",
                barDataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        CategoryPlot barPlot = barChart.getCategoryPlot();
        barPlot.setRangeGridlinePaint(Color.GRAY);

        ChartPanel barPanel = new ChartPanel(barChart);
        barPanel.setPreferredSize(new Dimension(400, 250));

        // === PIE CHART: Produk Terlaris ===
        Map<String, Integer> pieData = StatistikController.getProdukTerlaris();
        DefaultPieDataset pieDataset = new DefaultPieDataset();

        for (Map.Entry<String, Integer> entry : pieData.entrySet()) {
            pieDataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "5 Produk Terlaris",
                pieDataset,
                true, true, false
        );

        PiePlot piePlot = (PiePlot) pieChart.getPlot();
        piePlot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        piePlot.setLabelBackgroundPaint(Color.WHITE);

        ChartPanel piePanel = new ChartPanel(pieChart);
        piePanel.setPreferredSize(new Dimension(400, 250));

        // Tambahkan ke panel utama
        add(barPanel);
        add(piePanel);
    }
}
