package com.example.pengingataktivitas.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "aktivitas_table")
public class Aktivitas {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String judul;
    private String deskripsi;
    private String tanggal; // Format: yyyy-MM-dd
    private String waktu;   // Format: HH:mm

    // Constructor utama
    public Aktivitas(String judul, String deskripsi, String tanggal, String waktu) {
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.tanggal = tanggal;
        this.waktu = waktu;
    }

    // Constructor kosong (diperlukan oleh Room dalam beberapa kasus)
    public Aktivitas() {
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
