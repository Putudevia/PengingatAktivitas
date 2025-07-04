package com.example.pengingataktivitas.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.pengingataktivitas.model.Aktivitas;

import java.util.List;

@Dao
public interface AktivitasDao {
    @Insert
    void insert(Aktivitas aktivitas);

    @Update
    void update(Aktivitas aktivitas);

    @Delete
    void delete(Aktivitas aktivitas);


    @Query("SELECT * FROM aktivitas_table ORDER BY tanggal, waktu ASC")
    LiveData<List<Aktivitas>> getAllAktivitas();
}
