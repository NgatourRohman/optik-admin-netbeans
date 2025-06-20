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
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import tokooptikadmin.models.ProdukModel;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.List;
import java.util.Map;
import tokooptikadmin.controllers.LaporanController;

public class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean clicked;
    private Object targetPanel;
    private int selectedRow;

    public ButtonEditor(JCheckBox checkBox, String label, Object targetPanel) {
        super(checkBox);
        this.targetPanel = targetPanel;
        button = new JButton();
        button.setOpaque(true);
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setBackground(new Color(193, 214, 193));
        button.setFocusPainted(false);
        button.setForeground(Color.BLACK);
        button.setIcon(new ImageIcon(getClass().getResource("/assets/ic_detail.png")));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();

                if (targetPanel instanceof ProdukPanel) {
                    ProdukPanel produkPanel = (ProdukPanel) targetPanel;
                    ProdukModel selected = getProdukFromTable(produkPanel);
                    if (selected != null) {
                        produkPanel.showDetailDialog(selected);
                    }
                } else if (targetPanel instanceof LaporanPenjualanPanel) {
                    LaporanPenjualanPanel laporanPanel = (LaporanPenjualanPanel) targetPanel;
                    JTable table = laporanPanel.getTable();
                    int modelRow = table.convertRowIndexToModel(selectedRow);
                    Object idObj = table.getModel().getValueAt(modelRow, 0);
                    Object pelanggan = table.getModel().getValueAt(modelRow, 2);
                    Object tanggal = table.getModel().getValueAt(modelRow, 1);
                    Object total = table.getModel().getValueAt(modelRow, 3);
                    Object status = table.getModel().getValueAt(modelRow, 4);

                    if (idObj != null) {
                        try {
                            Matcher matcher = Pattern.compile("\\d+").matcher(idObj.toString());
                            if (matcher.find()) {
                                int id = Integer.parseInt(matcher.group());

                                List<Map<String, Object>> detail = LaporanController.getDetailPenjualan(id);

                                StringBuilder sb = new StringBuilder();
                                sb.append("No Faktur : ").append(idObj.toString()).append("\n");
                                sb.append("Tanggal   : ").append(tanggal).append("\n");
                                sb.append("Pelanggan : ").append(pelanggan).append("\n");
                                sb.append("Status    : ").append(status).append("\n");
                                sb.append("Total     : ").append(total).append("\n\n");

                                sb.append("Detail Produk:\n");

                                for (Map<String, Object> row : detail) {
                                    sb.append("- ")
                                            .append(row.get("nama_produk")).append(" | ")
                                            .append(row.get("jumlah")).append(" x ")
                                            .append(row.get("harga")).append(" = ")
                                            .append(row.get("subtotal")).append("\n");
                                }

                                JOptionPane.showMessageDialog(null, sb.toString(), "Detail Transaksi", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Gagal memuat rincian transaksi.");
                        }
                    }
                }
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object obj,
            boolean isSelected, int row, int col) {
        this.label = obj != null ? obj.toString() : "Rincian";
        button.setText(label);
        this.selectedRow = row;
        this.clicked = true;
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    private ProdukModel getProdukFromTable(ProdukPanel produkPanel) {
        JTable table = produkPanel.getTable();
        int modelRow = table.convertRowIndexToModel(selectedRow);

        try {
            return new ProdukModel(
                    0,
                    table.getValueAt(modelRow, 1).toString(),
                    table.getValueAt(modelRow, 2).toString(),
                    table.getValueAt(modelRow, 3).toString(),
                    "", 0,
                    Double.parseDouble(table.getValueAt(modelRow, 4).toString()),
                    Integer.parseInt(table.getValueAt(modelRow, 5).toString())
            );
        } catch (Exception e) {
            return null;
        }
    }
}
