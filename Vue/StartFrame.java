package Vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;


import javax.swing.*;

import Controller.CtrlCardPrev;
import Model.*;


public class StartFrame extends JFrame {


    int nbJoueurs;
    int nbJoueurCreer = 0;
    Modele modele;
    Color buttonColor = new Color(80, 80 , 80, 150);
    PileRole pileRole = new PileRole();
    String role;

    public StartFrame() {
        super();
        modele = new Modele();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(500, 500);   
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Desert Interdit");
        this.setLayout(new BorderLayout());
        Image ico = new ImageIcon("src/images/camel.png").getImage();
        this.setIconImage(ico);
        Dimension size = new Dimension(this.getWidth(), this.getHeight());
        JPanel panel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/startScreen.png").getImage();
                g.drawImage(img, 0, 0, size.width, size.height, this);
            };
        };
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        // panel.setBackground(Color.WHITE);
        panel.setOpaque(false);

        JLabel title = new JLabel("Desert Interdit");
        title.setFont(new Font("Arial", Font.BOLD, 40));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(50));
        panel.add(title);

        panel.add(Box.createVerticalStrut(50));

        JButtonCustom start = new JButtonCustom("     Start     ", buttonColor);
        
        start.setForeground(Color.WHITE);
        start.setFont(new Font("Arial", Font.PLAIN, 25));
        start.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(start);

        panel.add(Box.createVerticalStrut(10));

        JButtonCustom quit = new JButtonCustom("     Quit     ", buttonColor);
        quit.setForeground(Color.WHITE);
        quit.setFont(new Font("Arial", Font.PLAIN, 25));
        quit.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(quit);

        this.add(panel, BorderLayout.CENTER);


        JPanel choixNombreJoueurs = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/startScreen.png").getImage();
                g.drawImage(img, 0, 0, size.width, size.height, this);
            };
        };
        choixNombreJoueurs.setLayout(new BoxLayout(choixNombreJoueurs, BoxLayout.Y_AXIS));
        // choixNombreJoueurs.setBackground(Color.WHITE);
        choixNombreJoueurs.setOpaque(false);
        choixNombreJoueurs.add(Box.createVerticalStrut(50));
        JLabel titre = new JLabel("Choisissez le nombre de joueurs");
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setForeground(Color.WHITE);
        titre.setAlignmentX(Component.CENTER_ALIGNMENT);
        choixNombreJoueurs.add(titre);
        choixNombreJoueurs.add(Box.createVerticalStrut(50));
        // add slider
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 2, 5, 2);
        slider.setOpaque(false);
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setForeground(Color.WHITE);
        slider.setAlignmentX(Component.CENTER_ALIGNMENT);
        choixNombreJoueurs.add(slider);
        choixNombreJoueurs.add(Box.createVerticalStrut(50));

        JButtonCustom valider = new JButtonCustom("   Valider   ", buttonColor);
        valider.setFont(new Font("Arial", Font.PLAIN, 25));
        valider.setForeground(Color.WHITE);
        valider.setAlignmentX(Component.CENTER_ALIGNMENT);
        choixNombreJoueurs.add(valider);


        JPanel choixNomJoueurs = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/startScreen.png").getImage();
                g.drawImage(img, 0, 0, size.width, size.height, this);
            };
        };
        choixNomJoueurs.setLayout(new BoxLayout(choixNomJoueurs, BoxLayout.Y_AXIS));
        // choixNomJoueurs.setBackground(Color.WHITE);

        choixNomJoueurs.add(Box.createVerticalStrut(50));
        JLabel titre2 = new JLabel("Choisissez le nom du Joueur 1");
        titre2.setFont(new Font("Arial", Font.BOLD, 20));
        titre2.setForeground(Color.WHITE);
        titre2.setAlignmentX(Component.CENTER_ALIGNMENT);
        choixNomJoueurs.add(titre2);
        choixNomJoueurs.add(Box.createVerticalStrut(50));

        
        JTextField nomJoueur = new JTextField();
        nomJoueur.setMaximumSize(new java.awt.Dimension(200, 30));
        nomJoueur.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        nomJoueur.setAlignmentX(Component.CENTER_ALIGNMENT);
        nomJoueur.setBackground(new Color(0, 0, 0, 0));
        nomJoueur.setOpaque(false);
        nomJoueur.setForeground(Color.WHITE);
        choixNomJoueurs.add(nomJoueur);
        choixNomJoueurs.add(Box.createVerticalStrut(50));


        this.role = "dos";
        JPanel deckRole = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image img = new ImageIcon("src/images/cartes/"+role+".png").getImage();
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            };
        };

        deckRole.setSize(new Dimension(320/3, 450/3));
        deckRole.setMinimumSize(new Dimension(320/2, 450/2));
        deckRole.setMaximumSize(new Dimension(200/2, 300/2));
        deckRole.setAlignmentX(CENTER_ALIGNMENT);
        deckRole.setAlignmentY(CENTER_ALIGNMENT);
        deckRole.setOpaque(false);

        
        choixNomJoueurs.add(deckRole);

        choixNomJoueurs.add(Box.createVerticalStrut(20));


        JButtonCustom valider2 = new JButtonCustom("   Valider   ", buttonColor);
        valider2.setForeground(Color.WHITE);
        valider2.setFont(new Font("Arial", Font.PLAIN, 25));
        valider2.setAlignmentX(Component.CENTER_ALIGNMENT);
        choixNomJoueurs.add(valider2);





        start.addActionListener(e -> {
            this.remove(panel);
            this.add(choixNombreJoueurs, BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
        });

        
        quit.addActionListener(e -> {
            System.exit(0);
        });

        valider.addActionListener(e -> {
            nbJoueurs = slider.getValue();
            this.remove(choixNombreJoueurs);
            this.add(choixNomJoueurs, BorderLayout.CENTER);
            this.revalidate();
            this.repaint();
        });

        valider2.addActionListener(e -> {
            if(nomJoueur.getText().equals("")){
                JOptionPane.showMessageDialog(this, "Veuillez entrer un nom de joueur", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(role.equals("dos")){
                JOptionPane.showMessageDialog(this, "Veuillez cliquez sur la pile de carte pour choisir un rôle", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            modele.addJoueur(nomJoueur.getText(), role);
            role = "dos";
            for(MouseListener ml : deckRole.getMouseListeners()){
                deckRole.removeMouseListener(ml);
            }
            deckRole.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if(role.equals("dos")){
                        role = pileRole.retirerCarte();
                        deckRole.addMouseListener(new CtrlCardPrev(role + ".png"));
                    }
                    deckRole.revalidate();
                    deckRole.repaint();
                };
            });
            nomJoueur.setText("");
            nbJoueurCreer++;
            titre2.setText("Choisissez le nom du Joueur " + (nbJoueurCreer + 1));
            if (nbJoueurCreer == nbJoueurs) {
                new Vue(modele);
                this.dispose();

            }
            this.revalidate();
            this.repaint();
            deckRole.revalidate();
            deckRole.repaint();
        });

        StartFrame frame = this;
        deckRole.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(frame.role.equals("dos")){
                    frame.role = pileRole.retirerCarte();
                    deckRole.addMouseListener(new CtrlCardPrev(frame.role + ".png"));
                }
                deckRole.revalidate();
                deckRole.repaint();
            };
        });


        nomJoueur.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    if(nomJoueur.getText().equals("")){
                        JOptionPane.showMessageDialog(frame, "Veuillez entrer un nom de joueur", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if(frame.role.equals("dos")){
                        JOptionPane.showMessageDialog(frame, "Veuillez cliquez sur la pile de carte pour choisir un rôle", "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                        
                    }
                    modele.addJoueur(nomJoueur.getText(), frame.role);
                    frame.role = "dos";
                    for(MouseListener ml : deckRole.getMouseListeners()){
                        deckRole.removeMouseListener(ml);
                    }
                    deckRole.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if(frame.role.equals("dos")){
                                frame.role = pileRole.retirerCarte();
                                deckRole.addMouseListener(new CtrlCardPrev(frame.role + ".png"));
                            }
                            deckRole.revalidate();
                            deckRole.repaint();
                        };
                    });
                    nomJoueur.setText("");
                    nbJoueurCreer++;
                    titre2.setText("Choisissez le nom du Joueur " + (nbJoueurCreer + 1));
                    if (nbJoueurCreer == nbJoueurs) {
                        new Vue(modele);
                        frame.dispose();
        
                    }
                    frame.revalidate();
                    frame.repaint();
                    deckRole.revalidate();
                    deckRole.repaint();
                }                
                
            };
        });
    }

}
