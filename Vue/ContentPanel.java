package Vue;
import javax.swing.*;

import Model.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.util.ArrayList;


public class ContentPanel extends JPanel implements Observer{
    public TuilePanel[][] tuilePanels;
    public Modele modele;
    public Vue vue;
    public ArrayList<Case> voisins = new ArrayList<Case>();
    public JPanel plateauPanel;
    
    public ContentPanel(Vue vue){
    super();
    this.modele = vue.modele;
    this.tuilePanels = new TuilePanel[modele.LARGEUR_PLATEAU][modele.LARGEUR_PLATEAU];
    this.vue = vue;
    this.modele.addObserver(this);
    this.setLayout(new GridLayout(1, 1));
    plateauPanel = new JPanel();

    voisins = modele.getJoueurCourant().getVoisinsOuOnPeutFaireDesTrucs();
    GridLayout grid = new GridLayout(modele.LARGEUR_PLATEAU, modele.LARGEUR_PLATEAU);


    grid.setVgap(5);
    grid.setHgap(5);
    Rectangle sizeScreen =  GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
    plateauPanel.setLayout(grid);
    // plateauPanel.setSize(new Dimension((int)Math.floor(sizeScreen.height*0.8), (int)Math.floor(sizeScreen.height*0.8)));
    plateauPanel.setPreferredSize(new Dimension((int)Math.floor(sizeScreen.height*0.8), (int)Math.floor(sizeScreen.height*0.8)));
    plateauPanel.setAlignmentX(CENTER_ALIGNMENT);
    plateauPanel.setAlignmentY(CENTER_ALIGNMENT);
    plateauPanel.setOpaque(false);
    
    
    int greyScale = 5;
    Color colorSidePanel = new Color(greyScale,greyScale, greyScale+40);

    this.setBackground(colorSidePanel); 

    // System.out.println("ContentPanel");
    for(int i = 0; i < modele.LARGEUR_PLATEAU; i++){
        for(int j = 0; j < modele.LARGEUR_PLATEAU; j++){
            TuilePanel tuilePanel = new TuilePanel(null, modele.plateau[i][j], modele.getJoueurCourant());
            // call paintComponent() on the panel
            plateauPanel.add(tuilePanel);
            tuilePanels[i][j]= tuilePanel;
        }
    }
    for(int i = 0; i < modele.LARGEUR_PLATEAU; i++){
        for(int j = 0; j < modele.LARGEUR_PLATEAU; j++){
            tuilePanels[i][j].addContentPanel(this);
        }
    }

    // System.out.println("ContentPanelFIN");

    this.add(plateauPanel);
    
    // addComponentListener(new ComponentAdapter() {

    //     @Override
    //     public void componentResized(ComponentEvent e) {
    //         int width = getWidth()-5*grid.getHgap();
    //         int height = getHeight()-5*grid.getVgap();
    //         int minSize = Math.min(width, height);
    //         int tuileSize = minSize / Math.max(modele.LARGEUR_PLATEAU, modele.LARGEUR_PLATEAU);

    //         Dimension tuileDimension = new Dimension(tuileSize, tuileSize);
    //         for (int i = 0; i < modele.LARGEUR_PLATEAU; i++) {
    //             for (int j = 0; j < modele.LARGEUR_PLATEAU; j++) {
    //                 TuilePanel tuilePanel = tuilePanels[i][j];
    //                 tuilePanel.setSize(tuileDimension);
    //                 tuilePanel.setPreferredSize(tuileDimension);
    //             };
    //         }
    //     }
    // });

}

public void update(){
    // System.out.println("ContentPanel update");
    voisins = modele.getJoueurCourant().getVoisinsOuOnPeutFaireDesTrucs();

    for(int i = 0; i < modele.LARGEUR_PLATEAU; i++){
        for(int j = 0; j < modele.LARGEUR_PLATEAU; j++){
            tuilePanels[i][j].tuile = (modele.plateau[i][j]);
            // tuilePanels[i][j].update();

        }
    }


    // System.out.println("ContentPanel update FIN");
    revalidate();
    repaint();
}


}
