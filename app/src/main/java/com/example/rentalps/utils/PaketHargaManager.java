package com.yourapp.rentalps.utils;

import com.example.rentalps.models.PaketHarga;

import java.util.ArrayList;
import java.util.List;

public class PaketHargaManager {
    private static final List<PaketHarga> paketList = new ArrayList<>();

    static {
        paketList.add(new PaketHarga("30 Menit", 30, 3000));
        paketList.add(new PaketHarga("1 Jam", 60, 5000));
        paketList.add(new PaketHarga("2 Jam", 120, 9000));
    }

    public static List<PaketHarga> getAllPaket() {
        return paketList;
    }

    public static void addPaket(PaketHarga paket) {
        paketList.add(paket);
    }

    public static void editPaket(int index, PaketHarga newPaket) {
        paketList.set(index, newPaket);
    }

    public static void deletePaket(int index) {
        paketList.remove(index);
    }
}
