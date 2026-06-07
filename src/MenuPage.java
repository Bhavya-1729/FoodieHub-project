package src;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.io.File;

public class MenuPage extends JFrame {

    Color primaryRed = new Color(226, 75, 74);
    Color bgWhite = new Color(255, 255, 255);
    Color bgGray = new Color(245, 245, 245);
    Color textDark = new Color(44, 44, 42);
    Color textGray = new Color(136, 135, 128);

    String[] categories = {"All", "Starters", "Main Course", "Biryani", "Pizza", "Burgers", "Desserts", "Ice Cream", "Drinks"};

    // name, category, price, rating, image, ingredients, description, speciality
    String[][] menuItems = {
        {"Paneer Tikka", "Starters", "249", "4.5", "https://images.unsplash.com/photo-1567188040759-fb8a883dc6d8?w=400", "Paneer, Yogurt, Spices, Bell Peppers", "Grilled cottage cheese marinated in spices", "⭐ Chef's Special | 🌶 Medium Spicy"},
        {"Veg Spring Rolls", "Starters", "149", "4.3", "https://images.unsplash.com/photo-1548943487-a2e4e43b4853?w=400", "Cabbage, Carrots, Noodles, Flour", "Crispy rolls stuffed with fresh vegetables", "🌱 100% Veg | 🔥 Crispy"},
        {"Chicken Wings", "Starters", "299", "4.6", "https://images.unsplash.com/photo-1567620832903-9fc6debc209f?w=400", "Chicken Wings, BBQ Sauce, Spices", "Crispy fried chicken wings with BBQ sauce", "🔥 Hot & Spicy | 🍗 Non-Veg"},
        {"Veg Manchurian", "Starters", "179", "4.4", "local:veg_manchurian.jpg.jpg", "Cabbage, Carrots, Flour, Soy Sauce", "Indo-Chinese fried balls in spicy sauce", "🌱 Veg | 🌶 Spicy"},
        {"Aloo Tikki", "Starters", "99", "4.2", "local:aloo_tikki.jpg.jpg", "Potato, Peas, Spices, Chutney", "Crispy potato patties with mint chutney", "🌱 Veg | ❤️ Street Food"},
        {"Chicken Seekh Kebab", "Starters", "329", "4.7", "https://images.unsplash.com/photo-1599487488170-d11ec9c172f0?w=400", "Chicken Mince, Onion, Spices, Herbs", "Juicy kebabs grilled on skewers", "🍗 Non-Veg | 🔥 Tandoor Grilled"},
        {"Samosa", "Starters", "49", "4.5", "https://images.unsplash.com/photo-1601050690597-df0568f70950?w=400", "Potato, Peas, Flour, Spices", "Crispy pastry filled with spiced potatoes", "🌱 Veg | ❤️ All Time Favourite"},
        
        // Main Course
        {"Butter Chicken", "Main Course", "349", "4.8", "https://images.unsplash.com/photo-1603894584373-5ac82b2ae398?w=400", "Chicken, Tomato, Butter, Cream, Spices", "Tender chicken in rich creamy tomato gravy", "🍗 Non-Veg | ⭐ Best Seller"},
        {"Palak Paneer", "Main Course", "279", "4.5", "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=400", "Spinach, Paneer, Onion, Spices", "Fresh spinach curry with cottage cheese", "🌱 Veg | 💚 Healthy"},
        {"Dal Makhani", "Main Course", "249", "4.7", "https://images.unsplash.com/photo-1546833999-b9f581a1996d?w=400", "Black Lentils, Butter, Cream, Spices", "Slow cooked lentils in buttery gravy", "🌱 Veg | ⭐ Must Try"},
        {"White Sauce Pasta", "Main Course", "249", "4.9", "local:white_sauce_pasta.jpg.jpg", "Pasta, Milk, Cheese, Garlic, Herbs", "Creamy pasta in rich white sauce", "🌱 Veg | 🧀 Cheesy"},
        {"Paneer Butter Masala", "Main Course", "299", "4.6", "https://images.unsplash.com/photo-1631452180519-c014fe946bc7?w=400", "Paneer, Tomato, Butter, Cashew, Cream", "Rich and creamy paneer curry", "🌱 Veg | ⭐ Chef's Special"},
        {"Chicken Curry", "Main Course", "329", "4.7", "https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=400", "Chicken, Onion, Tomato, Spices", "Home style chicken curry with spices", "🍗 Non-Veg | ❤️ Classic"},
        {"Chole Bhature", "Main Course", "199", "4.6", "local:Chole_Bhature.jpg.jpg", "Chickpeas, Flour, Spices, Yogurt", "Spicy chickpeas with fluffy fried bread", "🌱 Veg | ❤️ Street Food"},
        {"Veg Noodles", "Main Course", "199", "4.3", "local:veg_noodles.jpg.jpg", "Noodles, Veggies, Soy Sauce, Vinegar", "Stir fried noodles with fresh vegetables", "🌱 Veg | 🍜 Indo-Chinese"},
        // Biryani
        {"Chicken Biryani", "Biryani", "399", "4.9", "local:chicken_biryani.jpg.jpg", "Basmati Rice, Chicken, Saffron, Spices", "Aromatic basmati rice with tender chicken", "🍗 Non-Veg | ⭐ Best Seller"},
        {"Veg Biryani", "Biryani", "299", "4.6", "https://images.unsplash.com/photo-1596797038530-2c107229654b?w=400", "Basmati Rice, Mixed Veggies, Saffron", "Fragrant rice cooked with fresh vegetables", "🌱 Veg | 🌿 Fresh"},
        {"Mutton Biryani", "Biryani", "449", "4.8", "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=400", "Basmati Rice, Mutton, Saffron, Spices", "Slow cooked mutton with aromatic rice", "🍖 Non-Veg | 👑 Premium"},
        {"Egg Biryani", "Biryani", "349", "4.5", "local:Egg_Biryani.jpg.png", "Basmati Rice, Eggs, Spices, Onion", "Flavourful rice with boiled eggs", "🥚 Egg | 💰 Value"},
        {"Prawn Biryani", "Biryani", "499", "4.7", "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=400", "Basmati Rice, Prawns, Coconut, Spices", "Coastal style prawn biryani", "🦐 Seafood | 👑 Premium"},
        {"Paneer Biryani", "Biryani", "349", "4.6", "https://images.unsplash.com/photo-1596797038530-2c107229654b?w=400", "Basmati Rice, Paneer, Saffron, Spices", "Rich paneer biryani with saffron", "🌱 Veg | ⭐ Popular"},
        {"Hyderabadi Biryani", "Biryani", "429", "4.9", "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=400", "Basmati Rice, Chicken, Fried Onion, Saffron", "Authentic Hyderabadi dum biryani", "🍗 Non-Veg | 👑 Royal"},
        {"Dum Biryani", "Biryani", "399", "4.8", "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=400", "Basmati Rice, Chicken, Herbs, Spices", "Slow cooked dum style biryani", "🍗 Non-Veg | 🔥 Slow Cooked"},
        // Pizza
        {"Margherita Pizza", "Pizza", "349", "4.5", "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=400", "Flour, Tomato Sauce, Mozzarella, Basil", "Classic Italian pizza with fresh basil", "🌱 Veg | 🍕 Classic"},
        {"Paneer Pizza", "Pizza", "399", "4.6", "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400", "Flour, Paneer, Bell Peppers, Cheese", "Indian style paneer pizza", "🌱 Veg | 🇮🇳 Desi Style"},
        {"Chicken Pizza", "Pizza", "449", "4.7", "https://images.unsplash.com/photo-1628840042765-356cda07504e?w=400", "Flour, Chicken, Cheese, Peppers", "Loaded chicken pizza with extra cheese", "🍗 Non-Veg | 🧀 Extra Cheese"},
        {"Cheese Lava Pizza", "Pizza", "499", "4.9", "local:cheese_lava_pizza.jpg.jpeg", "Flour, Triple Cheese, Sauce, Herbs", "Pizza with molten cheese lava crust", "🌱 Veg | 🧀 Cheese Lover"},
        {"BBQ Chicken Pizza", "Pizza", "479", "4.8", "https://images.unsplash.com/photo-1534308983496-4fabb1a015ee?w=400", "Flour, Chicken, BBQ Sauce, Onion", "Smoky BBQ chicken pizza", "🍗 Non-Veg | 🔥 Smoky BBQ"},
        {"Mushroom Pizza", "Pizza", "379", "4.5", "https://images.unsplash.com/photo-1565299624946-b28f40a0ae38?w=400", "Flour, Mushrooms, Cheese, Herbs", "Fresh mushroom pizza with herbs", "🌱 Veg | 🍄 Earthy"},
        {"Double Cheese Pizza", "Pizza", "429", "4.7", "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?w=400", "Flour, Double Mozzarella, Sauce", "Extra cheesy double mozzarella pizza", "🌱 Veg | 🧀 Double Cheese"},
        {"Pepperoni Pizza", "Pizza", "459", "4.8", "https://images.unsplash.com/photo-1628840042765-356cda07504e?w=400", "Flour, Pepperoni, Cheese, Sauce", "Classic American pepperoni pizza", "🍗 Non-Veg | 🇺🇸 American"},
        // Burgers
        {"Veg Burger", "Burgers", "199", "4.3", "https://images.unsplash.com/photo-1550547660-d9450f859349?w=400", "Bun, Veggie Patty, Lettuce, Tomato", "Fresh veg burger with crispy patty", "🌱 Veg | 💰 Value"},
        {"Chicken Burger", "Burgers", "249", "4.6", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400", "Bun, Chicken Patty, Cheese, Lettuce", "Juicy chicken burger with cheese", "🍗 Non-Veg | ⭐ Popular"},
        {"Double Patty Burger", "Burgers", "299", "4.7", "https://images.unsplash.com/photo-1553979459-d2229ba7433b?w=400", "Bun, Double Patty, Cheese, Sauce", "Double the patty double the fun", "🍗 Non-Veg | 💪 Extra Large"},
        {"Cheese Burger", "Burgers", "269", "4.6", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400", "Bun, Patty, Double Cheese, Lettuce", "Classic cheeseburger with extra cheese", "🍗 Non-Veg | 🧀 Cheesy"},
        {"Spicy Chicken Burger", "Burgers", "279", "4.8", "https://images.unsplash.com/photo-1553979459-d2229ba7433b?w=400", "Bun, Spicy Chicken, Jalapeno, Sauce", "Hot and spicy chicken burger", "🍗 Non-Veg | 🌶 Extra Spicy"},
        {"Mushroom Swiss Burger", "Burgers", "319", "4.7", "https://images.unsplash.com/photo-1550547660-d9450f859349?w=400", "Bun, Patty, Mushroom, Swiss Cheese", "Gourmet mushroom and swiss burger", "🍗 Non-Veg | 👑 Gourmet"},
        {"BBQ Burger", "Burgers", "289", "4.5", "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=400", "Bun, Patty, BBQ Sauce, Onion Rings", "Smoky BBQ burger with onion rings", "🍗 Non-Veg | 🔥 Smoky"},
        {"Paneer Burger", "Burgers", "229", "4.4", "https://images.unsplash.com/photo-1550547660-d9450f859349?w=400", "Bun, Paneer Patty, Chutney, Lettuce", "Indian style paneer burger", "🌱 Veg | 🇮🇳 Desi"},
        // Desserts
        {"Chocolate Lava Cake", "Desserts", "199", "4.9", "https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=400", "Chocolate, Flour, Butter, Eggs, Sugar", "Warm chocolate cake with molten center", "🍫 Chocolate | 🔥 Served Warm"},
        {"Cheesecake", "Desserts", "279", "4.8", "https://images.unsplash.com/photo-1533134242443-d4fd215305ad?w=400", "Cream Cheese, Sugar, Biscuit, Berries", "Classic NY style creamy cheesecake", "🌱 Veg | 👑 Premium"},
        {"Tiramisu", "Desserts", "249", "4.9", "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9?w=400", "Mascarpone, Coffee, Ladyfinger, Cocoa", "Italian coffee flavoured dessert", "🌱 Veg | 🇮🇹 Italian"},
        {"Kheer", "Desserts", "149", "4.6", "https://images.unsplash.com/photo-1588166524941-3bf61a9c41db?w=400", "Milk, Rice, Sugar, Cardamom, Nuts", "Traditional Indian rice pudding", "🌱 Veg | 🇮🇳 Traditional"},
        {"Gajar Halwa", "Desserts", "159", "4.8", "local:gajar_halwa.jpg", "Carrots, Milk, Sugar, Ghee, Nuts", "Classic carrot halwa with dry fruits", "🌱 Veg | ❤️ Traditional"},
        {"Jalebi", "Desserts", "99", "4.5", "local:jalebi.jpg.jpg", "Flour, Sugar Syrup, Saffron, Ghee", "Crispy spirals soaked in sugar syrup", "🌱 Veg | 🍯 Sweet"},
        {"Brownie with Ice Cream", "Desserts", "229", "4.9", "https://images.unsplash.com/photo-1564355808539-22fda35bed7e?w=400", "Chocolate, Flour, Butter, Vanilla Ice Cream", "Warm brownie with cold vanilla ice cream", "🌱 Veg | 🔥❄️ Hot & Cold"},
        
        // Ice Cream
        {"Vanilla Ice Cream", "Ice Cream", "99", "4.5", "https://images.unsplash.com/photo-1570197788417-0e82375c9371?w=400", "Milk, Cream, Vanilla, Sugar", "Classic creamy vanilla ice cream", "🌱 Veg | ❄️ Classic"},
        {"Chocolate Ice Cream", "Ice Cream", "109", "4.7", "https://images.unsplash.com/photo-1563805042-7684c019e1cb?w=400", "Milk, Cream, Dark Chocolate, Sugar", "Rich dark chocolate ice cream", "🌱 Veg | 🍫 Chocolate"},
        {"Butterscotch Ice Cream", "Ice Cream", "119", "4.7", "local:butterscotch_ice_cream.jpg.jpg", "Milk, Cream, Butterscotch, Caramel", "Sweet butterscotch with caramel swirl", "🌱 Veg | 🍯 Caramel"},
        {"Mango Ice Cream", "Ice Cream", "119", "4.8", "local:mango_ice_cream.jpg.jpg", "Milk, Cream, Alphonso Mango, Sugar", "Fresh Alphonso mango ice cream", "🌱 Veg | 🥭 Seasonal"},
        {"Pista Ice Cream", "Ice Cream", "129", "4.6", "local:pista_icecream.jpg.jpg", "Milk, Cream, Pistachio, Sugar, Cardamom", "Premium pistachio ice cream", "🌱 Veg | 💚 Premium"},
        {"Oreo Ice Cream", "Ice Cream", "149", "4.9", "local:oreo_ice_cream.jpg.jpg", "Milk, Cream, Oreo Cookies, Sugar", "Cookies and cream ice cream", "🌱 Veg | 🍪 Cookie Lover"},
        
        {"Choco Bar", "Ice Cream", "79", "4.5", "https://images.unsplash.com/photo-1497034825429-c343d7c6a68f?w=400", "Milk, Dark Chocolate, Almonds", "Chocolate coated ice cream bar", "🌱 Veg | 🍫 Chocolate"},
        // Drinks
        {"Cold Coffee", "Drinks", "129", "4.5", "https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=400", "Coffee, Milk, Sugar, Ice Cream", "Chilled coffee with creamy ice cream", "☕ Coffee | ❄️ Chilled"},
        {"Fresh Lime Soda", "Drinks", "79", "4.4", "https://images.unsplash.com/photo-1556679343-c7306c1976bc?w=400", "Lime, Soda, Sugar, Mint, Salt", "Refreshing lime soda with mint", "🌱 Veg | 🍋 Refreshing"},
        {"Pepsi", "Drinks", "59", "4.3", "local:pepsi.jpg.jpeg", "Carbonated Water, Sugar, Caramel", "Chilled Pepsi soft drink", "🌱 Veg | ❄️ Chilled"},
        
        
        {"Orange Juice", "Drinks", "119", "4.6", "https://images.unsplash.com/photo-1621506289937-a8e4df240d0b?w=400", "Fresh Oranges, Sugar, Ice", "Freshly squeezed orange juice", "🌱 Veg | 🍊 Fresh"},
        {"Watermelon Juice", "Drinks", "109", "4.5", "https://images.unsplash.com/photo-1527661591475-527312dd65f5?w=400", "Watermelon, Mint, Sugar, Lime", "Fresh watermelon juice with mint", "🌱 Veg | 💧 Hydrating"},
        {"Virgin Mojito", "Drinks", "139", "4.7", "https://images.unsplash.com/photo-1556679343-c7306c1976bc?w=400", "Mint, Lime, Sugar, Soda, Ice", "Refreshing mint lime mojito", "🌱 Veg | 🌿 Refreshing"},
        {"Blue Lagoon", "Drinks", "149", "4.8", "https://images.unsplash.com/photo-1544145945-f90425340c7e?w=400", "Blue Curacao, Lemonade, Ice, Mint", "Beautiful blue coloured mocktail", "🌱 Veg | 💙 Mocktail"},
        {"Lassi", "Drinks", "99", "4.6", "https://images.unsplash.com/photo-1571506165871-ee72a35bc9d4?w=400", "Yogurt, Sugar, Cardamom, Rose Water", "Traditional sweet yogurt drink", "🌱 Veg | 🇮🇳 Traditional"},
    };

    ArrayList<String[]> cartItems = new ArrayList<>();
    JPanel foodGrid;
    JButton cartBtn;
    String selectedCategory = "All";
    BufferedImage[] foodImages;

    public MenuPage() {
        setTitle("FoodieHub - Menu");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(bgGray);

        foodImages = new BufferedImage[menuItems.length];
        loadAllImages();

        JPanel navbar = new JPanel(new BorderLayout());
        navbar.setBackground(primaryRed);
        navbar.setPreferredSize(new Dimension(1200, 60));
        navbar.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        JLabel logo = new JLabel("🍽️ FoodieHub");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        logo.setForeground(Color.WHITE);

        JPanel navRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        navRight.setBackground(primaryRed);

        JTextField searchField = new JTextField("🔍 Search food...");
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setForeground(textGray);
        searchField.setPreferredSize(new Dimension(220, 34));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1, true),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { filterFood(searchField.getText()); }
            public void removeUpdate(DocumentEvent e) { filterFood(searchField.getText()); }
            public void changedUpdate(DocumentEvent e) {}
        });
        searchField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("🔍 Search food...")) {
                    searchField.setText("");
                    searchField.setForeground(textDark);
                }
            }
            public void focusLost(FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("🔍 Search food...");
                    searchField.setForeground(textGray);
                }
            }
        });

        cartBtn = new JButton("🛒 Cart (0)");
        cartBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cartBtn.setForeground(primaryRed);
        cartBtn.setBackground(Color.WHITE);
        cartBtn.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        cartBtn.setFocusPainted(false);
        cartBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cartBtn.addActionListener(e -> new CartPage(cartItems, this));

        JButton homeBtn = new JButton("🏠 Home");
        homeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        homeBtn.setForeground(Color.WHITE);
        homeBtn.setBackground(primaryRed);
        homeBtn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1, true));
        homeBtn.setFocusPainted(false);
        homeBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeBtn.addActionListener(e -> { dispose(); new HomePage(); });

        navRight.add(homeBtn);
        navRight.add(searchField);
        navRight.add(cartBtn);
        navbar.add(logo, BorderLayout.WEST);
        navbar.add(navRight, BorderLayout.EAST);

        JPanel categoryBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        categoryBar.setBackground(bgWhite);
        categoryBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        for (String cat : categories) {
            JButton catBtn = new JButton(getCatEmoji(cat) + " " + cat);
            catBtn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            catBtn.setFocusPainted(false);
            catBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            if (cat.equals("All")) {
                catBtn.setBackground(primaryRed);
                catBtn.setForeground(Color.WHITE);
                catBtn.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
            } else {
                catBtn.setBackground(bgWhite);
                catBtn.setForeground(textDark);
                catBtn.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));
            }
            catBtn.addActionListener(e -> {
                selectedCategory = cat;
                updateCategoryButtons(categoryBar, cat);
                buildFoodGrid();
            });
            categoryBar.add(catBtn);
        }

        foodGrid = new JPanel(new GridLayout(0, 3, 15, 15));
        foodGrid.setBackground(bgGray);
        foodGrid.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        buildFoodGrid();

        JScrollPane scrollPane = new JScrollPane(foodGrid);
        scrollPane.setBackground(bgGray);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(navbar, BorderLayout.NORTH);
        topPanel.add(categoryBar, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

    void showFoodDetail(int index) {
        String[] item = menuItems[index];
        JDialog dialog = new JDialog(this, item[0], true);
        dialog.setSize(500, 580);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(bgWhite);

        // Food image
        JPanel imgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                if (foodImages[index] != null) {
                    g2.drawImage(foodImages[index], 0, 0, getWidth(), getHeight(), null);
                } else {
                    g2.setColor(new Color(245, 245, 245));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                }
                g2.setColor(new Color(0, 0, 0, 100));
                g2.fillRect(0, getHeight()-60, getWidth(), 60);
            }
        };
        imgPanel.setPreferredSize(new Dimension(500, 220));
        imgPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 220));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(bgWhite);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel catTag = new JLabel(item[1]);
        catTag.setFont(new Font("Segoe UI", Font.BOLD, 12));
        catTag.setForeground(primaryRed);
        catTag.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel(item[0]);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        nameLabel.setForeground(textDark);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ratingLabel = new JLabel("⭐ " + item[3] + "  •  Free Delivery  •  30-45 mins");
        ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ratingLabel.setForeground(textGray);
        ratingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep1 = new JSeparator();
        sep1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JLabel descTitle = new JLabel("📝 Description");
        descTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        descTitle.setForeground(textDark);
        descTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel descLabel = new JLabel("<html><body style='width:400px'>" + item[6] + "</body></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        descLabel.setForeground(textGray);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ingTitle = new JLabel("🥗 Ingredients");
        ingTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        ingTitle.setForeground(textDark);
        ingTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ingLabel = new JLabel("<html><body style='width:400px'>" + item[5] + "</body></html>");
        ingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        ingLabel.setForeground(textGray);
        ingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel specLabel = new JLabel(item[7]);
        specLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        specLabel.setForeground(new Color(40, 167, 69));
        specLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JSeparator sep2 = new JSeparator();
        sep2.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));

        JPanel bottomRow = new JPanel(new BorderLayout(10, 0));
        bottomRow.setBackground(bgWhite);
        bottomRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JLabel priceLabel = new JLabel("₹" + item[2]);
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        priceLabel.setForeground(primaryRed);

        JButton addBtn = new JButton("+ Add to Cart");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(primaryRed);
        addBtn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        addBtn.setFocusPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.addActionListener(e -> {
            cartItems.add(item);
            cartBtn.setText("🛒 Cart (" + cartItems.size() + ")");
            addBtn.setText("✓ Added!");
            addBtn.setBackground(new Color(40, 167, 69));
            Timer t = new Timer(1500, ev -> {
                dialog.dispose();
            });
            t.setRepeats(false);
            t.start();
        });

        bottomRow.add(priceLabel, BorderLayout.WEST);
        bottomRow.add(addBtn, BorderLayout.EAST);

        infoPanel.add(catTag);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(ratingLabel);
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(sep1);
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(descTitle);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(descLabel);
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(ingTitle);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(ingLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(specLabel);
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(sep2);
        infoPanel.add(Box.createVerticalStrut(12));
        infoPanel.add(bottomRow);

        content.add(imgPanel);
        content.add(infoPanel);

        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(null);
        dialog.add(scroll);
        dialog.setVisible(true);
    }

    void filterFood(String query) {
        if (query.isEmpty() || query.equals("🔍 Search food...")) {
            buildFoodGrid();
            return;
        }
        foodGrid.removeAll();
        for (int i = 0; i < menuItems.length; i++) {
            if (menuItems[i][0].toLowerCase().contains(query.toLowerCase())) {
                foodGrid.add(createFoodCard(i));
            }
        }
        foodGrid.revalidate();
        foodGrid.repaint();
    }

    void buildFoodGrid() {
        foodGrid.removeAll();
        for (int i = 0; i < menuItems.length; i++) {
            if (selectedCategory.equals("All") || menuItems[i][1].equals(selectedCategory)) {
                foodGrid.add(createFoodCard(i));
            }
        }
        foodGrid.revalidate();
        foodGrid.repaint();
    }

    JPanel createFoodCard(int index) {
        String[] item = menuItems[index];
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(bgWhite);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1, true),
            BorderFactory.createEmptyBorder(0, 0, 12, 0)
        ));

        JPanel imagePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                if (foodImages[index] != null) {
                    g2.drawImage(foodImages[index], 0, 0, getWidth(), getHeight(), null);
                } else {
                    g2.setColor(new Color(245, 245, 245));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                    g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
                    g2.setColor(new Color(200, 200, 200));
                    g2.drawString("🍽️", getWidth()/2 - 20, getHeight()/2 + 15);
                }
                GradientPaint gp = new GradientPaint(0, getHeight()-40, new Color(0,0,0,0), 0, getHeight(), new Color(0,0,0,80));
                g2.setPaint(gp);
                g2.fillRect(0, getHeight()-40, getWidth(), 40);
            }
        };
        imagePanel.setPreferredSize(new Dimension(300, 180));
        imagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 180));
        imagePanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        imagePanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showFoodDetail(index);
            }
        });

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(bgWhite);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 12, 0, 12));
        infoPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        infoPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showFoodDetail(index);
            }
        });

        JLabel catTag = new JLabel(item[1]);
        catTag.setFont(new Font("Segoe UI", Font.BOLD, 11));
        catTag.setForeground(primaryRed);
        catTag.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel nameLabel = new JLabel(item[0]);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(textDark);
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel ratingLabel = new JLabel("⭐ " + item[3] + "  •  Free delivery");
        ratingLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        ratingLabel.setForeground(textGray);
        ratingLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel bottomRow = new JPanel(new BorderLayout());
        bottomRow.setBackground(bgWhite);
        bottomRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel priceLabel = new JLabel("₹" + item[2]);
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        priceLabel.setForeground(primaryRed);

        JButton addBtn = new JButton("+ Add");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        addBtn.setForeground(Color.WHITE);
        addBtn.setBackground(primaryRed);
        addBtn.setBorder(BorderFactory.createEmptyBorder(6, 16, 6, 16));
        addBtn.setFocusPainted(false);
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.addActionListener(e -> {
            cartItems.add(item);
            cartBtn.setText("🛒 Cart (" + cartItems.size() + ")");
            addBtn.setText("✓ Added");
            addBtn.setBackground(new Color(40, 167, 69));
            Timer t = new Timer(1500, ev -> {
                addBtn.setText("+ Add");
                addBtn.setBackground(primaryRed);
            });
            t.setRepeats(false);
            t.start();
        });

        bottomRow.add(priceLabel, BorderLayout.WEST);
        bottomRow.add(addBtn, BorderLayout.EAST);

        infoPanel.add(catTag);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(ratingLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(bottomRow);

        card.add(imagePanel);
        card.add(infoPanel);
        return card;
    }

    void updateCategoryButtons(JPanel categoryBar, String selected) {
        for (Component c : categoryBar.getComponents()) {
            if (c instanceof JButton) {
                JButton btn = (JButton) c;
                if (btn.getText().contains(selected)) {
                    btn.setBackground(primaryRed);
                    btn.setForeground(Color.WHITE);
                    btn.setBorder(BorderFactory.createEmptyBorder(7, 16, 7, 16));
                } else {
                    btn.setBackground(bgWhite);
                    btn.setForeground(textDark);
                    btn.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true));
                }
            }
        }
    }

    String getCatEmoji(String cat) {
        switch (cat) {
            case "All": return "🍽️";
            case "Starters": return "🥗";
            case "Main Course": return "🍛";
            case "Biryani": return "🍚";
            case "Pizza": return "🍕";
            case "Burgers": return "🍔";
            case "Desserts": return "🍮";
            case "Ice Cream": return "🍦";
            case "Drinks": return "🥤";
            default: return "🍴";
        }
    }

    void loadAllImages() {
        new Thread(() -> {
            for (int i = 0; i < menuItems.length; i++) {
                final int idx = i;
                try {
                    String imgPath = menuItems[i][4];
                    BufferedImage img = null;
                    if (imgPath.startsWith("local:")) {
                        String fileName = imgPath.replace("local:", "");
                        File f = new File("images/" + fileName);
                        if (f.exists()) img = ImageIO.read(f);
                    } else {
                        img = ImageIO.read(new URL(imgPath));
                    }
                    foodImages[idx] = img;
                    SwingUtilities.invokeLater(() -> { if (foodGrid != null) foodGrid.repaint(); });
                } catch (Exception e) {
                    System.out.println("Error: " + menuItems[idx][0]);
                }
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPage());
    }
}