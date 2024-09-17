package Vue;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import Controller.CtrlCardPrev;
import Model.Joueur;
import Model.Modele;
import Model.Observer;
import Model.PiecesMoteur;

public class WaterPanel extends JPanel implements Observer {
    Modele modele;
    Vue vue; 
    ArrayList<JProgressBar> progressBars = new ArrayList<>();
    ArrayList<JLabel> labels = new ArrayList<>();
    ArrayList<JPanel> pieces = new ArrayList<>();

    
    public WaterPanel(Vue vue){
        super();
        this.vue = vue;
        this.modele = vue.modele;
        modele.addObserver(this);
        ArrayList<Joueur> joueurs = modele.getJoueurs();
        this.setOpaque(false);
        
        for(Joueur joueur : joueurs){
            JProgressBar progressBar = new JProgressBar();
            JPanel head = new JPanel();
            JPanel info = new JPanel();
            head.setOpaque(false);
            head.setLayout(new FlowLayout());
            head.setAlignmentX(CENTER_ALIGNMENT);
            head.addMouseListener(new CtrlCardPrev(joueur.role()+".png"));

            info.setOpaque(false);
            info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
            info.setAlignmentX(LEFT_ALIGNMENT);
            info.setAlignmentY(CENTER_ALIGNMENT);
            BufferedImage pic = null;
            try{
                pic = ImageIO.read(new File("src/images/avatar/avatar"+(joueur.getId()+1)+".png"));
            } catch(Exception e){
                System.out.println("Image not found" + e);
            }
            
            Image pic2 = pic.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            JLabel img = new JLabel(new ImageIcon(pic2));
            img.setOpaque(false);
            head.add(img);
            JLabel nomJoueur = new JLabel(joueur.getNom());
            nomJoueur.setAlignmentX(LEFT_ALIGNMENT);
            nomJoueur.setFont(new Font("Arial", Font.BOLD, 20));
            nomJoueur.setForeground(Color.WHITE);
            if(joueur == modele.getJoueurCourant()){
                nomJoueur.setForeground(Color.GREEN);
            }
            nomJoueur.setOpaque(false);
            JLabel role = new JLabel(joueur.role());
            role.setAlignmentX(LEFT_ALIGNMENT);
            role.setFont(new Font("Arial", Font.BOLD, 15));
            role.setForeground(new Color(0, 255, 204));
            role.setOpaque(false);


            JPanel piece = new JPanel();
            piece.setOpaque(false);
            piece.setLayout(new FlowLayout());
            piece.setAlignmentX(LEFT_ALIGNMENT);
            piece.setAlignmentY(CENTER_ALIGNMENT);

            for(PiecesMoteur pm : joueur.getPieces()){
                BufferedImage pic3 = null;
                try{
                    pic3 = ImageIO.read(new File("src/images/avatar/"+pm.getNom()+".png"));
                } catch(Exception e){
                    System.out.println("Image not found" + e);
                }
                
                Image pic4 = pic3.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
                JLabel img2 = new JLabel(new ImageIcon(pic4));
                img2.setOpaque(false);
                img2.setAlignmentX(LEFT_ALIGNMENT);
                piece.add(img2);
            }
            




            progressBar.setValue(joueur.getWaterLvl());
            progressBar.setMaximum(joueur.getMaxWaterLvl());
            progressBar.setMinimum(0);
            progressBar.setStringPainted(true);
            progressBar.setString(joueur.getWaterLvl() + "/" + joueur.getMaxWaterLvl());
            progressBar.setOpaque(false);
            progressBars.add(progressBar);
            labels.add(nomJoueur);
            pieces.add(piece);
            info.add(nomJoueur);
            info.add(role);
            info.add(piece);

            head.add(info);
            this.add(head);
            this.add(progressBar);
        }


        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    }


    public void update() {
        ArrayList<Joueur> joueurs = modele.getJoueurs();
        
        
        for(int i = 0; i < joueurs.size(); i++){
            labels.get(i).setForeground(Color.WHITE);
            if(joueurs.get(i) == modele.getJoueurCourant()){
                labels.get(i).setForeground(Color.GREEN);
            }
            pieces.get(i).removeAll();
            for(PiecesMoteur pm : joueurs.get(i).getPieces()){
                BufferedImage pic3 = null;
                try{
                    pic3 = ImageIO.read(new File("src/images/avatar/"+pm.filename()+"2.png"));
                } catch(Exception e){
                    System.out.println("Image not found" + e);
                }
                
                Image pic4 = pic3.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                JLabel img2 = new JLabel(new ImageIcon(pic4));
                img2.setOpaque(false);
                pieces.get(i).add(img2);
            }
            progressBars.get(i).setValue(joueurs.get(i).getWaterLvl());
            progressBars.get(i).setMaximum(joueurs.get(i).getMaxWaterLvl());
            progressBars.get(i).setString(joueurs.get(i).getWaterLvl() + "/" + joueurs.get(i).getMaxWaterLvl());
        }
    }

}
