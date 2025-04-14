package com.example.rentalps.models;

public class Catatan {
    private int id; // Tambahkan atribut id
    private String isi;
    private String tanggal;
    private String hari;

    // Constructor dengan id
    public Catatan(int id, String isi, String tanggal, String hari) {
        this.id = id;
        this.isi = isi;
        this.tanggal = tanggal;
        this.hari = hari;
    }

    // Constructor tanpa id
    public Catatan(String isi, String tanggal, String hari) {
        this.isi = isi;
        this.tanggal = tanggal;
        this.hari = hari;
    }

    // Getter untuk id
    public int getId() {
        return id;
    }

    // Setter untuk id
    public void setId(int id) {
        this.id = id;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }
}
