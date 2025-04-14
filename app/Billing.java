package com.example.rentalps;

public class Billing {
    private String tv;
    private String konsol;
    private String durasi;
    private String harga;
    private String waktu;

    public Billing(String tv, String konsol, String durasi, String harga, String waktu) {
        this.tv = tv;
        this.konsol = konsol;
        this.durasi = durasi;
        this.harga = harga;
        this.waktu = waktu;
    }

    public String getTv() { return tv; }
    public String getKonsol() { return konsol; }
    public String getDurasi() { return durasi; }
    public String getHarga() { return harga; }
    public String getWaktu() { return waktu; }
}
