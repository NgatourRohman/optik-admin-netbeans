/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.models;

/**
 *
 * @author ngato
 */
public class KeranjangItem {

    private ProdukModel produk;
    private int jumlah;

    public KeranjangItem(ProdukModel produk, int jumlah) {
        this.produk = produk;
        this.jumlah = jumlah;
    }

    public ProdukModel getProduk() {
        return produk;
    }

    public int getJumlah() {
        return jumlah;
    }

    public double getSubtotal() {
        return produk.getHargaJual() * jumlah;
    }
}
