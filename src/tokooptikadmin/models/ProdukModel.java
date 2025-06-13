/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.models;

/**
 *
 * @author ngato
 */
public class ProdukModel {

    private int id;
    private String kodeProduk;
    private String namaProduk;
    private String jenis;
    private String merk;
    private double hargaBeli;
    private double hargaJual;
    private int stok;

    // Constructor, Getter, Setter
    public ProdukModel(int id, String kodeProduk, String namaProduk, String jenis,
            String merk, double hargaBeli, double hargaJual, int stok) {
        this.id = id;
        this.kodeProduk = kodeProduk;
        this.namaProduk = namaProduk;
        this.jenis = jenis;
        this.merk = merk;
        this.hargaBeli = hargaBeli;
        this.hargaJual = hargaJual;
        this.stok = stok;
    }

    // Getters and Setters (boleh generate via NetBeans)
    public int getId() {
        return id;
    }

    public String getKodeProduk() {
        return kodeProduk;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public String getJenis() {
        return jenis;
    }

    public String getMerk() {
        return merk;
    }

    public double getHargaBeli() {
        return hargaBeli;
    }

    public double getHargaJual() {
        return hargaJual;
    }

    public int getStok() {
        return stok;
    }

    @Override
    public String toString() {
        return kodeProduk + " - " + namaProduk;
    }

}
