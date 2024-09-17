package Controller;


import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import Model.*;
import Vue.ActionPanel;
import Vue.JButtonCustom;
import Vue.TuilePanel;


public class SelectCase extends MouseAdapter{
    Modele modele;
    ActionPanel actionPanel;
    TuilePanel selectedCase;
    TuilePanel[] trucsQuiSontADeselect;
    ArrayList<JButtonCustom> boutonsDonEau = new ArrayList<>();
    ArrayList<JButtonCustom> boutonsDonEauPorteuse = new ArrayList<>();
    ArrayList<JButtonCustom> boutons = new ArrayList<>();
    public SelectCase(TuilePanel selectedCase, ActionPanel actionPanel, TuilePanel[] panelVoisins, Modele modele) {
        this.modele = modele;
        this.selectedCase = selectedCase;
        this.actionPanel = actionPanel;
        this.trucsQuiSontADeselect = panelVoisins;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        boutons.removeAll(boutons);
        if(e.getButton() != MouseEvent.BUTTON1){
            System.out.println("Click droit detecté");
            return; 
        }
        System.out.println("Click Gauche detectée");    
        for(TuilePanel t : trucsQuiSontADeselect){
            t.isSelected = false;
        }
        for(Case c : selectedCase.voisins){
            if(c.equals(selectedCase.tuile)){
                selectedCase.isSelected = true;
            }
        }
        Tuile tuileDeSelectedCase = (Tuile) selectedCase.tuile;
        //definition des boutons
        JButtonCustom desensableButton = new JButtonCustom("Désensabler", new Color(255, 193, 48));
        desensableButton.addActionListener(e1 -> {
            modele.getJoueurCourant().desensable(selectedCase.tuile.getPosition());
            actionPanel.setBoutons(new ArrayList<>());
            selectedCase.isSelected = false;
            modele.notifyObservers();
        });
        JButtonCustom deplacerButton = new JButtonCustom("Déplacer", new Color(197, 255, 110));
        deplacerButton.addActionListener(e12 -> {
            System.out.println(selectedCase.tuile.getPosition());
            modele.getJoueurCourant().deplacer(tuileDeSelectedCase.getPosition());
            actionPanel.setBoutons(new ArrayList<>());
            selectedCase.isSelected = false;
            modele.notifyObservers();
        });
        JButtonCustom explorerButton = new JButtonCustom("Explorer", new Color(255, 255, 110));
        explorerButton.addActionListener(e13 -> {
            modele.getJoueurCourant().explorer();
            actionPanel.setBoutons(new ArrayList<>());
            selectedCase.isSelected = false;
            modele.notifyObservers();
        });
        JButtonCustom boireOasis = new JButtonCustom("Boire", new Color(110, 197, 255));
        if(modele.getJoueurCourant() instanceof Porteuse){
            boireOasis.addActionListener(e14 -> {
                ((Porteuse)modele.getJoueurCourant()).piocherEau();
                actionPanel.setBoutons(new ArrayList<>());
                selectedCase.isSelected = false;
                modele.notifyObservers();
            });
        }

        Color prendrePieceColor = new Color(255, 239, 104);
        if(modele.getJoueurCourant().getEmplacement().getPieces().size() != 0){
        switch (modele.getJoueurCourant().getEmplacement().getPieces().get(0).getNom()){
            case "Aile":
                prendrePieceColor = new Color(255, 239, 104);
                break;
            case "Moteur":
                prendrePieceColor = new Color(255, 193, 104);
                break;
            case "Roue":
                prendrePieceColor = new Color(180,198, 255);
                break;
            case "Hélice":
                prendrePieceColor = new Color(226, 180, 255);
                break;
            default:
                prendrePieceColor = new Color(255, 239, 104);
                break;

        }
    }



        JButtonCustom prendrePieceButton = new JButtonCustom("Prendre pièce", prendrePieceColor);
        prendrePieceButton.addActionListener(e13 -> {
            modele.getJoueurCourant().prendrePiece();
            actionPanel.setBoutons(new ArrayList<>());
            selectedCase.isSelected = false;
            modele.notifyObservers();
        });
        Tuile tuileCliquee = (Tuile)selectedCase.tuile;
        Tuile joueurTuile = modele.getJoueurCourant().getEmplacement();
        //don d'eau pour le joueur courant
        for(Joueur j : tuileCliquee.getJoueurs()){
            if(j.equals(modele.getJoueurCourant()) || j.getWaterLvl() == j.getMaxWaterLvl()){
                continue;
            }
            JButtonCustom bouton = new JButtonCustom("Donner de l'eau à " + j.getNom(), new Color(110, 197, 255));
            bouton.addActionListener(e14 -> {
                modele.getJoueurCourant().donneEau(j);
                actionPanel.setBoutons(new ArrayList<>());
                selectedCase.isSelected = false;
                modele.notifyObservers();
            });
            boutonsDonEau.add(bouton);
        }

        //verifiaction que la porteuse existe
        boolean existeporteuse = false;
        Joueur porteuse = null;
        for(Joueur j : modele.joueurs){
            if(j instanceof Porteuse){
                existeporteuse = true;
                porteuse = j;
            }
        }
        //boutons pour que la porteuse donne, normalement affichés tout le temps sauf pour la porteuse
        if(existeporteuse) {
            for (Joueur j : tuileCliquee.getJoueurs()) {
                if (j.equals(porteuse) || j.getWaterLvl() == j.getMaxWaterLvl()) {
                    continue;
                }
                System.out.println("J'ai créé le bouton pour donner de l'eau à " + j.getNom() + " par la porteuse");
                JButtonCustom bouton = new JButtonCustom(porteuse.getNom() + " (porteuse) donne de l'eau à " + j.getNom(), new Color(110, 197, 255));
                Joueur finalPorteuse = porteuse;
                bouton.addActionListener(e14 -> {
                    finalPorteuse.donneEau(j);
                    actionPanel.setBoutons(new ArrayList<>());
                    selectedCase.isSelected = false;
                    modele.notifyObservers();
                });
                boutonsDonEauPorteuse.add(bouton);
            }
        }
        if(joueurTuile.retournable() && joueurTuile.equals(tuileCliquee) && modele.getJoueurCourant().getNbActionsReste() > 0){
            boutons.add(explorerButton);
        }
        if(tuileCliquee.desensablable() && modele.getJoueurCourant().getNbActionsReste() > 0){
            boutons.add(desensableButton);
        }
        if(modele.getJoueurCourant().peutSeDeplacer(tuileCliquee) && modele.getJoueurCourant().getNbActionsReste() > 0){
            boutons.add(deplacerButton);
        }
        if(tuileCliquee.equals(modele.getJoueurCourant().getEmplacement()) && modele.getJoueurCourant().peutPrendrePiece() && !(tuileCliquee instanceof PisteDecollage) && modele.getJoueurCourant().getNbActionsReste() > 0){
            boutons.add(prendrePieceButton);
        }
        if(modele.getJoueurCourant().peutDonnerEauVoisin(tuileCliquee) && (modele.getJoueurCourant().getNbActionsReste() > 0 || modele.getJoueurCourant() instanceof Porteuse)){
            boutons.addAll(boutonsDonEau);
        }
        if(!(modele.getJoueurCourant() instanceof Porteuse) && existeporteuse && porteuse.peutDonnerEauVoisin(tuileCliquee)){
            System.out.println("Boutons pour la porteuse ajoutés");
            boutons.addAll(boutonsDonEauPorteuse);
        }
        if(tuileCliquee == modele.getJoueurCourant().getEmplacement() && (modele.getJoueurCourant() instanceof Porteuse) && ((Porteuse)modele.getJoueurCourant()).peutPiocherEau() && modele.getJoueurCourant().getNbActionsReste() > 0){
            boutons.add(boireOasis);
        }

        actionPanel.setBoutons(this.boutons);
        modele.faitApparaitrePiecesSiPossible();
        modele.notifyObservers();
    }
}
