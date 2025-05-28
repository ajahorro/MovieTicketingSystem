package ticketsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MovieTicketGUI extends JFrame {
    private JComboBox<String> movieBox, timeBox, seatBox, snackBox;
    private JCheckBox pwdBox, wheelchairBox;
    private JTextField nameField, idField;
    private JTextArea outputArea;
    private JButton confirmBtn;

    private final String[] movies = {
        "Avengers: Endgame (PG-13)",
        "Finding Nemo (G)",
        "Joker (R-18)"
    };

    private final String[][] showtimes = {
        {"1:00 PM", "3:00 PM", "7:00 PM"},
        {"10:00 AM", "1:00 PM", "4:00 PM"},
        {"6:00 PM", "9:00 PM", "11:00 PM"}
    };

    private final String[] seats = {"A1", "A2", "A3", "B1", "B2", "B3"};
    private final String[] wheelchairSeats = {"B1", "B2", "B3"};
    private final String[] snacks = {"Popcorn", "Chips", "Candy"};

    public MovieTicketGUI() {
        setTitle("🎬 Movie Ticketing System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Shared font and color settings
        Font font = new Font("SansSerif", Font.PLAIN, 14);
        Color bg = Color.BLACK;
        Color fg = Color.WHITE;

        // Panel for form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        formPanel.setBackground(bg);

        // Fields
        nameField = createTextField();
        idField = createTextField();
        movieBox = createComboBox(movies);
        timeBox = createComboBox(new String[]{});
        seatBox = createComboBox(seats);
        snackBox = createComboBox(snacks);
        pwdBox = createCheckBox("PWD?");
        wheelchairBox = createCheckBox("With Wheelchair?");
        confirmBtn = createButton("Confirm Ticket");

        // Add fields to form
        formPanel.add(createLabeledField("Name:", nameField, font, bg, fg));
        formPanel.add(createLabeledField("Select Movie:", movieBox, font, bg, fg));
        formPanel.add(createLabeledField("Select Time:", timeBox, font, bg, fg));
        formPanel.add(createLabeledField("Seat:", seatBox, font, bg, fg));

        JPanel checkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkPanel.setBackground(bg);
        pwdBox.setForeground(fg);
        wheelchairBox.setForeground(fg);
        checkPanel.add(pwdBox);
        checkPanel.add(wheelchairBox);
        formPanel.add(checkPanel);

        formPanel.add(createLabeledField("PWD ID (8 digits):", idField, font, bg, fg));
        formPanel.add(createLabeledField("Select Snack:", snackBox, font, bg, fg));

        confirmBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(confirmBtn);

        add(formPanel, BorderLayout.NORTH);

        outputArea = new JTextArea(12, 40);
        outputArea.setEditable(false);
        outputArea.setMargin(new Insets(10, 10, 10, 10));
        outputArea.setBackground(Color.DARK_GRAY);
        outputArea.setForeground(fg);
        outputArea.setFont(font);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Ticket Summary"));
        add(scrollPane, BorderLayout.CENTER);

        updateTimeOptions();
        movieBox.addActionListener(e -> updateTimeOptions());
        confirmBtn.addActionListener(e -> processTicket());

        setVisible(true);
    }

    private JPanel createLabeledField(String labelText, JComponent field, Font font, Color bg, Color fg) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(bg);
        JLabel label = new JLabel(labelText);
        label.setForeground(fg);
        label.setFont(font);
        field.setFont(font);
        panel.add(label);
        panel.add(field);
        return panel;
    }

    private JTextField createTextField() {
        JTextField field = new JTextField(20);
        field.setBackground(Color.DARK_GRAY);
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        return field;
    }

    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> box = new JComboBox<>(items);
        box.setBackground(Color.DARK_GRAY);
        box.setForeground(Color.WHITE);
        return box;
    }

    private JCheckBox createCheckBox(String label) {
        JCheckBox box = new JCheckBox(label);
        box.setBackground(Color.BLACK);
        return box;
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setBackground(new Color(0x1f1f1f));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }

    private void updateTimeOptions() {
        timeBox.removeAllItems();
        int index = movieBox.getSelectedIndex();
        for (String time : showtimes[index]) {
            timeBox.addItem(time);
        }
    }

    private void processTicket() {
        String name = nameField.getText().trim();
        boolean isPWD = pwdBox.isSelected();
        boolean hasWheelchair = wheelchairBox.isSelected();
        String seat = (String) seatBox.getSelectedItem();
        String movie = (String) movieBox.getSelectedItem();
        String time = (String) timeBox.getSelectedItem();
        String snack = (String) snackBox.getSelectedItem();
        String id = idField.getText().trim();

        double price = 300.0;

        if (isPWD) {
            if (!id.matches("\\d{8}")) {
                outputArea.setText("❌ Invalid PWD ID. Must be 8 digits.");
                return;
            }
            price -= 50.0;

            boolean seatValid = false;
            for (String s : wheelchairSeats) {
                if (s.equals(seat)) seatValid = true;
            }
            if (hasWheelchair && !seatValid) {
                outputArea.setText("❌ Wheelchair users can only choose B1, B2, or B3.");
                return;
            }
        }

        outputArea.setText("=== 🎟️ Ticket Summary ===\n");
        outputArea.append("Name: " + name + "\n");
        outputArea.append("PWD: " + (isPWD ? "Yes" + (hasWheelchair ? " (Wheelchair)" : "") : "No") + "\n");
        outputArea.append("Movie: " + movie + "\n");
        outputArea.append("Showtime: " + time + "\n");
        outputArea.append("Seat: " + seat + "\n");
        outputArea.append("Free Drink: Ice Cold Water\n");
        outputArea.append("Free Snack: " + snack + "\n");
        outputArea.append("Total Price: ₱" + price + "\n\n");

        outputArea.append("--- REMINDERS ---\n");
        outputArea.append("- No refunds\n");
        outputArea.append("- No food requiring utensils\n");
        outputArea.append("- No loud talking in the cinema\n");
        outputArea.append("🎬 Enjoy your movie!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MovieTicketGUI::new);
    }
}
