/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nea;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Michal
 */
public class formManageItemCategories extends javax.swing.JFrame {

    /**
     * Creates new form formManageItemCategories
     */
    formMainMenu previousForm = null;
    Connection conn = nea.formLogin.conn;
    DefaultTableModel model; // Init

    public formManageItemCategories() {
        initComponents(); // Built in process
        model = (DefaultTableModel) jTable_ItemCategories.getModel(); // Fetches the table model of the table component

        JTableHeader header = jTable_ItemCategories.getTableHeader();
        header.setFont(new Font("Dialog", Font.PLAIN, 14));         // Makes the font of the of header in the table larger - this may just be a windows 1440p scaling issue on my end

        loadCategories(); // Loads all the categories from the DB into the table component in the form
    }

    public formManageItemCategories getFrame() {
        return this;
    }

    public void loadCategories() {
        model.setRowCount(0); // Empties the table
        String query = "SELECT item_category_id, category_name, date_created FROM tblItemCategories";
        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                System.out.println("-------------------------------");
                System.out.println(rs.getString(1));
                System.out.println(rs.getString(2));    // For debugging, shows each category that was added to the table
                System.out.println(rs.getString(3));

                model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

        jButton4 = new javax.swing.JButton();
        lblManageItemCategories = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_ItemCategories = new javax.swing.JTable();
        btnAddNew = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        btnExit = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        jButton4.setText("jButton4");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Item Category Management");

        lblManageItemCategories.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblManageItemCategories.setText("Manage Item Categories");

        jTable_ItemCategories.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTable_ItemCategories.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Category ID", "Category name", "Date created"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable_ItemCategories);

        btnAddNew.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnAddNew.setText("Add New");
        btnAddNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewActionPerformed(evt);
            }
        });

        btnEdit.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnRemove.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnExit.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnExit.setText("Exit");
        btnExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAddNew, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                        .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblManageItemCategories)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblManageItemCategories)
                    .addComponent(btnExit)
                    .addComponent(btnBack))
                .addGap(41, 41, 41)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddNew, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void btnAddNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewActionPerformed
        String inputCategory = Utility.StringInputDialog("What should the name of the new category be?", "Add new category");
        if (inputCategory != null) { // If the dialog input was valid 
            inputCategory = inputCategory.trim(); // Removes all leading and trailing whitespace characters

            if (sqlManager.RecordExists(conn, "tblItemCategories", "category_name", inputCategory)) { // Checks if category already exists in DB
                System.out.println("-------------------------------");
                System.out.println("Category under this name already exists");

            } else {

                String query = "INSERT INTO tblItemCategories (item_category_id, category_name, date_created) VALUES (?,?,?)";
                try {
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    int newID = sqlManager.getNextPKValue(conn, "tblItemCategories", "item_category_id");
                    pstmt.setInt(1, newID); // Gets the next available PK value
                    pstmt.setString(2, inputCategory);
                    pstmt.setString(3, Utility.getCurrentDate());

                    int rowsAffected = pstmt.executeUpdate();
                    System.out.println("-------------------------------");
                    System.out.println(rowsAffected + " row inserted.");
                    loadCategories(); // Refreshes Table

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }//GEN-LAST:event_btnAddNewActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        int row = jTable_ItemCategories.getSelectedRow();

        if (row == -1) { // If no row is selected
            System.out.println("-------------------------------");
            System.out.println("No row selected");
        } else {
            String string_id = model.getValueAt(row, 0).toString(); // Gets the values from the selected row in the table as strings
            String category = model.getValueAt(row, 1).toString();

            int id = Utility.StringToInt(string_id);

            if (id == 1) { // Checks if the user is trying to edit the first row - this is the default row
                System.out.println("-------------------------------");
                System.out.println("This is the default row and cannot be removed");
            } else {

                int YesNo = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove the category - '" + category + "'?", "Remove Category", JOptionPane.WARNING_MESSAGE, JOptionPane.YES_NO_OPTION);
                if (YesNo == 0) { // If response is yes
                    System.out.println("-------------------------------");
                    System.out.println("Removing category " + string_id + " - " + category + ".");

                    sqlManager.removeRecord(conn, "tblItemCategories", "item_category_id", id);
                    loadCategories(); //Refreshes table since a record was removed
                }

            }
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int row = jTable_ItemCategories.getSelectedRow();

        if (row == -1) { // If no row is selected
            System.out.println("-------------------------------");
            System.out.println("No row selected");
        } else {
            String string_id = model.getValueAt(row, 0).toString(); // Gets the values from the selected row in the table as strings
            String category = model.getValueAt(row, 1).toString();

            int id = Utility.StringToInt(string_id);

            if (id == 1) { // Checks if the user is trying to edit the first row - this is the default row
                System.out.println("-------------------------------");
                System.out.println("This is the default row and cannot be edited");
                
            } else {
                String inputCategory = Utility.StringInputDialog("Current name:  '" + category + "'", "Edit category name");

                if (inputCategory != null) { // If the dialog input was valid   
                    inputCategory = inputCategory.trim(); // Removes all leading and trailing whitespace characters
                    
                    if (sqlManager.RecordExists(conn, "tblItemCategories", "category_name", inputCategory)) { // Checks if category already exists in DB
                        
                        System.out.println("-------------------------------");
                        System.out.println("Category under this name already exists");
                        // # TODO reopen dialog
                        // # TODO Allow the user to merge the two categories together under the wanted name

                    } else {

                        String query = "UPDATE tblItemCategories SET category_name = ? WHERE item_category_id = ?";
                        PreparedStatement pstmt = null;
                        try {
                            pstmt = conn.prepareStatement(query);
                            pstmt.setString(1, inputCategory);
                            pstmt.setInt(2, id);

                            int rowsAffected = pstmt.executeUpdate();
                            System.out.println(rowsAffected + " row updated.");
                            loadCategories(); //Refreshes table since a record was updated
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitActionPerformed
        sqlManager.closeConnection(conn);
        this.dispose();
        System.exit(0);
    }//GEN-LAST:event_btnExitActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        previousForm.setVisible(true);
        this.dispose();
        
    }//GEN-LAST:event_btnBackActionPerformed

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
            java.util.logging.Logger.getLogger(formManageItemCategories.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formManageItemCategories.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formManageItemCategories.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formManageItemCategories.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formManageItemCategories().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNew;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_ItemCategories;
    private javax.swing.JLabel lblManageItemCategories;
    // End of variables declaration//GEN-END:variables
}
