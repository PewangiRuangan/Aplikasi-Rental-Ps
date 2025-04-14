package com.yourapp.rentalps.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.rentalps.models.PaketHarga;
import com.yourapp.rentalps.utils.PaketHargaManager;

import java.util.List;

public class PilihPaketDialog extends DialogFragment {

    public interface OnPaketDipilihListener {
        void onPaketDipilih(PaketHarga paket);
        void onCustomDurasiDipilih(int durasiMenit);
    }

    private OnPaketDipilihListener listener;

    public PilihPaketDialog(OnPaketDipilihListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Ambil semua paket dari manager
        List<PaketHarga> paketList = PaketHargaManager.getAllPaket();

        // Siapkan opsi dalam bentuk array String untuk dialog
        String[] options = new String[paketList.size() + 1];
        for (int i = 0; i < paketList.size(); i++) {
            PaketHarga paket = paketList.get(i);
            options[i] = paket.getNama() + " - Rp " + paket.getHarga();
        }
        options[paketList.size()] = "Custom Durasi"; // Opsi terakhir untuk custom durasi

        return new AlertDialog.Builder(requireActivity())
                .setTitle("Pilih Paket Billing")
                .setItems(options, (dialog, which) -> {
                    if (which < paketList.size()) {
                        listener.onPaketDipilih(paketList.get(which));
                    } else {
                        showCustomDurasiInput();
                    }
                })
                .create();
    }

    private void showCustomDurasiInput() {
        EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setHint("Masukkan durasi (menit)");

        new AlertDialog.Builder(requireContext())
                .setTitle("Custom Durasi")
                .setView(input)
                .setPositiveButton("OK", (dialog, which) -> {
                    String value = input.getText().toString();
                    if (!value.isEmpty()) {
                        try {
                            int durasi = Integer.parseInt(value);
                            if (durasi > 0) {
                                listener.onCustomDurasiDipilih(durasi);
                            } else {
                                Toast.makeText(getContext(), "Durasi harus lebih dari 0", Toast.LENGTH_SHORT).show();
                            }
                        } catch (NumberFormatException e) {
                            Toast.makeText(getContext(), "Input tidak valid", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Durasi tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Batal", null)
                .show();
    }
}
