package software.developer.bhushan.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class StylishButtons {

//    public static void main(String[] args) {
//        JFrame frame = new JFrame(Banking System");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 500);
//        frame.setLayout(new GridBagLayout());
//
//        ArrayList<JButton> buttons = getStyledButtons();
//
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(10, 10, 10, 10);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        for (int i = 0; i < buttons.size(); i++) {
//            gbc.gridx = 0;
//            gbc.gridy = i;
//            frame.add(buttons.get(i), gbc);
//        }
//
//        frame.setVisible(true);
//    }

    public static ArrayList<JButton> getStyledButtons() {
        String[] buttonLabels = {
                "Account Balance Inquiry",
                "Cash Withdrawal",
                "Cash Deposit",
                "PIN Change",
                "Transaction History",
                "My Profile",
                "Logout"
        };

        ArrayList<JButton> buttons = new ArrayList<>();

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            styleButton(button);
            buttons.add(button);
        }

        return buttons;
    }

    public static void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(30, 144, 255)); // Dodger Blue
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(25, 25, 112), 2),  // Dark Blue Border
                BorderFactory.createEmptyBorder(10, 20, 10, 20)  // Padding
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Hover Effects
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180)); // Steel Blue
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(30, 144, 255)); // Dodger Blue
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(new Color(0, 102, 204)); // Darker Blue
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180)); // Steel Blue
            }
        });
    }
}
