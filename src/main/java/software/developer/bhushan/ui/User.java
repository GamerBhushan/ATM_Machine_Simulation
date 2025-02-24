package software.developer.bhushan.ui;

import software.developer.bhushan.components.FormTextField;
import software.developer.bhushan.components.StylishButtons;
import software.developer.bhushan.font.FontUtils;
import software.developer.bhushan.sqlite.models.TransactionModel;
import software.developer.bhushan.sqlite.models.UserModel;
import software.developer.bhushan.sqlite.tables.TransactionTable;
import software.developer.bhushan.utils.FieldUtils;
import software.developer.bhushan.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class User {
    private  UserModel currentLoginUser;

    private JPanel mainPanel = new JPanel();

    private CardLayout cardLayout = new CardLayout();
    private JPanel cardMainPanel = new JPanel(cardLayout);
    private ATM atmGui;

    private final String CardUserOptions = "CardUserOptions",
            CardAccountBalanceInquiry = "CardAccountBalanceInquiry"
            ,CardCashWithdrawal = "CardCashWithdrawal"
            ,CardCashDeposit = "CardCashDeposit"
            ,CardPINChange = "CardPINChange"
            ,CardTransactionHistory = "CardTransactionHistory"
            ,CardMyProfile = "CardMyProfile";

    private JLabel balanceLabel = new JLabel();

    public User(ATM atmGui, UserModel currentLoginUser){
        this.currentLoginUser = currentLoginUser;
        this.atmGui = atmGui;
        balanceLabel.setText("$ "+currentLoginUser.getUser_Balance());
    }

    private JButton getBackButton(){
        JButton backButton = new JButton("Back");
        StylishButtons.styleButton(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCard(CardUserOptions);
            }
        });
        return backButton;
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


//        balancePanel.add(balanceLabel);

//        JButton settingButton = new JButton("Settings");
//        settingButton.setFont(FontUtils.Heading_3_Plain);
        balanceLabel.setFont(FontUtils.Heading_2_Bold);

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
        cashWithdrawalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCard(CardCashWithdrawal);
            }
        });
        JButton cashDepositButton = new JButton("Cash Deposit");
        cashDepositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCard(CardCashDeposit);
            }
        });

        JButton pinChangeButton = new JButton("PIN Change");
        pinChangeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCard(CardPINChange);
            }
        });

        JButton transactionHistoryButton = new JButton("Transaction History");
        transactionHistoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCard(CardTransactionHistory);
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int choice = JOptionPane.showOptionDialog(
                        atmGui, // Parent component
                        "Are You Sure To Logout? 😊", // Message
                        "Logout Confirmation", // Title
                        JOptionPane.YES_NO_OPTION, // Option type
                        JOptionPane.QUESTION_MESSAGE, // Message type
                        null, // Custom icon (null = default)
                        new String[]{"Logout", "Cancel"}, // Custom button labels
                        "Cancel" // Default selected option
                );
                if (choice == JOptionPane.YES_OPTION) {
//                    System.out.println("User chose to logout.");
                    // Perform logout logic here
                    currentLoginUser = null;
                    atmGui.showCard("Login");
                } else {
//                    System.out.println("User canceled logout.");
                }

            }
        });
        JButton myProfile = new JButton("My Profile");
        myProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCard(CardMyProfile);
            }
        });

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
        panel.add(getBackButton(),gbc);

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
                    ATM.showMessageDialog(
                            ATM.getWindows()[0],
                            "Balance Inquiry Successful",
                            "Your Balance Is: $ " + currentLoginUser.getUser_Balance(),
                            JOptionPane.INFORMATION_MESSAGE,
                            null
                    );
                } else {
                    ATM.showMessageDialog(
                            ATM.getWindows()[0],
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

    private JPanel cardCashWithdrawalDepositPanel(String transactionType) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel headingLabel = new JLabel("",JLabel.CENTER);

        headingLabel.setFont(FontUtils.Heading_2_Plain);
        panel.add(headingLabel, gbc);

        gbc.gridwidth = 1;
        JLabel acNo = new JLabel("Ac No : ");
        acNo.setFont(FontUtils.Heading_2_Plain);
        gbc.gridy++; panel.add(acNo,gbc);
        JLabel uAcNo =  new JLabel(currentLoginUser.getUser_Account_Number());
        uAcNo.setFont(FontUtils.Heading_2_Plain);
        gbc.gridx=1;
        panel.add(uAcNo,gbc);

        gbc.gridx = 0; gbc.gridy++;
        JLabel amountLabel = new JLabel("Amount : $");
        amountLabel.setFont(FontUtils.Heading_2_Plain);
        panel.add(amountLabel,gbc);
        gbc.gridx = 1;
        JTextField amountField = new JTextField();
        FieldUtils.makeFieldFloatField(amountField);
        amountField.setColumns(10);
        amountField.setFont(FontUtils.Heading_2_Plain);
        panel.add(amountField,gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(getBackButton(),gbc);
        gbc.gridx = 1;
        JButton makeTransactionButton = new JButton();
        makeTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleWithdrawDeposit(amountField.getText(),transactionType);

            }
        });
        StylishButtons.styleButton(makeTransactionButton);
        panel.add(makeTransactionButton,gbc);

        if (transactionType.equals(TransactionModel.TYPE_WITHDRAWAL)){
            headingLabel.setText("Cash Withdrawal");
            makeTransactionButton.setText("Withdraw");
        } else if (transactionType.equals(TransactionModel.TYPE_DEPOSIT)) {
            headingLabel.setText("Cash Deposit");
            makeTransactionButton.setText("Deposit");
        }

        return panel;
    }

    private void handleWithdrawDeposit(String amountStr,String transactionType) {
        try {
            double amount = Double.parseDouble(amountStr);
            String errMsg = "";
            if (transactionType.equals(TransactionModel.TYPE_WITHDRAWAL)){
                errMsg = "Insufficient Balance";
            }else if (transactionType.equals(TransactionModel.TYPE_DEPOSIT)){
                errMsg = "Invalid Amount";
            }

            if (transactionType.equals(TransactionModel.TYPE_DEPOSIT) && amount > 0){
                currentLoginUser.setUser_Balance(Double.parseDouble(String.format("%.2f",currentLoginUser.getUser_Balance()+amount)));
                ATM.databaseHelper.getUserTable().updateByAccountNumber(ATM.databaseHelper.getConnection(),currentLoginUser);
                TransactionModel transactionModel = getTransactionModel(amountStr,"Self","---",TransactionModel.TYPE_DEPOSIT);
                ATM.databaseHelper.getTransactionTable().insert(ATM.databaseHelper.getConnection(),transactionModel);
                balanceLabel.setText("$ "+currentLoginUser.getUser_Balance());
                ATM.showMessageDialog(atmGui,"Transaction Successful","Deposited Amount : "+amountStr,JOptionPane.INFORMATION_MESSAGE,null);
                errMsg = null;
            }else if (transactionType.equals(TransactionModel.TYPE_WITHDRAWAL) && currentLoginUser.getUser_Balance() >= amount && amount > 0){
                currentLoginUser.setUser_Balance(Double.parseDouble(String.format("%.2f",currentLoginUser.getUser_Balance()-amount)));
                ATM.databaseHelper.getUserTable().updateByAccountNumber(ATM.databaseHelper.getConnection(),currentLoginUser);
                TransactionModel transactionModel = getTransactionModel(amountStr,"Self","---",TransactionModel.TYPE_WITHDRAWAL);
                ATM.databaseHelper.getTransactionTable().insert(ATM.databaseHelper.getConnection(),transactionModel);
                balanceLabel.setText("$ "+currentLoginUser.getUser_Balance());
                ATM.showMessageDialog(atmGui,"Transaction Successful","Withdrawal Amount : "+amountStr,JOptionPane.INFORMATION_MESSAGE,null);
                errMsg = null;
            }else {
                throw new Exception(errMsg);
            }

        } catch (Exception e) {
            ATM.showMessageDialog(atmGui,"Transaction Cancel","Error : "+e.getMessage(),JOptionPane.ERROR_MESSAGE,null);
        }
    }

    private  TransactionModel getTransactionModel(String amountStr,String From, String To,String Type) {
        String cDate = Utils.getCurrentDate(), cTime = Utils.getCurrentTime();
        TransactionModel transactionModel = new TransactionModel();
        transactionModel.setTransaction_ID(cDate+" "+cTime);
        transactionModel.setTransaction_Amount(amountStr);
        transactionModel.setTransaction_Date(cDate);
        transactionModel.setTransaction_From(From);
        transactionModel.setTransaction_To(To);
        transactionModel.setTransaction_Type(Type);
        transactionModel.setTransaction_Time(cTime);
        return transactionModel;
    }


    private JPanel cardPINChangePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel heading = new JLabel("Pin Change", SwingConstants.CENTER);
        heading.setFont(FontUtils.Heading_2_Bold);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(heading, gbc);

        gbc.gridwidth = 1;
        JLabel oldPinLabel = new JLabel("Old PIN :");
        oldPinLabel.setFont(FontUtils.Heading_2_Plain);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(oldPinLabel, gbc);

        JTextField oldPinField = new JTextField(10);
        FieldUtils.makeFieldPinField(oldPinField);
        oldPinField.setFont(FontUtils.Heading_2_Plain);
        gbc.gridx = 1;
        panel.add(oldPinField, gbc);

        JLabel newPinLabel = new JLabel("New PIN :");
        newPinLabel.setFont(FontUtils.Heading_2_Plain);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(newPinLabel, gbc);

        JTextField newPinField = new JTextField(10);
        FieldUtils.makeFieldPinField(newPinField);
        newPinField.setFont(FontUtils.Heading_2_Plain);
        gbc.gridx = 1;
        panel.add(newPinField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton changeButton = new JButton("Change");
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handlePinChange(oldPinField.getText(),newPinField.getText());
            }
        });
        StylishButtons.styleButton(changeButton);
        buttonPanel.add(getBackButton());
        buttonPanel.add(changeButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    private void handlePinChange(String oldPin, String newPin) {
        try{
            if (oldPin.isEmpty() || newPin.isEmpty()){
                throw new Exception("Please Fill Proper Information.");
            }else if(oldPin.equals(currentLoginUser.getUser_Pin())){
                if (newPin.length() == 4){
                    currentLoginUser.setUser_Pin(newPin);
                    ATM.databaseHelper.getUserTable().updateByAccountNumber(ATM.databaseHelper.getConnection(),currentLoginUser);
                    ATM.showMessageDialog(atmGui,"Transaction Success","Pin Change Successfully",JOptionPane.INFORMATION_MESSAGE,null);
                }else{
                    throw new Exception("New Pin Length Should Be 4 Digits Long.");
                }

            }else if (!oldPin.equals(currentLoginUser.getUser_Pin())){
                throw new Exception("Incorrect Old Pin.");
            }
        }catch (Exception e){
            ATM.showMessageDialog(atmGui,"Transaction Cancel","Error : "+e.getMessage(),JOptionPane.ERROR_MESSAGE,null);
        }

    }


    private JPanel showAllTransactionsPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel heading = new JLabel("Transaction History", SwingConstants.CENTER);
        heading.setFont(FontUtils.Heading_2_Bold);
        panel.add(heading, BorderLayout.NORTH);

        String[] columnNames = {
                "ID", "From", "To", "Amount", "Type", "Date", "Time"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        JTable transactionTable = new JTable(tableModel);
        transactionTable.setRowHeight(20);
        transactionTable.setFont(FontUtils.Heading_3_Plain);
        transactionTable.getTableHeader().setFont(FontUtils.Heading_3_Bold);

        JScrollPane scrollPane = new JScrollPane(transactionTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        panel.add(getBackButton(), BorderLayout.SOUTH);

        loadTransactionData(tableModel);
        return panel;
    }


    private void loadTransactionData(DefaultTableModel tableModel) {
        String QUERY = "SELECT * FROM Transactions";
        try (Connection connection = ATM.databaseHelper.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY)) {

            while (rs.next()) {
                Object[] rowData = {
                        rs.getString("Transaction_ID"),
                        rs.getString("Transaction_From"),
                        rs.getString("Transaction_To"),
                        rs.getDouble("Transaction_Amount"),
                        rs.getString("Transaction_Type"),
                        rs.getString("Transaction_Date"),
                        rs.getString("Transaction_Time")
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            System.err.println("Error fetching transactions: " + e.getMessage());
        }
    }

    private JPanel cardMyProfilePanel(){
        JPanel panel = new JPanel();

        return panel;
    }

    private void showCard(String cardName) {
        cardMainPanel.removeAll();

        if (cardName.equals(CardUserOptions)){
            cardMainPanel.add(cardUserOptionsPanel(),CardUserOptions);
        } else if (cardName.equals(CardAccountBalanceInquiry)) {
            cardMainPanel.add(cardAccountBalanceInquiryPanel(),CardAccountBalanceInquiry);
        }else if(cardName.equals(CardCashWithdrawal)){
            cardMainPanel.add(cardCashWithdrawalDepositPanel(TransactionModel.TYPE_WITHDRAWAL),CardCashWithdrawal);
        } else if (cardName.equals(CardCashDeposit)) {
            cardMainPanel.add(cardCashWithdrawalDepositPanel(TransactionModel.TYPE_DEPOSIT),CardCashDeposit);
        } else if (cardName.equals(CardPINChange)) {
            cardMainPanel.add(cardPINChangePanel(),CardPINChange);
        } else if (cardName.equals(CardMyProfile)) {
            cardMainPanel.add(cardMyProfilePanel(),CardMyProfile);
        }else if(cardName.equals(CardTransactionHistory)){
            cardMainPanel.add(showAllTransactionsPanel(),CardTransactionHistory);
        }

        cardLayout.show(cardMainPanel,cardName);
        cardMainPanel.revalidate();
        cardMainPanel.repaint();
    }

}
