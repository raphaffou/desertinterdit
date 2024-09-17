package Model;


/**
 * Application avec interface graphique.
 * Thibaut Balabonski, Université Paris-Sud.
 * Matériel pédagogique lié au cours POGL,
 *
 * Un principe directeur est la séparation stricte des deux parties suivantes :
 *  - Le coeur de l'application, appelé le modèle, où est fait l'essentiel
 *    du travail.
 *  - L'interface utilisateur, appelée la vue, qui à la fois montre des choses
 *    à l'utilisateur et lui fournit des moyens d'interagir.
 *
 * Notre cas d'étude : le jeu de la vie de Conway.
 * Une grille bidimensionnelle de dimensions finies est peuplée de cellules
 * pouvant être vivantes ou mortes. À chaque tour un nouvel état est calculé
 * pour chaque cellule en fonction de l'état de ses voisines immédiates.
 * Un bouton permet de passer au tour suivant (on dit aussi la génération
 * suivante).
 */

/**
 * Un lien entre vue et modèle : les informations montrées à l'utilisateur
 * reflètent l'état du modèle et doivent être maintenues à jour.
 *
 * Pour réaliser cette synchronisation, on peut suivre le schéma de conception
 * observateur/observé, dont le principe est le suivant :
 *  - Un observateur (en l'occurrence la vue) est lié à un objet observé et se
 *    met à jour pour refléter les changement de l'observé.
 *  - Un observé est lié à un ensemble d'objets observateurs et les notifie de
 *    tout changement de son propre état.
 *
 * Java fournit une interface [Observer] (observateur) et une classe
 * [Observable] (observé) assurant cette jonction.
 * Voici une manière sommaire de les recoder.
 */



/**
 * Interface des objets observateurs.
 */
public interface Observer {
    /**
     * Un observateur doit posséder une méthode [update] déclenchant la mise à
     * jour.
     */
    public void update();
    /**
     * La version officielle de Java possède des paramètres précisant le
     * changement qui a eu lieu.
     */
}

