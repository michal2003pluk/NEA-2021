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
public class formManageQuotations extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(formManageQuotations.class.getName());
    formMainMenu previousForm = null;

    // Init
    DefaultTableModel model;
    private String sp = "";

    // Whether the user is currently viewing a quotation in another form
    formOneQuotation Quotation_in_view = null;

    public formManageQuotations() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fetches Table model and makes table non-editable
        model = (DefaultTableModel) jTable_Quotations.getModel();
        jTable_Quotations.setDefaultEditor(Object.class, null);

        // Sets up the table header to be a bit larger
        JTableHeader header = jTable_Quotations.getTableHeader();
        header.setFont(new Font("Dialog", Font.PLAIN, 14));

        // When the user clicks on a row in the table
        jTable_Quotations.addMouseListener(new MouseListener() {
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
                // Gets the id of the quotaiton which is currently selected in the table
                int selectedID = getSelectedQuotation();
                if (selectedID != -1) {
                    if (Quotation_in_view != null) {
                        // If the user is viewing another quotation then that form is closed
                        Quotation_in_view.dispose();
                    }

                    // NEA OBJECTIVE 3.3: Once the user clicks on the shortened receipt, a separate
                    // form should open with the entire receipt.
                    formOneQuotation form = new formOneQuotation().getFrame();
                    form.setVisible(true);

                    // Positions the opened form to the right of the current form
                    Point locPoint = formManageQuotations.this.getLocationOnScreen();
                    locPoint.translate(formManageQuotations.this.getWidth(), 0);
                    form.setLocation(locPoint);

                    // Loads quotation into the other form and sets up previousForm variable
                    form.QuotationID = selectedID;
                    form.previousForm = formManageQuotations.this;
                    form.loadQuotation();

                    // Sets quotation in view variable to the quotation which is being viewed
                    Quotation_in_view = form;
                }
            }
        });

        // NEA OBJECTIVE 3.2: The user must be able to view all their receipts in a table and filter through results
        // using a search box. The table should update automatically when the search parameter in the search box changes.
        // When the user changes their search in the search box
        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Updates the search parameter and refreshes the table
                sp = txtSearch.getText();
                loadQuotations();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Updates the search parameter and refreshes the table
                sp = txtSearch.getText();
                loadQuotations();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

        });

        //Loads the initial data
        loadQuotations();

        // Adjusting the header widths
        jTable_Quotations = Utility.setColumnWidths(jTable_Quotations, new int[]{40, 120, 120, 120, 80});
    }

    // Used when the form is opened from within another form
    public formManageQuotations getFrame() {
        return this;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblManageQuotations = new javax.swing.JLabel();
        lblSearch = new javax.swing.JLabel();
        lblQuotationCount = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnAddNew = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Quotations = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quotation Management");

        lblManageQuotations.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblManageQuotations.setText("Manage Quotations");
        lblManageQuotations.setToolTipText("");

        lblSearch.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblSearch.setText("Search");

        lblQuotationCount.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblQuotationCount.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblQuotationCount.setText("Number of quotations:");

        txtSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtSearch.setName(""); // NOI18N

        btnAddNew.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnAddNew.setText("Add New");
        btnAddNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewActionPerformed(evt);
            }
        });

        jTable_Quotations.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTable_Quotations.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Customer", "Employee", "Date created", "Total"
            }
        ));
        jScrollPane1.setViewportView(jTable_Quotations);

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
                                .addGap(179, 179, 179)
                                .addComponent(lblManageQuotations)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblQuotationCount, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10))))
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
                    .addComponent(lblManageQuotations))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblQuotationCount))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(btnAddNew, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void loadQuotations() {
        // NEA OBJECTIVE 3.1: The user must be able to see key information about each receipt such as
        // the employee who created it, total amount billed, date created, and the associated customer. 
        // The receipts should be displayed in a shortened format in a table.

        // Empties the table
        model.setRowCount(0);

        // Raw SQL query: https://pastebin.com/YyF8jJ6b
        String query = "SELECT q.quotation_id AS id,"
                + " CONCAT(c.forename, ' ', c.surname) AS customerFullName,"
                + " CONCAT(e.forename, ' ', e.surname) AS employeeFullName,"
                + " q.date_created,"
                + " COALESCE(SUM(qD.quantity * qD.unit_price), 0) AS quotationTotal"
                + " FROM tblQuotation q"
                + " LEFT JOIN tblCustomer c ON q.customer_id = c.customer_id"
                + " LEFT JOIN tblEmployee e ON q.employee_id = e.employee_id"
                + " JOIN tblQuotationDetail qD ON q.quotation_id = qD.quotation_id"
                + " GROUP BY q.quotation_id";
        // If the user entered a search into the search box, the WHERE clause is adjusted
        if (!sp.isEmpty()) {
            query += " HAVING";
            query += " q.quotation_id LIKE '%" + sp + "%'";
            query += " OR customerFullName LIKE '%" + sp + "%'";
            query += " OR employeeFullName LIKE '%" + sp + "%'";
            query += " OR q.date_created LIKE '%" + sp + "%'";
            query += " OR quotationTotal LIKE '%" + sp + "%'";
        }
        query += " ORDER BY q.quotation_id";

        try (Connection conn = sqlManager.openConnection()) {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Counts the amount of quotations which are being shown
            int quotationCounter = 0;
            while (rs.next()) {
                String quotationTotal = Utility.formatCurrency(rs.getDouble(5));

                // Adds the quotation from the DB to the table
                model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), quotationTotal});
                quotationCounter++;
            }

            // Updates quotation counter label
            lblQuotationCount.setText("Number of quotations: " + String.valueOf(quotationCounter));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException");
        }
    }

    // Returns the quotation_id of the selected quotation in the table
    private int getSelectedQuotation() {
        int selectedRow = jTable_Quotations.getSelectedRow();

        if (selectedRow == -1) {
            // If no row is selected in the table
            ErrorMsg.throwError(ErrorMsg.NOTHING_SELECTED_ERROR);

        } else {
            // Returns id of selected quotation
            String string_id = model.getValueAt(selectedRow, 0).toString();
            int id = Utility.StringToInt(string_id);
            return id;
        }

        //  Returns -1 if there were to be an error somewhere
        return -1;
    }

    // Goes back to the previous form
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // Checks if the user is viewing an quotation in another form
        if (Quotation_in_view != null) {
            Quotation_in_view.dispose();
        }

        // Makes previous form visible and closes current form
        previousForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnAddNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewActionPerformed
        formNewQuotation form = new formNewQuotation().getFrame();
        form.previousForm2 = this;
        this.setVisible(false);
        form.setVisible(true);
    }//GEN-LAST:event_btnAddNewActionPerformed

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
            java.util.logging.Logger.getLogger(formManageQuotations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formManageQuotations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formManageQuotations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formManageQuotations.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formManageQuotations().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNew;
    private javax.swing.JButton btnBack;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_Quotations;
    private javax.swing.JLabel lblManageQuotations;
    private javax.swing.JLabel lblQuotationCount;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
