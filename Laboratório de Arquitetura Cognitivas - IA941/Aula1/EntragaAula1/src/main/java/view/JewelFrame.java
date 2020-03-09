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
public class JewelFrame extends javax.swing.JFrame {
    
    WS3DProxy proxy;
    World world;

    /**
     * Creates new form JewelFrame
     */
    public JewelFrame(WS3DProxy proxy, World world) {
        this.proxy = proxy;
        this.world = world;
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        createPanel = new javax.swing.JPanel();
        xPositionLabel = new javax.swing.JLabel();
        yPositionLabel = new javax.swing.JLabel();
        createJewelButton = new javax.swing.JButton();
        xPositionTextField = new javax.swing.JTextField();
        yPositionTextField = new javax.swing.JTextField();
        colorLabel = new javax.swing.JLabel();
        colorComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Jewel");
        setAlwaysOnTop(true);

        createPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Create Jewel", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        xPositionLabel.setText("X Position");

        yPositionLabel.setText("Y Position");

        createJewelButton.setText("Create Jewel");
        createJewelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createJewelButtonActionPerformed(evt);
            }
        });

        xPositionTextField.setText("0");

        yPositionTextField.setText("0");

        colorLabel.setText("Color");

        colorComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Green", "Blue", "Yellow", "Magenta", "White" }));
        colorComboBox.setActionCommand("");

        javax.swing.GroupLayout createPanelLayout = new javax.swing.GroupLayout(createPanel);
        createPanel.setLayout(createPanelLayout);
        createPanelLayout.setHorizontalGroup(
            createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(createJewelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(createPanelLayout.createSequentialGroup()
                            .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(createPanelLayout.createSequentialGroup()
                                    .addComponent(colorLabel)
                                    .addGap(40, 40, 40))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, createPanelLayout.createSequentialGroup()
                                    .addComponent(xPositionLabel)
                                    .addGap(18, 18, 18)))
                            .addGap(4, 4, 4)
                            .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(colorComboBox, 0, 125, Short.MAX_VALUE)
                                .addComponent(xPositionTextField)
                                .addComponent(yPositionTextField)))
                        .addComponent(yPositionLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        createPanelLayout.setVerticalGroup(
            createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(createPanelLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(createPanelLayout.createSequentialGroup()
                        .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(colorLabel)
                            .addComponent(colorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31))
                    .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(xPositionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(xPositionLabel)))
                .addGap(5, 5, 5)
                .addGroup(createPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yPositionLabel)
                    .addComponent(yPositionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(createJewelButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(createPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void createJewelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createJewelButtonActionPerformed
        int color = colorComboBox.getSelectedIndex();
        double xPosition = Double.parseDouble(xPositionTextField.getText());
        double yPosition = Double.parseDouble(yPositionTextField.getText());
        try {
            World.createJewel(color, xPosition, yPosition);
            JOptionPane.showMessageDialog(this, "Jewel created!");
        } catch (CommandExecException ex) {
            Logger.getLogger(JewelFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Failed to create jewel!");
        }
    }//GEN-LAST:event_createJewelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> colorComboBox;
    private javax.swing.JLabel colorLabel;
    private javax.swing.JButton createJewelButton;
    private javax.swing.JPanel createPanel;
    private javax.swing.JLabel xPositionLabel;
    private javax.swing.JTextField xPositionTextField;
    private javax.swing.JLabel yPositionLabel;
    private javax.swing.JTextField yPositionTextField;
    // End of variables declaration//GEN-END:variables
}
