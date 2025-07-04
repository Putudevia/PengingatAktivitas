package com.example.pengingataktivitas.ui;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pengingataktivitas.R;
import com.example.pengingataktivitas.model.Aktivitas;
import com.example.pengingataktivitas.receiver.AlarmReceiver;
import com.example.pengingataktivitas.viewmodel.AktivitasViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditActivity extends AppCompatActivity {

    private EditText editJudul, editDeskripsi, editTanggal, editWaktu;
    private AktivitasViewModel aktivitasViewModel;
    private int idAktivitas = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        // Inisialisasi view
        editJudul = findViewById(R.id.edit_judul);
        editDeskripsi = findViewById(R.id.edit_deskripsi);
        editTanggal = findViewById(R.id.edit_tanggal);
        editWaktu = findViewById(R.id.edit_waktu);
        Button btnSimpan = findViewById(R.id.btn_simpan);

        aktivitasViewModel = new AktivitasViewModel(getApplication());

        // Ambil data dari Intent (jika mode edit)
        Intent intent = getIntent();
        if (intent.hasExtra("ID")) {
            setTitle("Edit Aktivitas");
            idAktivitas = intent.getIntExtra("ID", -1);
            editJudul.setText(intent.getStringExtra("JUDUL"));
            editDeskripsi.setText(intent.getStringExtra("DESKRIPSI"));
            editTanggal.setText(intent.getStringExtra("TANGGAL"));
            editWaktu.setText(intent.getStringExtra("WAKTU"));
        } else {
            setTitle("Tambah Aktivitas");
        }

        // Klik tombol Simpan
        btnSimpan.setOnClickListener(v -> simpanAktivitas());

        // Klik Tanggal
        editTanggal.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year1, month1, dayOfMonth) -> {
                        String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year1, month1 + 1, dayOfMonth);
                        editTanggal.setText(selectedDate);
                    }, year, month, day);

            datePickerDialog.show();
        });

        // Klik Waktu
        editWaktu.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hourOfDay, minute1) -> {
                        String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
                        editWaktu.setText(selectedTime);
                    }, hour, minute, true);

            timePickerDialog.show();
        });
    }

    private void simpanAktivitas() {
        String judul = editJudul.getText().toString().trim();
        String deskripsi = editDeskripsi.getText().toString().trim();
        String tanggal = editTanggal.getText().toString().trim();
        String waktu = editWaktu.getText().toString().trim();

        if (judul.isEmpty() || tanggal.isEmpty() || waktu.isEmpty()) {
            Toast.makeText(this, "Judul, Tanggal, dan Waktu wajib diisi", Toast.LENGTH_SHORT).show();
            return;
        }

        Aktivitas aktivitas = new Aktivitas(judul, deskripsi, tanggal, waktu);

        if (idAktivitas != -1) {
            aktivitas.setId(idAktivitas);
            aktivitasViewModel.update(aktivitas);
        } else {
            aktivitasViewModel.insert(aktivitas);
        }

        // Atur alarm
        setAlarm(aktivitas);

        // Kembalikan data ke MainActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("JUDUL", judul);
        resultIntent.putExtra("DESKRIPSI", deskripsi);
        resultIntent.putExtra("TANGGAL", tanggal);
        resultIntent.putExtra("WAKTU", waktu);
        if (idAktivitas != -1) {
            resultIntent.putExtra("ID", idAktivitas);
        }
        setResult(RESULT_OK, resultIntent);

        Toast.makeText(this, "Aktivitas disimpan", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void setAlarm(Aktivitas aktivitas) {
        try {
            String dateTimeStr = aktivitas.getTanggal() + " " + aktivitas.getWaktu();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            Date date = format.parse(dateTimeStr);
            if (date == null) return;

            long waktuMillis = date.getTime();

            // Cek apakah waktu alarm di masa depan
            if (waktuMillis < System.currentTimeMillis()) {
                Toast.makeText(this, "Waktu sudah lewat, alarm tidak diset", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra("JUDUL", aktivitas.getJudul());

            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    this,
                    aktivitas.getJudul().hashCode(), // Pakai hash judul supaya unik
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
            );

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (alarmManager != null) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, waktuMillis, pendingIntent);
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, waktuMillis, pendingIntent);
                }
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
