package Vue;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.*;

import Model.Modele;
import Model.Observer;

public class ActionPanel extends JPanel implements Observer{
    public ArrayList<JButtonCustom> boutons;
    // public Vue vue;
    public Modele modele;
    public JButtonCustom b;
    public JLabel titre;
    
    public ActionPanel(Modele modele){
        super();
        // this.vue = vue;
        this.modele = modele;
        this.modele.addObserver(this);
        this.boutons = new ArrayList<JButtonCustom>();
        this.titre = new JLabel("Actions Possibles");
        this.titre.setAlignmentX(CENTER_ALIGNMENT);
        this.titre.setForeground(Color.WHITE);
        this.setOpaque(false);

        this.titre.setFont(new Font("Arial", Font.BOLD, 20));

        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setAlignmentX(CENTER_ALIGNMENT);
        
    }

    public void setBoutons(ArrayList<JButtonCustom> boutons){

        System.out.println(boutons.size());
        this.boutons = boutons;
        this.removeAll();
        if(boutons.size() != 0){
            this.titre.setText("Actions Possibles");
            // this.add(titre);
        }
        else{
            if(modele.getJoueurCourant().getNbActionsReste() > 0)
                this.titre.setText("Veuillez selectionner une case");
            else
                this.titre.setText("Piochez une carte svp");
            }
        this.add(titre);
        for(JButtonCustom bouton : this.boutons){
            bouton.setAlignmentX(CENTER_ALIGNMENT);
            this.add(Box.createVerticalStrut(10));
            this.add(bouton);

        }
        
    }

    public void update(){ revalidate(); repaint(); }


    }



