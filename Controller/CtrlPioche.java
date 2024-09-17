package Controller;

import Vue.StartFrame;
import Model.CarteTempete;
import Model.Modele;
import Vue.DeckPanel;
import Vue.JButtonCustom;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CtrlPioche extends MouseAdapter {
    DeckPanel deckPanel;
    Modele modele;
    int nbCartesPiochees = 0;
    public CtrlPioche(DeckPanel deckPanel){
        this.deckPanel = deckPanel;
        this.modele = deckPanel.modele;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Click sur la pile");
        if(modele.getJoueurCourant().getNbActionsReste() == 0) {
            System.out.println("Action == 0");
            CarteTempete cartePiochee = modele.piocher();
            modele.faitApparaitrePiecesSiPossible();
            deckPanel.setLink(cartePiochee.filename()); //definit aussi le nouveau controleur du panel du tas askip
            System.out.println("Carte piochée : " + cartePiochee.type);
            nbCartesPiochees++;
            if(nbCartesPiochees == modele.getNiveauDeTempete()){
                nbCartesPiochees = 0;
                modele.nextJoueur();
                System.out.println("Nombre de cartes piochées max atteint je passe au joueur suivant");
            }
            modele.notifyObservers();

            if (modele.estPerdu()) {
                EventQueue.invokeLater(() -> {
                    deckPanel.vue.ferme();
                });
                JFrame frame = new JFrame("Perdu");
                frame.setSize(300, 300);
                frame.setVisible(true);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                Dimension size = new Dimension(frame.getWidth(), frame.getHeight());
                JPanel panel = new JPanel(){
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Image img = new ImageIcon("src/images/startScreen.png").getImage();
                        g.drawImage(img, 0, 0, size.width, size.height, this);
                    };
                };
                
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                

                JLabel label = new JLabel("Game Over");
                label.setFont(new Font("Arial", Font.BOLD, 30));
                label.setForeground(Color.WHITE);
                label.setAlignmentX(0.5f);
                
                panel.add(Box.createVerticalStrut(20));
                panel.add(label);
                panel.add(Box.createVerticalStrut(50));
                
                Color buttonColor = new Color(80, 80 , 80, 150);
                
                JPanel subPanel = new JPanel();
                subPanel.setLayout(new FlowLayout());
                subPanel.setOpaque(false);
                subPanel.setAlignmentX(0.5f);


                

                JButtonCustom restart = new JButtonCustom("  Recommencer  ", buttonColor);
                restart.setForeground(Color.WHITE);
                restart.setFont(new Font("Arial", Font.PLAIN, 15));
                restart.addActionListener(e1 -> {
                    frame.dispose();
                    new StartFrame();
                });
                subPanel.add(restart);

                subPanel.add(Box.createHorizontalStrut(20));

                JButtonCustom close = new JButtonCustom("    Quitter    ", buttonColor);
                close.setForeground(Color.WHITE);
                close.setFont(new Font("Arial", Font.PLAIN, 15));
                close.addActionListener(e1 -> {
                    frame.dispose();
                    System.exit(0);
                });
                subPanel.add(close);

                panel.add(subPanel);





                frame.add(panel, BorderLayout.CENTER);



                

            } else if (modele.estGagne()) {
                EventQueue.invokeLater(() -> {
                    deckPanel.vue.ferme();
                });
                JFrame frame = new JFrame("Gagné");
                frame.setSize(300, 300);
                frame.setVisible(true);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                Dimension size = new Dimension(frame.getWidth(), frame.getHeight());
                JPanel panel = new JPanel(){
                    @Override
                    protected void paintComponent(Graphics g) {
                        super.paintComponent(g);
                        Image img = new ImageIcon("src/images/startScreen.png").getImage();
                        g.drawImage(img, 0, 0, size.width, size.height, this);
                    };
                };
                
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                

                JLabel label = new JLabel("C'est gagné !");
                label.setFont(new Font("Arial", Font.BOLD, 30));
                label.setForeground(Color.WHITE);
                label.setAlignmentX(0.5f);
                
                panel.add(Box.createVerticalStrut(20));
                panel.add(label);
                panel.add(Box.createVerticalStrut(50));
                
                Color buttonColor = new Color(80, 80 , 80, 150);
                
                JPanel subPanel = new JPanel();
                subPanel.setLayout(new FlowLayout());
                subPanel.setOpaque(false);
                subPanel.setAlignmentX(0.5f);


                

                JButtonCustom restart = new JButtonCustom("  Recommencer  ", buttonColor);
                restart.setForeground(Color.WHITE);
                restart.setFont(new Font("Arial", Font.PLAIN, 15));
                restart.addActionListener(e1 -> {
                    frame.dispose();
                    new StartFrame();
                });
                subPanel.add(restart);

                subPanel.add(Box.createHorizontalStrut(20));

                JButtonCustom close = new JButtonCustom("    Quitter    ", buttonColor);
                close.setForeground(Color.WHITE);
                close.setFont(new Font("Arial", Font.PLAIN, 15));
                close.addActionListener(e1 -> {
                    frame.dispose();
                    System.exit(0);
                });
                subPanel.add(close);

                panel.add(subPanel);





                frame.add(panel, BorderLayout.CENTER);

            }
        }
    }
}
