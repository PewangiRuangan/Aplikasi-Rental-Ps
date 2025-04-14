package com.example.rentalps.models;

public class BillingData {
    private String tv;
    private String konsol;
    private int durasi;
    private int harga;
    private String tanggal;
    private String waktu;

    // ✅ Constructor dengan 6 parameter
    public BillingData(String tv, String konsol, int durasi, int harga, String tanggal, String waktu) {
        this.tv = tv;
        this.konsol = konsol;
        this.durasi = durasi;
        this.harga = harga;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }

    public String getTv() { return tv; }
    public String getKonsol() { return konsol; }
    public int getDurasi() { return durasi; }
    public int getHarga() { return harga; }
    public String getTanggal() { return tanggal; }
    public String getWaktu() { return waktu; }
}
