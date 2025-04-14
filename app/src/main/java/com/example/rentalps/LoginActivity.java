package com.example.rentalps;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.rentalps.R;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtNewUsername);
        edtPassword = findViewById(R.id.edtNewPassword);
        btnLogin = findViewById(R.id.btnLogin);

        SharedPreferences prefs = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String savedUsername = prefs.getString("username", "admin");
        String savedPassword = prefs.getString("password", "admin");

        btnLogin.setOnClickListener(v -> {
            String inputUsername = edtUsername.getText().toString();
            String inputPassword = edtPassword.getText().toString();

            if (inputUsername.equals(savedUsername) && inputPassword.equals(savedPassword)) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Username atau Password salah!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
