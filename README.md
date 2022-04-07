# ProjetTwizy
Repositories du projet Twizy de détection de signalisation routière.

# Sprint 1
## Backlog
### Fonctionnalités
* Importer une image
* Détecter les panneaux de signalisations
* Renvoyer une image avec la détection
* Avoir accès à toutes ces fonctionnalités depuis une interface logiciel
* (Discrimination des panneaux par réseau de neurones)

### Répartition des tâches
* Clément : Création de l'interface graphique.
* Rémi : Discrimination du panneau
* Xavier : Discrimination du panneau
* Thomas : Renvoyer une image "explicite" de la détection
* Bertrand : Importer une image / Détection comme vue en cours

## Review
### Rétrospective
* Importer une image - OK
* Détecter les panneaux de signalisations - OK
* Renvoyer une image avec la détection - OK
* Avoir accès à toutes ces fonctionnalités depuis une interface logiciel - OK
* (Discrimination des panneaux par réseau de neurones) - NOK

### Point positifs / négatifs
* Toutes les fonctionnalités ont étés implémentées +
* Mauvaise division du travail -
* Mauvaise appréciation de la priorité des fonctionnalités -
* Deadline et diagramme de classe à ajouter -
* Ne pas hésiter à demander de l'aide !!! (et à demander si besoin d'aide) -

# Sprint 2
## Backlog
### Fonctionnalités
* Importation et lecture des vidéos
* Amélioration discrimination des panneaux
* Détection des panneaux sur une vidéo
* Affichage du résultat sous forme d'une vidéo

### Répartition des tâches
* Clément : Affichage du résultat & mise en commun & diagramme de classe
* Rémi : Amélioration discrimination et détection des panneaux 
* Xavier : Amélioration discrimination et détection des panneaux
* Thomas : Détection des panneaux sur la vidéo
* Bertrand : importation et lecture des vidéos (classe VideoReading avec une fonction LectureVideo(File fichier), et VidShow qui lis la vidéo)

## Installation d'OpenCV
Pour utiliser OpenCV 2.4.13 téléchargez et déplacez la dll correspondante dans
```
C:\Program Files\Java\bin
```
Le .jar est importé automatiquement avec Maven.

## Lancer le programme
Pour lancer le programme il suffit de lancer la commande suivi dans le répertoire du projet
```
command line is to define while maven is not setup
```
