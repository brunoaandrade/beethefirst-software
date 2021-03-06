package replicatorg.app.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import replicatorg.app.Base;
import replicatorg.app.ProperDefault;
import replicatorg.app.ui.mainWindow.UpdateChecker;
import replicatorg.app.ui.panels.TourWelcome;
import replicatorg.app.ui.panels.Warning;

/**
 * Copyright (c) 2013 BEEVC - Electronic Systems This file is part of BEESOFT
 * software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either
 * version 3 of the License, or (at your option) any later version. BEESOFT is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You
 * should have received a copy of the GNU General Public License along with
 * BEESOFT. If not, see <http://www.gnu.org/licenses/>.
 */
public class WelcomeSplash extends javax.swing.JFrame {

    private final MainWindow window;
    private ImageIcon image;
    private int newWidth = 600;
    private int newHeight = 333;
    private int duration = 50;

    /**
     * Welcome Splash init
     *
     * @param wind MainWindow for visual and feature control
     */
    public WelcomeSplash(MainWindow wind) {
        initComponents();
        Base.welcomeSplashVisible = true;
        Base.writeLog("Welcome Splash started ...", this.getClass());
        window = wind;
        // Loads Splash image
        image = new ImageIcon(Base.getImage("images/welcomeSplash.png", this));
        Image img = image.getImage();
        // Loads Splash image for dimensios getter
        BufferedImage img2 = Base.getImage("images/welcomeSplash.png", this);

        // Gets Screen Dimension
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        // Calculates ratio for modular adjustement to every screen
        double screenRatio = 0.75;
        newWidth = (int) (d.width * screenRatio);
        double scale = d.width * screenRatio / img2.getWidth();
        newHeight = (int) (img2.getHeight() * scale);

        // Scales original image to new size values with Smooth conversion
        Image newimg = img2.getScaledInstance(newWidth, newHeight, java.awt.Image.SCALE_SMOOTH);

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(newimg.getWidth(null), newimg.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(newimg, 0, 0, null);
        bGr.dispose();

        // epic hammering just to draw BEESOFT version, that's how much we like swing
        // hammer time
        Graphics g = bimage.getGraphics();
        g.setFont(GraphicDesignComponents.getSSProLight("18"));
        g.setColor(Color.BLACK);
        int stringPixelSize = (img.getGraphics().getFontMetrics()).stringWidth("Version: " + Base.VERSION_BEESOFT);
        g.drawString("Version: " + Base.VERSION_BEESOFT, bimage.getWidth() - 40 - stringPixelSize, bimage.getHeight() - 10);
        g.dispose();
        
        
        image = new ImageIcon(bimage);
        // Sets bar preferences and size.
        // Bar width is equal to image width. Height has value = 5
        jProgressBar1.setMaximum(newWidth - 5);
        jProgressBar1.setPreferredSize(new Dimension(newWidth - 5, 5));
        jLabel1.setIcon(image);
        jLabel1.setPreferredSize(new Dimension(newWidth, newHeight));
        jLabel1.setSize(new Dimension(newWidth, newHeight));
        jPanel1.setPreferredSize(new Dimension(newWidth, newHeight));
        jPanel1.setSize(new Dimension(newWidth, newHeight));
        this.setPreferredSize(new Dimension(newWidth, newHeight + jProgressBar1.getHeight()));
        this.setSize(new Dimension(newWidth, newHeight + jProgressBar1.getHeight()));
        this.setLocationRelativeTo(null);
        // Thread to update JProgress Bar
        new Thread() {
            @Override
            public void run() {
                int i = 0;
                // Increment value for each bar update
                int inc = 0;

                // Number of seconds to hold splash screen
                while (i < getDuration()) {
                    jProgressBar1.setValue(inc);
                    i++;
                    inc += getWidth() / 50;
                    try {
                        sleep(100);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(WelcomeSplash.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                changeState();
            }
        }.start();

    }

    /**
     * Monitor changes in MainWindow and performs several operations at startup.
     */
    public void changeState() {
        window.setVisible(true);
        window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(false);
        Base.welcomeSplashVisible = false;
        Base.writeLog("BEESOFT main window loaded ... ", this.getClass());

        //GuideWizard
        if (Boolean.valueOf(ProperDefault.get("firstTime"))) {
            Base.writeLog("BEESOFT tour loaded ... ", this.getClass());

            TourWelcome p = new TourWelcome();
            p.setVisible(true);
        }

        /**
         * Check is 3DModels folder exists. If not, create it.
         */
        Base.getAppDataDirectory();
        Base.copy3DFiles();
        Base.cleanDirectoryTempFiles(Base.getAppDataDirectory() + "/" + Base.MODELS_FOLDER);
        window.setEnabled(true);

        /**
         * Error occured during driver initialization
         */
        if (Base.errorOccured == true) {
            Warning flashError = new Warning("close");
            flashError.setMessage("ErrorUpdating");
            flashError.setVisible(true);
        }

    }

    /**
     * Get Splash Width.
     *
     * @return splash width
     */
    public int getWidth() {
        return newWidth;
    }

    /**
     * Set Splash Screen duration.
     *
     * @param milis Duration
     */
    public void setDuration(int milis) {
        this.duration = milis;
    }

    /**
     * Get Splash Screen Duration.
     *
     * @return duration
     */
    public int getDuration() {
        return duration;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        jProgressBar1.setBorderPainted(false);
        jProgressBar1.setPreferredSize(new java.awt.Dimension(150, 5));

        jLabel1.setBackground(new java.awt.Color(255, 128, 0));

        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 168, Short.MAX_VALUE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
