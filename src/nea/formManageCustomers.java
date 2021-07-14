/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nea;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author Michal
 */
public class formManageCustomers extends javax.swing.JFrame {

    /**
     * Creates new form formAddNewCustomer
     */
    formMainMenu previousForm = null;
    Connection conn = null;
    DefaultTableModel model; // Init
    formOneCustomer Customer_in_view = null;

    public formManageCustomers() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        model = (DefaultTableModel) jTable_Customers.getModel(); // Fetches the table model of the table component
        jTable_Customers.setDefaultEditor(Object.class, null);

        JTableHeader header = jTable_Customers.getTableHeader();
        header.setFont(new Font("Dialog", Font.PLAIN, 14));         // Makes the font of the of header in the table larger - this may just be a windows 1440p scaling issue on my end

        loadCustomers(""); // Loads all the customer types from the DB into the table component in the form

        jTable_Customers.addMouseListener(new MouseListener() {
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
                int selectedID = getSelectedCustomer();
                if (selectedID != -1) {
                    if (Customer_in_view != null) {
                        Customer_in_view.dispose();
                    }

                    formOneCustomer form = new formOneCustomer().getFrame();
                    form.setLocation(1630, 422);
                    form.setVisible(true);
                    form.CustomerID = selectedID;
                    form.loadCustomer();
                    Customer_in_view = form;

                } else {
                    System.out.println("Something is truly wrong");
                }

            }
        });

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                loadCustomers(txtSearch.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadCustomers(txtSearch.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

        });
    }

    public formManageCustomers getFrame() {
        return this;
    }

    public void loadCustomers(String sp) {
        model.setRowCount(0); // Empties the table
        conn = sqlManager.openConnection();
        String query = "SELECT customer_id, title, forename, surname, postcode, phone_number, email_address FROM tblCustomers";
        
        if (!sp.equals("")) {
            query += " WHERE";
            query += " customer_id LIKE '%" + sp + "%'";
            query += " OR title LIKE '%" + sp + "%'";
            query += " OR forename LIKE '%" + sp + "%'";
            query += " OR surname LIKE '%" + sp + "%'";
            query += " OR postcode LIKE '%" + sp + "%'";
            query += " OR phone_number LIKE '%" + sp + "%'";
            query += " OR email_address LIKE '%" + sp + "%'";
        }

        System.out.println(query);

        try {
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(query);
            int customerCounter = 0;
            while (rs.next()) {
                System.out.println("-------------------------------");
                System.out.println(rs.getString(1));
                String FullName = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4);
                System.out.println(FullName);    // For debugging, shows each customer that was added to the table
                System.out.println(rs.getString(5));
                System.out.println(rs.getString(6));
                System.out.println(rs.getString(7));

                customerCounter++;
                model.addRow(new Object[]{rs.getString(1), FullName, rs.getString(5), rs.getString(6), rs.getString(7)});

            }
            lblCustomerCount.setText("Number of customers: " + String.valueOf(customerCounter));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sqlManager.closeConnection(conn);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_Customers = new javax.swing.JTable();
        btnAddNew = new javax.swing.JButton();
        lblCustomerCount = new javax.swing.JLabel();
        lblManageCustomers = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        txtSearch = new javax.swing.JTextField();
        lblSearch = new javax.swing.JLabel();

        jScrollPane2.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Manage Customers");

        jTable_Customers.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jTable_Customers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Full Name", "Postcode", "Phone Number", "Email Address"
            }
        ));
        jScrollPane1.setViewportView(jTable_Customers);

        btnAddNew.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        btnAddNew.setText("Add New");

        lblCustomerCount.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblCustomerCount.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblCustomerCount.setText("Number of customers:");

        lblManageCustomers.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblManageCustomers.setText("Manage Customers");

        btnBack.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        txtSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtSearch.setName(""); // NOI18N
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        lblSearch.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblSearch.setText("Search");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 688, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAddNew, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblSearch)
                            .addComponent(btnBack))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(179, 179, 179)
                                .addComponent(lblManageCustomers)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblCustomerCount, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(lblManageCustomers))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCustomerCount))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnAddNew, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        if (Customer_in_view != null) {
            Customer_in_view.dispose();
        }

        previousForm.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_btnBackActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        System.out.println(txtSearch.getText());
    }//GEN-LAST:event_txtSearchActionPerformed

    public int getSelectedCustomer() {
        int selectedRow = jTable_Customers.getSelectedRow();
        if (selectedRow == -1) {
            System.out.println("-------------------------------");
            System.out.println("No row selected");
        } else {
            String string_id = model.getValueAt(selectedRow, 0).toString();
            int id = Utility.StringToInt(string_id);
            return id;
        }
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
            java.util.logging.Logger.getLogger(formManageCustomers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formManageCustomers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formManageCustomers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formManageCustomers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formManageCustomers().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddNew;
    private javax.swing.JButton btnBack;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_Customers;
    private javax.swing.JLabel lblCustomerCount;
    private javax.swing.JLabel lblManageCustomers;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
