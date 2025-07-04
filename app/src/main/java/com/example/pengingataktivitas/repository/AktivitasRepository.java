package com.example.pengingataktivitas.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.pengingataktivitas.database.AktivitasDatabase;
import com.example.pengingataktivitas.model.Aktivitas;
import com.example.pengingataktivitas.database.AktivitasDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AktivitasRepository {
    private AktivitasDao aktivitasDao;
    private LiveData<List<Aktivitas>> allAktivitas;
    private ExecutorService executorService;

    public AktivitasRepository(Application application) {
        AktivitasDatabase database = AktivitasDatabase.getInstance(application);
        aktivitasDao = database.aktivitasDao();
        allAktivitas = aktivitasDao.getAllAktivitas();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Aktivitas>> getAllAktivitas() {
        return allAktivitas;
    }

    public void insert(Aktivitas aktivitas) {
        executorService.execute(() -> aktivitasDao.insert(aktivitas));
    }

    public void update(Aktivitas aktivitas) {
        executorService.execute(() -> aktivitasDao.update(aktivitas));
    }

    public void delete(Aktivitas aktivitas) {
        executorService.execute(() -> aktivitasDao.delete(aktivitas));
    }


}