﻿namespace sample3dscan.cs
{
    partial class MainForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            this.Start = new System.Windows.Forms.Button();
            this.DeviceMenu = new System.Windows.Forms.ToolStripMenuItem();
            this.ColorMenu = new System.Windows.Forms.ToolStripMenuItem();
            this.ColorNone = new System.Windows.Forms.ToolStripMenuItem();
            this.MainMenu = new System.Windows.Forms.MenuStrip();
            this.DepthMenu = new System.Windows.Forms.ToolStripMenuItem();
            this.DepthNone = new System.Windows.Forms.ToolStripMenuItem();
            this.ModeMenu = new System.Windows.Forms.ToolStripMenuItem();
            this.ModeLive = new System.Windows.Forms.ToolStripMenuItem();
            this.ModePlayback = new System.Windows.Forms.ToolStripMenuItem();
            this.ModeRecord = new System.Windows.Forms.ToolStripMenuItem();
            this.Status2 = new System.Windows.Forms.StatusStrip();
            this.StatusLabel = new System.Windows.Forms.ToolStripStatusLabel();
            this.MainPanel = new System.Windows.Forms.PictureBox();
            this.Reconstruct = new System.Windows.Forms.Button();
            this.Solid = new System.Windows.Forms.CheckBox();
            this.targetingOptions = new System.Windows.Forms.ComboBox();
            this.Textured = new System.Windows.Forms.CheckBox();
            this.tooClose = new System.Windows.Forms.Label();
            this.tooFar = new System.Windows.Forms.Label();
            this.trackingLost = new System.Windows.Forms.Label();
            this.panel1 = new System.Windows.Forms.Panel();
            this.MainMenu.SuspendLayout();
            this.Status2.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.MainPanel)).BeginInit();
            this.panel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // Start
            // 
            this.Start.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.Start.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Start.Location = new System.Drawing.Point(905, 507);
            this.Start.Name = "Start";
            this.Start.Size = new System.Drawing.Size(147, 45);
            this.Start.TabIndex = 2;
            this.Start.Text = "Start Camera";
            this.Start.UseVisualStyleBackColor = true;
            this.Start.Visible = false;
            this.Start.Click += new System.EventHandler(this.Start_Click);
            // 
            // DeviceMenu
            // 
            this.DeviceMenu.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.DeviceMenu.Name = "DeviceMenu";
            this.DeviceMenu.Size = new System.Drawing.Size(110, 35);
            this.DeviceMenu.Text = "Device";
            this.DeviceMenu.Visible = false;
            // 
            // ColorMenu
            // 
            this.ColorMenu.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.ColorNone});
            this.ColorMenu.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ColorMenu.Name = "ColorMenu";
            this.ColorMenu.Size = new System.Drawing.Size(91, 35);
            this.ColorMenu.Text = "Color";
            this.ColorMenu.Visible = false;
            this.ColorMenu.Click += new System.EventHandler(this.ColorMenu_Click);
            // 
            // ColorNone
            // 
            this.ColorNone.Name = "ColorNone";
            this.ColorNone.Size = new System.Drawing.Size(159, 36);
            this.ColorNone.Text = "None";
            // 
            // MainMenu
            // 
            this.MainMenu.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.MainMenu.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.MainMenu.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.DeviceMenu,
            this.ColorMenu,
            this.DepthMenu,
            this.ModeMenu});
            this.MainMenu.Location = new System.Drawing.Point(0, 0);
            this.MainMenu.Name = "MainMenu";
            this.MainMenu.Padding = new System.Windows.Forms.Padding(7, 2, 0, 2);
            this.MainMenu.RenderMode = System.Windows.Forms.ToolStripRenderMode.Professional;
            this.MainMenu.Size = new System.Drawing.Size(1094, 39);
            this.MainMenu.TabIndex = 0;
            this.MainMenu.Text = "MainMenu";
            this.MainMenu.Visible = false;
            // 
            // DepthMenu
            // 
            this.DepthMenu.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.DepthNone});
            this.DepthMenu.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.DepthMenu.Name = "DepthMenu";
            this.DepthMenu.Size = new System.Drawing.Size(99, 35);
            this.DepthMenu.Text = "Depth";
            this.DepthMenu.Visible = false;
            // 
            // DepthNone
            // 
            this.DepthNone.Name = "DepthNone";
            this.DepthNone.Size = new System.Drawing.Size(159, 36);
            this.DepthNone.Text = "None";
            // 
            // ModeMenu
            // 
            this.ModeMenu.DropDownItems.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.ModeLive,
            this.ModePlayback,
            this.ModeRecord});
            this.ModeMenu.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ModeMenu.Name = "ModeMenu";
            this.ModeMenu.Size = new System.Drawing.Size(93, 35);
            this.ModeMenu.Text = "Mode";
            this.ModeMenu.Visible = false;
            // 
            // ModeLive
            // 
            this.ModeLive.Checked = true;
            this.ModeLive.CheckState = System.Windows.Forms.CheckState.Checked;
            this.ModeLive.Name = "ModeLive";
            this.ModeLive.Size = new System.Drawing.Size(205, 36);
            this.ModeLive.Text = "Live";
            this.ModeLive.Click += new System.EventHandler(this.ModeLive_Click);
            // 
            // ModePlayback
            // 
            this.ModePlayback.Name = "ModePlayback";
            this.ModePlayback.Size = new System.Drawing.Size(205, 36);
            this.ModePlayback.Text = "Playback";
            this.ModePlayback.Click += new System.EventHandler(this.ModePlayback_Click);
            // 
            // ModeRecord
            // 
            this.ModeRecord.Name = "ModeRecord";
            this.ModeRecord.Size = new System.Drawing.Size(205, 36);
            this.ModeRecord.Text = "Record";
            this.ModeRecord.Click += new System.EventHandler(this.ModeRecord_Click);
            // 
            // Status2
            // 
            this.Status2.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F);
            this.Status2.ImageScalingSize = new System.Drawing.Size(20, 20);
            this.Status2.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.StatusLabel});
            this.Status2.LayoutStyle = System.Windows.Forms.ToolStripLayoutStyle.Flow;
            this.Status2.Location = new System.Drawing.Point(0, 663);
            this.Status2.Name = "Status2";
            this.Status2.Padding = new System.Windows.Forms.Padding(1, 0, 16, 0);
            this.Status2.Size = new System.Drawing.Size(1094, 5);
            this.Status2.TabIndex = 25;
            this.Status2.Text = "OK";
            // 
            // StatusLabel
            // 
            this.StatusLabel.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.StatusLabel.Margin = new System.Windows.Forms.Padding(120, 3, 0, 2);
            this.StatusLabel.Name = "StatusLabel";
            this.StatusLabel.Size = new System.Drawing.Size(0, 0);
            // 
            // MainPanel
            // 
            this.MainPanel.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.MainPanel.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.MainPanel.ErrorImage = null;
            this.MainPanel.InitialImage = null;
            this.MainPanel.Location = new System.Drawing.Point(213, 12);
            this.MainPanel.Name = "MainPanel";
            this.MainPanel.Size = new System.Drawing.Size(561, 622);
            this.MainPanel.TabIndex = 27;
            this.MainPanel.TabStop = false;
            // 
            // Reconstruct
            // 
            this.Reconstruct.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.Reconstruct.BackColor = System.Drawing.Color.Gold;
            this.Reconstruct.Enabled = false;
            this.Reconstruct.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.Reconstruct.Font = new System.Drawing.Font("Tahoma", 10.8F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Reconstruct.ForeColor = System.Drawing.Color.White;
            this.Reconstruct.Location = new System.Drawing.Point(849, 51);
            this.Reconstruct.Name = "Reconstruct";
            this.Reconstruct.Size = new System.Drawing.Size(203, 42);
            this.Reconstruct.TabIndex = 37;
            this.Reconstruct.Text = "START SCANNING";
            this.Reconstruct.UseVisualStyleBackColor = false;
            this.Reconstruct.Click += new System.EventHandler(this.Reconstruct_Click);
            // 
            // Solid
            // 
            this.Solid.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.Solid.AutoSize = true;
            this.Solid.BackColor = System.Drawing.SystemColors.Control;
            this.Solid.BackgroundImageLayout = System.Windows.Forms.ImageLayout.None;
            this.Solid.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.Solid.Checked = true;
            this.Solid.CheckState = System.Windows.Forms.CheckState.Checked;
            this.Solid.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Solid.Location = new System.Drawing.Point(901, 409);
            this.Solid.Name = "Solid";
            this.Solid.Size = new System.Drawing.Size(96, 35);
            this.Solid.TabIndex = 46;
            this.Solid.Text = "Solid";
            this.Solid.UseVisualStyleBackColor = false;
            this.Solid.Visible = false;
            // 
            // targetingOptions
            // 
            this.targetingOptions.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.targetingOptions.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.targetingOptions.Items.AddRange(new object[] {
            "Object",
            "Face",
            "Head",
            "Body",
            "Full"});
            this.targetingOptions.Location = new System.Drawing.Point(901, 544);
            this.targetingOptions.Name = "targetingOptions";
            this.targetingOptions.RightToLeft = System.Windows.Forms.RightToLeft.No;
            this.targetingOptions.Size = new System.Drawing.Size(107, 38);
            this.targetingOptions.TabIndex = 44;
            this.targetingOptions.Text = "Object";
            this.targetingOptions.Visible = false;
            // 
            // Textured
            // 
            this.Textured.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.Textured.AutoSize = true;
            this.Textured.CheckAlign = System.Drawing.ContentAlignment.MiddleRight;
            this.Textured.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.Textured.Location = new System.Drawing.Point(880, 368);
            this.Textured.Name = "Textured";
            this.Textured.Size = new System.Drawing.Size(128, 35);
            this.Textured.TabIndex = 45;
            this.Textured.Text = "Texture";
            this.Textured.UseVisualStyleBackColor = true;
            this.Textured.Visible = false;
            // 
            // tooClose
            // 
            this.tooClose.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Right)));
            this.tooClose.BackColor = System.Drawing.Color.DarkGray;
            this.tooClose.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.tooClose.ForeColor = System.Drawing.Color.White;
            this.tooClose.Location = new System.Drawing.Point(26, 144);
            this.tooClose.Name = "tooClose";
            this.tooClose.Size = new System.Drawing.Size(160, 30);
            this.tooClose.TabIndex = 47;
            this.tooClose.Text = "Move back";
            this.tooClose.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // tooFar
            // 
            this.tooFar.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.tooFar.BackColor = System.Drawing.Color.DarkGray;
            this.tooFar.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F);
            this.tooFar.ForeColor = System.Drawing.Color.White;
            this.tooFar.Location = new System.Drawing.Point(26, 50);
            this.tooFar.Name = "tooFar";
            this.tooFar.Size = new System.Drawing.Size(160, 30);
            this.tooFar.TabIndex = 48;
            this.tooFar.Text = "Move forward";
            this.tooFar.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            this.tooFar.Click += new System.EventHandler(this.tooFar_Click);
            // 
            // trackingLost
            // 
            this.trackingLost.Anchor = System.Windows.Forms.AnchorStyles.Bottom;
            this.trackingLost.AutoSize = true;
            this.trackingLost.Font = new System.Drawing.Font("Microsoft Sans Serif", 15.75F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.trackingLost.ForeColor = System.Drawing.Color.Red;
            this.trackingLost.Location = new System.Drawing.Point(383, 638);
            this.trackingLost.Name = "trackingLost";
            this.trackingLost.Size = new System.Drawing.Size(474, 31);
            this.trackingLost.TabIndex = 49;
            this.trackingLost.Text = "Tracking lost. Align images to recover.";
            this.trackingLost.Visible = false;
            // 
            // panel1
            // 
            this.panel1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.panel1.BackColor = System.Drawing.SystemColors.ControlLight;
            this.panel1.Controls.Add(this.tooFar);
            this.panel1.Controls.Add(this.tooClose);
            this.panel1.Location = new System.Drawing.Point(849, 99);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(203, 232);
            this.panel1.TabIndex = 50;
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(9F, 18F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.White;
            this.ClientSize = new System.Drawing.Size(1094, 668);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.trackingLost);
            this.Controls.Add(this.Solid);
            this.Controls.Add(this.targetingOptions);
            this.Controls.Add(this.Textured);
            this.Controls.Add(this.Reconstruct);
            this.Controls.Add(this.MainPanel);
            this.Controls.Add(this.Status2);
            this.Controls.Add(this.Start);
            this.Controls.Add(this.MainMenu);
            this.DoubleBuffered = true;
            this.Font = new System.Drawing.Font("Microsoft Sans Serif", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.ForeColor = System.Drawing.Color.White;
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MainMenuStrip = this.MainMenu;
            this.Name = "MainForm";
            this.Text = "BEESOFT - Intel(R) RealSense(TM)";
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            this.MainMenu.ResumeLayout(false);
            this.MainMenu.PerformLayout();
            this.Status2.ResumeLayout(false);
            this.Status2.PerformLayout();
            ((System.ComponentModel.ISupportInitialize)(this.MainPanel)).EndInit();
            this.panel1.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        private void ColorMenu_Click(object sender, System.EventArgs e)
        {
            this.ColorMenu.ShowDropDown();
        }

        #endregion

        private System.Windows.Forms.Button Start;
        private System.Windows.Forms.ToolStripMenuItem DeviceMenu;
        private System.Windows.Forms.ToolStripMenuItem ColorMenu;
        private System.Windows.Forms.MenuStrip MainMenu;
        private System.Windows.Forms.StatusStrip Status2;
        private System.Windows.Forms.ToolStripStatusLabel StatusLabel;
        private System.Windows.Forms.PictureBox MainPanel;
        private System.Windows.Forms.ToolStripMenuItem ModeMenu;
        private System.Windows.Forms.ToolStripMenuItem ModeLive;
        private System.Windows.Forms.ToolStripMenuItem ModePlayback;
        private System.Windows.Forms.ToolStripMenuItem ModeRecord;
        private System.Windows.Forms.ToolStripMenuItem DepthMenu;
        private System.Windows.Forms.ToolStripMenuItem ColorNone;
        private System.Windows.Forms.ToolStripMenuItem DepthNone;
        private System.Windows.Forms.Button Reconstruct;
        private System.Windows.Forms.CheckBox Solid;
        private System.Windows.Forms.ComboBox targetingOptions;
        private System.Windows.Forms.CheckBox Textured;
        private System.Windows.Forms.Label tooClose;
        private System.Windows.Forms.Label tooFar;
        private System.Windows.Forms.Label trackingLost;
        private System.Windows.Forms.Panel panel1;
    }
}