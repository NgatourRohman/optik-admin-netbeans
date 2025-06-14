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
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.*;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class StatistikPanel extends JPanel {

    public StatistikPanel() {
        setLayout(new BorderLayout());

        Map<String, Integer> data = StatistikController.getJumlahPenjualanPerHari();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Penjualan", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Grafik Penjualan 7 Hari Terakhir",
                "Tanggal", "Jumlah Transaksi",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );

        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRangeGridlinePaint(Color.GRAY);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400));
        add(chartPanel, BorderLayout.CENTER);
    }
}
