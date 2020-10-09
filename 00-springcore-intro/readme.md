
## Structure du projet

**Principe de séparation des responsabilités**
> Découpage de l'application en plusieurs couches techniques.
> Chacune de ces couches fait l'objet d'un package spécifique.


| Package     | Responsabilité |
|-------------|----------------|
| controller  | gestion des interactions utilisateurs |
| entity      | représentation du modèle de données |
| repository  | accès au modèle de données (lecture/écriture/suppression) |
| service     | traitement de la logique métier |

**Arborescence**
```j
net.jmb.tuto.spring    // racine
 |__ Application.java  // point d'entrée de l'application
 |__ controller        // interactions utilisateurs
 |__ entity            // modèle de données
 |__ repository        // accès aux données
 |__ service           // traitements métiers
```

## Commit : 00_programmation naturelle

### 1. Demande
L'application a pour fonction d'établir un devis à un client en fonction d'articles choisis dans un catalogue
- Le client s'identifie
- L'application lui propose de choisir un ou plusieurs articles dans une liste 
	- La liste affichée provient d'un catalogue stocké en mémoire
	- Le catalogue référence les articles et leur tarif 
- Une fois le choix effectué, l'application calcule le devis selon la règle suivante
	- montant de chaque article = quantité x tarif
	- montant du devis = somme des montants de chaque article
- Le devis calculé est ensuite affiché

### 2. Réalisation

Séquence de dépendances
```java
Application.main()
^ CatalogService.afficherListeArticles()
  ^ ArticleRepository.getAllArticles() 
^ DevisService.calculerDevis(nomClient, numArticles)
  ^ CatalogService.chercherArticle(numArticle)
    ^ ArticleRepository.findArticle()
```
Façon de faire
- Chaque dépendance est une classe concrète **explicitement déclarée**
- Chaque dépendance est **instanciée par l'objet qui l'utilise** au moment où il en a besoin

Résultat
L'application fonctionne mais elle est peu flexible 