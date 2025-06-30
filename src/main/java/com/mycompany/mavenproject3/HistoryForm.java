/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

/**
 *
 * @author ASUS
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class HistoryForm extends JFrame {
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private List<History> history;
    private JComboBox<String> filterBox;

    public HistoryForm() {
        System.out.println("🚀 Initializing HistoryForm...");

        // 🔁 Load data from DB
        HistoryDAO dao = new HistoryDAO();
        this.history = dao.getAllHistories();

        if (history == null) {
            System.out.println("⚠️ History list is null!");
            history = new ArrayList<>();
        } else {
            System.out.println("✅ Fetched " + history.size() + " history entries from DB.");
        }

        setTitle("WK. Cuan | Laporan Penjualan");
        setSize(600, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Table Model
        tableModel = new DefaultTableModel(new String[]{
            "ID Pesanan", "Nama", "Pesanan", "Jumlah", "Jumlah Pembayaran", "Waktu Pemesanan"
        }, 0);
        historyTable = new JTable(tableModel);

        // Load data to table
        loadProductData(history);

        // Filter Panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterBox = new JComboBox<>(new String[]{"Semua", "Hari Ini", "Minggu Ini", "Bulan Ini"});
        topPanel.add(new JLabel("Filter:"));
        topPanel.add(filterBox);
        add(topPanel, BorderLayout.NORTH);

        // Table Scroll Pane
        JScrollPane scrollPane = new JScrollPane(historyTable);
        add(scrollPane, BorderLayout.CENTER);

        // Filter Action
        filterBox.addActionListener(e -> applyFilter());
    }

    private void loadProductData(List<History> historyList) {
        System.out.println("📦 Loading data into table...");

        tableModel.setRowCount(0); // Clear table
        if (historyList == null || historyList.isEmpty()) {
            System.out.println("📭 No data to display.");
            return;
        }

        for (History h : historyList) {
            System.out.println("➡️ Adding row: " + h.getNamaCustomer() + " - " + h.getNamaProduk());
            tableModel.addRow(new Object[]{
                h.getIdPesanan(),
                h.getNamaCustomer(),
                h.getNamaProduk(),
                h.getJumlah() + " Botol",
                formatRupiah(h.getJumlahPembayaran()),
                h.getWaktu()
            });
        }
    }

    private String formatRupiah(double harga) {
        java.text.NumberFormat formatter = java.text.NumberFormat.getCurrencyInstance(
            java.util.Locale.forLanguageTag("id-ID")
        );
        return formatter.format(harga).replace(",00", "");
    }

    private void applyFilter() {
        String selected = (String) filterBox.getSelectedItem();
        List<History> filtered = new ArrayList<>();
        LocalDate now = LocalDate.now();

        System.out.println("🔎 Applying filter: " + selected);

        for (History h : history) {
            LocalDate date = h.getWaktu().toLocalDate();

            switch (selected) {
                case "Hari Ini":
                    if (date.equals(now)) filtered.add(h);
                    break;
                case "Minggu Ini":
                    if (!date.isBefore(now.minusDays(6))) filtered.add(h);
                    break;
                case "Bulan Ini":
                    if (date.getMonth() == now.getMonth() && date.getYear() == now.getYear())
                        filtered.add(h);
                    break;
                default:
                    filtered = history;
                    break;
            }
        }

        loadProductData(filtered);
    }
}
