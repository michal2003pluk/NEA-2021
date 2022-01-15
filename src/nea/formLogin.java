package nea;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class formLogin extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(formLogin.class.getName());
    private static int attemptsRemaining = 3;

    // Sets up the form and loads all the company images into the form.
    public formLogin() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Hides the text in the password JTextField
        txtPassword.setEchoChar('•');

        // Initial value of the caps lock key
        boolean isCapsLockOn = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
        lblCapsLock.setVisible(isCapsLockOn);

        // NEA OBJECTIVE 2.4: Notify the user if they accidentally clicked their Caps Lock key as this
        // may cause them to enter their password in wrong because passwords are case-sensitive.
        // KeyListener for when the user presses the caps lock key
        KeyListener CapsLock = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                boolean isCapsLockOn = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
                lblCapsLock.setVisible(isCapsLockOn);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                boolean isCapsLockOn = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
                lblCapsLock.setVisible(isCapsLockOn);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                boolean isCapsLockOn = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
                lblCapsLock.setVisible(isCapsLockOn);
            }

        };

        // Adds the key listener to the username and password field
        txtUsername.addKeyListener(CapsLock);
        txtPassword.addKeyListener(CapsLock);

        // Init
        BufferedImage imgMainLogo = null;
        BufferedImage imgLogos = null;
        // Loads the company images
        try {
            imgMainLogo = ImageIO.read(new File("Main Logo.png"));
            imgLogos = ImageIO.read(new File("Logos.png"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException");
        }

        // Scales the loaded buffered images and turns them into image icons
        Image scaledMainLogo = imgMainLogo.getScaledInstance(lblMainLogo.getWidth(), lblMainLogo.getHeight(), Image.SCALE_SMOOTH);
        Image scaledLogos = imgLogos.getScaledInstance(lblLogos.getWidth(), lblLogos.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon imageIcon1 = new ImageIcon(scaledMainLogo);
        ImageIcon imageIcon2 = new ImageIcon(scaledLogos);

        // Adds the image icons to the label.
        lblMainLogo.setIcon(imageIcon1);
        lblLogos.setIcon(imageIcon2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pContainer = new javax.swing.JPanel();
        lblMainLogo = new javax.swing.JLabel();
        lblLogos = new javax.swing.JLabel();
        pTextBackground = new javax.swing.JPanel();
        lblCompanyName = new javax.swing.JLabel();
        lblUsername = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblCapsLock = new javax.swing.JLabel();
        cbPassword = new javax.swing.JCheckBox();
        btnLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");

        pTextBackground.setBackground(new java.awt.Color(255, 255, 255));

        lblCompanyName.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblCompanyName.setForeground(new java.awt.Color(39, 245, 103));
        lblCompanyName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCompanyName.setText("ROBIP-PGHEM-ENGINEERING");
        lblCompanyName.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout pTextBackgroundLayout = new javax.swing.GroupLayout(pTextBackground);
        pTextBackground.setLayout(pTextBackgroundLayout);
        pTextBackgroundLayout.setHorizontalGroup(
            pTextBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblCompanyName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pTextBackgroundLayout.setVerticalGroup(
            pTextBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pTextBackgroundLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(lblCompanyName))
        );

        lblUsername.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblUsername.setText("Username:");

        txtUsername.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtUsername.setNextFocusableComponent(txtPassword);

        lblPassword.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblPassword.setText("Password:");

        txtPassword.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        txtPassword.setNextFocusableComponent(cbPassword);

        lblCapsLock.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblCapsLock.setForeground(new java.awt.Color(255, 39, 93));
        lblCapsLock.setText("Caps Lock is on");

        cbPassword.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        cbPassword.setText("Show Password");
        cbPassword.setNextFocusableComponent(btnLogin);
        cbPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPasswordActionPerformed(evt);
            }
        });

        btnLogin.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.setNextFocusableComponent(txtUsername);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pContainerLayout = new javax.swing.GroupLayout(pContainer);
        pContainer.setLayout(pContainerLayout);
        pContainerLayout.setHorizontalGroup(
            pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pContainerLayout.createSequentialGroup()
                .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pContainerLayout.createSequentialGroup()
                        .addComponent(lblCapsLock)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbPassword))
                    .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(pContainerLayout.createSequentialGroup()
                            .addComponent(lblMainLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(lblLogos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(pContainerLayout.createSequentialGroup()
                            .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblPassword)
                                .addComponent(lblUsername))
                            .addGap(14, 14, 14)
                            .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtUsername)
                                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pContainerLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
            .addGroup(pContainerLayout.createSequentialGroup()
                .addComponent(pTextBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pContainerLayout.setVerticalGroup(
            pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pContainerLayout.createSequentialGroup()
                .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMainLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblLogos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addComponent(pTextBackground, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsername)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addGroup(pContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbPassword)
                    .addComponent(lblCapsLock))
                .addGap(18, 18, 18)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(pContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(pContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Fetches password from the password field and returns the string
    private String getPassword() {
        char[] passwordArray = txtPassword.getPassword();
        return new String(passwordArray);
    }

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // User input and hashing
        String inputUsername = txtUsername.getText();
        String inputPassword = getPassword();
        String hashedInputPassword = Utility.hash(inputPassword);

        // Init
        Boolean found = false;
        int fetchedID = -1;

        try (Connection conn = sqlManager.openConnection()) {
            // Query Setup & Execution
            String query = "SELECT employee_id FROM tblEmployee WHERE username = ? AND password_hash = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, inputUsername);
            pstmt.setString(2, hashedInputPassword);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Gets the id of whoever logged in
                fetchedID = rs.getInt(1);
                found = true;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException");
        }

        if (!found) {
            unsuccessfulLogin();
        } else {
            successfulLogin(fetchedID);
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void unsuccessfulLogin() {
        attemptsRemaining--; // Initialised to 3 at the top of the class

        // NEA OBJECTIVE 2.2: Allow a maximum of three attempts to enter the correct username & password.
        if (attemptsRemaining == 0) {
            ErrorMsg.throwCustomError("You have run out of login attempts", "No login attempts remaining");
            // Disables user input since they have run out of login attempts
            incorrectPasswordLock();
        } else {
            // NEA OBJECTIVE 2.1: Display a message dialog if the user enters an incorrect password.
            ErrorMsg.throwError(ErrorMsg.INVALID_LOGIN_DETAILS_ERROR);
        }
    }

    private void successfulLogin(int fetchedID) {
        sqlManager.updateLastLogin(fetchedID);

        formMainMenu MainMenu = new formMainMenu().getFrame();

        // Updates the values for the class which stores the logged in user
        LoggedInUser.setID(fetchedID);
        LoggedInUser.updateAdminStatus();

        // Updates label to show who logged in
        MainMenu.whoLoggedIn();
        // If the user is an admin then they will have permission to all the management features
        MainMenu.checkWhetherAdmin();

        // Makes the main menu visible and closes login form
        MainMenu.setVisible(true);
        this.dispose();
    }

    private void cbPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPasswordActionPerformed
        // Reveals the password if the user selected the CheckBox
        if (cbPassword.isSelected()) {
            txtPassword.setEchoChar((char) 0);
        } else {
            txtPassword.setEchoChar('•');

        }
    }//GEN-LAST:event_cbPasswordActionPerformed

    private void incorrectPasswordLock() {
        // Disables JTextField and Login button after 3 unsuccessful login attempts
        txtUsername.setEditable(false);
        txtPassword.setEditable(false);
        btnLogin.setEnabled(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(formLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formLogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JCheckBox cbPassword;
    private javax.swing.JLabel lblCapsLock;
    private javax.swing.JLabel lblCompanyName;
    private javax.swing.JLabel lblLogos;
    private javax.swing.JLabel lblMainLogo;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JPanel pContainer;
    private javax.swing.JPanel pTextBackground;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
