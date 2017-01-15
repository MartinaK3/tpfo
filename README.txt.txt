Architecture:
Pour le modèle sélectionné,  nous avons 3 couches cachées de neurones :
-	La première a pour fonction d’activation une fonction sigmoïde et sa taille est égale à la moitié de celle d’entrée (0.5*taille d’entrée) ;
-	La deuxième a également pour fonction d’activation une fonction sigmoïde et sa taille est égale à 1.5* celle d’entrée ;
-	Et la troisième a elle aussi une fonction d’activation sigmoïde sa taille est égale à la moitié de celle d’entrée.
Nous avons 15 itérations pour ce modèle.

Représentation:
La taille de notre représentation est de 700, avec un seuil de fréquence égal à 50 ; 
comme trait nous avons le filtrage, la normalisation, la lemmatisation et le BOW.
Nous avons essayé d’améliorer nos résultats en essayant les N-grammes (2G et 3G) et en rajoutant des mesures de similarité de disco. 
(Le disco on a essayé pour mettre des poids sur les frequences. Ce poid depend de la similarité d'un mot aux mots "bien" et "mauvais", si c'est
un mot très positif ou un mot très negatif, le poids augmente.)


Le résultat obtenu est F= 0.7207.
