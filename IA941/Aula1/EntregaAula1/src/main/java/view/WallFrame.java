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
public class WallFrame extends javax.swing.JFrame {

    WS3DProxy proxy;
    World world;
    /**
     * Creates new form WallFrame
     */
    public WallFrame(WS3DProxy proxy, World world) {
        this.proxy = proxy;
        this.world = world;
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        wallPanel = new javax.swing.JPanel();
        colorLabel = new javax.swing.JLabel();
        colorComboBox = new javax.swing.JComboBox<>();
        startXPositionLabel = new javax.swing.JLabel();
        startYPositionLabel = new javax.swing.JLabel();
        finalXPositionLabel = new javax.swing.JLabel();
        finalYPositionLabel = new javax.swing.JLabel();
        createWallButton = new javax.swing.JButton();
        startXPositionTextField = new javax.swing.JTextField();
        startYPositionTextField = new javax.swing.JTextField();
        finalXPositionTextField = new javax.swing.JTextField();
        finalYPositionTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Wall");
        setAlwaysOnTop(true);

        wallPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Create Wall", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        colorLabel.setText("Color");

        colorComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Red", "Green", "Blue", "Yellow", "Magenta", "White" }));

        startXPositionLabel.setText("Start X Position");

        startYPositionLabel.setText("Start Y Position");

        finalXPositionLabel.setText("Final X Position");

        finalYPositionLabel.setText("Final Y Position");

        createWallButton.setText("Create Wall");
        createWallButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createWallButtonActionPerformed(evt);
            }
        });

        startXPositionTextField.setText("0");

        startYPositionTextField.setText("0");

        finalXPositionTextField.setText("0");

        finalYPositionTextField.setText("0");

        javax.swing.GroupLayout wallPanelLayout = new javax.swing.GroupLayout(wallPanel);
        wallPanel.setLayout(wallPanelLayout);
        wallPanelLayout.setHorizontalGroup(
            wallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wallPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(wallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(createWallButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(wallPanelLayout.createSequentialGroup()
                        .addComponent(colorLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(colorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(wallPanelLayout.createSequentialGroup()
                        .addGroup(wallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startYPositionLabel)
                            .addComponent(finalXPositionLabel)
                            .addComponent(finalYPositionLabel)
                            .addComponent(startXPositionLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(wallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(startXPositionTextField)
                            .addComponent(finalYPositionTextField)
                            .addComponent(finalXPositionTextField)
                            .addComponent(startYPositionTextField))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        wallPanelLayout.setVerticalGroup(
            wallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(wallPanelLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(wallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colorLabel)
                    .addComponent(colorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(wallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startXPositionLabel)
                    .addComponent(startXPositionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(wallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(startYPositionLabel)
                    .addComponent(startYPositionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(wallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(finalXPositionLabel)
                    .addComponent(finalXPositionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(wallPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(finalYPositionLabel)
                    .addComponent(finalYPositionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(createWallButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(wallPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(wallPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createWallButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createWallButtonActionPerformed
        try {
            int color = colorComboBox.getSelectedIndex();
            double startXPosition = Double.parseDouble(startXPositionTextField.getText());
            double startYPosition = Double.parseDouble(startYPositionTextField.getText());
            double finalXPosition = Double.parseDouble(finalXPositionTextField.getText());
            double finalYPosition = Double.parseDouble(finalYPositionTextField.getText());
            World.createBrick(color, finalXPosition, finalYPosition, startXPosition, startYPosition);
            JOptionPane.showMessageDialog(this, "Wall created!");
        } catch (CommandExecException ex) {
            Logger.getLogger(WallFrame.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Failed to create a wall!");
        }
    }//GEN-LAST:event_createWallButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> colorComboBox;
    private javax.swing.JLabel colorLabel;
    private javax.swing.JButton createWallButton;
    private javax.swing.JLabel finalXPositionLabel;
    private javax.swing.JTextField finalXPositionTextField;
    private javax.swing.JLabel finalYPositionLabel;
    private javax.swing.JTextField finalYPositionTextField;
    private javax.swing.JLabel startXPositionLabel;
    private javax.swing.JTextField startXPositionTextField;
    private javax.swing.JLabel startYPositionLabel;
    private javax.swing.JTextField startYPositionTextField;
    private javax.swing.JPanel wallPanel;
    // End of variables declaration//GEN-END:variables
}
