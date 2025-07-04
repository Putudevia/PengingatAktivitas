package com.example.pengingataktivitas.receiver;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.pengingataktivitas.R;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "aktivitas_channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        String judul = intent.getStringExtra("JUDUL");

        if (judul == null) judul = "Aktivitas tanpa judul";

        createNotificationChannel(context);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications) // Pastikan icon ini ada di drawable
                .setContentTitle("Pengingat Aktivitas")
                .setContentText("Saatnya melakukan: " + judul)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManagerCompat manager = NotificationManagerCompat.from(context);

        // Cek permission untuk notifikasi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                // Kalau tidak diizinkan, keluar saja
                return;
            }
        }

        // ID unik supaya tiap notifikasi tidak saling tumpuk
        int notificationId = (int) System.currentTimeMillis();
        manager.notify(notificationId, builder.build());
    }

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Pengingat Aktivitas",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                if (manager.getNotificationChannel(CHANNEL_ID) == null) {
                    manager.createNotificationChannel(channel);
                }
            }
        }
    }
}
