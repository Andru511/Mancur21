/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author ASUS
 */
public class Unit {
    private String satuan;
    private int jumlah; // jumlah botol per satuan (contoh: Box = 12)

    public Unit(String satuan, int jumlah) {
        this.satuan = satuan;
        this.jumlah = jumlah;
    }

    public String getSatuan() { return satuan; }
    public int getJumlah() { return jumlah; }

    public void setSatuan(String satuan) { this.satuan = satuan; }
    public void setJumlah(int jumlah) { this.jumlah = jumlah; }

    public double getHargaDariProduk(Product product) {
        double basePrice = product.getPrice();
        double discount = satuan.equalsIgnoreCase("Box") ? 0.9 : 1.0; // 10% diskon untuk box
        return basePrice * jumlah * discount;
    }

    @Override
    public String toString() {
        return satuan + " (" + jumlah + " botol)";
    }
}
