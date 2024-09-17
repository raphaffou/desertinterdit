package Vue;

import Model.*;

import java.awt.Color;

import javax.swing.*;

public class LeftPanel extends JPanel{
    private Modele modele;
    public ActionPanel actionPanel;
    public PlayerPanel playerPanel;
    public Vue vue;


    public LeftPanel(Vue vue, Color color){
        super();
        this.vue = vue;
        this.modele = this.vue.modele;
        
        
        this.setBackground(color);
        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        
        this.setLayout(boxLayout);
        this.setAlignmentY(TOP_ALIGNMENT);

        playerPanel = new PlayerPanel(this.modele);
        this.add(playerPanel);



        

        this.add(Box.createVerticalStrut(100));

        actionPanel = new ActionPanel(this.modele);
        this.add(actionPanel);


        
        this.add(Box.createVerticalStrut(10000));
    }

    

}
