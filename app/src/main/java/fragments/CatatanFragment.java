package com.example.rentalps.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalps.R;
import com.example.rentalps.adapters.CatatanAdapter;
import com.example.rentalps.models.Catatan;
import com.example.rentalps.database.CatatanDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CatatanFragment extends Fragment {

    private EditText editTextCatatan;
    private RecyclerView recyclerView;
    private CatatanAdapter adapter;
    private List<Catatan> catatanList;
    private CatatanDatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catatan, container, false);

        editTextCatatan = view.findViewById(R.id.editTextCatatan);
        Button btnSimpan = view.findViewById(R.id.btnSimpanCatatan);
        recyclerView = view.findViewById(R.id.recyclerViewCatatan);

        db = new CatatanDatabaseHelper(getContext());
        catatanList = db.getSemuaCatatan();

        // Adapter dengan listener hapus
        adapter = new CatatanAdapter(catatanList, catatan -> {
            // Hapus catatan dari database
            db.hapusCatatan(catatan.getId());
            // Perbarui daftar
            catatanList.clear();
            catatanList.addAll(db.getSemuaCatatan());
            adapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "Catatan dihapus", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        btnSimpan.setOnClickListener(v -> {
            String isi = editTextCatatan.getText().toString().trim();
            if (!isi.isEmpty()) {
                String tanggal = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
                String hari = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date());

                db.tambahCatatan(isi, tanggal, hari);
                catatanList.clear();
                catatanList.addAll(db.getSemuaCatatan());
                adapter.notifyDataSetChanged();
                editTextCatatan.setText("");
            }
        });

        return view;
    }
}
