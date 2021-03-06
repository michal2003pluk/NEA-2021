/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Michal
 */
public class formReportFour extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(formReportFour.class.getName());
    formMainMenu previousForm = null;

    public formReportFour() {
        initComponents();
        this.setLocationRelativeTo(null);

        // Makes the option to set a custom start date and end date invisible temporarily
        lblStart.setVisible(false);
        dcStart.setVisible(false);
        lblEnd.setVisible(false);
        dcEnd.setVisible(false);

        // NEA Objective 4.2: If the user selects the ‘Other’ option, then the user
        // should be able to select between two different dates using jCalendar components.
        // Listens for a change in the selectedIndex
        cbTime.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbTime.getSelectedIndex() == cbTime.getItemCount() - 1) {
                    // If the user selected the last item i.e. 'Other'
                    // Makes the date selectors for start and end date visible
                    lblStart.setVisible(true);
                    dcStart.setVisible(true);
                    lblEnd.setVisible(true);
                    dcEnd.setVisible(true);
                } else {
                    // Makes the date selectors for start and end date invisible
                    lblStart.setVisible(false);
                    dcStart.setVisible(false);
                    lblEnd.setVisible(false);
                    dcEnd.setVisible(false);
                }
            }
        });

        initialiseCustomerSpinner();
    }

    private void initialiseCustomerSpinner() {
        // Init
        int NoCustomers = 1;

        try (Connection conn = sqlManager.openConnection()) {
            // Query Setup & Execution
            String query = "SELECT COUNT(customer_id) FROM tblCustomer";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                NoCustomers = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException");
        }

        // The preferred amount of categories to display
        int preferredAmount = 5;
        int default_val = NoCustomers < preferredAmount ? NoCustomers : preferredAmount;

        // Default, Lower Bound, Upper Bound, Increment
        SpinnerModel sm = new SpinnerNumberModel(default_val, 1, NoCustomers, 1);
        spCustomerCount.setModel(sm);
    }

    // Generates the dataset by fetching the data from the DB
    private CategoryDataset getData(LocalDateTime start, LocalDateTime end, int CustomerCount) {
        // The final output dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        // Raw SQL query: https://pastebin.com/RXQdWpH1
        String queryInvoiceTotals = "SELECT i.customer_id, SUM(iD.quantity * iD.unit_price) AS invoiceSubtotal"
                + " FROM tblInvoice AS i"
                + " INNER JOIN tblInvoiceDetail AS iD ON i.invoice_id = iD.invoice_id"
                + " WHERE i.date_created BETWEEN ? AND ?"
                + " GROUP BY i.invoice_id";

        String queryQuotationTotals = "SELECT q.customer_id, SUM(qD.quantity * qD.unit_price) AS quotationSubtotal"
                + " FROM tblQuotation AS q"
                + " INNER JOIN tblQuotationDetail AS qD ON q.quotation_id = qD.quotation_id"
                + " WHERE q.date_created BETWEEN ? AND ?"
                + " GROUP BY q.quotation_id";

        String queryCustomerInvoiceTotals = "SELECT c.customer_id, COALESCE(SUM(iT.invoiceSubtotal), 0) AS cInvoiceTotal"
                + " FROM tblCustomer AS c"
                + " INNER JOIN (" + queryInvoiceTotals + ") AS iT ON c.customer_id = iT.customer_id"
                + " GROUP BY c.customer_id";

        String queryCustomerQuotationTotals = "SELECT c.customer_id, COALESCE(SUM(qT.quotationSubtotal), 0) AS cQuotationTotal"
                + " FROM tblCustomer AS c"
                + " INNER JOIN (" + queryQuotationTotals + ") AS qT ON c.customer_id = qT.customer_id"
                + " GROUP BY c.customer_id";

        String mainQuery = "SELECT CONCAT(c.forename, ' ', c.surname) AS Fullname,"
                + " COALESCE(cITs.cInvoiceTotal, 0) AS invoiceTotal,"
                + " COALESCE(cQTs.cQuotationTotal, 0) AS quotationTotal,"
                + " COALESCE(cITs.cInvoiceTotal, 0) + COALESCE(cQTs.cQuotationTotal, 0) AS overallTotal"
                + " FROM tblCustomer AS c"
                + " LEFT JOIN (" + queryCustomerInvoiceTotals + ") AS cITs ON c.customer_id = cITs.customer_id"
                + " LEFT JOIN (" + queryCustomerQuotationTotals + ") AS cQTs ON c.customer_id = cQTs.customer_id"
                + " ORDER BY overallTotal DESC"
                + " LIMIT ?";

        try (Connection conn = sqlManager.openConnection()) {
            // Query Setup & Execution
            PreparedStatement pstmt = conn.prepareStatement(mainQuery);
            pstmt.setObject(1, start);
            pstmt.setObject(2, end);
            pstmt.setObject(3, start);
            pstmt.setObject(4, end);
            pstmt.setInt(5, CustomerCount);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                // Adds the data for each customer to the dataset
                String customer_name = rs.getString(1);
                dataset.addValue(rs.getDouble(2), "Invoices", customer_name);
                dataset.addValue(rs.getDouble(3), "Quotations", customer_name);

                // If the employee has had both invoices and quotations set then a value for both is calculated
                if (rs.getDouble(2) > 0 && rs.getDouble(3) > 0) {
                    dataset.addValue(rs.getDouble(4), "Both", customer_name);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "SQLException");
        }

        return dataset;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnBack = new javax.swing.JButton();
        lblCustomerAnalysis = new javax.swing.JLabel();
        pParam = new javax.swing.JPanel();
        btnAnalyze = new javax.swing.JButton();
        lblCustomersToShow = new javax.swing.JLabel();
        cbTime = new javax.swing.JComboBox<>();
        lblTime = new javax.swing.JLabel();
        lblStart = new javax.swing.JLabel();
        dcStart = new com.toedter.calendar.JDateChooser();
        dcEnd = new com.toedter.calendar.JDateChooser();
        lblEnd = new javax.swing.JLabel();
        spCustomerCount = new javax.swing.JSpinner();
        pOutput = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Customer Analysis");

        btnBack.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        lblCustomerAnalysis.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        lblCustomerAnalysis.setText("Customer Analysis");

        pParam.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pParam.setMinimumSize(new java.awt.Dimension(0, 200));

        btnAnalyze.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btnAnalyze.setText("Analyze");
        btnAnalyze.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalyzeActionPerformed(evt);
            }
        });

        lblCustomersToShow.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblCustomersToShow.setText("Customers to show:");

        cbTime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Past month", "Past year", "This month", "This quarter", "This year", "This financial year", "All Time", "Other" }));

        lblTime.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblTime.setText("Time Period:");

        lblStart.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblStart.setText("Start Date: ");

        lblEnd.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lblEnd.setText("End Date: ");

        spCustomerCount.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        javax.swing.GroupLayout pParamLayout = new javax.swing.GroupLayout(pParam);
        pParam.setLayout(pParamLayout);
        pParamLayout.setHorizontalGroup(
            pParamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pParamLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(pParamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pParamLayout.createSequentialGroup()
                        .addGroup(pParamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustomersToShow)
                            .addComponent(lblTime))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pParamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cbTime, 0, 150, Short.MAX_VALUE)
                            .addComponent(spCustomerCount)))
                    .addGroup(pParamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnAnalyze)
                        .addGroup(pParamLayout.createSequentialGroup()
                            .addComponent(lblStart)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(dcStart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
                            .addComponent(lblEnd)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(dcEnd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6))
        );
        pParamLayout.setVerticalGroup(
            pParamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pParamLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pParamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomersToShow)
                    .addComponent(spCustomerCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pParamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTime))
                .addGap(10, 10, 10)
                .addGroup(pParamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pParamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dcStart, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(lblStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pParamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(dcEnd, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(lblEnd)))
                .addGap(18, 18, 18)
                .addComponent(btnAnalyze)
                .addContainerGap())
        );

        pOutput.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pOutput.setMinimumSize(new java.awt.Dimension(0, 0));
        pOutput.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addGap(222, 222, 222)
                        .addComponent(lblCustomerAnalysis)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pParam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pOutput, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCustomerAnalysis)
                    .addComponent(btnBack))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pParam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pOutput, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Goes back to the previous form
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        previousForm.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    // When the user clicks the Analyze button
    private void btnAnalyzeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalyzeActionPerformed
        // Dates from which to get the results from
        // End date is always the current date unless user specifies otherwise
        LocalDateTime start = null;
        LocalDateTime end = LocalDateTime.now();

        // NEA Objective 4.1: Allow the user to select between different time periods
        // to analyse such as a month, quarter, year or other.
        // Code for assigning the start date for each choice in cbTime
        boolean valid = false;
        switch (cbTime.getSelectedIndex()) {
            case 0:// Past month
                start = LocalDate.now().minusMonths(1).atTime(0, 0, 0);
                valid = true;
                break;
            case 1:// Past year
                start = LocalDate.now().minusMonths(12).atTime(0, 0, 0);
                valid = true;
                break;
            case 2:// This month
                start = LocalDate.now().withDayOfMonth(1).atTime(0, 0, 0);
                valid = true;
                break;
            case 3:// This quarter
                start = Utility.getQuarterStart(LocalDate.now()).atTime(0, 0, 0);
                valid = true;
                break;
            case 4:// This year
                start = LocalDate.now().withDayOfYear(1).atTime(0, 0, 0);
                valid = true;
                break;
            case 5:// This financial year
                start = Utility.getFinancialYear(LocalDate.now()).atTime(0, 0, 0);
                valid = true;
                break;
            case 6:// All time
                start = sqlManager.getDateOfFirstReceipt();
                valid = true;
                break;
            case 7:// Other

                if (dcStart.getDate() == null && dcEnd.getDate() == null) {
                    ErrorMsg.throwError(ErrorMsg.EMPTY_INPUT_FIELD_ERROR, "Start & End date cannot be empty");
                    valid = false;

                } else if (dcStart.getDate() == null) {
                    ErrorMsg.throwError(ErrorMsg.EMPTY_INPUT_FIELD_ERROR, "Start date cannot be empty");
                    valid = false;

                } else if (dcEnd.getDate() == null) {
                    ErrorMsg.throwError(ErrorMsg.EMPTY_INPUT_FIELD_ERROR, "End date cannot be empty");
                    valid = false;

                } else if (dcEnd.getDate().before(dcStart.getDate())) {
                    ErrorMsg.throwCustomError("Start Date should be before the end date", "Invalid Input Error");
                    valid = false;

                } else {
                    // If the date inputs pass the obove checks then these are set as the start and end date
                    start = dcStart.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(0, 0, 0);
                    end = dcEnd.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().atTime(23, 59, 59);
                    valid = true;
                }
                break;
        }

        if (valid) {
            displayBarChart(start, end);
        }

    }//GEN-LAST:event_btnAnalyzeActionPerformed

    private void displayBarChart(LocalDateTime start, LocalDateTime end) {

        // See 4.3.2 in the 'How To' section in the NEA for an explanation of this
        // Gets the amount of customers the user wants to see
        int customerCount = (int) spCustomerCount.getValue();

        CategoryDataset data;
        data = getData(start, end, customerCount);
        JFreeChart barChart = ChartFactory.createBarChart(
                "Value invoiced/quoted per employee",
                "Employee name",
                "Value invoiced/quoted",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false);

        // Adds horizontal grid lines to the plot
        CategoryPlot p = barChart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.black);

        // Makes the x axis labels vertical to conserve space
        CategoryAxis axis = barChart.getCategoryPlot().getDomainAxis();
        axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);

        // Clears the JPanel and adds the ChartPanel which holds the barChart graphic
        ChartPanel barPanel = new ChartPanel(barChart);
        pOutput.removeAll();
        pOutput.add(barPanel, BorderLayout.CENTER);

        // Validates the JPanel to make sure changes are visible
        pOutput.validate();
    }

    // Used when the form is opened from within another form
    public formReportFour getFrame() {
        return this;
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
            java.util.logging.Logger.getLogger(formReportFour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formReportFour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formReportFour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formReportFour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new formReportFour().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnalyze;
    private javax.swing.JButton btnBack;
    private javax.swing.JComboBox<String> cbTime;
    private com.toedter.calendar.JDateChooser dcEnd;
    private com.toedter.calendar.JDateChooser dcStart;
    private javax.swing.JLabel lblCustomerAnalysis;
    private javax.swing.JLabel lblCustomersToShow;
    private javax.swing.JLabel lblEnd;
    private javax.swing.JLabel lblStart;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPanel pOutput;
    private javax.swing.JPanel pParam;
    private javax.swing.JSpinner spCustomerCount;
    // End of variables declaration//GEN-END:variables
}
