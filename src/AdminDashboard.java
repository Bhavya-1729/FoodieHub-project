package src;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminDashboard extends JFrame {

    Color primaryRed = new Color(226, 75, 74);
    Color bgWhite = new Color(255, 255, 255);
    Color bgGray = new Color(245, 245, 245);
    Color textDark = new Color(44, 44, 42);
    Color textGray = new Color(136, 135, 128);
    Color green = new Color(40, 167, 69);
    Color blue = new Color(23, 162, 184);
    Color orange = new Color(255, 153, 0);

    public AdminDashboard() {
        setTitle("FoodieHub - Admin Dashboard");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgGray);

        // NAVBAR
        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(primaryRed);
        navbar.setPreferredSize(new Dimension(1200, 60));
        navbar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        JLabel logo = new JLabel("🍽️ FoodieHub - Admin Panel");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(Color.WHITE);
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        logoutBtn.setForeground(primaryRed);
        logoutBtn.setBackground(Color.WHITE);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> { dispose(); new LoginPage(); });
        navbar.add(logo, BorderLayout.WEST);
        navbar.add(logoutBtn, BorderLayout.EAST);

        // STATS
        int[] stats = getStats();
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setBackground(bgGray);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        statsPanel.add(createStatCard("👥 Total Users", String.valueOf(stats[0]), blue));
        statsPanel.add(createStatCard("📦 Total Orders", String.valueOf(stats[1]), green));
        statsPanel.add(createStatCard("🍽️ Menu Items", "65", orange));
        statsPanel.add(createStatCard("💰 Revenue", "₹" + stats[2], primaryRed));

        // TABS
        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabs.setBackground(bgWhite);
        tabs.addTab("📦 Orders", createOrdersPanel());
        tabs.addTab("👥 Users", createUsersPanel());
        tabs.addTab("🍽️ Menu Items", createMenuPanel());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(bgGray);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        centerPanel.add(tabs);

        JPanel topSection = new JPanel(new BorderLayout());
        topSection.add(navbar, BorderLayout.NORTH);
        topSection.add(statsPanel, BorderLayout.CENTER);

        add(topSection, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    int[] getStats() {
        int users = 0, orders = 0, revenue = 0;
        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs1 = con.createStatement().executeQuery("SELECT COUNT(*) FROM users");
            if (rs1.next()) users = rs1.getInt(1);
            ResultSet rs2 = con.createStatement().executeQuery("SELECT COUNT(*), SUM(amount) FROM orders");
            if (rs2.next()) {
                orders = rs2.getInt(1);
                revenue = rs2.getInt(2);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Stats error: " + e.getMessage());
        }
        return new int[]{users, orders, revenue};
    }

    JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgWhite);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        titleLabel.setForeground(textGray);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(color);
        valueLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(valueLabel);
        return card;
    }

    JPanel createOrdersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgWhite);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] cols = {"Order ID", "Customer", "Items", "Amount", "Payment", "Status", "Date"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery(
                "SELECT id, customer_name, items, amount, payment, status, order_date FROM orders ORDER BY id DESC"
            );
            while (rs.next()) {
                String items = rs.getString("items");
                if (items != null && items.length() > 30) items = items.substring(0, 30) + "...";
                String date = rs.getString("order_date");
                if (date != null && date.length() > 10) date = date.substring(0, 10);
                model.addRow(new Object[]{
                    "#" + rs.getInt("id"),
                    rs.getString("customer_name"),
                    items,
                    "₹" + rs.getInt("amount"),
                    rs.getString("payment"),
                    rs.getString("status"),
                    date
                });
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Orders error: " + e.getMessage());
        }

        JTable table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (col == 5) {
                    String status = (String) getValueAt(row, col);
                    if (status != null) {
                        if (status.equals("Delivered")) c.setForeground(green);
                        else if (status.equals("Preparing")) c.setForeground(orange);
                        else if (status.equals("Out for Delivery")) c.setForeground(blue);
                        else c.setForeground(primaryRed);
                    }
                } else {
                    c.setForeground(textDark);
                }
                c.setBackground(row % 2 == 0 ? bgWhite : bgGray);
                return c;
            }
        };
        styleTable(table);

        // Refresh button
        JButton refreshBtn = new JButton("🔄 Refresh");
        refreshBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        refreshBtn.setForeground(Color.WHITE);
        refreshBtn.setBackground(primaryRed);
        refreshBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        refreshBtn.setFocusPainted(false);
        refreshBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        refreshBtn.addActionListener(e -> {
            dispose();
            new AdminDashboard();
        });

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topBar.setBackground(bgWhite);
        topBar.add(refreshBtn);

        panel.add(topBar, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgWhite);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] cols = {"ID", "Name", "Email", "Phone", "Role"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };

        try {
            Connection con = DBConnection.getConnection();
            ResultSet rs = con.createStatement().executeQuery(
                "SELECT id, name, email, phone, role FROM users ORDER BY id DESC"
            );
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("role")
                });
            }
            con.close();
        } catch (Exception e) {
            System.out.println("Users error: " + e.getMessage());
        }

        JTable table = new JTable(model) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (col == 4) {
                    String role = (String) getValueAt(row, col);
                    c.setForeground(role != null && role.equals("admin") ? primaryRed : green);
                } else {
                    c.setForeground(textDark);
                }
                c.setBackground(row % 2 == 0 ? bgWhite : bgGray);
                return c;
            }
        };
        styleTable(table);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    JPanel createMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(bgWhite);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        String[] cols = {"#", "Item Name", "Category", "Price", "Rating", "Status"};
        Object[][] data = {
            {"1", "Butter Chicken", "Main Course", "₹349", "4.8 ⭐", "Available"},
            {"2", "Chicken Biryani", "Biryani", "₹399", "4.9 ⭐", "Available"},
            {"3", "Margherita Pizza", "Pizza", "₹349", "4.5 ⭐", "Available"},
            {"4", "Cheese Burger", "Burgers", "₹269", "4.6 ⭐", "Available"},
            {"5", "Gulab Jamun", "Desserts", "₹129", "4.8 ⭐", "Available"},
            {"6", "Mango Lassi", "Drinks", "₹99", "4.6 ⭐", "Available"},
            {"7", "White Sauce Pasta", "Main Course", "₹249", "4.9 ⭐", "Available"},
            {"8", "Vanilla Ice Cream", "Ice Cream", "₹99", "4.5 ⭐", "Available"},
            {"9", "Cheese Lava Pizza", "Pizza", "₹499", "4.9 ⭐", "Available"},
            {"10", "Paneer Tikka", "Starters", "₹249", "4.5 ⭐", "Available"},
        };

        JTable table = new JTable(data, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (col == 5) c.setForeground(green);
                else c.setForeground(textDark);
                c.setBackground(row % 2 == 0 ? bgWhite : bgGray);
                return c;
            }
        };
        styleTable(table);

        JButton addItemBtn = new JButton("+ Add New Item");
        addItemBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addItemBtn.setForeground(Color.WHITE);
        addItemBtn.setBackground(primaryRed);
        addItemBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        addItemBtn.setFocusPainted(false);
        addItemBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addItemBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Add item feature coming soon!",
                "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topBar.setBackground(bgWhite);
        topBar.add(addItemBtn);

        panel.add(topBar, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    void styleTable(JTable table) {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        table.getTableHeader().setBackground(primaryRed);
        table.getTableHeader().setForeground(Color.WHITE);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setSelectionBackground(new Color(252, 235, 235));
        table.setSelectionForeground(textDark);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard());
    }
}