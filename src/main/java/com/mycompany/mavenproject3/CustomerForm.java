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
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class CustomerForm extends JFrame {
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, nohpField, emailField, passField;
    private JComboBox<String> genderBox;
    private JButton saveButton, editButton, deleteButton;
    private CustomerDAO customerDAO;
    private List<Customer> customers;

    public CustomerForm() {
        customerDAO = new CustomerDAO();
        customers = customerDAO.getAllCustomers();

        setTitle("WK. Cuan | Daftar Customer");
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Kelola Customer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nama:"), gbc);
        nameField = new JTextField(10); gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nomor HP:"), gbc);
        nohpField = new JTextField(10); gbc.gridx = 1;
        formPanel.add(nohpField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(10); gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Password:"), gbc);
        passField = new JTextField(10); gbc.gridx = 1;
        formPanel.add(passField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Gender:"), gbc);
        genderBox = new JComboBox<>(new String[]{"Pria", "Wanita"}); gbc.gridx = 1;
        formPanel.add(genderBox, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        saveButton = new JButton("Simpan");
        formPanel.add(saveButton, gbc);

        gbc.gridy = 6;
        editButton = new JButton("Edit");
        formPanel.add(editButton, gbc);

        gbc.gridy = 7;
        deleteButton = new JButton("Hapus");
        formPanel.add(deleteButton, gbc);

        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Nomor HP", "Email", "Password", "Gender"}, 0);
        customerTable = new JTable(tableModel);
        loadCustomerData();

        JScrollPane scrollPane = new JScrollPane(customerTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String nohp = nohpField.getText();
            String email = emailField.getText();
            String password = passField.getText();
            String genderStr = (String) genderBox.getSelectedItem();
            boolean gender = "Pria".equals(genderStr);

            if (name.isEmpty() || nohp.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                Customer customer = customers.get(selectedRow);
                customer.setName(name);
                customer.setPhone(nohp);
                customer.setEmail(email);
                customer.setPassword(password);
                customer.setGender(gender);

                customerDAO.updateCustomer(customer);
                loadCustomerData();
                JOptionPane.showMessageDialog(this, "Data berhasil diperbarui.");
            } else {
                Customer newCustomer = new Customer();
                newCustomer.setId(generateNewId());
                newCustomer.setName(name);
                newCustomer.setPhone(nohp);
                newCustomer.setEmail(email);
                newCustomer.setPassword(password);
                newCustomer.setGender(gender);

                customerDAO.addCustomer(newCustomer);
                loadCustomerData();
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan.");
            }
            clearForm();
        });

        editButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                Customer customer = customers.get(selectedRow);
                nameField.setText(customer.getName());
                nohpField.setText(customer.getPhone());
                emailField.setText(customer.getEmail());
                passField.setText(customer.getPassword());
                genderBox.setSelectedItem(customer.getGender() ? "Pria" : "Wanita");
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = customerTable.getSelectedRow();
            if (selectedRow != -1) {
                int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    Customer customer = customers.get(selectedRow);
                    customerDAO.deleteCustomer(customer.getId());
                    loadCustomerData();
                    clearForm();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris yang ingin dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void loadCustomerData() {
        customers = customerDAO.getAllCustomers();
        tableModel.setRowCount(0);
        for (Customer customer : customers) {
            tableModel.addRow(new Object[]{
                customer.getId(),
                customer.getName(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getPassword(),
                customer.getGender() ? "Pria" : "Wanita"
            });
        }
    }

    private int generateNewId() {
        int maxId = 0;
        for (Customer c : customers) {
            if (c.getId() > maxId) {
                maxId = c.getId();
            }
        }
        return maxId + 1;
    }

    private void clearForm() {
        nameField.setText("");
        nohpField.setText("");
        emailField.setText("");
        passField.setText("");
        genderBox.setSelectedIndex(0);
        customerTable.clearSelection();
    }
}
