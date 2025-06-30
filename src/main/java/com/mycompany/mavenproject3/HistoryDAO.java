package com.mycompany.mavenproject3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {
    private final String URL = "jdbc:mysql://localhost:3306/latihanbuyuricha";
    private final String USER = "test";
    private final String PASSWORD = "test123";

    private Connection getConnection() throws SQLException {
        System.out.println("🔌 Connecting to database...");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addHistory(History history) {
        String sql = "INSERT INTO history (namacustomer, namaproduk, jumlah, jumlahPembayaran, waktu) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, history.getNamaCustomer());
            stmt.setString(2, history.getNamaProduk());
            stmt.setInt(3, history.getJumlah());
            stmt.setInt(4, history.getJumlahPembayaran());
            stmt.setTimestamp(5, Timestamp.valueOf(history.getWaktu()));
            int rows = stmt.executeUpdate();
            System.out.println("✅ Inserted " + rows + " row(s) into history.");
        } catch (SQLException e) {
            System.err.println("❌ Error inserting into history:");
            e.printStackTrace();
        }
    }

    public List<History> getAllHistories() {
        List<History> historyList = new ArrayList<>();
        String sql = "SELECT * FROM history";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("📥 Executing query: " + sql);

            while (rs.next()) {
                int id = rs.getInt("idPesanan"); // ✅ Corrected column name
                String namaCustomer = rs.getString("namacustomer");
                String namaProduk = rs.getString("namaproduk");
                int jumlah = rs.getInt("jumlah");
                int jumlahPembayaran = rs.getInt("jumlahPembayaran");
                Timestamp waktu = rs.getTimestamp("waktu");

                System.out.printf("📄 Row -> ID: %d, Customer: %s, Produk: %s, Jumlah: %d, Bayar: %d, Waktu: %s%n",
                        id, namaCustomer, namaProduk, jumlah, jumlahPembayaran, waktu);

                History history = new History(
                        id,
                        namaCustomer,
                        namaProduk,
                        jumlah,
                        jumlahPembayaran,
                        waktu.toLocalDateTime()
                );
                historyList.add(history);
            }

            System.out.println("✅ Total records fetched: " + historyList.size());

        } catch (SQLException e) {
            System.err.println("❌ Error fetching history:");
            e.printStackTrace();
        }

        return historyList;
    }
}
