package software.developer.bhushan.ui;

import software.developer.bhushan.components.StylishButtons;
import software.developer.bhushan.font.FontUtils;
import software.developer.bhushan.sqlite.models.UserModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class User {
    private  UserModel currentLoginUser;

    private JPanel mainPanel = new JPanel();

    private CardLayout cardLayout = new CardLayout();
    private JPanel cardMainPanel = new JPanel(cardLayout);
    private ATM_GUI atmGui;

    private final String CardUserOptions = "CardUserOptions",
            CardAccountBalanceInquiry = "CardAccountBalanceInquiry"
            ,CardCashWithdrawal = "CardCashWithdrawal"
            ,CardCashDeposit = "CardCashDeposit"
            ,CardPINChange = "CardPINChange"
            ,CardTransactionHistory = "CardTransactionHistory"
            ,CardMyProfile = "CardMyProfile";


    public User(ATM_GUI atmGui,UserModel currentLoginUser){
        this.currentLoginUser = currentLoginUser;
        this.atmGui = atmGui;
    }

    public JPanel userHomePanel(){
//        JPanel panel = new JPanel();
        BorderLayout bl = new BorderLayout();
        mainPanel.setLayout(bl);

        mainPanel.add(headerPanel(),BorderLayout.NORTH);
        mainPanel.add(cardMainPanel,BorderLayout.CENTER);

        showCard(CardUserOptions);

        return mainPanel;
    }

    public JPanel headerPanel(){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        JLabel welcomeLabel = new JLabel();
        if (currentLoginUser.getUser_Name().length() >= 16){
           welcomeLabel.setText("Welcome "+currentLoginUser.getUser_Name().substring(0,16)+"...");
        }else {
            welcomeLabel.setText("Welcome "+currentLoginUser.getUser_Name());
        }
        welcomeLabel.setFont(FontUtils.Heading_2_Bold);
        panel.add(welcomeLabel);

//        JPanel balancePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,10));
        JLabel balanceLabel = new JLabel("$ "+currentLoginUser.getUser_Balance());
        balanceLabel.setFont(FontUtils.Heading_2_Bold);

//        balancePanel.add(balanceLabel);

//        JButton settingButton = new JButton("Settings");
//        settingButton.setFont(FontUtils.Heading_3_Plain);

        panel.add(balanceLabel);

        return panel;
    }

    public JPanel headerPanel(String heading){
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
        JLabel welcomeLabel = new JLabel();
        welcomeLabel.setText(heading);
        welcomeLabel.setFont(FontUtils.Heading_3_Bold);
        panel.add(welcomeLabel);
//        JLabel balanceLabel = new JLabel("$ "+currentLoginUser.getUser_Balance());
//        balanceLabel.setFont(FontUtils.Heading_2_Bold);
//        panel.add(balanceLabel);
        return panel;
    }

    public JPanel cardUserOptionsPanel() {
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel panel = new JPanel();
        panel.setLayout(gbl);

        ArrayList<JButton> buttons = getJButtons();
        int nCol = 2;

        for (int i = 0; i < buttons.size(); i++) {
            gbc.gridx = i % nCol;
            gbc.gridy = i / nCol;
//            buttons.get(i).setFont(FontUtils.Heading_2_Plain);
            StylishButtons.styleButton(buttons.get(i));
            if (buttons.get(i).getText().equals("Logout")){
                gbc.gridwidth = 2;
            }
            panel.add(buttons.get(i), gbc);
        }

        return panel;
    }


    private  ArrayList<JButton> getJButtons() {
        JButton accountBalanceInquiryButton = new JButton("Account Balance Inquiry");
        accountBalanceInquiryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCard(CardAccountBalanceInquiry);
            }
        });

        JButton cashWithdrawalButton = new JButton("Cash Withdrawal");
        JButton cashDepositButton = new JButton("Cash Deposit");
        JButton pinChangeButton = new JButton("PIN Change");
        JButton transactionHistoryButton = new JButton("Transaction History");
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentLoginUser = null;
                atmGui.showCard("Login");
            }
        });
        JButton myProfile = new JButton("My Profile");

        ArrayList<JButton> buttons = new ArrayList<>();
        buttons.add(myProfile);
        buttons.add(accountBalanceInquiryButton);
        buttons.add(cashWithdrawalButton);
        buttons.add(cashDepositButton);
        buttons.add(pinChangeButton);
        buttons.add(transactionHistoryButton);
        buttons.add(logoutButton);
        return buttons;
    }

    private JPanel cardAccountBalanceInquiryPanel() {
        JPanel panel = new JPanel();
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST; // Align labels to the left

        panel.setLayout(gbl);
//        TitledBorder balanceInquiry = new TitledBorder("Account Balance Inquiry");
//        balanceInquiry.setTitleFont(FontUtils.Heading_2_Plain);
//        balanceInquiry.setTitlePosition(TitledBorder.BELOW_TOP);
//        balanceInquiry.setTitleJustification(TitledBorder.CENTER);
//        panel.setBorder(balanceInquiry);

        // Heading
        JLabel headingLabel = new JLabel("Account Balance Inquiry");
        headingLabel.setFont(FontUtils.Heading_2_Plain);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(headingLabel, gbc);

        gbc.gridwidth = 1;


        // PIN Label
        JLabel pinLabel = new JLabel("Enter PIN: ");
        pinLabel.setFont(FontUtils.Heading_2_Plain);
        gbc.gridx = 0; gbc.gridy++;
        panel.add(pinLabel, gbc);

        // PIN Input Field (Secure)
        JPasswordField pinField = new JPasswordField(15);
        pinField.setFont(FontUtils.Heading_2_Plain);
        pinField.setToolTipText("Enter your PIN");
        gbc.gridx++;
        panel.add(pinField, gbc);

        // Ensure PIN field gets focus
        SwingUtilities.invokeLater(pinField::requestFocusInWindow);

        gbc.gridy++; gbc.gridx--;
        JButton backButton = new JButton("Back");
        StylishButtons.styleButton(backButton);
//        backButton.setFont(FontUtils.Heading_3_Bold);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCard(CardUserOptions);
            }
        });
        panel.add(backButton,gbc);

        gbc.gridx++;
        JButton checkButton = new JButton("Check");
        StylishButtons.styleButton(checkButton);
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] pinChars = pinField.getPassword(); // Get password as char array
                String enteredPin = new String(pinChars); // Convert to String safely

                // Clear the password array after use for security
                Arrays.fill(pinChars, ' ');

                if (enteredPin.equals(currentLoginUser.getUser_Pin())) {
                    ATM_GUI.showMessageDialog(
                            ATM_GUI.getWindows()[0],
                            "Balance Inquiry Successful",
                            "Your Balance Is: $ " + currentLoginUser.getUser_Balance(),
                            JOptionPane.INFORMATION_MESSAGE,
                            null
                    );
                } else {
                    ATM_GUI.showMessageDialog(
                            ATM_GUI.getWindows()[0],
                            "Invalid PIN",
                            "Please enter the correct PIN.",
                            JOptionPane.ERROR_MESSAGE,
                            null
                    );
                }
            }
        });

        panel.add(checkButton,gbc);

        return panel;
    }


    public void showCard(String cardName) {
        cardMainPanel.removeAll();

        if (cardName.equals(CardUserOptions)){
            cardMainPanel.add(cardUserOptionsPanel(),CardUserOptions);
        } else if (cardName.equals(CardAccountBalanceInquiry)) {
            cardMainPanel.add(cardAccountBalanceInquiryPanel(),CardAccountBalanceInquiry);
        }

        cardLayout.show(cardMainPanel,cardName);
        cardMainPanel.revalidate();
        cardMainPanel.repaint();
    }

}
