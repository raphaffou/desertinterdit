# Rapport
Voici notre resultat pour ce projet de POGL. 

## Fonctionnalités implémentées
- [x] Tests unitaires
- [x] Un modele MVC 
- [x] Une vue faite avec swing  
- [x] Un modele composé de :
- Tous les types de cases (sauf le mirage pour le moment)
- Plusieurs roles de joueurs (il manque la navigatrice, et la météorologue est trop incomplete pour fonctionner du coté des contrôleurs. La porteuse d'eau fonctionne plus ou moins parfaitement modulo des bugs non détectés pendant les tests. Les autres n'ont aucun problème)
- De nombreuses méthodes de manipulation permettant d'implémenter certains objets à l'avenir
- Une pile de carte tempête pour les effets de la tempete
- Un type pile abstrait pour une potentielle implementation future des objets
- Des pieces d'avion disposables sur les cases
- Le tout avec des fonctions permettant de savoir si le jeu est gagné ou perdu
- [x] Des contrôleurs cadançant les actions et règles du jeu, grace un systeme de séléction de cases

## Choix d'architechture

### MVC
Comme indiqué plus haut, nous avons fait un modèle MVC. Nous nous sommes basé au début sur le code de "Conway" pour séparer nos fichiers. Elle nous a finalement plutôt servi d'inspiration car la modification du code existant n'était pas pertinente dans la mesure où il fallait reconstruire le modèle à partir de rien.

### Le modèle
Concernant le modèle, le diagramme de classe est disponible dans les fichiers du jeu. Cette architecture a été pensée en amont avant même de commencer à écrire quoi que ce soit. C'est pour cela que par exemple nous avons un type pile abstract permettant de, si nous avions eu le temps, d'intégrer les objets du jeu. N'ayant que 4 type de joueurs, et le jeu devant pouvoir se jouer à 5, il etait plus agréable pour le joueur de ne pas être abstrait (pour les tests notamment, et pour permettre aux tests unitaires, écrits avant d'implémenter les roles, de fonctionner).

Il est à noter que la classe abstraite "Case" représente un emplacement pour une Tuile en quelque sorte. Dans le jeu original, l'oeil était un espace vide sur le plateau, comme un jeu de taquin. Ainsi, Case est un emplacement "vide" et Tuile est une piece de carton "posée" sur cette case.  C'est pourquoi quand on regarde les details de la classe Oeil, on remarque qu'il y a finalement peu de rajouts par rapport à la classe Case. 

La pile a été faite dès le début, cette dernière n'ayant que peu de relations avec des éléments particuliers du modèle. 

Pour les positions il était intéressant d'avoir un type Vecteur. Aussi, un type énuméré Direction a été très utile pour diriger l'Oeil dans ses déplacements.

PiecesMoteur quand à lui se construit au fur et à mesure du jeu, le porteur valant null au départ, on peut en dire autant concernant la piece d'apparition (pouvant ne pas exister si la position d'apparition de cette dernière est l'Oeil) etc... 

La piece se pose automatiquement sur la piece de décollage lorsque le porteur est sur la case de décollage ou bien qu'il l'explore. Dans ce cas là, un tag "estInstallé" est mis à true, qu'on ramasse la piece après ou non. 

Le modèle implémente l'interface Observable, ce qui permet de faire des mises à jour de la vue à chaque changement de modèle, de manière analogue au code de Conway.

### La vue

La vue tourne autour de la classe Vue principalement pour le déroulement du jeu. Néanmoins, StartFrame est la classe faisant parti de la vue qui fixe toutes les informations en construisant le modèle et en créant une classe Vue à laquelle on donne le modèle nouvellement créé.

La classe Vue quand à elle est composée de plusieurs autres "vues" (Des extend de JPanel) qui implémentent selon les cas l'interface Observer. La classe vue elle même implemente cette interface pour pouvoir mettre à jour les jauges en haut et en bas de l'écran.
La classe vue possède une frame comme attribut contenant LeftPanel, RightPanel, ContentPanel

Dans RightPanel, on retrouve un DeckPanel qui s'occupe de mettre en forme les cartes de la pile tempête.
Par ailleurs on y retrouve aussi WaterPanel qui s'occupe d'afficher les jauges d'eau de chaque joueur, d'afficher les roles, et d'afficher les pieces portées par les joueurs. Le joueur courant est coloré en vert et des icônes sont assignées à chaque personnage.

Dans LeftPanel, on retrouve PlayerPanel qui affiche le joueur courant et le joueur suivant, avec son role et son icône.
Enfin, dans ce même panel, on retrouve ActionPanel qui affiche les actions associées à la case sélectionnée, mais nous y reviendrons quand nous parlerons des contrôleurs.

Dans ContentPanel, on retrouve le plateau de jeu, qui est une grille de 5x5 cases. Chaque case est un TuilePanel.

Tous les extend de JPanel qui ont besoin de s'actualiser en fonction de l'etat du modèle implémentent l'interface Observer. Ils sont tous notifiés par le modèle lorsqu'il y a un changement. Ils se mettent alors à jour en fonction de ce changement.

### Les contrôleurs

#### SelectCase

Nous avons commencé les contrôleurs avec SelectCase, un contrôleur mouseAdapter qui permet de sélectionner une case. Il est associé à chaque TuilePanel, et touche directement à la vue pour manipuler l'affichage et les contrôles possible en fonctions des données contenues dans le modèle.

Pour cela, il a fallu fournir à ce contrôleur un accès au modèle, un accès à ActionPanel (pour pouvoir afficher les actions possibles) et un accès aux "voisins" (TuilePanel sélectionnables) pour pouvoir les entourer en rouge au moment du clique, et enlever l'entourage en rouge lorsque l'on clique sur une autre case. Ce passage fut un peu embêtant dans la mesure il pour obtenir les TuilePanel, il fallait que ContentPanel ait finit de tous les initialiser. Nous avons donc du faire une deuxième passe pour donner ces "voisins".

C'est ce contrôleur principalement qui se charge de limiter les actions que le joueur peut faire en fonction des règles du jeu.

Une fois que le joueur n'a plus d'actions, il faut piocher des cartes. 

#### CtrlPioche

Ce contrôleur est "plus simple" dans la mesure où il ne s'occupe que de gérer la pioche des cartes. Quand on clique sur la pioche, l'image de la carte piochée est affichée dans la défausse (qui est dans DeckPanel). Lorsqu'on clique sur cette défausse, cela ouvre une nouvelle fenêtre pour voir la case en plus grand. Le principe de fonctionnement est globalement le meme, excepté qu'on ne récupère que DeckPanel (qui contient une reference vers le modèle).

## Problème non résolu

Nous avons remarqué un problème, lorsqu'on se deplace sur la case de décollage avec une piece portée, un bout de code doit se déclencher pour poser cette piece automatiquement sans dépenser d'action. Pour la poser, on set le porteur à null et on set la piece à "estInstallé" à true. Néanmoins, le bouton pour se déplacer reste enfoncer en on voit dans les log apparaitre une erreur.

Cependant, ce n'est pas un problème majeur dans la mesure ou si on clique sur une case sélectionnable après ce léger bug, on peut se déplacer normalement et continuer le jeu avec la piece posée correctement.

Une recherche google de l'erreur nous a parlé de conflits de threads, et n'en n'ayant créé aucun, il est clair que cette action a créé un conflit avec swing. Nous n'avons pas pu résoudre ce problème.

## Bouts de code empruntés

RoundedBorder a été plutôt inspiré d'un bout de code trouvé sur internet. Il en va de même pour CustomButton. Néanmoins nous comprenons très bien comment ces emprunts fonctionnent.

## Diagramme de classe

### Diagramme du modele

![Diagramme du modèle](src/diagramme%20du%20modele.png)

### Diagramme de la vue

![Diagramme de la vue](src/diagramme%20du%20modele.png)