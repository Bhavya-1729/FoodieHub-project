package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class HomePage extends JFrame {

    Color primaryRed = new Color(226, 75, 74);
    Color bgWhite = new Color(255, 255, 255);
    Color bgGray = new Color(245, 245, 245);
    Color textGray = new Color(136, 135, 128);

    String[] imageUrls = {
        "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=1200",
        "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=1200",
        "https://images.unsplash.com/photo-1546069901-ba9599a7e63c?w=1200",
        "https://images.unsplash.com/photo-1555939594-58d7cb561ad1?w=1200",
        "https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=1200"
    };

    String[] quotes = {
        "Taste the Difference 🍔",
        "Fresh Ingredients, Bold Flavours 🌿",
        "Every Bite Tells a Story 🍕",
        "Food is Love Made Visible 🍛",
        "Delivered Hot, Served Fresh 🔥"
    };

    BufferedImage[] images = new BufferedImage[imageUrls.length];
    int currentSlide = 0;
    JPanel heroPanel;
    JLabel quoteLabel;
    Timer slideTimer;

    public HomePage() {
        setTitle("FoodieHub - Food Ordering");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(primaryRed);
        navbar.setPreferredSize(new Dimension(1100, 60));
        navbar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        JLabel logo = new JLabel("🍽️ FoodieHub");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(Color.WHITE);

        JPanel navButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navButtons.setBackground(primaryRed);

        JButton menuBtn = createNavButton("Menu");
        JButton aboutBtn = createNavButton("About");
        JButton loginBtn = createWhiteButton("Login");

        menuBtn.addActionListener(e -> { dispose(); new MenuPage(); });
        aboutBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
            "🍽️ FoodieHub\n\nDelicious food delivered to your door!\nFast • Fresh • Affordable\n\nVersion 1.0",
            "About FoodieHub", JOptionPane.INFORMATION_MESSAGE));
        loginBtn.addActionListener(e -> { dispose(); new LoginPage(); });

        navButtons.add(menuBtn);
        navButtons.add(aboutBtn);
        navButtons.add(loginBtn);
        navbar.add(logo, BorderLayout.WEST);
        navbar.add(navButtons, BorderLayout.EAST);

        heroPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                if (images[currentSlide] != null) {
                    g2.drawImage(images[currentSlide], 0, 0, getWidth(), getHeight(), null);
                } else {
                    g2.setColor(new Color(44, 16, 16));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
                g2.setColor(new Color(0, 0, 0, 150));
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        heroPanel.setPreferredSize(new Dimension(1100, 400));
        heroPanel.setLayout(new GridBagLayout());

        JPanel heroContent = new JPanel();
        heroContent.setLayout(new BoxLayout(heroContent, BoxLayout.Y_AXIS));
        heroContent.setOpaque(false);

        JLabel badge = new JLabel("🎉  Free delivery on your first order!");
        badge.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        badge.setForeground(new Color(240, 149, 149));
        badge.setAlignmentX(Component.CENTER_ALIGNMENT);

        quoteLabel = new JLabel(quotes[0]);
        quoteLabel.setFont(new Font("Segoe UI", Font.BOLD, 40));
        quoteLabel.setForeground(Color.WHITE);
        quoteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Order from your favourite restaurants. Fast, fresh & always on time.");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitle.setForeground(new Color(255, 255, 255, 200));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel searchBar = new JPanel(new BorderLayout(8, 0));
        searchBar.setBackground(Color.WHITE);
        searchBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1, true),
            BorderFactory.createEmptyBorder(8, 15, 8, 8)
        ));
        searchBar.setMaximumSize(new Dimension(480, 50));
        searchBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField searchField = new JTextField("Enter your delivery address...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setForeground(textGray);
        searchField.setBorder(null);
        searchField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Enter your delivery address...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
        });

        JButton findBtn = new JButton("Find Food");
        findBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        findBtn.setForeground(Color.WHITE);
        findBtn.setBackground(primaryRed);
        findBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        findBtn.setFocusPainted(false);
        findBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        findBtn.addActionListener(e -> { dispose(); new MenuPage(); });

        searchBar.add(searchField, BorderLayout.CENTER);
        searchBar.add(findBtn, BorderLayout.EAST);

        heroContent.add(badge);
        heroContent.add(Box.createVerticalStrut(15));
        heroContent.add(quoteLabel);
        heroContent.add(Box.createVerticalStrut(12));
        heroContent.add(subtitle);
        heroContent.add(Box.createVerticalStrut(25));
        heroContent.add(searchBar);
        heroPanel.add(heroContent);

        JPanel categories = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 12));
        categories.setBackground(bgGray);
        categories.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        String[] cats = {"🔥 Popular", "🥗 Veg", "🍕 Pizza", "🍛 Biryani", "🍦 Desserts", "🍔 Burgers", "🥤 Drinks"};
        for (String cat : cats) {
            JButton pill = new JButton(cat);
            pill.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            pill.setBackground(Color.WHITE);
            pill.setForeground(textGray);
            pill.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));
            pill.setFocusPainted(false);
            pill.setCursor(new Cursor(Cursor.HAND_CURSOR));
            pill.addActionListener(e -> { dispose(); new MenuPage(); });
            categories.add(pill);
        }

        JPanel statsBar = new JPanel(new GridLayout(1, 3));
        statsBar.setBackground(bgWhite);
        statsBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(15, 0, 15, 0)
        ));
        statsBar.add(createStatItem("500+", "Restaurants"));
        statsBar.add(createStatItem("30 min", "Avg Delivery"));
        statsBar.add(createStatItem("4.8 ★", "Avg Rating"));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(categories, BorderLayout.NORTH);
        bottomPanel.add(statsBar, BorderLayout.SOUTH);

        add(navbar, BorderLayout.NORTH);
        add(heroPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        setVisible(true);
        loadImagesInBackground();

        slideTimer = new Timer(3000, e -> {
            currentSlide = (currentSlide + 1) % imageUrls.length;
            quoteLabel.setText(quotes[currentSlide]);
            heroPanel.repaint();
        });
        slideTimer.start();
    }

    void loadImagesInBackground() {
        new Thread(() -> {
            for (int i = 0; i < imageUrls.length; i++) {
                try {
                    URL url = new URL(imageUrls[i]);
                    images[i] = ImageIO.read(url);
                    heroPanel.repaint();
                } catch (Exception e) {
                    System.out.println("Could not load image " + i);
                }
            }
        }).start();
    }

    JButton createNavButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(primaryRed);
        btn.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 1, true));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(80, 32));
        return btn;
    }

    JButton createWhiteButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(primaryRed);
        btn.setBackground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(80, 32));
        return btn;
    }

    JPanel createStatItem(String value, String label) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        JLabel valLabel = new JLabel(value);
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valLabel.setForeground(primaryRed);
        valLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblLabel.setForeground(textGray);
        lblLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(valLabel);
        panel.add(Box.createVerticalStrut(4));
        panel.add(lblLabel);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomePage());
    }
}