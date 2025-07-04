package com.example.pengingataktivitas.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.pengingataktivitas.model.Aktivitas;
import com.example.pengingataktivitas.repository.AktivitasRepository;

import java.util.List;

public class AktivitasViewModel extends AndroidViewModel {
    private final AktivitasRepository repository;
    private final LiveData<List<Aktivitas>> allAktivitas;

    public AktivitasViewModel(@NonNull Application application) {
        super(application);
        repository = new AktivitasRepository(application);
        allAktivitas = repository.getAllAktivitas();
    }

    public void insert(Aktivitas aktivitas) {
        repository.insert(aktivitas);
    }

    public void update(Aktivitas aktivitas) {
        repository.update(aktivitas);
    }

    public void delete(Aktivitas aktivitas) {
        repository.delete(aktivitas);
    }

    public LiveData<List<Aktivitas>> getAllAktivitas() {
        return allAktivitas;
    }
}
