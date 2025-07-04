package com.example.pengingataktivitas.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pengingataktivitas.model.Aktivitas;

@Database(entities = {Aktivitas.class}, version = 1)
public abstract class AktivitasDatabase extends RoomDatabase {
    private static AktivitasDatabase instance;

    public abstract AktivitasDao aktivitasDao();

    public static synchronized AktivitasDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AktivitasDatabase.class, "aktivitas_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
