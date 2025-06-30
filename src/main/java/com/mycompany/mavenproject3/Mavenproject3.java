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
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Mavenproject3 extends JFrame {
    private JButton addProductButton;
    private JButton viewCustomerButton;
    private JButton sellProductButton;
    private JButton reportButton;
    private JButton regisButton;

    private List<Product> productList = new ArrayList<>();
    private List<Customer> customerList = new ArrayList<>();
    private List<History> historyList = new ArrayList<>();
    private JPanel menuPanel;

    public Mavenproject3() {
        setTitle("WK. STI Chill | Tampilan Penjual");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        productList.add(new Product(1, "P001", "Americano", "Coffee", 10000, 100));
        productList.add(new Product(2, "P002", "Pandan Latte", "Dairy", 20000, 100));
        productList.add(new Product(3, "P003", "Root Beer", "Soda", 15000, 100));
        productList.add(new Product(4, "P004", "Matcha Frappuccino", "Tea", 28000, 100));
        productList.add(new Product(5, "P005", "Jus Apel", "Juice", 17000, 100));

        customerList.add(new Customer(1, "Karbit", "+621312421421", "john.terry@itbss.ac.id", "kopisusuventi", true));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 4, 8, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel titleLabel = new JLabel("Home");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        leftPanel.add(titleLabel, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        addProductButton = new JButton("Kelola Produk");
        leftPanel.add(addProductButton, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        viewCustomerButton = new JButton("Kelola Customer");
        leftPanel.add(viewCustomerButton, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        sellProductButton = new JButton("Jual Produk");
        leftPanel.add(sellProductButton, gbc);

        gbc.gridx = 0; gbc.gridy = 4;
        reportButton = new JButton("Laporan Penjualan");
        leftPanel.add(reportButton, gbc);

        gbc.gridx = 0; gbc.gridy = 5;
        regisButton = new JButton("Registrasi Akun");
        leftPanel.add(regisButton, gbc);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(leftPanel, BorderLayout.NORTH);
        wrapperPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

        add(wrapperPanel, BorderLayout.WEST);

        menuPanel = new JPanel(new GridBagLayout());
        refreshBanner(); 
        add(menuPanel, BorderLayout.CENTER);

        addProductButton.addActionListener(e -> new ProductForm(this).setVisible(true));
        viewCustomerButton.addActionListener(e -> new CustomerForm().setVisible(true));
        sellProductButton.addActionListener(e -> new SellForm(this).setVisible(true));
        reportButton.addActionListener(e -> new HistoryForm().setVisible(true));
        regisButton.addActionListener(e -> new RegisForm(this).setVisible(true));

        setVisible(true);
    }

    public void refreshBanner() {
        menuPanel.removeAll();
        menuPanel.revalidate();
        menuPanel.repaint();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 25, 8, 25);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] categories = { "Coffee", "Dairy", "Juice", "Soda", "Tea" };

        for (int column = 0; column < categories.length; column++) {
            String category = categories[column];

            gbc.gridx = column;
            gbc.gridy = 0;
            JLabel categoryLabel = new JLabel(category);
            categoryLabel.setFont(new Font("Arial", Font.BOLD, 20));
            categoryLabel.setHorizontalAlignment(JLabel.CENTER);
            menuPanel.add(categoryLabel, gbc);

            int row = 1;
            for (Product p : productList) {
                if (category.equalsIgnoreCase(p.getCategory()) && p.getStock() > 0) {
                    gbc.gridx = column;
                    gbc.gridy = row++;
                    menuPanel.add(new JLabel(p.getName()), gbc);
                }
            }
        }
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
        refreshBanner(); 
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    public List<History> getHistoryList() {
        return historyList;
    }

    public void addHistory(History h) {
        historyList.add(h);
    }

    public String getPriceByProductName(String productName) {
        for (Product p : productList) {
            if (p.getName().equalsIgnoreCase(productName)) {
                return String.valueOf(p.getPrice());
            }
        }
        return "Product not found";
    }

    public static void main(String[] args) {
        new Mavenproject3();
    }
}
