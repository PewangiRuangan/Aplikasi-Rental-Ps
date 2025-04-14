package com.example.rentalps.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rentalps.R;
import com.example.rentalps.adapters.TVAdapter;
import com.example.rentalps.models.TV;
import com.example.rentalps.utils.DialogUtils;
import com.example.rentalps.viewmodel.TVViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private TVAdapter adapter;

    private TVAdapter tvAdapter;
    private List<TV> tvList = new ArrayList<>();
    private TVStartListener listener;
    private FloatingActionButton btnTambahTV;
    private TVViewModel tvViewModel;

    public HomeFragment() {}

    public HomeFragment(TVStartListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (savedInstanceState != null) {
            ArrayList<TV> savedTVs = savedInstanceState.getParcelableArrayList("tv_list");
            if (savedTVs != null) {
                tvList = savedTVs;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewTV);
        btnTambahTV = view.findViewById(R.id.btnTambahTV);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));

        tvAdapter = new TVAdapter(requireContext(), tvList, new TVAdapter.OnTVClickListener() {
            @Override
            public void onTVClick(TV tv, int position) {
                DialogUtils.showDialogPilihKonsolDurasi(requireActivity(), tv, position, tvAdapter, (updatedTV, pos) -> {
                    if (listener != null) {
                        listener.onStartTVTimer(updatedTV, pos, tvAdapter);
                    }
                    tvViewModel.updateTV(pos, updatedTV);
                });
            }

            @Override
            public void onPauseClick(int position) {
                tvList.get(position).pauseTimer();
                tvAdapter.notifyItemChanged(position);
                tvViewModel.updateTV(position, tvList.get(position));
            }

            @Override
            public void onResumeClick(int position) {
                tvList.get(position).resumeTimer(requireContext(), () -> {
                    tvList.get(position).setSedangDigunakan(false);
                    adapter.notifyItemChanged(position);
                });

            }

            @Override
            public void onResetClick(int position) {
                tvList.get(position).resetTimer(() -> {
                    tvAdapter.notifyItemChanged(position);
                    tvViewModel.updateTV(position, tvList.get(position));
                });
            }


            @Override
            public void onTVLongClick(TV tv, int position) {
                showDialogHapusTV(tv, position);
            }

            @Override
            public void onDeleteClick(int position) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Hapus TV")
                        .setMessage("Apakah Anda yakin ingin menghapus TV ini?")
                        .setPositiveButton("Ya", (dialog, which) -> {
                            tvAdapter.removeTV(position);
                        })
                        .setNegativeButton("Batal", null)
                        .show();
            }
        });

        recyclerView.setAdapter(tvAdapter);
        btnTambahTV.setVisibility(View.GONE);

        // ViewModel dan LiveData
        tvViewModel = new ViewModelProvider(requireActivity()).get(TVViewModel.class);
        tvViewModel.getTVList().observe(getViewLifecycleOwner(), tvs -> {
            tvList = tvs;
            tvAdapter.setTVList(tvList);
            for (int i = 0; i < tvList.size(); i++) {
                int finalI = i;
                TV tv = tvList.get(i);
                if (tv.isSedangDigunakan() && !tv.isPaused() && tv.getWaktuSisaMillis() > 0) {
                    tv.startTimer(requireContext(), () -> {
                        tv.setSedangDigunakan(false);
                        tvAdapter.notifyItemChanged(finalI);
                        tvViewModel.updateTV(finalI, tv);
                    });
                }
            }
        });
    }

    private void showDialogHapusTV(TV tv, int position) {
        new AlertDialog.Builder(getContext())
                .setTitle("Hapus TV")
                .setMessage("Yakin ingin menghapus TV nomor " + tv.getNomor() + "?")
                .setPositiveButton("Hapus", (dialog, which) -> {
                    tvList.remove(position);
                    tvAdapter.notifyItemRemoved(position);
                    tvViewModel.setTVList(tvList);
                })
                .setNegativeButton("Batal", null)
                .show();
    }

    private void showDialogTambahTV() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Tambah TV");

        final EditText input = new EditText(getContext());
        input.setHint("Masukkan nomor TV");
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("Tambah", (dialog, which) -> {
            String nomorStr = input.getText().toString().trim();
            if (!nomorStr.isEmpty()) {
                int nomor = Integer.parseInt(nomorStr);
                for (TV tv : tvList) {
                    if (tv.getNomor() == nomor) {
                        showError("TV nomor " + nomor + " sudah ada.");
                        return;
                    }
                }

                TV tvBaru = new TV(nomor);
                tvList.add(tvBaru);
                tvAdapter.notifyItemInserted(tvList.size() - 1);
                tvViewModel.setTVList(tvList);
            }
        });

        builder.setNegativeButton("Batal", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    private void showError(String message) {
        new AlertDialog.Builder(getContext())
                .setTitle("Peringatan")
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_tambah_tv) {
            showDialogTambahTV();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public interface TVStartListener {
        void onStartTVTimer(TV tv, int posisi, TVAdapter adapter);
    }

    public interface OnCustomDurasiSelectedListener {
        void onCustomDurasiSelected(int durasiDalamMenit, String displayText);
    }
}
