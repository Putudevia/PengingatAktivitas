# ⏰ Aplikasi Pengingat Aktivitas

Aplikasi Android yang berfungsi untuk membantu pengguna mengatur dan mengingat aktivitas harian mereka. Notifikasi akan muncul secara otomatis berdasarkan waktu yang telah disetel pengguna.

---

## 🚀 Fitur Utama

### 1. ➕ Tambah Aktivitas Baru
- Tambahkan aktivitas menggunakan tombol **Floating Action Button**.
- Form input terdiri dari: **Judul**, **Deskripsi**, **Tanggal**, dan **Waktu**.

### 2. ✏️ Edit Aktivitas
- Ketuk salah satu aktivitas untuk masuk ke halaman edit.
- Ubah data dengan mudah kapan pun diperlukan.

### 3. 🗑️ Hapus Aktivitas
- Tekan lama pada daftar aktivitas.
- Pilih opsi "Hapus" untuk menghapus aktivitas langsung dari list.

### 4. 🔔 Alarm / Notifikasi Otomatis
- Notifikasi akan muncul tepat waktu sesuai yang diatur pengguna.
- Menggunakan `AlarmManager` untuk menjamin notifikasi aktif walau aplikasi tidak dibuka.

### 5. 📋 Tampilan Data dalam List
- Daftar aktivitas tampil rapi menggunakan **RecyclerView**.
- Mendukung scroll jika data banyak.

### 6. 🕐 Alarm Tetap Aktif
- Menggunakan fitur **AlarmManager Android** agar alarm tetap jalan walau HP standby atau layar mati.

### 7. 📅 DatePicker dan TimePicker
- Pemilihan tanggal dan waktu jadi lebih mudah.
- Kalender dan jam otomatis muncul saat kolom diklik.

### 8. 💾 Penyimpanan Lokal (Room Database)
- Data aktivitas disimpan secara lokal di perangkat.
- Aman meskipun aplikasi ditutup atau HP direstart.

### 9. ✅ Auto Cancel Alarm saat Edit/Hapus
- Alarm lama otomatis dibatalkan saat aktivitas diubah atau dihapus.
- Mencegah notifikasi ganda.

---


## 🛠️ Teknologi yang Digunakan

- Android Studio (Java)
- Room Database
- RecyclerView
- AlarmManager & Notification
- Intent, DatePicker, TimePicker
