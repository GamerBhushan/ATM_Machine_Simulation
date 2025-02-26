package software.developer.bhushan.ui;

import software.developer.bhushan.annotations.MC;
import software.developer.bhushan.annotations.MCOptionTypes;
import software.developer.bhushan.components.StylishButtons;
import software.developer.bhushan.font.FontUtils;
import software.developer.bhushan.res.Resources;
import software.developer.bhushan.sqlite.SQLiteDatabaseHelper;
import software.developer.bhushan.sqlite.models.UserModel;
import software.developer.bhushan.utils.FieldUtils;
import software.developer.bhushan.utils.Utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class ATM extends JFrame {
    public static SQLiteDatabaseHelper databaseHelper = new SQLiteDatabaseHelper();
    private UserModel autoLoginUser;
    private Container container = this.getContentPane();
    private String title = "ATM GUI Machine Simulation";

    private JPanel atmPanel = new JPanel();
    private BorderLayout borderLayout = new BorderLayout();
    private  Thread autoLoginThread;
    private CardLayout cardLayout = new CardLayout();
    private JPanel mainPanel ;

    public User user;


    public ATM() {
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.setLayout(new BorderLayout());

        // === Main Panel with CardLayout ===
        mainPanel = new JPanel(cardLayout);
//        mainPanel.add(userLoginPanel(), "Login");
//        mainPanel.add(userSignUpPanel(), "SignUp");
//        mainPanel.add(showAllUsersPanel(), "ShowAllUsers");

        // === Header Panel (Title + Logo) ===
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center aligned
        headerPanel.setBackground(Color.DARK_GRAY);

        // App Title Label
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(FontUtils.Heading_1_Bold);
        titleLabel.setForeground(Color.WHITE);

        // App Logo
        ImageIcon icon = new ImageIcon(Resources.logoPath);
        icon.setImage(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)); // Resize Logo
        JLabel logoLabel = new JLabel(icon);

        // Add logo & title to header panel
        headerPanel.add(logoLabel);
        headerPanel.add(titleLabel);

        // === Footer Panel (Developer Name) ===
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.DARK_GRAY);

        JLabel developerLabel = new JLabel("Developed By Bhushan");
        developerLabel.setFont(FontUtils.Heading_2_Plain);
        developerLabel.setForeground(Color.WHITE);

        footerPanel.add(developerLabel);

        // === Adding Components to Frame ===
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH); // Add footer

        // Show Login Panel First
        showCard("Login");

        this.setVisible(true);

    }



    public JPanel userLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Adds padding around panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adds spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Labels
        JLabel uan = new JLabel("Account Number:");
        uan.setFont(FontUtils.Heading_2_Plain);
//        uan.setForeground(Color.WHITE);

        JLabel upin = new JLabel("Enter PIN:");
        upin.setFont(FontUtils.Heading_2_Plain);

        JLabel sup = new JLabel("Don't Have Account : ");
        sup.setFont(FontUtils.Heading_3_Plain);

//        upin.setForeground(Color.WHITE);

        // Input Fields
        JTextField accountno = new JTextField(15);
        accountno.setFont(FontUtils.Heading_2_Plain);

        JPasswordField pin = new JPasswordField(15);
        pin.setFont(FontUtils.Heading_2_Plain);

        // Buttons
        JButton loginButton = new JButton("Login");
        loginButton.setFont(FontUtils.Heading_2_Plain);
        loginButton.setBackground(new Color(50, 205, 50)); // Green button
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserModel uModel = databaseHelper.getUserTable().findUserByACNo(databaseHelper.getConnection(), accountno.getText());
                if (uModel != null ){
                    if (uModel.getUser_Pin().equals(new String(pin.getPassword()))){
                        user = new User(ATM.this,uModel);
                        showCard("UserHome");

                    }else {
                        showMessageDialog(ATM.this,"Invalid Credentials","The Account Number Or Pin Is Invalid",JOptionPane.ERROR_MESSAGE,null);
                    }
                }else {
                    showMessageDialog(ATM.this,"Invalid Credentials","The Account Number Or Pin Is Invalid",JOptionPane.ERROR_MESSAGE,null);
                }
            }
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(FontUtils.Heading_3_Plain);
        signUpButton.setBackground(new Color(30, 144, 255)); // Blue button
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.addActionListener(e ->  showCard("SignUp"));

        JButton showUsersBtn = new JButton("Show All Users");
        showUsersBtn.setFont(FontUtils.Heading_3_Plain);
        showUsersBtn.setBackground(new Color(255, 69, 0)); // Red button
        showUsersBtn.setForeground(Color.WHITE);
        showUsersBtn.setFocusPainted(false);
        showUsersBtn.addActionListener(e -> showCard("ShowAllUsers"));


        // Positioning Components
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(uan, gbc);

        gbc.gridy = 0;
        gbc.gridx = 1;
        panel.add(accountno, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(upin, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;
        panel.add(pin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(sup,gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(signUpButton, gbc);


        gbc.gridx =  0;
        gbc.gridy = 3;
        panel.add(showUsersBtn,gbc);

        // Login Button
        gbc.gridx = 1;
        gbc.gridy = 3;
//        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

//        AUTO
         autoLoginThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (autoLoginUser != null){
                        Thread.sleep(500);
                        accountno.setText(autoLoginUser.getUser_Account_Number());
                        Thread.sleep(500);
                        pin.setText(autoLoginUser.getUser_Pin());
                        Thread.sleep(500);
                        loginButton.doClick();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return panel;
    }

    public JPanel userSignUpPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Adds padding around panel

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Adds spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        final String[] acNo = {Utils.generateUniqueAccountNumber()};

        // Labels
        JLabel acNoLabel = new JLabel("AC No : "+ acNo[0]);
        acNoLabel.setFont(FontUtils.Heading_2_Plain);

        JLabel nameLabel = new JLabel("Full Name : ");
        nameLabel.setFont(FontUtils.Heading_2_Plain);

        JLabel pinLabel = new JLabel("Pin : ");
        pinLabel.setFont(FontUtils.Heading_2_Plain);

        JLabel mobileLabel = new JLabel("Mobile Number : ");
        mobileLabel.setFont(FontUtils.Heading_2_Plain);

        JLabel emailLabel = new JLabel("Email : ");
        emailLabel.setFont(FontUtils.Heading_2_Plain);

        JLabel loginLabel = new JLabel("Already Have Account : ");
        loginLabel.setFont(FontUtils.Heading_3_Plain);

        // Input Fields
        JTextField nameField = new JTextField(15);
        nameField.setFont(FontUtils.Heading_2_Plain);

        JTextField pinField = new JTextField(15);
        FieldUtils.makeFieldPinField(pinField);
        pinField.setFont(FontUtils.Heading_2_Plain);

        JTextField mobileField = new JTextField(15);
        mobileField.setFont(FontUtils.Heading_2_Plain);

        JTextField emailField = new JTextField(15);
        emailField.setFont(FontUtils.Heading_2_Plain);

        // Buttons
        JButton generateACNo = new JButton("Change");
        generateACNo.setFont(FontUtils.Heading_3_Plain);
        generateACNo.setBackground(new Color(50, 205, 50)); // Green button
        generateACNo.setForeground(Color.WHITE);
        generateACNo.setFocusPainted(false);
        generateACNo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                acNo[0] = Utils.generateUniqueAccountNumber();
                acNoLabel.setText("AC No : "+acNo[0]);
            }
        });

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setFont(FontUtils.Heading_2_Plain);
        signUpButton.setBackground(new Color(50, 205, 50)); // Green button
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFocusPainted(false);
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().replace(" ","").length() == 0
                        || mobileField.getText().replace(" ","").length() == 0
                        || emailField.getText().replace(" ","").length() == 0
                        || pinField.getText().replace(" ","").length() == 0
                ){
                    showMessageDialog(ATM.this,"Invalid Credentials","Please Fill All Required Information",JOptionPane.ERROR_MESSAGE,null);
                } else if (pinField.getText().length() < 4) {
                    showMessageDialog(ATM.this,"Invalid Pin","Pin Should be 4 Digits Long",JOptionPane.ERROR_MESSAGE,null);
                } else{
                    UserModel uModel = new UserModel();

                    uModel.setUser_Balance(0.0);
                    uModel.setUser_Account_Number(acNo[0]);
                    uModel.setUser_Email(emailField.getText());
                    uModel.setUser_Name(nameField.getText());
                    uModel.setUser_Pin(pinField.getText());
                    uModel.setUser_Mobile_Number(mobileField.getText());
                    databaseHelper.getUserTable().insert(databaseHelper.getConnection(),uModel);
                    showMessageDialog(ATM.this,"Account Created Successfully","Account Created Successfully\n Remember Your AC NO : "+uModel.getUser_Account_Number()+"\nAnd Pin For Login.",JOptionPane.INFORMATION_MESSAGE,new ImageIcon((new ImageIcon(Resources.logoPath).getImage().getScaledInstance(20,20,1))));
                    showCard("Login");
                }
            }
        });

        JButton backButton = new JButton("Back to Login");
        backButton.setFont(FontUtils.Heading_3_Plain);
        backButton.setBackground(new Color(255, 69, 0)); // Red button
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> showCard("Login"));

        // Positioning Components

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(acNoLabel,gbc);
        gbc.gridx = 1;
        panel.add(generateACNo,gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(pinLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(pinField,gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(mobileLabel, gbc);
        gbc.gridx = 1;
        panel.add(mobileField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(loginLabel,gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(backButton, gbc);


        // Sign Up Button
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        panel.add(signUpButton, gbc);

        return panel;
    }

    private JPanel showAllUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] columnNames = {"AC No", "PIN", "Name", "Balance", "Email", "Mobile", "Login"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6; // Only allow editing for the "Login" column
            }
        };

        JTable userTable = new JTable(tableModel);
        userTable.setRowHeight(30);
        userTable.setFont(FontUtils.Heading_3_Plain);
        userTable.getTableHeader().setFont(FontUtils.Heading_3_Bold);

        JScrollPane scrollPane = new JScrollPane(userTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back to Login");
        StylishButtons.styleButton(backBtn);
        backBtn.addActionListener(e -> showCard("Login"));
        panel.add(backBtn, BorderLayout.SOUTH);

        // Load user data into the table
        loadUserData(tableModel, userTable);

        return panel;
    }


    private void loadUserData(DefaultTableModel tableModel, JTable userTable) {
        String QUERY = "SELECT * FROM Users";
        try (Connection connection = databaseHelper.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(QUERY)) {

            while (rs.next()) {
                Object[] rowData = {
                        rs.getString("User_Account_Number"),
                        rs.getString("User_Pin"),
                        rs.getString("User_Name"),
                        rs.getDouble("User_Balance"),
                        rs.getString("User_Email"),
                        rs.getString("User_Mobile_Number"),
                        "Login"  // Placeholder text for button column
                };
                tableModel.addRow(rowData);
            }
        } catch (Exception e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }

        // Add Login Button Renderer and Editor
        userTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        userTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox(), userTable));
    }


//    public static void showMessageDialog(Component parent, String title, String msg, @MC(intValues = {JOptionPane. INFORMATION_MESSAGE,JOptionPane. WARNING_MESSAGE,JOptionPane. ERROR_MESSAGE,JOptionPane. QUESTION_MESSAGE,JOptionPane. PLAIN_MESSAGE}) int msgType,Icon icon ){
//        JLabel msgLabel = new JLabel(msg);
//        msgLabel.setFont(FontUtils.Heading_3_Plain);
//        if (icon == null){
//        }else {
//        }
//
//    }

    public static void showMessageDialog(Component parent, String title, String msg,
                                         @MC(intValues = {JOptionPane.INFORMATION_MESSAGE, JOptionPane.WARNING_MESSAGE,
                                                 JOptionPane.ERROR_MESSAGE, JOptionPane.QUESTION_MESSAGE,
                                                 JOptionPane.PLAIN_MESSAGE}) int msgType, Icon icon) {

        // Wrap message in HTML for multi-line support

        if (icon == null) {
            JOptionPane.showMessageDialog(parent, messageLabel(msg), title, msgType);
        } else {
            JOptionPane.showMessageDialog(parent, messageLabel(msg), title, msgType, icon);
        }
    }

    public static int showOptionDialog(Component parent, String title, String message, @MCOptionTypes(intValues = {JOptionPane. DEFAULT_OPTION,JOptionPane. YES_NO_OPTION,JOptionPane. YES_NO_CANCEL_OPTION,JOptionPane. OK_CANCEL_OPTION}) int optionType,
                                       @MC(intValues = {JOptionPane.INFORMATION_MESSAGE, JOptionPane.WARNING_MESSAGE,
                                               JOptionPane.ERROR_MESSAGE, JOptionPane.QUESTION_MESSAGE,
                                               JOptionPane.PLAIN_MESSAGE}) int msgType, Icon icon,String[] options, String defaultSelected){

//        ArrayList<JButton> buttons = new ArrayList<>();
//        int defSel = 0;
//        for (String op : options){
//            buttons.add(dialogButton(op));
//            if (op.equals(defaultSelected)){
//                defSel = buttons.size()-1;
//            }
//        }
        int val = JOptionPane.showOptionDialog(parent,messageLabel(message),title,optionType,msgType,icon,options,defaultSelected);
        return val;
    }

    public static JLabel messageLabel(String msg){
        JLabel msgLabel = new JLabel("<html><body style='text-align: center;'>" + msg.replace("\n", "<br>") + "</body></html>");
        msgLabel.setFont(FontUtils.Heading_3_Plain);
        return msgLabel;
    }

    public static JButton dialogButton(String title){
        JButton button = new JButton(title);
        StylishButtons.styleButton(button);
        return button;
    }

    public void showCard(String cardName) {
        mainPanel.removeAll();  // 🔄 Clear existing content before adding a new one
        if (cardName.equals("Login")) {
            mainPanel.add(userLoginPanel(), "Login");
        } else if (cardName.equals("SignUp")) {
            mainPanel.add(userSignUpPanel(), "SignUp");
        } else if (cardName.equals("ShowAllUsers")) {
            mainPanel.add(showAllUsersPanel(), "ShowAllUsers");
        } else if (cardName.equals("UserHome")) {
            mainPanel.add(user.userHomePanel(), "UserHome");
        }

        cardLayout.show(mainPanel, cardName);
        mainPanel.revalidate();  // 🔄 Recalculate layout
        mainPanel.repaint();     // 🔄 Repaint UI
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
            StylishButtons.styleButton(this);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Login");
            return this;
        }
    }
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String userAccountNumber;
        private JTable table;

        public ButtonEditor(JCheckBox checkBox, JTable table) {
            super(checkBox);
            this.table = table;
            button = new JButton("Login");
            StylishButtons.styleButton(button);
            button.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (row != -1) {
                    userAccountNumber = (String) table.getValueAt(row, 0); // Get AC No
                    performLogin(userAccountNumber);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            userAccountNumber = (String) table.getValueAt(row, 0); // Get AC No
            button.setText("Login");
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Login";
        }

        private void performLogin(String userAccountNumber) {
            UserModel user = databaseHelper.getUserTable().fetchByAccountNumber(databaseHelper.getConnection(), userAccountNumber);
            if (user != null) {
                showCard("Login");
                autoLoginUser = user;
                autoLoginThread.start();
            } else {
                ATM.showMessageDialog(ATM.this, "Error", "User not found!", JOptionPane.ERROR_MESSAGE, null);
            }
        }
    }

}



