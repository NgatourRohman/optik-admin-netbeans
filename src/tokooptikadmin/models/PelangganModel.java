/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tokooptikadmin.models;

/**
 *
 * @author ngato
 */
public class PelangganModel {

    private int id;
    private String nama, noHp, alamat, catatan;

    public PelangganModel(int id, String nama, String noHp, String alamat, String catatan) {
        this.id = id;
        this.nama = nama;
        this.noHp = noHp;
        this.alamat = alamat;
        this.catatan = catatan;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getCatatan() {
        return catatan;
    }

    @Override
    public String toString() {
        return nama;
    }
}
