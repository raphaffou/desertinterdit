package Vue;

import Model.Case;
import Model.Tuile;
import Model.Vecteur;
import Model.Joueur;
import Model.Oasis;
import Controller.SelectCase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import Model.Observer;
import Model.PiecesMoteur;
import Model.PisteDecollage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class TuilePanel extends JPanel implements Observer{

    private Image backgroundImage;
    public Vue vue;
    public ContentPanel contentPanel;

    public Case tuile;
    public boolean isSelected = false;
    public ArrayList<Case> voisins = new ArrayList<>();
    public TuilePanel[][] tuilePanels = null;
    public Joueur currentJoueur;
    public JLabel coord;
    public boolean debug = false;
    

    public TuilePanel(Dimension dim, Case tuile, Joueur currentJoueur){
        super();
        this.tuile = tuile;
        this.currentJoueur = currentJoueur;
        
        this.tuile.modele.addObserver(this);
        // this.voisins.add(currentJoueur.getEmplacement());
        this.setBackground(Color.WHITE);

        System.out.println(tuile.filename());
        if(!(tuile instanceof Tuile)){
            backgroundImage = new ImageIcon("src/images/rounded/" + tuile.filename() +".png").getImage();
        }
        else{
        if(!((Tuile) tuile).isExplored() && !debug){
            if(tuile instanceof Oasis){
                backgroundImage = new ImageIcon("src/images/tuile_oasis.png").getImage();
            }
            else
            backgroundImage = new ImageIcon("src/images/tuile.png").getImage();
        }
        else{

            backgroundImage = new ImageIcon("src/images/rounded/" + tuile.filename() +".png").getImage();
        }
        }
        

        this.setOpaque(false);
        this.setLayout(null);
        this.setPreferredSize(dim);
        this.setAlignmentX(CENTER_ALIGNMENT);
        
        if(debug){
            coord = new JLabel();
            
            coord.setText(tuile.getPosition().toString());
            coord.setForeground(Color.PINK);
            coord.setAlignmentX(CENTER_ALIGNMENT);
            coord.setFont(new Font("Arial", Font.BOLD, 20));
            coord.setBounds(0, 0, 50, 50);
            this.add(coord);
        }

        // Ajouter une bordure vide pour réserver de l'espace pour la bordure arrondie
        // this.setBorder(new RoundedBorder(45, Color.RED, 5));

    }

    public void addContentPanel(ContentPanel contentPanel) {
        this.contentPanel = contentPanel;
        this.vue = contentPanel.vue;
        this.tuilePanels = contentPanel.tuilePanels;
        this.voisins = contentPanel.voisins;
        // this.voisins.add(currentJoueur.getEmpla  cement());
        
        TuilePanel[] voisinsPanel = new TuilePanel[voisins.size()];
        for(Case c : voisins){
            Vecteur pos = c.getPosition();
            voisinsPanel[voisins.indexOf(c)] = tuilePanels[pos.getLigne()][pos.getColonne()];
        }
        


        SelectCase selectCase = new SelectCase(this, this.vue.leftPanel.actionPanel, voisinsPanel, vue.modele) ;
        for(Case c : voisins){
            if(c.equals(tuile)){
                this.addMouseListener(selectCase);
            }
        }
        // Ajouter une bordure vide pour réserver de l'espace pour la bordure arrondie
        // this.setBorder(new RoundedBorder(45, Color.RED, 5));

    }

    public void update(){
        for(MouseListener ml : this.getMouseListeners()){
            this.removeMouseListener(ml);
        }
      
        this.currentJoueur = this.vue.modele.getJoueurCourant();
        voisins = contentPanel.voisins;
        if(debug){
            if(tuile instanceof Tuile){
                if(((Tuile) tuile).isExplored()){
                    coord.setForeground(Color.GREEN);
                }
                else{
                    coord.setForeground(Color.PINK);
                }
            }
            coord.setText(tuile.getPosition().toString());
        }

        if(!(tuile instanceof Tuile)){
            backgroundImage = new ImageIcon("src/images/rounded/" + tuile.filename() +".png").getImage();
        }
        else{
        if(!((Tuile) tuile).isExplored() && !debug){
            if(tuile instanceof Oasis){
                backgroundImage = new ImageIcon("src/images/tuile_oasis.png").getImage();
            }
            else
            backgroundImage = new ImageIcon("src/images/tuile.png").getImage();
        }
        else{

            backgroundImage = new ImageIcon("src/images/rounded/" + tuile.filename() +".png").getImage();
        }
        }
        TuilePanel[] voisinsPanel = new TuilePanel[voisins.size()];
        for(Case c : voisins){
            Vecteur pos = c.getPosition();
            voisinsPanel[voisins.indexOf(c)] = tuilePanels[pos.getLigne()][pos.getColonne()];
        }
        


        SelectCase selectCase = new SelectCase(this, this.vue.leftPanel.actionPanel, voisinsPanel, vue.modele) ;
        for(Case c : this.voisins){
            if(c.equals(tuile)){
                this.addMouseListener(selectCase);
            }
        }


        revalidate();
        repaint();
    }



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // System.out.println("zizi");
        if (backgroundImage != null) {
            //System.out.println("w = " + this.getWidth() + ", h = " + this.getHeight());
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            if(tuile instanceof Tuile && ((Tuile) tuile).getSable() > 0){
                g.drawImage(new ImageIcon("src/images/sable"+(((Tuile) tuile).getSable())+".png").getImage(), 0, 0, getWidth(), getHeight(), this);
                
            }
            if(tuile instanceof Tuile && ((Tuile) tuile).getPieces().size() > 0 && !(tuile instanceof PisteDecollage)){
                System.out.print("PIECE AFFICHEEEEE NORMALEMENT : " + tuile.getPosition());
                g.drawImage(new ImageIcon("src/images/"+((Tuile) tuile).getPieces().get(0).filename()+".png").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
                // g.drawImage(new ImageIcon("src/images/sable6.png").getImage(), 0, 0, getWidth(), getHeight(), this);

        }
        for(Case c : this.voisins){
            if(tuile.equals(c)){
                if(this.isSelected)
                    g.drawImage(new ImageIcon("src/images/rounded/red_bold_border.png").getImage(), 0, 0, getWidth(), getHeight(), this);
                else{
                    if(this.currentJoueur.peutSeDeplacer(tuile)){
                        if(this.currentJoueur.peutDonnerEau(tuile)){
                            g.drawImage(new ImageIcon("src/images/rounded/blue_bold_border.png").getImage(), 0, 0, getWidth(), getHeight(), this);
                        }
                        else{
                            g.drawImage(new ImageIcon("src/images/rounded/blue_border.png").getImage(), 0, 0, getWidth(), getHeight(), this);
                        }
                        g.drawImage(new ImageIcon("src/images/rounded/green_border.png").getImage(), 0, 0, getWidth(), getHeight(), this);
                    }
                    else if(this.currentJoueur.peutDonnerEau(tuile)){
                        if(this.currentJoueur.peutDesensabler(tuile)){
                            g.drawImage(new ImageIcon("src/images/rounded/blue_bold_border.png").getImage(), 0, 0, getWidth(), getHeight(), this);
                            g.drawImage(new ImageIcon("src/images/rounded/yellow_border.png").getImage(), 0, 0, getWidth(), getHeight(), this);
                        }
                        else{
                            if(this.currentJoueur.getEmplacement().equals(tuile)){
                                g.drawImage(new ImageIcon("src/images/rounded/blue_bold_border.png").getImage(), 0, 0, getWidth(), getHeight(), this);
                                g.drawImage(new ImageIcon("src/images/rounded/orange_border.png").getImage(), 0, 0, getWidth(), getHeight(), this);
                            }
                            else
                            g.drawImage(new ImageIcon("src/images/rounded/blue_border.png").getImage(), 0, 0, getWidth(), getHeight(), this);
                        }
                    }
                    else {
                        if(this.currentJoueur.peutDesensabler(tuile)){
                            g.drawImage(new ImageIcon("src/images/rounded/yellow_border.png").getImage(), 0, 0, getWidth(), getHeight(), this);
                        }
                        else if(this.currentJoueur.getEmplacement().equals(tuile)){
                            g.drawImage(new ImageIcon("src/images/rounded/orange_border.png").getImage(), 0, 0, getWidth(), getHeight(), this);
                        }
                    }
                }
            }
        }
        if(tuile instanceof PisteDecollage){

            int size = getWidth()/4;
            int gap = 5;
            int i = 0;
            for(PiecesMoteur pm : ((PisteDecollage) tuile).getPieces()){
                g.drawImage(new ImageIcon("src/images/avatar/"+pm.filename()+"2.png").getImage(), size*i+gap, size, size-gap, size-gap, this);
                i++;
            }
        }

        if(tuile instanceof Tuile){
            // System.out.println("Joueur sur la tuile : " + ((Tuile) tuile).getJoueurs().size() + " " + tuile.getPosition());
            int i = 0;
            int size;
            if(((Tuile) tuile).getJoueurs().size() < 3){
                size = getWidth()/3;
            }
            else size = getWidth()/((Tuile) tuile).getJoueurs().size();
            int gap = 5;
            for(Joueur j : ((Tuile) tuile).getJoueurs()) {
                // System.out.println("Joueur sur la tuile : " + j.getId() + " " + tuile.getPosition() + " " + "src/images/avatar/avatar" + (j.getId()+1) + ".png");
                g.drawImage(new ImageIcon("src/images/avatar/avatar" + (j.getId()+1) + ".png").getImage(), i*(size) + gap , getHeight()-size, size-gap, size-gap, this);
                i++;
            }
        }
        
    }
}
