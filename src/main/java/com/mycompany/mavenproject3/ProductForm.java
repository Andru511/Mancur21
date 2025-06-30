package com.mycompany.mavenproject3;

import com.mycompany.mavenproject3.dao.ProductDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ProductForm extends JFrame {
    private JTable drinkTable;
    private DefaultTableModel tableModel;
    private JTextField codeField;
    private JTextField nameField;
    private JComboBox<String> categoryField;
    private JTextField priceField;
    private JTextField stockField;
    private JButton saveButton;
    private JButton editButton;
    private JButton deleteButton;
    private ProductDAO productDAO;
    private boolean isEditMode = false;

    public ProductForm(Mavenproject3 mainApp) {
        this.productDAO = new ProductDAO();

        setTitle("WK. Cuan | Stok Barang");
        setSize(600, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Kelola Produk");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Kode Barang"));
        codeField = new JTextField(8);
        gbc.gridx = 1;
        formPanel.add(codeField);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nama Barang:"), gbc);
        nameField = new JTextField(8);
        gbc.gridx = 1;
        formPanel.add(nameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Kategori:"), gbc);
        categoryField = new JComboBox<>(new String[]{"Coffee", "Dairy", "Juice", "Soda", "Tea"});
        gbc.gridx = 1;
        formPanel.add(categoryField, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Harga Jual:"), gbc);
        priceField = new JTextField(8);
        gbc.gridx = 1;
        formPanel.add(priceField, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Stok Tersedia:"), gbc);
        stockField = new JTextField(8);
        gbc.gridx = 1;
        formPanel.add(stockField, gbc);

        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        saveButton = new JButton("Simpan");
        formPanel.add(saveButton, gbc);

        gbc.gridx = 0; gbc.gridy = 6;
        editButton = new JButton("Edit");
        formPanel.add(editButton, gbc);

        gbc.gridx = 0; gbc.gridy = 7;
        deleteButton = new JButton("Hapus");
        formPanel.add(deleteButton, gbc);

        tableModel = new DefaultTableModel(new String[]{"Kode", "Nama", "Kategori", "Harga Jual", "Stok"}, 0);
        drinkTable = new JTable(tableModel);
        loadProductData();

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(formPanel, BorderLayout.NORTH);
        add(leftPanel, BorderLayout.WEST);

        JScrollPane scrollPane = new JScrollPane(drinkTable);
        add(scrollPane, BorderLayout.CENTER);

        saveButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            String code = codeField.getText();
            String name = nameField.getText();
            String category = (String) categoryField.getSelectedItem();
            String priceText = priceField.getText().replace(".", "").replace(",", "");
            String stockText = stockField.getText();

            if (code.isEmpty() || name.isEmpty() || priceText.isEmpty() || stockText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                int stock = Integer.parseInt(stockText);

                if (isEditMode && selectedRow != -1) {
                    Product existing = productDAO.getAllProducts().get(selectedRow);
                    Product updated = new Product(existing.getId(), code, name, category, price, stock);

                    productDAO.updateProduct(updated);
                    JOptionPane.showMessageDialog(this, "Data berhasil diperbarui.");
                } else {
                    Product newProduct = new Product(0, code, name, category, price, stock);

                    productDAO.addProduct(newProduct);
                    JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan.");
                }

                clearForm();
                isEditMode = false;
                loadProductData();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Harga dan stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        editButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                Product selectedProduct = productDAO.getAllProducts().get(selectedRow);
                codeField.setText(selectedProduct.getCode());
                nameField.setText(selectedProduct.getName());
                categoryField.setSelectedItem(selectedProduct.getCategory());
                priceField.setText(String.valueOf((int) selectedProduct.getPrice()));
                stockField.setText(String.valueOf(selectedProduct.getStock()));
                isEditMode = true;
            } else {
                JOptionPane.showMessageDialog(this, "Pilih baris yang ingin diedit!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteButton.addActionListener(e -> {
            int selectedRow = drinkTable.getSelectedRow();
            if (selectedRow != -1) {
                int id = productDAO.getAllProducts().get(selectedRow).getId();
                productDAO.deleteProduct(id);
                loadProductData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Tidak ada yang dipilih", "Peringatan", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    private void clearForm() {
        codeField.setText("");
        nameField.setText("");
        categoryField.setSelectedIndex(0);
        priceField.setText("");
        stockField.setText("");
    }

    private void loadProductData() {
        tableModel.setRowCount(0);
        List<Product> productList = productDAO.getAllProducts();
        for (Product product : productList) {
            tableModel.addRow(new Object[]{
                    product.getCode(), product.getName(), product.getCategory(), formatRupiah(product.getPrice()), product.getStock()
            });
        }
    }

    private String formatRupiah(double harga) {
        java.text.NumberFormat formatter = java.text.NumberFormat.getCurrencyInstance(
                java.util.Locale.forLanguageTag("id-ID")
        );
        return formatter.format(harga).replace(",00", "");
    }
}
