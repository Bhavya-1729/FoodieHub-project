package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class OrderPage extends JFrame {

    Color primaryRed = new Color(226, 75, 74);
    Color bgWhite = new Color(255, 255, 255);
    Color bgGray = new Color(245, 245, 245);
    Color textDark = new Color(44, 44, 42);
    Color textGray = new Color(136, 135, 128);
    Color green = new Color(40, 167, 69);

    ArrayList<String[]> cartItems;
    int totalAmount;

    public OrderPage(ArrayList<String[]> cartItems, int totalAmount) {
        this.cartItems = cartItems;
        this.totalAmount = totalAmount;
        setTitle("FoodieHub - Place Order");
        setSize(750, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgGray);

        // NAVBAR
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(primaryRed);
        navbar.setPreferredSize(new Dimension(750, 60));
        navbar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        JLabel logo = new JLabel("📦 Place Your Order");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(Color.WHITE);
        navbar.add(logo, BorderLayout.WEST);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(bgGray);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ADDRESS CARD
        JPanel addressCard = createCard("📍 Delivery Address");
        JTextField nameField = createField("Full Name");
        JTextField addressField = createField("House No, Street, Area");
        JTextField cityField = createField("City");
        JTextField pincodeField = new JTextField("Pincode (6 digits)");
        pincodeField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pincodeField.setForeground(textGray);
        pincodeField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        pincodeField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        pincodeField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) || pincodeField.getText().replace("Pincode (6 digits)", "").length() >= 6) {
                    e.consume();
                }
            }
        });
        pincodeField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (pincodeField.getText().equals("Pincode (6 digits)")) {
                    pincodeField.setText("");
                    pincodeField.setForeground(textDark);
                }
            }
            public void focusLost(FocusEvent e) {
                if (pincodeField.getText().isEmpty()) {
                    pincodeField.setText("Pincode (6 digits)");
                    pincodeField.setForeground(textGray);
                }
            }
        });

        addressCard.add(nameField);
        addressCard.add(Box.createVerticalStrut(10));
        addressCard.add(addressField);
        addressCard.add(Box.createVerticalStrut(10));
        addressCard.add(cityField);
        addressCard.add(Box.createVerticalStrut(10));
        addressCard.add(pincodeField);

        // PAYMENT CARD
        JPanel paymentCard = createCard("💳 Payment Method");

        ButtonGroup payGroup = new ButtonGroup();
        JRadioButton cashBtn = createRadio("💵 Cash on Delivery");
        JRadioButton upiBtn = createRadio("📱 UPI Payment");
        JRadioButton cardBtn = createRadio("💳 Credit/Debit Card");
        cashBtn.setSelected(true);
        payGroup.add(cashBtn);
        payGroup.add(upiBtn);
        payGroup.add(cardBtn);

        // Payment details panel
        JPanel payDetailsPanel = new JPanel();
        payDetailsPanel.setLayout(new BoxLayout(payDetailsPanel, BoxLayout.Y_AXIS));
        payDetailsPanel.setBackground(new Color(248, 248, 248));
        payDetailsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        payDetailsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));

        // COD details
        JPanel codPanel = new JPanel();
        codPanel.setLayout(new BoxLayout(codPanel, BoxLayout.Y_AXIS));
        codPanel.setBackground(new Color(248, 248, 248));
        JLabel codIcon = new JLabel("💵");
        codIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 30));
        codIcon.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel codText = new JLabel("Pay when your order arrives!");
        codText.setFont(new Font("Segoe UI", Font.BOLD, 14));
        codText.setForeground(green);
        codText.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel codSub = new JLabel("No advance payment needed.");
        codSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        codSub.setForeground(textGray);
        codSub.setAlignmentX(Component.LEFT_ALIGNMENT);
        codPanel.add(codIcon);
        codPanel.add(Box.createVerticalStrut(5));
        codPanel.add(codText);
        codPanel.add(Box.createVerticalStrut(3));
        codPanel.add(codSub);

        // UPI details
        JPanel upiPanel = new JPanel();
        upiPanel.setLayout(new BoxLayout(upiPanel, BoxLayout.Y_AXIS));
        upiPanel.setBackground(new Color(248, 248, 248));
        JLabel upiTitle = new JLabel("📱 Enter UPI ID");
        upiTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        upiTitle.setForeground(textDark);
        upiTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField upiField = new JTextField("e.g. bhavya@upi");
        upiField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        upiField.setForeground(textGray);
        upiField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        upiField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        upiField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (upiField.getText().equals("e.g. bhavya@upi")) {
                    upiField.setText("");
                    upiField.setForeground(textDark);
                }
            }
        });
        JLabel upiHint = new JLabel("Accepted: GPay, PhonePe, Paytm, BHIM");
        upiHint.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        upiHint.setForeground(textGray);
        upiHint.setAlignmentX(Component.LEFT_ALIGNMENT);
        upiPanel.add(upiTitle);
        upiPanel.add(Box.createVerticalStrut(8));
        upiPanel.add(upiField);
        upiPanel.add(Box.createVerticalStrut(5));
        upiPanel.add(upiHint);
        upiPanel.setVisible(false);

        // Card details
        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(new Color(248, 248, 248));
        JLabel cardTitle = new JLabel("💳 Enter Card Details");
        cardTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cardTitle.setForeground(textDark);
        cardTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        JTextField cardNumField = new JTextField("Card Number (16 digits)");
        cardNumField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cardNumField.setForeground(textGray);
        cardNumField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        cardNumField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        cardNumField.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) || cardNumField.getText().length() >= 16) e.consume();
            }
        });
        cardNumField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (cardNumField.getText().equals("Card Number (16 digits)")) {
                    cardNumField.setText("");
                    cardNumField.setForeground(textDark);
                }
            }
        });

        JPanel cardRow = new JPanel(new GridLayout(1, 2, 10, 0));
        cardRow.setBackground(new Color(248, 248, 248));
        cardRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        JTextField expiryField = new JTextField("Expiry MM/YY");
        expiryField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        expiryField.setForeground(textGray);
        expiryField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        JTextField cvvField = new JTextField("CVV");
        cvvField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cvvField.setForeground(textGray);
        cvvField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        cardRow.add(expiryField);
        cardRow.add(cvvField);
        cardPanel.add(cardTitle);
        cardPanel.add(Box.createVerticalStrut(8));
        cardPanel.add(cardNumField);
        cardPanel.add(Box.createVerticalStrut(8));
        cardPanel.add(cardRow);
        cardPanel.setVisible(false);

        payDetailsPanel.add(codPanel);
        payDetailsPanel.add(upiPanel);
        payDetailsPanel.add(cardPanel);

        cashBtn.addActionListener(e -> {
            codPanel.setVisible(true);
            upiPanel.setVisible(false);
            cardPanel.setVisible(false);
            payDetailsPanel.revalidate();
            payDetailsPanel.repaint();
        });

        upiBtn.addActionListener(e -> {
            codPanel.setVisible(false);
            upiPanel.setVisible(true);
            cardPanel.setVisible(false);
            payDetailsPanel.revalidate();
            payDetailsPanel.repaint();
        });

        cardBtn.addActionListener(e -> {
            codPanel.setVisible(false);
            upiPanel.setVisible(false);
            cardPanel.setVisible(true);
            payDetailsPanel.revalidate();
            payDetailsPanel.repaint();
        });

        paymentCard.add(cashBtn);
        paymentCard.add(Box.createVerticalStrut(8));
        paymentCard.add(upiBtn);
        paymentCard.add(Box.createVerticalStrut(8));
        paymentCard.add(cardBtn);
        paymentCard.add(Box.createVerticalStrut(12));
        paymentCard.add(payDetailsPanel);

        // ORDER SUMMARY
        JPanel summaryCard = createCard("🧾 Order Summary");
        for (String[] item : cartItems) {
            JPanel row = new JPanel(new BorderLayout());
            row.setBackground(bgWhite);
            row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
            JLabel nameL = new JLabel("• " + item[0]);
            nameL.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            nameL.setForeground(textDark);
            JLabel priceL = new JLabel("₹" + item[2]);
            priceL.setFont(new Font("Segoe UI", Font.BOLD, 13));
            priceL.setForeground(primaryRed);
            row.add(nameL, BorderLayout.WEST);
            row.add(priceL, BorderLayout.EAST);
            summaryCard.add(row);
            summaryCard.add(Box.createVerticalStrut(5));
        }

        // Delivery charge row
        JPanel deliveryRow = new JPanel(new BorderLayout());
        deliveryRow.setBackground(bgWhite);
        deliveryRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        JLabel delL = new JLabel("Delivery Charges");
        delL.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        delL.setForeground(textGray);
        JLabel delV = new JLabel("₹40");
        delV.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        delV.setForeground(textGray);
        deliveryRow.add(delL, BorderLayout.WEST);
        deliveryRow.add(delV, BorderLayout.EAST);
        summaryCard.add(deliveryRow);

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        summaryCard.add(Box.createVerticalStrut(5));
        summaryCard.add(sep);
        summaryCard.add(Box.createVerticalStrut(8));

        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setBackground(bgWhite);
        totalRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        JLabel totalL = new JLabel("Total Amount:");
        totalL.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalL.setForeground(textDark);
        JLabel totalV = new JLabel("₹" + totalAmount);
        totalV.setFont(new Font("Segoe UI", Font.BOLD, 16));
        totalV.setForeground(primaryRed);
        totalRow.add(totalL, BorderLayout.WEST);
        totalRow.add(totalV, BorderLayout.EAST);
        summaryCard.add(totalRow);

        // CONFIRM BUTTON
        JButton confirmBtn = new JButton("✓  Confirm Order  •  ₹" + totalAmount);
        confirmBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        confirmBtn.setForeground(Color.WHITE);
        confirmBtn.setBackground(primaryRed);
        confirmBtn.setBorder(BorderFactory.createEmptyBorder(16, 0, 16, 0));
        confirmBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        confirmBtn.setFocusPainted(false);
        confirmBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        confirmBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        confirmBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String address = addressField.getText().trim();
            String pincode = pincodeField.getText().trim();

            if (name.isEmpty() || name.equals("Full Name")) {
                JOptionPane.showMessageDialog(this, "Please enter your name!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (address.isEmpty() || address.equals("House No, Street, Area")) {
                JOptionPane.showMessageDialog(this, "Please enter delivery address!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (pincode.isEmpty() || pincode.equals("Pincode (6 digits)") || pincode.length() != 6) {
                JOptionPane.showMessageDialog(this, "Please enter valid 6 digit pincode!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (upiBtn.isSelected() && (upiField.getText().isEmpty() || upiField.getText().equals("e.g. bhavya@upi"))) {
                JOptionPane.showMessageDialog(this, "Please enter UPI ID!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cardBtn.isSelected() && (cardNumField.getText().isEmpty() || cardNumField.getText().equals("Card Number (16 digits)"))) {
                JOptionPane.showMessageDialog(this, "Please enter card number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String payment = cashBtn.isSelected() ? "Cash on Delivery" : upiBtn.isSelected() ? "UPI" : "Card";
            saveOrderToDatabase(name, address + ", " + cityField.getText() + " - " + pincode, payment);
            showOrderSuccess();
        });

        mainPanel.add(addressCard);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(paymentCard);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(summaryCard);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(confirmBtn);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(navbar, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    void saveOrderToDatabase(String name, String address, String payment) {
        try {
            Connection con = DBConnection.getConnection();
            StringBuilder items = new StringBuilder();
            for (String[] item : cartItems) {
                items.append(item[0]).append(", ");
            }
            String query = "INSERT INTO orders (customer_name, items, amount, address, payment, status) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, items.toString());
            ps.setInt(3, totalAmount);
            ps.setString(4, address);
            ps.setString(5, payment);
            ps.setString(6, "Confirmed");
            ps.executeUpdate();
            con.close();
        } catch (Exception ex) {
            System.out.println("DB Error: " + ex.getMessage());
        }
    }

    void showOrderSuccess() {
        JPanel successPanel = new JPanel();
        successPanel.setLayout(new BoxLayout(successPanel, BoxLayout.Y_AXIS));
        successPanel.setBackground(bgWhite);
        successPanel.setBorder(BorderFactory.createEmptyBorder(50, 40, 40, 40));

        JLabel emoji = new JLabel("🎉");
        emoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        emoji.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel title = new JLabel("Order Placed Successfully!");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(green);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Your food is being prepared 🍳");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(textGray);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel time = new JLabel("⏱ Estimated delivery: 30-45 minutes");
        time.setFont(new Font("Segoe UI", Font.BOLD, 14));
        time.setForeground(primaryRed);
        time.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel trackPanel = new JPanel(new GridLayout(1, 4, 10, 0));
        trackPanel.setBackground(bgWhite);
        trackPanel.setMaximumSize(new Dimension(550, 90));
        trackPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        trackPanel.add(createTrackStep("✅", "Order\nPlaced", true));
        trackPanel.add(createTrackStep("🍳", "Being\nPrepared", false));
        trackPanel.add(createTrackStep("🚴", "Out for\nDelivery", false));
        trackPanel.add(createTrackStep("🏠", "Delivered", false));

        JButton homeBtn = new JButton("🏠  Back to Home");
        homeBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        homeBtn.setForeground(Color.WHITE);
        homeBtn.setBackground(primaryRed);
        homeBtn.setBorder(BorderFactory.createEmptyBorder(14, 40, 14, 40));
        homeBtn.setFocusPainted(false);
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        homeBtn.addActionListener(e -> { dispose(); new HomePage(); });

        getContentPane().removeAll();
        successPanel.add(emoji);
        successPanel.add(Box.createVerticalStrut(15));
        successPanel.add(title);
        successPanel.add(Box.createVerticalStrut(10));
        successPanel.add(subtitle);
        successPanel.add(Box.createVerticalStrut(8));
        successPanel.add(time);
        successPanel.add(Box.createVerticalStrut(30));
        successPanel.add(trackPanel);
        successPanel.add(Box.createVerticalStrut(35));
        successPanel.add(homeBtn);
        getContentPane().add(successPanel);
        revalidate();
        repaint();
    }

    JPanel createTrackStep(String icon, String label, boolean active) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(bgWhite);
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel textLabel = new JLabel("<html><center>" + label + "</center></html>");
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        textLabel.setForeground(active ? primaryRed : textGray);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(iconLabel);
        panel.add(Box.createVerticalStrut(5));
        panel.add(textLabel);
        return panel;
    }

    JPanel createCard(String title) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgWhite);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(textDark);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(12));
        return card;
    }

    JTextField createField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(textGray);
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(textDark);
                }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(textGray);
                }
            }
        });
        return field;
    }

    JRadioButton createRadio(String text) {
        JRadioButton btn = new JRadioButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(textDark);
        btn.setBackground(bgWhite);
        btn.setFocusPainted(false);
        return btn;
    }
}