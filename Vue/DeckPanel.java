package Vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseListener;

import javax.swing.*;

import Controller.CtrlCardPrev;
import Controller.CtrlPioche;
import Model.*;


public class DeckPanel extends JPanel implements Observer{

    public Vue vue;
    public Modele modele;
    public RightPanel rightPanel;

    public JPanel pile, tas;

    public String link = null;




    public DeckPanel(Vue vue, RightPanel rightPanel){
        super();
        this.vue = vue;
        this.modele = vue.modele;
        this.rightPanel = rightPanel;

        
        this.modele.addObserver(this);

        // this.setBackground(Color.RED);
        this.setOpaque(false);

        GridLayout gridLayout = new GridLayout(1, 2);
        gridLayout.setHgap(10);
        this.setLayout(gridLayout);

        


        pile = new JPanel(){
            public void paintComponent(Graphics g){
                if(modele.getJoueurCourant().getNbActions() == 0){
                    pile.setBorder(new RoundedBorder(20, Color.GREEN, 2));
                }
                else{
                    pile.setBorder(new RoundedBorder(20, Color.LIGHT_GRAY, 2));
                }
                super.paintComponent(g);
                    Image img = new ImageIcon("src/images/cartes/dos.png").getImage();
                    g.drawImage(img, 0, 0, pile.getWidth(), pile.getHeight(), pile);
            };
        };
        tas = new JPanel(){
            public void paintComponent(Graphics g){

                super.paintComponent(g);
                if(link != null){
                    this.setOpaque(false);
                    Image img = new ImageIcon("src/images/cartes/"+link).getImage();
                    g.drawImage(img, 0, 0, tas.getWidth(), tas.getHeight(), tas);
                }
            };
        };
        pile.setBorder(new RoundedBorder(20, Color.LIGHT_GRAY, 2));

        tas.setBackground(new Color(255, 255, 255, 30));
        tas.setBorder(new RoundedBorder(20, Color.LIGHT_GRAY, 2));
        pile.setOpaque(false);
        tas.setOpaque(true);

        CtrlPioche ctrl = new CtrlPioche(this);
        pile.addMouseListener(ctrl);


        float ratio = 450f/320f;
        int width = (int)(rightPanel.sidePanelSize.width/2) - gridLayout.getHgap();
        Dimension dim = new Dimension(width, (int) (width*ratio));
        System.out.println(dim);
        pile.setPreferredSize(dim);
        pile.setSize(dim);
        pile.setMinimumSize(dim);
        pile.setMaximumSize(dim);
        
        tas.setPreferredSize(dim);
        tas.setSize(dim);
        tas.setMinimumSize(dim);
        tas.setMaximumSize(dim);

        // Image img = new ImageIcon("src/images/cartes/dos.png").getImage();
        // img.getScaledInstance(10, 20, Image.SCALE_SMOOTH);
        // JLabel pileImg = new JLabel(new ImageIcon(img));

        // pile.add(pileImg);

        this.add(pile);
        this.add(tas);

        // this.add(new JLabel("DeckPanel"));

        


        
    }


    public void setLink(String link){
        for(MouseListener ml : tas.getMouseListeners()){
            tas.removeMouseListener(ml);
        }
        
        this.link = link;
        CtrlCardPrev ctrl = new CtrlCardPrev(this.link);
        tas.addMouseListener(ctrl);
    }


    public void update() {


        revalidate();
        repaint();
        
    }



}

