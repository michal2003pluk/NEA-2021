/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nea;

import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Michal
 */
public class formManageEmployees extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(formManageEmployees.class.getName());
    formMainMenu previousForm = null;

    // Init
    DefaultTableModel model;
    private String sp = "";

    // Whether the user is currently viewing an employee in another form or adding a new employee 
    formOneEmployee Employee_in_view = null;
    public boolean CurrentlyAddingEmployee = false;

    public formManageEmployees() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fetches Table model and makes table non-editable
        model = (DefaultTableModel) jTable_Employees.getModel();
        jTable_Employees.setDefaultEditor(Object.class, null);

        // Sets up the table header to be a bit larger
        JTableHeader header = jTable_Employees.getTableHeader();
        header.setFont(new Font("Dialog", Font.PLAIN, 14));

        // When the user clicks on a row in the table
        jTable_Employees.addMouseListener(new MouseListener() {
            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                // Gets the id of the employee which is currently selected in the table
                int selectedID = getSelectedEmployee();
                if (selectedID != -1) {
                    if (Employee_in_view != null) {
                        // If the user is viewing another employee then that form is closed
                        Employee_in_view.dispose();
                    }

                    formOneEmployee form = new formOneEmployee().getFrame();
                    form.setVisible(true);

                    // Positions the opened form to the right of the current form
                    Point locPoint = formManageEmployees.this.getLocationOnScreen();
                    locPoint.translate(formManageEmployees.this.getWidth(), 0);
                    form.setLocation(locPoint);

                    // Loads employee into the other form and sets up previousForm variable
                    form.EmployeeID = selectedID;
                    form.previousForm = formManageEmployees.this;
                    form.loadEmployee();

                    // Sets employee in view variable to the employee which is being viewed
                    Employee_in_view = form;

                    // Makes sure logged in user cannot remove themself from the system or make themself an admin in the other form
                    if (selectedID == LoggedInUser.getID()) {
                        form.disableButtonsForSelfChanges();
                    }
                }
            }
        });

        // When the user changes their search in the search box
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Updates the search parameter and refreshes the table
                sp = txtSearch.getText();
                loadEmployees();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Updates the search parameter and refreshes the table
                sp = txtSearch.getText();
                loadEmployees();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

        });

        //Loads the initial data
        loadEmployees();

        // Adjusting the header widths
        jTable_Employees = Utility.setColumnWidths(jTable_Employees, new int[]{40, 120, 100, 175, 120});
    }

    // Used when the form is opened from within another form
    public formManageEmployees getFrame() {
        return this;
    }

    // Loads all the employees into the table, the results are filtered using the searchParameter (sp)
    public void loadEmployees() {
        // Empties the table
        model.setRowCount(0);

        String query = "SELECT employee_id, CONCAT(forename,' ', surname) AS FullName, phone_number, email_address FROM tblEmployee";
        // If the user entered a search into the search box, the WHERE clause is adjusted
        if (!sp.isEmpty()) {
            query += " WHERE";
            query += " employee_id LIKE '%" + sp + "%'";
            query += " OR CONCAT(forename,' ', surname) LIKE '%" + sp + "%'";
            query += " OR phone_number LIKE '%" + sp + "%'";
            query += " OR email_address LIKE '%" + sp + "%'";
        }
        query += " ORDER BY employee_id";

        try (Connection conn = sqlManager.openConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Counts the amount of employees which are being shown
            int employeeCounter = 0;
            while (rs.next()) {
                String last_login_date = sqlManager.getLastLogin(Utility.StringToInt(rs.getString(1)));

                // Adds the employee from the DB to the table
                model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), last_login_date});
                employeeCounter++;
            }

            // Updates employee counter label
            lblEmployeeCount.setText("Number of employees: " + String.valueOf(employeeCounter));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException");
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

        lblManageEmployees = new javax.swing.JLabel();
        lblSearch = new javax.swing.JLabel();
        lblEmployeeCount = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnAddNew = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Employees = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Employee Management");

        lblManageEmployees.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblManageEmployees.setText("Manage Employees");

        lblSearch.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblSearch.setText("Search");

        lblEmployeeCount.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblEmployeeCount.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblEmployeeCount.setText("Number of employees:");

        txtSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtSearch.setName(""); // NOI18N

        btnAddNew.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnAddNew.setText("Add New");
        btnAddNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewActionPerformed(evt);
            }
        });

        jTable_Employees.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTable_Employees.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Full Name", "Phone Number", "Email Address", "Last Logged In"
            }
        ));
        jScrollPane1.setViewportView(jTable_Employees);

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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblSearch)
                            .addComponent(btnBack))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblEmployeeCount, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(179, 179, 179)
                                .addComponent(lblManageEmployees)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddNew, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(lblManageEmployees))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmployeeCount))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(btnAddNew, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Goes back to the previous form
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // Checks if the user is viewing a employee in another form
        if (Employee_in_view != null) {
            Employee_in_view.dispose();
        }

        // Makes previous form visible and closes current form
        previousForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnAddNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewActionPerformed
        // Checks if the user is adding an employee in another form
        if (!CurrentlyAddingEmployee) {
            formAddEmployee form = new formAddEmployee().getFrame();
            form.setVisible(true);
            form.previousForm = this;
            CurrentlyAddingEmployee = true;
        }
    }//GEN-LAST:event_btnAddNewActionPerformed

    // Returns the employee_id of the selected employee in the table
    private int getSelectedEmployee() {
        int selectedRow = jTable_Employees.getSelectedRow();

        if (selectedRow == -1) {
            // If no row is selected in the table
            ErrorMsg.throwError(ErrorMsg.NOTHING_SELECTED_ERROR);

        } else {
            // Returns id of selected employee
            String string_id = model.getValueAt(selectedRow, 0).toString();
            int id = Utility.StringToInt(string_id);
            return id;
        }

        // Returns -1 if there were to be an error somewhere
        return -1;
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
            java.util.logging.Logger.getLogger(formManageEmployees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formManageEmployees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formManageEmployees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formManageEmployees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formManageEmployees().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNew;
    private javax.swing.JButton btnBack;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Employees;
    private javax.swing.JLabel lblEmployeeCount;
    private javax.swing.JLabel lblManageEmployees;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
