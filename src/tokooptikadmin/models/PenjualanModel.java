/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.models;

/**
 *
 * @author ngato
 */
import java.time.LocalDateTime;
import java.util.List;

public class PenjualanModel {
    private int id;
    private String pelanggan;
    private LocalDateTime tanggal;
    private double total;
    private double bayar;
    private double kembali;
    private List<KeranjangItem> detail;

    public PenjualanModel(int id, String pelanggan, LocalDateTime tanggal,
                          double total, double bayar, double kembali,
                          List<KeranjangItem> detail) {
        this.id = id;
        this.pelanggan = pelanggan;
        this.tanggal = tanggal;
        this.total = total;
        this.bayar = bayar;
        this.kembali = kembali;
        this.detail = detail;
    }

    public int getId() { return id; }
    public String getPelanggan() { return pelanggan; }
    public LocalDateTime getTanggal() { return tanggal; }
    public double getTotal() { return total; }
    public double getBayar() { return bayar; }
    public double getKembali() { return kembali; }
    public List<KeranjangItem> getDetail() { return detail; }
}
