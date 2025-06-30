/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject3;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author ASUS
 */
public class RegisForm extends JFrame {
    private JTextField customerField;
    private JTextField nohpField;
    private JTextField emailField;
    private JTextField passwordField;
    private JComboBox<String> genderBox;
    private JButton regisButton;
    private List<Customer> customers;
    private Mavenproject3 mainApp;

    public RegisForm(Mavenproject3 mainApp) {
        this.mainApp = mainApp;
        this.customers = mainApp.getCustomerList();

        setTitle("WK. Cuan | Login");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel regisPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Customer
        gbc.gridx = 0; gbc.gridy = 0;
        regisPanel.add(new JLabel("Nama:"), gbc);

        customerField = new JTextField(10);
        gbc.gridx = 1;
        regisPanel.add(customerField, gbc);

        // Gender
        gbc.gridx = 0; gbc.gridy = 1;
        regisPanel.add(new JLabel("Gender:"), gbc);

        genderBox = new JComboBox<>(new String[] {"Pria", "Wanita"});
        gbc.gridx = 1;
        regisPanel.add(genderBox, gbc);

        // Nomor HP
        gbc.gridx = 0; gbc.gridy = 2;
        regisPanel.add(new JLabel("Nomor HP:"), gbc);

        nohpField = new JTextField(10);
        gbc.gridx = 1;
        regisPanel.add(nohpField, gbc);

        // Email
        gbc.gridx = 0; gbc.gridy = 3;
        regisPanel.add(new JLabel("Email:"), gbc);

        emailField = new JTextField(10);
        gbc.gridx = 1;
        regisPanel.add(emailField, gbc);

        // Password
        gbc.gridx = 0; gbc.gridy = 4;
        regisPanel.add(new JLabel("Password:"), gbc);

        passwordField = new JTextField(10);
        gbc.gridx = 1;
        regisPanel.add(passwordField, gbc);

        // Regis Button
        regisButton = new JButton("Buat Akun");
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        regisPanel.add(regisButton, gbc);

        add(regisPanel);
        setVisible(true);

        regisButton.addActionListener(e -> {
            String nama = customerField.getText().trim();
            String nohp = nohpField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String gender = (String) genderBox.getSelectedItem();

            if (nama.isEmpty() || nohp.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            boolean genderValue = gender.equals("Pria");

            // ID bisa diabaikan jika AUTO_INCREMENT di DB
            Customer newCustomer = new Customer(0, nama, nohp, email, password, genderValue);

            try {
                CustomerDAO dao = new CustomerDAO();
                dao.addCustomer(newCustomer);

                // Refresh list from database (optional)
                customers.add(newCustomer);

                JOptionPane.showMessageDialog(this, "Registrasi berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Registrasi gagal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }
}
