package com.example.rentalps.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalps.R;
import com.example.rentalps.adapters.PendapatanAdapter;
import com.example.rentalps.database.BillingDatabaseHelper;
import com.example.rentalps.models.PendapatanItem;

import java.util.List;

public class PendapatanBulananFragment extends Fragment {

    private RecyclerView recyclerView;
    private PendapatanAdapter adapter;
    private BillingDatabaseHelper dbHelper;

    public PendapatanBulananFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pendapatan_bulanan, container, false);
        recyclerView = view.findViewById(R.id.recyclerPendapatanBulanan);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new BillingDatabaseHelper(getContext());
        List<PendapatanItem> data = dbHelper.getPendapatanBulanan(); // Sudah return List<PendapatanItem>

        adapter = new PendapatanAdapter(data, true);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
