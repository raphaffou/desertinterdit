package Vue;


import Model.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.*;

public class Vue extends JFrame implements Observer{
    public LeftPanel leftPanel;
    public RightPanel rightPanel;
    public ContentPanel contentPanel;
    public JProgressBar pbTopStormLevel;
    public JProgressBar pbBottomSandLevel;


    public Modele modele;

    public int stormLevel;
    public int sandLevel;


    public Vue(Modele modele){
        super();
        
        this.modele = modele;
        stormLevel = modele.getNiveauDeTempete();
        sandLevel = modele.getQteSable();
        modele.addObserver(this);

        Rectangle sizeScreen =  GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        Dimension sidePanelSize = new Dimension((sizeScreen.width-sizeScreen.height)/2, 0);

        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        this.setSize(sizeScreen.width, sizeScreen.height);

        this.setResizable(false);
        this.setVisible(true);

        this.setTitle("Desert Interdit");
        this.setLayout(new BorderLayout());
        Image ico = (Image) new ImageIcon("src/images/camel.png").getImage();
        this.setIconImage(ico);

        int greyScale = 35;
        Color colorSidePanel = new Color(greyScale,greyScale, greyScale+10);

        leftPanel = new LeftPanel(this, colorSidePanel);
        // leftPanel.setBackground(colorSidePanel);
        leftPanel.setPreferredSize(sidePanelSize);
        this.add(leftPanel, BorderLayout.WEST);
        
        rightPanel = new RightPanel(this, colorSidePanel, sidePanelSize);
        // rightPanel.setBackground(colorSidePanel);
        rightPanel.setPreferredSize(sidePanelSize);
        this.add(rightPanel, BorderLayout.EAST);

        contentPanel = new ContentPanel(this);
        
        // contentPanel.setBackground(Color.BLUE);
        this.add(contentPanel, BorderLayout.CENTER);


        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(219, 255, 0));
        topPanel.setLayout(new GridLayout(1, 1));

        pbTopStormLevel = new JProgressBar();
        pbTopStormLevel.setStringPainted(true);
        pbTopStormLevel.setMinimum(0);
        pbTopStormLevel.setMaximum(modele.MAX_TEMPETE_LEVEL);
        pbTopStormLevel.setBackground(new Color(0, 0, 0, 0));
        pbTopStormLevel.setOpaque(false);
        pbTopStormLevel.setForeground(new Color(0, 100, 255));
        pbTopStormLevel.setString("Storm Level " + stormLevel + "/" + modele.MAX_TEMPETE_LEVEL);
        // change color text progress bar

        pbTopStormLevel.setValue(stormLevel);
        
        topPanel.add(pbTopStormLevel);
        this.add(topPanel, BorderLayout.NORTH);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(255, 196, 0));
        bottomPanel.setLayout(new GridLayout(1, 1));



        pbBottomSandLevel = new JProgressBar();
        // pbBottomSandLevel.setOpaque(false);

        pbBottomSandLevel.setStringPainted(true);
        pbBottomSandLevel.setString("Sand Level " + sandLevel + "/" + modele.MAX_SABLE_LEVEL);
        pbBottomSandLevel.setMinimum(0);
        pbBottomSandLevel.setMaximum(modele.MAX_SABLE_LEVEL);
        pbBottomSandLevel.setValue(sandLevel);
        pbBottomSandLevel.setBackground(new Color(0, 0, 0, 0));
        pbBottomSandLevel.setOpaque(false);
        pbBottomSandLevel.setForeground(new Color(0, 100, 255));


        bottomPanel.add(pbBottomSandLevel);
        this.add(bottomPanel, BorderLayout.SOUTH);


    }

    public void update(){
        stormLevel = modele.getNiveauDeTempete();
        sandLevel = modele.getQteSable();
        pbTopStormLevel.setValue(stormLevel);
        pbTopStormLevel.setString("Storm Level " + stormLevel + "/" + modele.MAX_TEMPETE_LEVEL);
        pbBottomSandLevel.setValue(sandLevel);
        pbBottomSandLevel.setString("Sand Level " + sandLevel + "/" + modele.MAX_SABLE_LEVEL);
        this.revalidate();
        this.repaint();
    }

    public void ferme(){
        this.dispose();
    }
}
