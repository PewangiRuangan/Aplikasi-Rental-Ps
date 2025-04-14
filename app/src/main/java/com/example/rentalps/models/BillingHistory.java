package com.example.rentalps.models;

public class BillingHistory {
    private String namaTV;
    private String ps;
    private int durasi; // dalam menit
    private int harga;
    private long waktuTransaksi; // timestamp (waktu transaksi)

    public BillingHistory(String namaTV, String ps, int durasi, int harga, long waktuTransaksi) {
        this.namaTV = namaTV;
        this.ps = ps;
        this.durasi = durasi;
        this.harga = harga;
        this.waktuTransaksi = waktuTransaksi;
    }

    public String getNamaTV() {
        return namaTV;
    }

    public String getPs() {
        return ps;
    }

    public int getDurasi() {
        return durasi;
    }

    public int getHarga() {
        return harga;
    }

    public long getWaktuTransaksi() {
        return waktuTransaksi;
    }
}
