package com.example.rentalps.models;
import com.example.rentalps.models.PaketHarga;

public class PaketHarga {
    private String nama;
    private int durasiMenit;
    private int harga;

    public PaketHarga(String nama, int durasiMenit, int harga) {
        this.nama = nama;
        this.durasiMenit = durasiMenit;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public int getDurasiMenit() {
        return durasiMenit;
    }

    public int getHarga() {
        return harga;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setDurasiMenit(int durasiMenit) {
        this.durasiMenit = durasiMenit;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
