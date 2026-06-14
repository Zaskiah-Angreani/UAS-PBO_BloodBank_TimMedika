**#BloodBank**


**#Deskripsi Aplikasi**

BloodBank adalah aplikasi manajemen donor darah berbasis desktop yang dirancang untuk
mempercepat koordinasi antara pasien yang membutuhkan darah dan pendonor. Aplikasi ini menyediakan alur kerja yang terintegrasi bagi admin dalam mengelola stok darah secara real-time,
memvalidasi permohonan pasien, serta mengorganisir pendonor dengan efisien untuk memastikan setiap kebutuhan medis terpenuhi dengan cepat dan transparan.

**#Fitur Utama**

A. Sisi User (Pasien & Pendonor)

    1.Dashboard Statistik: Memantau progres pengajuan permintaan darah secara mandiri.
    
    2.Pengajuan Permintaan Darah: User dapat mengajukan permohonan darah dan memantau status verifikasi dari Admin.
    
    3.Cek Ketersediaan Stok: Fitur informasi ketersediaan stok darah berdasarkan golongan darah dan rumah sakit penyedia.
    
    4.Sistem Pendonor: User dapat mendaftar sebagai pendonor sukarela atau merespons permintaan darah spesifik dari pasien.
    
    5.Riwayat & Jadwal: Transparansi status pengajuan dan jadwal pengambilan/donor darah setelah disetujui oleh Admin.

B. Sisi Admin

    1.Manajemen Stok Darah: Admin memiliki akses untuk mengelola jumlah stok darah di berbagai rumah sakit secara real-time.
    
    2.Verifikasi Permohonan: Proses persetujuan atau penolakan permohonan darah dari user.
    
    3.Kelola Pendonor: Admin dapat memvalidasi data pendonor dan memasangkan pendonor sukarela dengan permintaan darah yang spesifik untuk efisiensi distribusi.

**#Cara Menjalankan Aplikasi**

A. Prasyarat:

JDK: Versi 17 atau lebih baru.

IDE: IntelliJ IDEA (direkomendasikan).

Build Tool: Maven (sudah terkonfigurasi dalam proyek).

Langkah Instalasi

1. Clone Repository:

git clone [https://github.com/Zaskiah-Angreani/UAS-PBO_BloodBank_TimMedika]

2. **Buka Proyek**: Buka IntelliJ IDEA, pilih *Open*, dan arahkan ke folder proyek ini. Maven akan otomatis mengunduh *dependencies* (Spring Boot, JavaFX, H2 Database) yang tertera pada file `pom.xml`.
3. **Konfigurasi Database**: Aplikasi menggunakan H2 Database (In-memory), sehingga tidak memerlukan konfigurasi server database eksternal.

### Menjalankan Aplikasi
* **Backend**: Jalankan kelas `DonorDarahApiApplication`.
* **Frontend**: Jalankan kelas `Main` yang berada di package `org.bloodblank`.

## Video Presentasi
[Klik di sini untuk menonton video presentasi aplikasi BloodBank di YouTube]
