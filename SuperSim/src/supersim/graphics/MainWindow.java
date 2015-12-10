/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supersim.graphics;

import java.awt.Point;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import supersim.Product.ProductWrapper;
import supersim.SuperSim;
import supersim.persons.Customer;
import supersim.persons.Employee;
import supersim.timer.ITimeable;

/**
 *
 * @author Jens
 */
public class MainWindow extends javax.swing.JFrame implements ITimeable {
    
    SuperSim simulator;
    
    DefaultTableModel employeeTableModel;
    DefaultTableModel customerTableModel;
    DefaultTableModel storageTableModel;

    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        
    }
    
    public MainWindow(SuperSim sim)
    {
        initComponents();
        this.simulator = sim;
        this.storeRenderer1.setSimulator(sim);
        
        //this.speedSlider.setMaximum(1000);
        
        sim.timer.addSubscriber(this);
        
        String[] EmployeeTableHeaders = new String[] {"Name","Task","TaskStation","State"};
        employeeTableModel = new DefaultTableModel(null, EmployeeTableHeaders) {
          // Make read-only
          public boolean isCellEditable(int x, int y) {
            return false;
          }
        };
        employeeList.setModel(employeeTableModel);
        
        
        String[] CustomerTableHeaders = new String[] {"Name", "State", "Customer Group", "Items in cart", "Items on grocery list"};
        customerTableModel = new DefaultTableModel(null, CustomerTableHeaders) {
          // Make read-only
          public boolean isCellEditable(int x, int y) {
            return false;
          }
        };
        customerList.setModel(customerTableModel);
        
        String[] StorageTableHeaders = new String[] {"Product Name","Unit Price","Amount in storage","Department"};
        storageTableModel = new DefaultTableModel(null, StorageTableHeaders) {
          // Make read-only
          public boolean isCellEditable(int x, int y) {
            return false;
          }
        };
        storageTable.setModel(storageTableModel);
        
        this.pack();
        this.setVisible(true);
    }
    
    float culDelta = 0;
    @Override
    public void onTick(Date simulatedDate, float deltatime) {
        if(culDelta > 250) //update gui 4 times a second
        {
            UpdateEmployees(this.simulator.store.employees);
            UpdateCustomers(this.simulator.store.customers);
            UpdateStorage(this.simulator.store.storage.getStorageList());
            culDelta = 0;
        }
        
        culDelta += deltatime;
    }
    
    public void UpdateStorage(List<ProductWrapper> storage)
    {
        int rowCount = storageTableModel.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            storageTableModel.removeRow(i);
        }
        
        //Add new rows
        for(int i = 0; i < storage.size(); i++)
        {
            ProductWrapper pw = storage.get(i);
                                
            storageTableModel.addRow(new String[] {pw.product.name, ""+pw.product.price, ""+pw.amount, pw.product.department});
        }
    }
    
    public void UpdateEmployees(List<Employee> employees)
    {      
        int rowCount = employeeTableModel.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            employeeTableModel.removeRow(i);
        }
        
        //Add new rows
        for(int i = 0; i < employees.size(); i++)
        {
            Employee e = employees.get(i);
            String taskStation = "None";
            if(e.currentTaskStation != null) taskStation = e.currentTaskStation.toString();
                    
            employeeTableModel.addRow(new String[] {e.name, e.currentTask.toString(), taskStation, e.currentState.toString()});
        }
        
        //employeeList.validate();
  
    }
    
    public void UpdateCustomers(List<Customer> customers)
    {      
        int rowCount = customerTableModel.getRowCount();
        //Remove rows one by one from the end of the table
        for (int i = rowCount - 1; i >= 0; i--) {
            customerTableModel.removeRow(i);
        }
        
        //Add new rows
        for(int i = 0; i < customers.size(); i++)
        {
            Customer c = customers.get(i);
            
            customerTableModel.addRow(new String[] {c.name, c.currentState.toString(), c.group.name, Integer.toString(c.shoppingCart.size()), Integer.toString(c.groceryList.size())});
        }
        
        //employeeList.validate();
  
    }
    
    public void logMessage(String s)
    {
      //jTextArea1.append(s + "\n"); 
        //System.out.println(s);
    }
    
    public StoreRenderer getStorePanel()
    {
        return this.storeRenderer1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        storeRenderer1 = new supersim.graphics.StoreRenderer();
        speedSlider = new javax.swing.JSlider();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane3 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        employeeList = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        customerList = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        storageTable = new javax.swing.JTable();

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jSplitPane1.setDividerLocation(250);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        storeRenderer1.setFont(new java.awt.Font("Verdana", 0, 10)); // NOI18N

        speedSlider.setMaximum(3000);
        speedSlider.setMinimum(1);
        speedSlider.setValue(1);
        speedSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                speedSliderStateChanged(evt);
            }
        });

        javax.swing.GroupLayout storeRenderer1Layout = new javax.swing.GroupLayout(storeRenderer1);
        storeRenderer1.setLayout(storeRenderer1Layout);
        storeRenderer1Layout.setHorizontalGroup(
            storeRenderer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, storeRenderer1Layout.createSequentialGroup()
                .addGap(0, 603, Short.MAX_VALUE)
                .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        storeRenderer1Layout.setVerticalGroup(
            storeRenderer1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(storeRenderer1Layout.createSequentialGroup()
                .addComponent(speedSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 226, Short.MAX_VALUE))
        );

        jSplitPane1.setLeftComponent(storeRenderer1);

        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jSplitPane3.setPreferredSize(new java.awt.Dimension(200, 308));

        jLabel1.setText("Employees");

        employeeList.setAutoCreateRowSorter(true);
        employeeList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(employeeList);

        jButton1.setText("+");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addGap(81, 81, 81)
                .addComponent(jButton1))
            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
        );

        jSplitPane3.setTopComponent(jPanel1);

        jLabel2.setText("Customers");

        customerList.setAutoCreateRowSorter(true);
        customerList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(customerList);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE))
        );

        jSplitPane3.setRightComponent(jPanel2);

        jSplitPane2.setRightComponent(jSplitPane3);

        storageTable.setAutoCreateRowSorter(true);
        storageTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(storageTable);

        jSplitPane2.setLeftComponent(jScrollPane3);

        jSplitPane1.setRightComponent(jSplitPane2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(this.simulator != null)
        {
            this.simulator.store.employees.add(new Employee("Employee "+(this.simulator.store.employees.size()+1), this.simulator.store, new Point(0,0)));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void speedSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_speedSliderStateChanged
       this.simulator.timer.speed = (float)Math.pow(10, this.speedSlider.getValue() / 1000f);
       
        
    }//GEN-LAST:event_speedSliderStateChanged

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.simulator.timer.stop();//Stop the timer thread..
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable customerList;
    private javax.swing.JTable employeeList;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSlider speedSlider;
    private javax.swing.JTable storageTable;
    private supersim.graphics.StoreRenderer storeRenderer1;
    // End of variables declaration//GEN-END:variables

}
