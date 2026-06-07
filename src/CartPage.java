package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CartPage extends JFrame {

    Color primaryRed = new Color(226, 75, 74);
    Color bgWhite = new Color(255, 255, 255);
    Color bgGray = new Color(245, 245, 245);
    Color textDark = new Color(44, 44, 42);
    Color textGray = new Color(136, 135, 128);

    ArrayList<String[]> cartItems;
    JPanel cartPanel;
    JFrame parentFrame;

    public CartPage(ArrayList<String[]> cartItems, JFrame parentFrame) {
        this.cartItems = cartItems;
        this.parentFrame = parentFrame;
        setTitle("FoodieHub - Cart");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgGray);

        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(primaryRed);
        navbar.setPreferredSize(new Dimension(600, 60));
        navbar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        JLabel logo = new JLabel("🛒 Your Cart");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(Color.WHITE);
        JButton backBtn = new JButton("← Back");
        backBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        backBtn.setForeground(Color.WHITE);
        backBtn.setBackground(primaryRed);
        backBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, true));
        backBtn.setFocusPainted(false);
        backBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backBtn.addActionListener(e -> dispose());
        navbar.add(logo, BorderLayout.WEST);
        navbar.add(backBtn, BorderLayout.EAST);

        cartPanel = new JPanel();
        cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
        cartPanel.setBackground(bgGray);
        cartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        buildCartItems();

        JScrollPane scrollPane = new JScrollPane(cartPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(bgWhite);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        int total = cartItems.stream().mapToInt(i -> Integer.parseInt(i[2])).sum();
        JPanel totalPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        totalPanel.setBackground(bgWhite);
        addTotalRow(totalPanel, "Subtotal:", "₹" + total);
        addTotalRow(totalPanel, "Delivery:", "₹40");
        addTotalRow(totalPanel, "Total:", "₹" + (total + 40));

        JButton placeOrderBtn = new JButton("Place Order →");
        placeOrderBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        placeOrderBtn.setForeground(Color.WHITE);
        placeOrderBtn.setBackground(primaryRed);
        placeOrderBtn.setBorder(BorderFactory.createEmptyBorder(14, 0, 14, 0));
        placeOrderBtn.setFocusPainted(false);
        placeOrderBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        placeOrderBtn.addActionListener(e -> {
            if (cartItems.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Your cart is empty!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();
            new OrderPage(cartItems, total + 40);
        });

        bottomPanel.add(totalPanel, BorderLayout.NORTH);
        bottomPanel.add(Box.createVerticalStrut(10), BorderLayout.CENTER);
        bottomPanel.add(placeOrderBtn, BorderLayout.SOUTH);

        add(navbar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    void buildCartItems() {
        cartPanel.removeAll();
        if (cartItems.isEmpty()) {
            JLabel emptyLabel = new JLabel("🛒 Your cart is empty!");
            emptyLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
            emptyLabel.setForeground(textGray);
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            cartPanel.add(Box.createVerticalStrut(100));
            cartPanel.add(emptyLabel);
            return;
        }
        for (int i = 0; i < cartItems.size(); i++) {
            final int idx = i;
            String[] item = cartItems.get(i);
            JPanel card = new JPanel(new BorderLayout(10, 0));
            card.setBackground(bgWhite);
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
            ));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

            JPanel leftInfo = new JPanel();
            leftInfo.setLayout(new BoxLayout(leftInfo, BoxLayout.Y_AXIS));
            leftInfo.setBackground(bgWhite);
            JLabel nameLabel = new JLabel(item[0]);
            nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
            nameLabel.setForeground(textDark);
            JLabel catLabel = new JLabel(item[1]);
            catLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            catLabel.setForeground(textGray);
            leftInfo.add(nameLabel);
            leftInfo.add(Box.createVerticalStrut(4));
            leftInfo.add(catLabel);

            JPanel rightPanel = new JPanel(new BorderLayout(10, 0));
            rightPanel.setBackground(bgWhite);
            JLabel priceLabel = new JLabel("₹" + item[2]);
            priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            priceLabel.setForeground(primaryRed);
            JButton removeBtn = new JButton("✕");
            removeBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            removeBtn.setForeground(Color.WHITE);
            removeBtn.setBackground(primaryRed);
            removeBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            removeBtn.setFocusPainted(false);
            removeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            removeBtn.addActionListener(e -> {
                cartItems.remove(idx);
                buildCartItems();
                cartPanel.revalidate();
                cartPanel.repaint();
            });
            rightPanel.add(priceLabel, BorderLayout.WEST);
            rightPanel.add(removeBtn, BorderLayout.EAST);

            card.add(leftInfo, BorderLayout.WEST);
            card.add(rightPanel, BorderLayout.EAST);
            cartPanel.add(card);
            cartPanel.add(Box.createVerticalStrut(10));
        }
        cartPanel.revalidate();
        cartPanel.repaint();
    }

    void addTotalRow(JPanel panel, String label, String value) {
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        l.setForeground(textGray);
        JLabel v = new JLabel(value);
        v.setFont(new Font("Segoe UI", Font.BOLD, 14));
        v.setForeground(textDark);
        v.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(l);
        panel.add(v);
    }
}