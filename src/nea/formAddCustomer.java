/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nea;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Michal
 */
public class formAddCustomer extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(formAddCustomer.class.getName());

    // Previous forms the user could have come from
    formManageCustomers previousForm1 = null;
    formNewInvoice previousForm2 = null;
    formNewQuotation previousForm3 = null;

    public formAddCustomer() {
        initComponents();
        // Don't close the entire program if the AddCustomer window is closed
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Loads all the possible customer categories into combo box
        loadCustomerCategoriesIntoCB();

        // Gets the ID for the new customer
        int CustomerID = sqlManager.getNextPKValue("tblCustomer", "customer_id");
        txtCustomerID.setText(String.valueOf(CustomerID));

        // Listens for a change in the selectedIndex
        cbCategory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // If the user selected the last item  i.e., 'Add a new category...'
                if (cbCategory.getSelectedIndex() == cbCategory.getItemCount() - 1) {
                    // Offers user the option to add a new customer category
                    String addedCategory = sqlManager.addNewCustomerCategory();

                    // If category was added successfully
                    if (addedCategory != null) {
                        // Refreshes ComboBox so the new category is visible
                        loadCustomerCategoriesIntoCB();
                        // Sets the selectedItem to whatever category the user just added
                        cbCategory.setSelectedItem(addedCategory);
                    } else {
                        cbCategory.setSelectedIndex(0);
                    }
                }
            }
        });

        // Notifies the previous form that the user is no longer adding a new customer.
        this.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                if (previousForm1 != null) {
                    previousForm1.CurrentlyAddingCustomer = false;
                }
                if (previousForm2 != null) {
                    previousForm2.CurrentlyAddingCustomer = false;
                }
                if (previousForm3 != null) {
                    previousForm3.CurrentlyAddingCustomer = false;
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        // Updates the fullName JTextField each time the input forename and surname changes
        DocumentListener dListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                lblFullName.setText(txtForename.getText() + " " + txtSurname.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                lblFullName.setText(txtForename.getText() + " " + txtSurname.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        };
        txtForename.getDocument().addDocumentListener(dListener);
        txtSurname.getDocument().addDocumentListener(dListener);
    }

    private void goBack() {
        if (previousForm1 != null) {
            // Refreshes the customer table in the previous form
            previousForm1.loadCustomers();
            previousForm1.CurrentlyAddingCustomer = false;
        }
        if (previousForm2 != null) {
            // Refreshes the customer combo box in the previous form
            previousForm2.loadCustomersIntoCB();
            previousForm2.CurrentlyAddingCustomer = false;
        }
        if (previousForm3 != null) {
            // Refreshes the customer combo box in the previous form
            previousForm3.loadCustomersIntoCB();
            previousForm3.CurrentlyAddingCustomer = false;
        }
    }

    // Used when the form is opened from within another form
    public formAddCustomer getFrame() {
        return this;
    }

    // Loads all the customer categories from the DB into the ComboBox
    private void loadCustomerCategoriesIntoCB() {
        // Clears ComboBox
        cbCategory.removeAllItems();

        try (Connection conn = sqlManager.openConnection()) {
            // Query Setup & Execution
            String query = "SELECT category_name FROM tblCustomerCategory";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                // Adds category to the ComboBox
                cbCategory.addItem(rs.getString(1));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException");
        }

        cbCategory.addItem("Add a new category...");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFullName = new javax.swing.JLabel();
        lblCustomerID = new javax.swing.JLabel();
        lblForename = new javax.swing.JLabel();
        lblSurname = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblCounty = new javax.swing.JLabel();
        lblPostcode = new javax.swing.JLabel();
        lblPhoneNumber = new javax.swing.JLabel();
        lblEmailAddress = new javax.swing.JLabel();
        lblCustomerCategory = new javax.swing.JLabel();
        txtCustomerID = new javax.swing.JTextField();
        txtForename = new javax.swing.JTextField();
        txtSurname = new javax.swing.JTextField();
        txtAddress1 = new javax.swing.JTextField();
        txtAddress2 = new javax.swing.JTextField();
        txtAddress3 = new javax.swing.JTextField();
        txtCounty = new javax.swing.JTextField();
        txtPostcode = new javax.swing.JTextField();
        txtPhoneNumber = new javax.swing.JTextField();
        txtEmailAddress = new javax.swing.JTextField();
        cbCategory = new javax.swing.JComboBox<>();
        btnAddCustomer = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Add Customer");

        lblFullName.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblFullName.setText(" ");
        lblFullName.setToolTipText("");

        lblCustomerID.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblCustomerID.setText("Customer ID:");

        lblForename.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblForename.setText("Forename:");

        lblSurname.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblSurname.setText("Surname:");

        lblAddress.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblAddress.setText("Address:");

        lblCounty.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblCounty.setText("County:");

        lblPostcode.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblPostcode.setText("Postcode:");
        lblPostcode.setToolTipText("");

        lblPhoneNumber.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblPhoneNumber.setText("Phone number:");

        lblEmailAddress.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblEmailAddress.setText("Email address:");

        lblCustomerCategory.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblCustomerCategory.setText("Category:");

        txtCustomerID.setEditable(false);

        txtAddress1.setToolTipText("");

        btnAddCustomer.setFont(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        btnAddCustomer.setText("Add customer");
        btnAddCustomer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddCustomerActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(lblCustomerID)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtCustomerID, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(36, 36, 36)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblForename)
                                .addComponent(lblSurname))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtForename, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                .addComponent(txtSurname)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(52, 52, 52)
                            .addComponent(lblAddress)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtAddress3)
                                .addComponent(txtAddress2)
                                .addComponent(txtAddress1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblPhoneNumber)
                                .addComponent(lblEmailAddress)
                                .addComponent(lblPostcode)
                                .addComponent(lblCounty)
                                .addComponent(lblCustomerCategory))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtPostcode, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCounty, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtEmailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(117, 117, 117)
                                    .addComponent(btnAddCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(lblFullName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(10, Short.MAX_VALUE)
                .addComponent(lblFullName)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomerID)
                    .addComponent(txtCustomerID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblForename)
                    .addComponent(txtForename, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSurname)
                    .addComponent(txtSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddress)
                    .addComponent(txtAddress1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAddress2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtAddress3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCounty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCounty))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPostcode)
                            .addComponent(txtPostcode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPhoneNumber)
                            .addComponent(txtPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmailAddress)
                            .addComponent(txtEmailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCustomerCategory)
                            .addComponent(cbCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(btnAddCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddCustomerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddCustomerActionPerformed
        JTextField[] inputFields = {txtForename, txtSurname, txtAddress1, txtCounty, txtPostcode, txtPhoneNumber, txtEmailAddress};

        // Checks if any of the input fields are empty
        if (Utility.countEmptyFields(inputFields) != 0) {
            ErrorMsg.throwError(ErrorMsg.EMPTY_INPUT_FIELD_ERROR);

        } else if (validInputs()) {
            // Asks user whether they really want to add this customer
            int YesNo = JOptionPane.showConfirmDialog(null, "Are you sure you want to add this customer?",
                    "Add new customer", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);

            // If response is yes
            if (YesNo == 0) {
                String query = "INSERT into tblCustomer "
                        + "(customer_id, forename, surname, address1, address2,"
                        + " address3, county, postcode, phone_number,"
                        + " email_address, category_id)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

                // NEA OBJECTIVE 6.1: Allow user to add a new customer to the system, automatically incrementing their customerID.
                int newCustomerID = sqlManager.getNextPKValue("tblCustomer", "customer_id");

                try (Connection conn = sqlManager.openConnection()) {
                    // Query Setup & Execution
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, newCustomerID);
                    pstmt.setString(2, txtForename.getText());
                    pstmt.setString(3, txtSurname.getText());
                    pstmt.setString(4, txtAddress1.getText());
                    // If address2 or address3 are empty then they are replaced by null instead of ""
                    pstmt.setString(5, (txtAddress2.getText().isEmpty() ? null : txtAddress2.getText()));
                    pstmt.setString(6, (txtAddress3.getText().isEmpty() ? null : txtAddress3.getText()));
                    pstmt.setString(7, txtCounty.getText());
                    pstmt.setString(8, txtPostcode.getText());
                    pstmt.setString(9, txtPhoneNumber.getText());
                    pstmt.setString(10, txtEmailAddress.getText());

                    String selectedCategory = cbCategory.getSelectedItem().toString();
                    pstmt.setInt(11, sqlManager.getIDofCategory("tblCustomerCategory", selectedCategory));

                    int rowsAffected = pstmt.executeUpdate();
                    logger.log(Level.INFO, rowsAffected + " rows inserted.");

                } catch (SQLException e) {
                    logger.log(Level.SEVERE, "SQLException");
                }

                goBack();

                // Closes the AddCustomer form (current form)
                this.dispose();
            }
        }
    }//GEN-LAST:event_btnAddCustomerActionPerformed

    // NEA OBJECTIVE 6.2: Automatically check whether certain fields are empty and check whether
    // the entered information is valid. If not, then display an error message to the user.
    // Validates input lengths against the max lengths allowed in the DBMS
    private boolean validInputs() {
        boolean valid = false;

        if (txtForename.getText().length() > sqlManager.getMaxColumnLength("tblCustomer", "forename")) {
            ErrorMsg.throwError(ErrorMsg.INPUT_LENGTH_ERROR_LONG, "forename");

        } else if (txtSurname.getText().length() > sqlManager.getMaxColumnLength("tblCustomer", "surname")) {
            ErrorMsg.throwError(ErrorMsg.INPUT_LENGTH_ERROR_LONG, "surname");

        } else if (txtAddress1.getText().length() > sqlManager.getMaxColumnLength("tblCustomer", "address1")) {
            ErrorMsg.throwError(ErrorMsg.INPUT_LENGTH_ERROR_LONG, "address line 1");

        } else if (txtAddress2.getText().length() > sqlManager.getMaxColumnLength("tblCustomer", "address2")) {
            ErrorMsg.throwError(ErrorMsg.INPUT_LENGTH_ERROR_LONG, "address line 2");

        } else if (txtAddress3.getText().length() > sqlManager.getMaxColumnLength("tblCustomer", "address3")) {
            ErrorMsg.throwError(ErrorMsg.INPUT_LENGTH_ERROR_LONG, "address line 3");

        } else if (txtCounty.getText().length() > sqlManager.getMaxColumnLength("tblCustomer", "county")) {
            ErrorMsg.throwError(ErrorMsg.INPUT_LENGTH_ERROR_LONG, "county");

        } else if (txtPostcode.getText().length() > sqlManager.getMaxColumnLength("tblCustomer", "postcode")) {
            ErrorMsg.throwError(ErrorMsg.INPUT_LENGTH_ERROR_LONG, "postcode");

        } else if (txtPhoneNumber.getText().length() > sqlManager.getMaxColumnLength("tblCustomer", "phone_number")) {
            ErrorMsg.throwError(ErrorMsg.INPUT_LENGTH_ERROR_LONG, "phone number");

        } else if (!Pattern.matches("^[0-9]+$", txtPhoneNumber.getText())) {
            ErrorMsg.throwError(ErrorMsg.NUMBER_FORMAT_ERROR, "The phone number must not contain letters");

        } else if (txtEmailAddress.getText().length() > sqlManager.getMaxColumnLength("tblCustomer", "email_address")) {
            ErrorMsg.throwError(ErrorMsg.INPUT_LENGTH_ERROR_LONG, "email address");

        } else {
            // If all inputs passed the validity checks then boolean set to true
            valid = true;
        }

        return valid;
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
            java.util.logging.Logger.getLogger(formAddCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formAddCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formAddCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formAddCustomer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formAddCustomer().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddCustomer;
    private javax.swing.JComboBox<String> cbCategory;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblCounty;
    private javax.swing.JLabel lblCustomerCategory;
    private javax.swing.JLabel lblCustomerID;
    private javax.swing.JLabel lblEmailAddress;
    private javax.swing.JLabel lblForename;
    private javax.swing.JLabel lblFullName;
    private javax.swing.JLabel lblPhoneNumber;
    private javax.swing.JLabel lblPostcode;
    private javax.swing.JLabel lblSurname;
    private javax.swing.JTextField txtAddress1;
    private javax.swing.JTextField txtAddress2;
    private javax.swing.JTextField txtAddress3;
    private javax.swing.JTextField txtCounty;
    private javax.swing.JTextField txtCustomerID;
    private javax.swing.JTextField txtEmailAddress;
    private javax.swing.JTextField txtForename;
    private javax.swing.JTextField txtPhoneNumber;
    private javax.swing.JTextField txtPostcode;
    private javax.swing.JTextField txtSurname;
    // End of variables declaration//GEN-END:variables
}
