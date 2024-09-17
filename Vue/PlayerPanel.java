package Vue;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import Controller.CtrlCardPrev;
import Model.*;


public class PlayerPanel extends JPanel implements Observer{
    private Modele modele;
    private Joueur joueur;
    private Joueur JoueurSuivant;

    JPanel panel;
    JPanel actionPanel;
    JPanel waterPanel;
    JProgressBar waterProgressBar;
    JProgressBar actionProgressBar;
    JLabel nom;
    JLabel role;
    JLabel nextJoueur;
    JLabel img;
    JPanel panel2;

    public PlayerPanel(Modele model){

        
        super();
        this.modele = model;
        this.modele.addObserver(this);
        this.joueur = modele.getJoueurCourant();
        this.JoueurSuivant = modele.getNextJoueur();
        this.setOpaque(false);
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        BufferedImage pic = null;
        try{
            pic = ImageIO.read(new File("src/images/avatar/avatar"+(joueur.getId()+1)+".png"));
        } catch(Exception e){
            System.out.println("Image not found" + e);
        }
        panel.addMouseListener(new CtrlCardPrev(joueur.role()+".png"));

        Image pic2 = pic.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        img = new JLabel(new ImageIcon(pic2));
        img.setOpaque(false);
        panel.add(img);
        
        panel2 = new JPanel();
        panel2.setOpaque(false);
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        nom = new JLabel(joueur.getNom());
        nom.setOpaque(false);
        // color JLabel
        nom.setForeground(new Color(0, 200, 255));
        nom.setFont(new Font("Arial", Font.BOLD, 20));
        panel2.add(nom);
        role = new JLabel("Role : " + joueur.role());
        role.setOpaque(false);
        // color JLabel
        role.setForeground(new Color(0, 255, 204));
        role.setFont(new Font("Arial", Font.BOLD, 15));
        panel2.add(role);

        nextJoueur = new JLabel("Prochain joueur : " + JoueurSuivant.getNom());
        nextJoueur.setOpaque(false);
        // color JLabel
        nextJoueur.setForeground(new Color(255, 0, 98));
        nextJoueur.setFont(new Font("Arial", Font.ITALIC, 12));
        panel2.add(nextJoueur);

        // panel.setBackground(Color.WHITE);
        panel.add(panel2);
        this.add(panel);

        this.add(Box.createVerticalStrut(10));

        
        actionPanel = new JPanel();
        actionPanel.setOpaque(false);
        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.Y_AXIS));


        JLabel action = new JLabel("Actions");
        action.setOpaque(false);
        action.setAlignmentX(CENTER_ALIGNMENT);
        // color JLabel
        action.setForeground(new Color(255, 255, 255));
        action.setFont(new Font("Arial", Font.BOLD, 15));
        actionPanel.add(action);
        actionProgressBar = new JProgressBar();
        actionProgressBar.setOpaque(false);
        actionProgressBar.setMinimum(0);
        actionProgressBar.setMaximum(joueur.getNbActionsMax());
        actionProgressBar.setValue(joueur.getNbActionsReste());
        actionProgressBar.setString(actionProgressBar.getValue() + "/" + actionProgressBar.getMaximum());
        actionProgressBar.setStringPainted(true);

        actionPanel.add(actionProgressBar);

        this.add(actionPanel);
        
        this.add(Box.createVerticalStrut(10));

        waterPanel = new JPanel();
        waterPanel.setOpaque(false);
        waterPanel.setLayout(new BoxLayout(waterPanel, BoxLayout.Y_AXIS));
        
        JLabel water = new JLabel("Niveau d'eau");
        water.setAlignmentX(CENTER_ALIGNMENT);
        water.setOpaque(false);
        // color JLabel
        water.setForeground(new Color(255, 255, 255));
        water.setFont(new Font("Arial", Font.BOLD, 15));
        waterPanel.add(water);
        waterProgressBar = new JProgressBar();
        waterProgressBar.setOpaque(false);
        waterProgressBar.setMinimum(0);
        waterProgressBar.setMaximum(joueur.getMaxWaterLvl());
        waterProgressBar.setValue(joueur.getWaterLvl());
        waterProgressBar.setStringPainted(true);
        waterProgressBar.setString(waterProgressBar.getValue() + "/" + waterProgressBar.getMaximum());
        waterPanel.add(waterProgressBar);

        this.add(waterPanel);

    }

    public void update(){
        this.joueur = modele.getJoueurCourant();
        this.JoueurSuivant = modele.getNextJoueur();
        nom.setText(joueur.getNom());
        // role.setText("Role : " + joueur.getRole());
        nextJoueur.setText("Prochain joueur : " + JoueurSuivant.getNom());
        waterProgressBar.setMaximum(joueur.getMaxWaterLvl());
        waterProgressBar.setValue(joueur.getWaterLvl());
        waterProgressBar.setString(waterProgressBar.getValue() + "/" + waterProgressBar.getMaximum());
        actionProgressBar.setMaximum(joueur.getNbActionsMax());
        actionProgressBar.setValue(joueur.getNbActionsReste());
        role.setText("Role : " + joueur.role());
        for(MouseListener ml : panel.getMouseListeners()){
            panel.removeMouseListener(ml);
        }
        panel.addMouseListener(new CtrlCardPrev(joueur.role()+".png"));

        actionProgressBar.setString(actionProgressBar.getValue() + "/" + actionProgressBar.getMaximum());

        BufferedImage pic = null;
        try{
            pic = ImageIO.read(new File("src/images/avatar/avatar"+(joueur.getId()+1)+".png"));
        } catch(Exception e){
            System.out.println("Image not found" + e);
        }
    
        Image pic2 = pic.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        img = new JLabel(new ImageIcon(pic2));
        panel.removeAll();
        panel.add(img);
        panel.add(panel2);
        
        revalidate();
        repaint();
    }


}
