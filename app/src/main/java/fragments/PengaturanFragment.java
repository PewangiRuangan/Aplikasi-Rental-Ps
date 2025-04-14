package com.example.rentalps.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rentalps.LoginActivity;
import com.example.rentalps.R;

public class PengaturanFragment extends Fragment {

    private TextView tvUsername, tvPassword;
    private Button btnReset, btnLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengaturan, container, false);

        tvUsername = view.findViewById(R.id.tvUsername);
        tvPassword = view.findViewById(R.id.tvPassword);
        btnReset = view.findViewById(R.id.btnResetPassword);
        btnLogout = view.findViewById(R.id.btnLogout);

        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        String username = prefs.getString("username", "admin");
        String password = prefs.getString("password", "admin");

        tvUsername.setText("Username: " + username);
        String hiddenPassword = password.replaceAll(".", "●");
        tvPassword.setText("Password: " + hiddenPassword);


        btnReset.setOnClickListener(v -> showResetDialog());

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        return view;
    }

    private void showResetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Reset Username dan Password");

        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_reset_username_password, null);
        EditText edtNewUsername = dialogView.findViewById(R.id.edtNewUsername);
        EditText edtNewPassword = dialogView.findViewById(R.id.edtNewPassword);
        builder.setView(dialogView);

        builder.setPositiveButton("Simpan", (dialog, which) -> {
            String newUsername = edtNewUsername.getText().toString();
            String newPassword = edtNewPassword.getText().toString();

            SharedPreferences.Editor editor = requireActivity()
                    .getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                    .edit();
            editor.putString("username", newUsername);
            editor.putString("password", newPassword);
            editor.apply();

            tvUsername.setText("Username: " + newUsername);
            String hiddenPassword = newPassword.replaceAll(".", "●");
            tvPassword.setText("Password: " + hiddenPassword);
        });


        builder.setNegativeButton("Batal", null);
        builder.show();
    }
}
