package com.example.pengingataktivitas.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pengingataktivitas.R;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TextView tvJudul = findViewById(R.id.tv_judul_detail);
        TextView tvDeskripsi = findViewById(R.id.tv_deskripsi_detail);
        TextView tvTanggal = findViewById(R.id.tv_tanggal_detail);
        TextView tvWaktu = findViewById(R.id.tv_waktu_detail);

        // Ambil data dari Intent
        String judul = getIntent().getStringExtra("JUDUL");
        String deskripsi = getIntent().getStringExtra("DESKRIPSI");
        String tanggal = getIntent().getStringExtra("TANGGAL");
        String waktu = getIntent().getStringExtra("WAKTU");

        // Tampilkan di TextView
        tvJudul.setText(judul);
        tvDeskripsi.setText(deskripsi);
        tvTanggal.setText("Tanggal: " + tanggal);
        tvWaktu.setText("Waktu: " + waktu);
    }
}
