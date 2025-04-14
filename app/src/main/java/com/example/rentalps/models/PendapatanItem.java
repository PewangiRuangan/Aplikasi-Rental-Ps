package com.example.rentalps.models;

public class PendapatanItem {
    private String tanggal;
    private String hari;
    private String jumlah;

    public PendapatanItem(String tanggal, String hari, String jumlah) {
        this.tanggal = tanggal;
        this.hari = hari;
        this.jumlah = jumlah;
    }

    public String getTanggal() { return tanggal; }
    public String getHari() { return hari; }
    public String getJumlah() { return jumlah; }
}

