package com.example.pengingataktivitas.ui;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pengingataktivitas.R;
import com.example.pengingataktivitas.adapter.AktivitasAdapter;
import com.example.pengingataktivitas.model.Aktivitas;
import com.example.pengingataktivitas.viewmodel.AktivitasViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_REQUEST = 1;
    public static final int EDIT_REQUEST = 2;

    private AktivitasViewModel aktivitasViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Permission untuk notifikasi (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }

        // Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "aktivitas_channel",
                    "Pengingat Aktivitas",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }

        // Tombol tambah aktivitas
        FloatingActionButton btnTambah = findViewById(R.id.fab_add);
        btnTambah.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivityForResult(intent, ADD_REQUEST);
        });

        // Setup RecyclerView dan Adapter
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        final AktivitasAdapter adapter = new AktivitasAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // ViewModel untuk mengambil data dari database
        aktivitasViewModel = new ViewModelProvider(this).get(AktivitasViewModel.class);
        aktivitasViewModel.getAllAktivitas().observe(this, aktivitasList -> {
            if (aktivitasList != null) {
                adapter.setAktivitas(aktivitasList);
            }
        });

        // Klik item = edit aktivitas
        adapter.setOnItemClickListener(aktivitas -> {
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            intent.putExtra("ID", aktivitas.getId());
            intent.putExtra("JUDUL", aktivitas.getJudul());
            intent.putExtra("DESKRIPSI", aktivitas.getDeskripsi());
            intent.putExtra("TANGGAL", aktivitas.getTanggal());
            intent.putExtra("WAKTU", aktivitas.getWaktu());
            startActivityForResult(intent, EDIT_REQUEST);
        });

        // Klik lama = tampilkan pilihan Edit atau Hapus
        adapter.setOnItemLongClickListener(aktivitas -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Pilih Aksi")
                    .setItems(new CharSequence[]{"Edit", "Hapus"}, (dialog, which) -> {
                        if (which == 0) {
                            // Edit
                            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                            intent.putExtra("ID", aktivitas.getId());
                            intent.putExtra("JUDUL", aktivitas.getJudul());
                            intent.putExtra("DESKRIPSI", aktivitas.getDeskripsi());
                            intent.putExtra("TANGGAL", aktivitas.getTanggal());
                            intent.putExtra("WAKTU", aktivitas.getWaktu());
                            startActivityForResult(intent, EDIT_REQUEST);
                        } else if (which == 1) {
                            // Hapus
                            aktivitasViewModel.delete(aktivitas);
                            Toast.makeText(MainActivity.this, "Aktivitas dihapus", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == ADD_REQUEST) {
                Toast.makeText(this, "Aktivitas berhasil disimpan", Toast.LENGTH_SHORT).show();
            } else if (requestCode == EDIT_REQUEST) {
                Toast.makeText(this, "Aktivitas berhasil diperbarui", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
