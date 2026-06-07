package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {

    Color primaryRed = new Color(226, 75, 74);
    Color bgWhite = new Color(255, 255, 255);
    Color textDark = new Color(44, 44, 42);
    Color textGray = new Color(136, 135, 128);

    boolean isLogin = true;
    JPanel formPanel;
    JLabel titleLabel, subtitleLabel;
    JButton toggleBtn, actionBtn;
    JTextField nameField, emailField, phoneField;
    JPasswordField passwordField;
    JLabel nameLabel, phoneLabel;

    public LoginPage() {
        setTitle("FoodieHub - Login");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(primaryRed);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(255, 255, 255, 20));
                g2.fillOval(-50, -50, 250, 250);
                g2.setColor(new Color(255, 255, 255, 15));
                g2.fillOval(getWidth()-150, getHeight()-150, 250, 250);
            }
        };
        leftPanel.setLayout(new GridBagLayout());

        JPanel leftContent = new JPanel();
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));
        leftContent.setOpaque(false);

        JLabel foodEmoji = new JLabel("🍽️");
        foodEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        foodEmoji.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel brandName = new JLabel("FoodieHub");
        brandName.setFont(new Font("Segoe UI", Font.BOLD, 36));
        brandName.setForeground(Color.WHITE);
        brandName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tagline = new JLabel("Delicious food, delivered fast");
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tagline.setForeground(new Color(255, 255, 255, 200));
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftContent.add(foodEmoji);
        leftContent.add(Box.createVerticalStrut(10));
        leftContent.add(brandName);
        leftContent.add(Box.createVerticalStrut(8));
        leftContent.add(tagline);
        leftContent.add(Box.createVerticalStrut(30));
        leftContent.add(createFeatureItem("🚀", "Fast Delivery"));
        leftContent.add(Box.createVerticalStrut(12));
        leftContent.add(createFeatureItem("⭐", "Top Rated"));
        leftContent.add(Box.createVerticalStrut(12));
        leftContent.add(createFeatureItem("💳", "Easy Payment"));
        leftPanel.add(leftContent);

        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(bgWhite);
        rightPanel.setLayout(new GridBagLayout());

        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(bgWhite);
        formPanel.setPreferredSize(new Dimension(350, 450));

        titleLabel = new JLabel("Welcome Back!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(textDark);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        subtitleLabel = new JLabel("Login to order your favourite food");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(textGray);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        nameLabel.setForeground(textDark);
        nameField = createTextField("Enter your full name");

        JLabel emailLabel = new JLabel("Email Address");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        emailLabel.setForeground(textDark);
        emailField = createTextField("Enter your email");

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordLabel.setForeground(textDark);
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        phoneLabel = new JLabel("Phone Number");
        phoneLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        phoneLabel.setForeground(textDark);
        phoneField = createTextField("Enter your phone number");

        actionBtn = new JButton("Login");
        actionBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        actionBtn.setForeground(Color.WHITE);
        actionBtn.setBackground(primaryRed);
        actionBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        actionBtn.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        actionBtn.setFocusPainted(false);
        actionBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        actionBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel togglePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        togglePanel.setBackground(bgWhite);
        JLabel toggleText = new JLabel("Don't have an account?");
        toggleText.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        toggleText.setForeground(textGray);
        toggleBtn = new JButton("Sign Up");
        toggleBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        toggleBtn.setForeground(primaryRed);
        toggleBtn.setBackground(bgWhite);
        toggleBtn.setBorder(null);
        toggleBtn.setFocusPainted(false);
        toggleBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        togglePanel.add(toggleText);
        togglePanel.add(toggleBtn);

        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(6));
        formPanel.add(subtitleLabel);
        formPanel.add(Box.createVerticalStrut(25));
        formPanel.add(nameLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(nameField);
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(emailLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(emailField);
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(passwordLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(passwordField);
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(phoneLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(phoneField);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(actionBtn);
        formPanel.add(Box.createVerticalStrut(12));
        formPanel.add(togglePanel);

        rightPanel.add(formPanel);
        nameLabel.setVisible(false);
        nameField.setVisible(false);
        phoneLabel.setVisible(false);
        phoneField.setVisible(false);

        toggleBtn.addActionListener(e -> {
            isLogin = !isLogin;
            if (isLogin) {
                titleLabel.setText("Welcome Back!");
                subtitleLabel.setText("Login to order your favourite food");
                actionBtn.setText("Login");
                toggleText.setText("Don't have an account?");
                toggleBtn.setText("Sign Up");
                nameLabel.setVisible(false); nameField.setVisible(false);
                phoneLabel.setVisible(false); phoneField.setVisible(false);
            } else {
                titleLabel.setText("Create Account");
                subtitleLabel.setText("Sign up to get started with FoodieHub");
                actionBtn.setText("Sign Up");
                toggleText.setText("Already have an account?");
                toggleBtn.setText("Login");
                nameLabel.setVisible(true); nameField.setVisible(true);
                phoneLabel.setVisible(true); phoneField.setVisible(true);
            }
            formPanel.revalidate();
            formPanel.repaint();
        });

        actionBtn.addActionListener(e -> {
            if (isLogin) handleLogin();
            else handleSignup();
        });

        add(leftPanel);
        add(rightPanel);
        setVisible(true);
    }

    void handleLogin() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter email and password!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE email=? AND password=?");
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                JOptionPane.showMessageDialog(this, "Welcome back, " + name + "! 🎉", "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                new HomePage();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    void handleSignup() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String phone = phoneField.getText().trim();
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (name, email, password, phone, role) VALUES (?,?,?,?,?)");
            ps.setString(1, name); ps.setString(2, email);
            ps.setString(3, password); ps.setString(4, phone);
            ps.setString(5, "customer");
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Account created! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
            toggleBtn.doClick();
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    JTextField createTextField(String placeholder) {
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
                if (field.getText().equals(placeholder)) { field.setText(""); field.setForeground(textDark); }
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) { field.setText(placeholder); field.setForeground(textGray); }
            }
        });
        return field;
    }

    JPanel createFeatureItem(String emoji, String text) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel emojiLabel = new JLabel(emoji);
        emojiLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 20));
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        textLabel.setForeground(Color.WHITE);
        panel.add(emojiLabel);
        panel.add(textLabel);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage());
    }
}