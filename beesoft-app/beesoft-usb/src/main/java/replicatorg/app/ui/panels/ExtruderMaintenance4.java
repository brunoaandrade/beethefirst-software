package replicatorg.app.ui.panels;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import pt.beeverycreative.beesoft.drivers.usb.UsbPassthroughDriver.COM;
import replicatorg.app.Base;
import replicatorg.app.Languager;
import replicatorg.app.ProperDefault;
import replicatorg.app.ui.GraphicDesignComponents;
import replicatorg.machine.MachineInterface;
import replicatorg.util.Point5d;

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
public class ExtruderMaintenance4 extends BaseDialog {

    private final MachineInterface machine;
    private boolean achievement;
    private boolean quickGuide;
    private double temperatureGoal;
    private final ExtruderMaintenanceUpdateThread updateThread;

    public ExtruderMaintenance4() {
        super(Base.getMainWindow(), Dialog.ModalityType.DOCUMENT_MODAL);
        initComponents();
        setFont();
        setTextLanguage();
        Base.THREAD_KEEP_ALIVE = false;
        machine = Base.getMachineLoader().getMachineInterface();
        machine.getDriver().resetToolTemperature();
        evaluateInitialConditions();
        enableDrag();
        centerOnScreen();
        setProgressBarColor();
        //moveToPosition();
        updateThread = new ExtruderMaintenanceUpdateThread(this);
        updateThread.start();
        Base.systemThreads.add(updateThread);
        setIconImage(new ImageIcon(Base.getImage("images/icon.png", this)).getImage());
    }

    private void setFont() {
        lTitle.setFont(GraphicDesignComponents.getSSProRegular("14"));
        pText1.setFont(GraphicDesignComponents.getSSProBold("12"));
        pText2.setFont(GraphicDesignComponents.getSSProRegular("12"));
        pWarning.setFont(GraphicDesignComponents.getSSProRegular("14"));
        bBack.setFont(GraphicDesignComponents.getSSProRegular("12"));
        bNext.setFont(GraphicDesignComponents.getSSProRegular("12"));
        bQuit.setFont(GraphicDesignComponents.getSSProRegular("12"));

    }

    private void setTextLanguage() {
        
        lTitle.setText(Languager.getTagValue(1, "ExtruderMaintenance", "Title4"));
        
        String text1 = "<html>"
                + "<br>"
                + Languager.getTagValue(1, "ExtruderMaintenance", "Info4a")
                + "<br>"
                + Languager.getTagValue(1, "ExtruderMaintenance", "Info4b")
                + "</html>";
        pText1.setText(splitString(text1));
        
        String warning = "<html><br><b>" + Languager.getTagValue(1, "FilamentWizard", "Info_Warning") + "</b></html>";
        pText2.setText(splitString(warning));
        
        pWarning.setText(Languager.getTagValue(1, "ExtruderMaintenance", "HeatingMessage4"));
        bBack.setText(Languager.getTagValue(1, "OptionPaneButtons", "Line4"));
        bNext.setText(Languager.getTagValue(1, "OptionPaneButtons", "Line7"));
        
        bQuit.setText(Languager.getTagValue(1, "OptionPaneButtons", "Line3"));
     

    }

    private String splitString(String s) {
        int width = 436;
        return buildString(s.split("\\."), width);
    }

    private String buildString(String[] parts, int width) {
        String text = "";
        String ihtml = "<html>";
        String ehtml = "</html>";
        String br = "<br>";

        for (int i = 0; i < parts.length; i++) {
            if (i + 1 < parts.length) {
                if (getStringPixelsWidth(parts[i]) + getStringPixelsWidth(parts[i + 1]) < width) {
                    text = text.concat(parts[i]).concat(".").concat(parts[i + 1]).concat(".").concat(br);
                    i++;
                } else {
                    text = text.concat(parts[i]).concat(".").concat(br);
                }
            } else {
                text = text.concat(parts[i]).concat(".");
            }
        }

        return ihtml.concat(text).concat(ehtml);
    }

    private int getStringPixelsWidth(String s) {
        Graphics g = getGraphics();
        FontMetrics fm = g.getFontMetrics(GraphicDesignComponents.getSSProRegular("10"));
        return fm.stringWidth(s);
    }

    private void setProgressBarColor() {
        jProgressBar1.setForeground(new Color(255, 203, 5));
    }

    public boolean getAchievement() {
        return achievement;
    }

    public void sinalizeHeatSuccess() {
        disableMessageDisplay();
        machine.runCommand(new replicatorg.drivers.commands.DispatchCommand("M300"));
        bNext.setIcon(new ImageIcon(GraphicDesignComponents.getImage("panels", "b_simple_21.png")));
    }

    public void updateHeatBar() {

        
        machine.runCommand(new replicatorg.drivers.commands.ReadTemperature());
        machine.runCommand(new replicatorg.drivers.commands.SetTemperature(temperatureGoal));

        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(ExtruderMaintenance4.class.getName()).log(Level.SEVERE, null, ex);
        }
    

        double temperature = machine.getDriver().getTemperature();
        if (temperature > (int) (jProgressBar1.getValue() * 2)) {
            int val = (int) (temperature / 2.25);
            if (val > jProgressBar1.getValue()) {
                jProgressBar1.setValue(val);
            }
        }

        if (temperature <= (temperatureGoal - 10)) {
            achievement = false;
        } else {
            achievement = true;
            jProgressBar1.setValue(100);
        }
    }

    private void enableMessageDisplay() {
        jPanel3.setBackground(new Color(255, 205, 3));
        pWarning.setForeground(new Color(0, 0, 0));
    }

    public void disableMessageDisplay() {
        jPanel3.setBackground(new Color(248, 248, 248));
        pWarning.setForeground(new Color(248, 248, 248));
    }

    public void showMessage() {
        enableMessageDisplay();
        pWarning.setText(Languager.getTagValue(1, "FeedbackLabel", "HeatingMessage"));
    }

    private void moveToPosition() {
        Base.writeLog("Heating...", this.getClass());
        Point5d heat = machine.getTablePoints("heat");

        double acHigh = machine.getAcceleration("acHigh");
        double acMedium = machine.getAcceleration("acMedium");
        double spHigh = machine.getFeedrate("spHigh");

        machine.runCommand(new replicatorg.drivers.commands.SetTemperature(temperatureGoal));

        machine.runCommand(new replicatorg.drivers.commands.SetBusy(true));
        machine.runCommand(new replicatorg.drivers.commands.DispatchCommand("G28", COM.BLOCK));
        //turn off blower before heating
        machine.runCommand(new replicatorg.drivers.commands.DispatchCommand("M107"));
        machine.runCommand(new replicatorg.drivers.commands.DispatchCommand("M206 X" + acMedium));
        machine.runCommand(new replicatorg.drivers.commands.SetFeedrate(spHigh));
        machine.runCommand(new replicatorg.drivers.commands.QueuePoint(heat));
        machine.runCommand(new replicatorg.drivers.commands.DispatchCommand("M206 X" + acHigh));
        machine.runCommand(new replicatorg.drivers.commands.SetBusy(false));

    }

    private void finalizeHeat() {
        Base.writeLog("Cooling down...", this.getClass());
        machine.runCommand(new replicatorg.drivers.commands.SetTemperature(0));
    }

    private void evaluateInitialConditions() {
        achievement = false;
        temperatureGoal = 220;
        Base.getMainWindow().setEnabled(false);
        disableMessageDisplay();

        bBack.setVisible(false);
        bBack.setIcon(new ImageIcon(GraphicDesignComponents.getImage("panels", "b_disabled_21.png")));

        if (Boolean.valueOf(ProperDefault.get("firstTime")) != true) {
            bBack.setIcon(new ImageIcon(GraphicDesignComponents.getImage("panels", "b_disabled_21.png")));
            quickGuide = false;
        } else {
            bBack.setIcon(new ImageIcon(GraphicDesignComponents.getImage("panels", "b_simple_21.png")));
            quickGuide = true;
        }

    }

    private void doCancel() {
        dispose();
        Base.THREAD_KEEP_ALIVE = true;
        finalizeHeat();
        updateThread.stop();
        Base.bringAllWindowsToFront();
        Base.getMainWindow().getButtons().updatePressedStateButton("quick_guide");
        Base.getMainWindow().getButtons().updatePressedStateButton("maintenance");
        Base.getMainWindow().setEnabled(true);

        Point5d b = machine.getTablePoints("safe");
        double acLow = machine.getAcceleration("acLow");
        double acHigh = machine.getAcceleration("acHigh");
        double spHigh = machine.getFeedrate("spHigh");

        machine.runCommand(new replicatorg.drivers.commands.SetBusy(true));
        machine.runCommand(new replicatorg.drivers.commands.DispatchCommand("M206 X" + acLow));
        machine.runCommand(new replicatorg.drivers.commands.SetFeedrate(spHigh));
        machine.runCommand(new replicatorg.drivers.commands.QueuePoint(b));
        machine.runCommand(new replicatorg.drivers.commands.DispatchCommand("M206 X" + acHigh));
        machine.runCommand(new replicatorg.drivers.commands.DispatchCommand("G28", COM.BLOCK));
        machine.runCommand(new replicatorg.drivers.commands.SetBusy(false));


        if (ProperDefault.get("maintenance").equals("1")) {
            ProperDefault.remove("maintenance");
        }

        machine.runCommand(new replicatorg.drivers.commands.SetTemperature(0));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        bBack = new javax.swing.JLabel();
        bNext = new javax.swing.JLabel();
        bQuit = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lTitle = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jSeparator2 = new javax.swing.JSeparator();
        pText1 = new javax.swing.JLabel();
        pText2 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        pWarning = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(567, 501));
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 203, 5));
        jPanel2.setMinimumSize(new java.awt.Dimension(20, 38));
        jPanel2.setPreferredSize(new java.awt.Dimension(567, 38));

        bBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/replicatorg/app/ui/panels/b_disabled_21.png"))); // NOI18N
        bBack.setText("ANTERIOR");
        bBack.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bBackMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bBackMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bBackMousePressed(evt);
            }
        });

        bNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/replicatorg/app/ui/panels/b_disabled_21.png"))); // NOI18N
        bNext.setText("SEGUINTE");
        bNext.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bNextMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bNextMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bNextMousePressed(evt);
            }
        });

        bQuit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/replicatorg/app/ui/panels/b_simple_21.png"))); // NOI18N
        bQuit.setText("SAIR");
        bQuit.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bQuit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                bQuitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                bQuitMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                bQuitMousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bQuit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 331, Short.MAX_VALUE)
                .addComponent(bBack)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bNext)
                .addGap(12, 12, 12))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bBack)
                    .addComponent(bNext)
                    .addComponent(bQuit))
                .addGap(20, 20, 20))
        );

        jPanel1.setBackground(new java.awt.Color(248, 248, 248));

        lTitle.setText("PREPARACAO");
        lTitle.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/replicatorg/app/ui/panels/bico_extrusao.png"))); // NOI18N

        jProgressBar1.setBackground(new java.awt.Color(186, 186, 186));
        jProgressBar1.setPreferredSize(new java.awt.Dimension(150, 18));
        jProgressBar1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jProgressBar1StateChanged(evt);
            }
        });

        jSeparator2.setBackground(new java.awt.Color(255, 255, 255));
        jSeparator2.setForeground(new java.awt.Color(222, 222, 222));
        jSeparator2.setMinimumSize(new java.awt.Dimension(4, 1));
        jSeparator2.setPreferredSize(new java.awt.Dimension(50, 1));

        pText1.setText("Temperatura da Cabeca de Impressao");

        pText2.setText("Suspendisse potenti.");
        pText2.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jPanel4.setBackground(new java.awt.Color(248, 248, 248));
        jPanel4.setMinimumSize(new java.awt.Dimension(62, 26));
        jPanel4.setPreferredSize(new java.awt.Dimension(70, 30));
        jPanel4.setRequestFocusEnabled(false);

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/replicatorg/app/ui/panels/b_pressed_9.png"))); // NOI18N
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabel15MousePressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(9, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 203, 5));
        jPanel3.setPreferredSize(new java.awt.Dimension(169, 17));

        pWarning.setText("Heating...Please wait.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(pWarning, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(pWarning)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pText1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pText2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(182, 182, 182))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lTitle, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pText1)
                .addGap(6, 6, 6)
                .addComponent(pText2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jProgressBar1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jProgressBar1StateChanged
//        if (jProgressBar1.getValue() == 100) {
//            jLabel18.setIcon(new ImageIcon(GraphicDesignComponents.getImage("panels", "b_simple_21.png")));
//        }
    }//GEN-LAST:event_jProgressBar1StateChanged

    private void bQuitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bQuitMouseEntered
        bQuit.setIcon(new ImageIcon(GraphicDesignComponents.getImage("panels", "b_hover_21.png")));
    }//GEN-LAST:event_bQuitMouseEntered

    private void bQuitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bQuitMouseExited
        bQuit.setIcon(new ImageIcon(GraphicDesignComponents.getImage("panels", "b_simple_21.png")));
    }//GEN-LAST:event_bQuitMouseExited

    private void bNextMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bNextMouseEntered
        if (achievement) {
            bNext.setIcon(new ImageIcon(GraphicDesignComponents.getImage("panels", "b_hover_21.png")));
        }
    }//GEN-LAST:event_bNextMouseEntered

    private void bNextMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bNextMouseExited
        if (achievement) {
            bNext.setIcon(new ImageIcon(GraphicDesignComponents.getImage("panels", "b_simple_21.png")));
        }
    }//GEN-LAST:event_bNextMouseExited

    private void bBackMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bBackMouseEntered
        if (Boolean.valueOf(ProperDefault.get("firstTime"))) {
            bBack.setIcon(new ImageIcon(GraphicDesignComponents.getImage("panels", "b_hover_21.png")));
        }
    }//GEN-LAST:event_bBackMouseEntered

    private void bBackMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bBackMouseExited
        if (Boolean.valueOf(ProperDefault.get("firstTime"))) {
            bBack.setIcon(new ImageIcon(GraphicDesignComponents.getImage("panels", "b_simple_21.png")));
        }

    }//GEN-LAST:event_bBackMouseExited

    private void bNextMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bNextMousePressed
        if (achievement) {
            updateThread.stop();
            dispose();
            ExtruderMaintenance5 p = new ExtruderMaintenance5();
            p.setVisible(true);
        }
    }//GEN-LAST:event_bNextMousePressed

    private void bBackMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bBackMousePressed
        if (quickGuide) {
            dispose();
            WelcomeQuickguide p = new WelcomeQuickguide();
            p.setVisible(true);
            finalizeHeat();
            updateThread.stop();
        }
    }//GEN-LAST:event_bBackMousePressed

    private void bQuitMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bQuitMousePressed
        doCancel();
    }//GEN-LAST:event_bQuitMousePressed

    private void jLabel15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MousePressed
        doCancel();
    }//GEN-LAST:event_jLabel15MousePressed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bBack;
    private javax.swing.JLabel bNext;
    private javax.swing.JLabel bQuit;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lTitle;
    private javax.swing.JLabel pText1;
    private javax.swing.JLabel pText2;
    private javax.swing.JLabel pWarning;
    // End of variables declaration//GEN-END:variables
}

class ExtruderMaintenanceUpdateThread extends Thread {

ExtruderMaintenance4 window;

    public ExtruderMaintenanceUpdateThread(ExtruderMaintenance4 w) {
        super("Filament Heating Thread");
        window = w;
        Base.writeLog("Reading Temperature...", this.getClass());
    }

    @Override
    public void run() {

        boolean temperatureAchieved = false;
        // we'll break on interrupts
        while (!temperatureAchieved && !Base.THREAD_KEEP_ALIVE) {
//            System.out.println("Thread Alive "+this.getName());
            try {
                window.updateHeatBar();
                temperatureAchieved = window.getAchievement();
                Thread.sleep(500);
            } catch (Exception e) {
                Base.writeLog("Exception occured while reading Temperature ...", this.getClass());
                this.stop();
                break;
            }
            window.showMessage();
        }
        Base.writeLog("Temperature achieved...", this.getClass());
        window.sinalizeHeatSuccess();
        this.stop();

    }
}
