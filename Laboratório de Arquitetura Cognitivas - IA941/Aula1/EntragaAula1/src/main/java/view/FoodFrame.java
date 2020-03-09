/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ws3dproxy.CommandExecException;
import ws3dproxy.WS3DProxy;
import ws3dproxy.model.World;

/**
 *
 * @author lucas
 */
public class FoodFrame extends javax.swing.JFrame {

    
    WS3DProxy proxy;
    World world;
    /**
     * Creates new form AppleFrame
     */
    public FoodFrame(WS3DProxy proxy, World world) {
        this.proxy = proxy;
        this.world = world;
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        createPanel = new javax.swing.JPanel();
        xPositionLabel = new javax.swing.JLabel();
        yPositionLabel = new javax.swing.JLabel();
        xPositionTextField = new javax.swing.JTextField();
        yPositionTextField = new javax.swing.JTextField();
        createFoodButton = new javax.swing.JButton();
        foodTypeLabel = new javax.swing.JLabel();
        foodTypeComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Apple");
        setAlwaysOnTop(true);

        createPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Create Food", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        xPositionLabel.setText("X Position");

        yPositionLabel.setText("Y Position");

        xPositionTextField.setText("0");

        yPositionTextField.setText("0");

        createFoodButton.setText("Create Food");
        createFoodButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createFoodButtonActionPerformed(evt);
            }
        });

        foodTypeLabel.setText("Food Type");

        foodTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Apple", "Nut" }));

        javax.swing.GroupLayout createPanelLayout = new javax.swing.GroupLayout(createPanel);
        createPanel.setLayout(createPanelLayout);
        createPanelLayout.setHorizontalGroup(
            createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(createFoodButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(createPanelLayout.createSequentialGroup()
                        .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(foodTypeLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(xPositionLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(yPositionLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yPositionTextField)
                            .addComponent(foodTypeComboBox, 0, 115, Short.MAX_VALUE)
                            .addComponent(xPositionTextField))))
                .addContainerGap())
        );
        createPanelLayout.setVerticalGroup(
            createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodTypeLabel)
                    .addComponent(foodTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(xPositionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xPositionLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yPositionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yPositionLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(createFoodButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(createPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(createPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createFoodButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createFoodButtonActionPerformed
        try {
            double xPosition = Double.parseDouble(xPositionTextField.getText());
            double yPosition = Double.parseDouble(yPositionTextField.getText());
            int foodType = foodTypeComboBox.getSelectedIndex();
            World.createFood(foodType, xPosition, yPosition);
            JOptionPane.showMessageDialog(this, "Food created!");
        } catch (CommandExecException ex) {
            JOptionPane.showMessageDialog(this, "Failed to create food!");
            Logger.getLogger(FoodFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_createFoodButtonActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createFoodButton;
    private javax.swing.JPanel createPanel;
    private javax.swing.JComboBox<String> foodTypeComboBox;
    private javax.swing.JLabel foodTypeLabel;
    private javax.swing.JLabel xPositionLabel;
    private javax.swing.JTextField xPositionTextField;
    private javax.swing.JLabel yPositionLabel;
    private javax.swing.JTextField yPositionTextField;
    // End of variables declaration//GEN-END:variables
}
