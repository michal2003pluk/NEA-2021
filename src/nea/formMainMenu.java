/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nea;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;

/**
 *
 * @author Michal
 */
public class formMainMenu extends javax.swing.JFrame {

    /**
     * Creates new form formMainMenu
     */
    Connection conn = null;                                         // Stores the connection object
    static int loggedIn_UserID = 0;                                 // id of whoever is currently logged in
    
    
    public formMainMenu() {
        initComponents();
        this.setLocationRelativeTo(null);                           // Positions form in the centre of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    // Fetches the full name of whoever is currently logged in and updates label
    public void whoLoggedIn() {
        conn = sqlManager.openConnection();                         // Opens connection to the DB
        String query = "SELECT forename, surname FROM tblEmployees WHERE employee_id = ?";
        Boolean found = false;                                      //
        String fetchedForename = "", fetchedSurname = "";           // Init
        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, loggedIn_UserID);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {                                        // Once the user under the id is found
                fetchedForename = rs.getString(1);                  //
                fetchedSurname = rs.getString(2);                   // Stores the full name into variables
                found = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sqlManager.closeConnection(conn);                           // Closes connection to DB
        
        if (found) {
            System.out.println("-------------------------------");
            System.out.println(loggedIn_UserID);                    // Debug code
            System.out.println(fetchedForename);
            System.out.println(fetchedSurname);
            lblLoggedInAs.setText("Logged in as " + fetchedForename + " " + fetchedSurname);    // Updates label to say who is currently logged in
        } else {
            System.out.println("Error logging in.");
            // This point should theoretically not be reachable as the user would not be able to login if the user's name data didnt exist
        }
  
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblLoggedInAs = new javax.swing.JLabel();
        pManagement = new javax.swing.JPanel();
        btnManageItemCategories = new javax.swing.JButton();
        btnManageEmployees = new javax.swing.JButton();
        btnManageCustomers = new javax.swing.JButton();
        btnManageCustomerCategories = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Main Menu");

        lblLoggedInAs.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblLoggedInAs.setText("Logged in as");

        pManagement.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Management", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 1, 24))); // NOI18N

        btnManageItemCategories.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnManageItemCategories.setText("Item Categories");
        btnManageItemCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageItemCategoriesActionPerformed(evt);
            }
        });

        btnManageEmployees.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnManageEmployees.setText("Employees");
        btnManageEmployees.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageEmployeesActionPerformed(evt);
            }
        });

        btnManageCustomers.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnManageCustomers.setText("Customers");
        btnManageCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageCustomersActionPerformed(evt);
            }
        });

        btnManageCustomerCategories.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnManageCustomerCategories.setText("Customer Categories");
        btnManageCustomerCategories.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnManageCustomerCategoriesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pManagementLayout = new javax.swing.GroupLayout(pManagement);
        pManagement.setLayout(pManagementLayout);
        pManagementLayout.setHorizontalGroup(
            pManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pManagementLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnManageCustomers, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnManageEmployees, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnManageItemCategories, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                    .addComponent(btnManageCustomerCategories, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE))
                .addContainerGap())
        );
        pManagementLayout.setVerticalGroup(
            pManagementLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pManagementLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnManageCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnManageEmployees, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnManageItemCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnManageCustomerCategories, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLoggedInAs)
                    .addComponent(pManagement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(314, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblLoggedInAs)
                .addGap(29, 29, 29)
                .addComponent(pManagement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(179, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnManageCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageCustomersActionPerformed
            formManageCustomers form = new formManageCustomers().getFrame();                    // Opens new Customer Management form
            form.previousForm = this;                                           // Makes this form the previousForm so the back buttons work
            form.sp = "";                                                       // Empties search parameter in next form
            form.loadCustomers();                                               // Load all the customers into the table
            this.setVisible(false);                                             // Makes main menu invisible
            form.setVisible(true);                                              // makes the next form visible
    }//GEN-LAST:event_btnManageCustomersActionPerformed

    private void btnManageEmployeesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageEmployeesActionPerformed
            formManageEmployees form = new formManageEmployees().getFrame();                    // Opens new Employee Management form
            form.previousForm = this;                                           // Makes this form the previousForm so the back buttons work
            this.setVisible(false);                                             // Makes main menu invisible
            form.setVisible(true);                                              // makes the next form visible
    }//GEN-LAST:event_btnManageEmployeesActionPerformed

    private void btnManageItemCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageItemCategoriesActionPerformed
            formManageItemCategories form = new formManageItemCategories().getFrame();          // Opens new Item Category Management form
            form.previousForm = this;                                           // Makes this form the previousForm so the back buttons work
            this.setVisible(false);                                             // Makes main menu invisible
            form.setVisible(true);                                              // makes the next form visible
    }//GEN-LAST:event_btnManageItemCategoriesActionPerformed

    private void btnManageCustomerCategoriesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnManageCustomerCategoriesActionPerformed
            formManageCustomerCategories form = new formManageCustomerCategories().getFrame();  // Opens new Customer Category Management form
            form.previousForm = this;                                           // Makes this form the previousForm so the back buttons work
            this.setVisible(false);                                             // Makes main menu invisible
            form.setVisible(true);                                              // makes the next form visible
    }//GEN-LAST:event_btnManageCustomerCategoriesActionPerformed

    /**
     * @param args the command line arguments
     */
    
    public formMainMenu getFrame() {
        return this;
    }
    
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
            java.util.logging.Logger.getLogger(formMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formMainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnManageCustomerCategories;
    private javax.swing.JButton btnManageCustomers;
    private javax.swing.JButton btnManageEmployees;
    private javax.swing.JButton btnManageItemCategories;
    private javax.swing.JLabel lblLoggedInAs;
    private javax.swing.JPanel pManagement;
    // End of variables declaration//GEN-END:variables
}
