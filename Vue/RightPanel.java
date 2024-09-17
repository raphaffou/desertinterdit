package Vue;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;

public class RightPanel extends JPanel{
    public DeckPanel deckPanel;
    public Vue vue;
    public Color colorSidePanel;
    public Dimension sidePanelSize;
    public WaterPanel waterPanel;

    public RightPanel(Vue vue, Color color, Dimension sidePanelSize){
        super();
        this.sidePanelSize = sidePanelSize;
        this.colorSidePanel = color;
        this.vue = vue;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(colorSidePanel);
        deckPanel = new DeckPanel(vue, this);

        this.add(deckPanel);

        this.add(Box.createVerticalStrut(50));
        waterPanel = new WaterPanel(vue);
        this.add(waterPanel);

        this.add(Box.createVerticalStrut(10000));


    }
}
