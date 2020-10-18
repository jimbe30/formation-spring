
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

## Branche 00_programmation_naturelle

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

## Commit : 01_programmation par contrat et inversion de contrôle

### 1. Demande
Un nouveau magasin est intéressé par l'application mais souhaite modifier son comportement
- Le client s'identifie
- L'application lui propose de choisir un ou plusieurs articles dans une liste 
	- La liste affichée doit présenter le prix de chaque article
	- Le catalogue est stocké non plus en mémoire mais en base de données,
- Le calcul est effectué en appliquant une remise de 10% si le client est VIP


### 2. Réalisation
Si on applique la façon de faire précédente, on se trouve confronté à plusieurs problèmes
- Modifier les classes existantes pour tenir compte des règles spécifiques à chaque contexte
- Instancier dans chacune des classes les dépendances explicites en fonction du contexte
- Le contrôle du flot d'exécution est dispersé, difficile à lire, avec des risques de régression et d'oubli

> Il faut modifier l'approche : programmation par contrat avec inversion de contrôle
> Un composant central est dédié au contrôle du flot d'exécution
> Il gère l'instanciation et la mise en relation des classes de traitement

```java
public class Application {

	public static void main(String[] args) {
	
		...
		// On instancie et on relie entre eux par injection de dépendances tous les objets intervenant dans le traitement

		ArticleRepositoryInterface articleRepository = null;
		CatalogServiceInterface catalogService = null;
		ClientServiceInterface clientService = null;
		DevisServiceInterface devisService = null;

		switch (contexte) {
			case 1:
				// les objets responsables du traitement sont les mêmes que précédemment
				articleRepository = new ArticleMemoryRepository();
				catalogService = new CatalogBasicService(articleRepository);
				devisService = new DevisSimpleService(catalogService);
				break;
	
			case 2:
				// les objets responsables du traitement sont ceux nouvellement créés pour
				// implémenter le nouveau comportement
				articleRepository = new ArticleDatabaseRepository();
				catalogService = new CatalogDetailService(articleRepository);
				clientService = new ClientSimpleService();
				devisService = new DevisRemiseService(catalogService, clientService);
				break;
		}
		...
		Devis devis = devisService.calculerDevis(nomClient, numArticles);
		...
}	
```

  

