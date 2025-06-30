package com.mycompany.mavenproject3;

import java.time.LocalDateTime;

public class History {
    private int idPesanan;
    private String namaCustomer;
    private String namaProduk;
    private int jumlah;
    private int jumlahPembayaran;
    private LocalDateTime waktu;

    public History(int idPesanan, String namaCustomer, String namaProduk, int jumlah, int jumlahPembayaran, LocalDateTime waktu) {
        this.idPesanan = idPesanan;
        this.namaCustomer = namaCustomer;
        this.namaProduk = namaProduk;
        this.jumlah = jumlah;
        this.jumlahPembayaran = jumlahPembayaran;
        this.waktu = waktu;
    }

    public int getIdPesanan() { return idPesanan; }
    public String getNamaCustomer() { return namaCustomer; }
    public String getNamaProduk() { return namaProduk; }
    public int getJumlah() { return jumlah; }
    public int getJumlahPembayaran() { return jumlahPembayaran; }
    public LocalDateTime getWaktu() { return waktu; }

    public void setIdPesanan(int idPesanan) { this.idPesanan = idPesanan; }
    public void setNamaCustomer(String namaCustomer) { this.namaCustomer = namaCustomer; }
    public void setNamaProduk(String namaProduk) { this.namaProduk = namaProduk; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }
    public void setJumlahPembayaran(int jumlahPembayaran) { this.jumlahPembayaran = jumlahPembayaran; }
    public void setWaktu(LocalDateTime waktu) { this.waktu = waktu; }
}
