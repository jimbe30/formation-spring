## Structure du cours : organisation repository Git

- Chaque concept important est matérialisé par un projet, auquel correspond une branche dédiée 
	- Les concepts sont abordés par ordre
	- Les projets et branches correspondantes sont donc numérotés en fonction
- Chaque notion rattachée à un concept fait l'objet d'un développement et d'un commit spécifique
	- Les notions sont abordées par ordre
	- Les commits correspondants sont numérotés en fonction
	
La suite du document liste les projets et décrit brièvement les concepts associés.
Pour des explications détaillées, se référer au contenu de chaque projet  

## 00-springcore-intro : Pourquoi Spring ?

Ce projet explique les besoins qui ont amené Spring à s'implanter et à devenir un standard dans le développement java d'applications d'entreprise

### Objectifs
> Produire des applications structurées, compréhensibles, flexibles, testables et maintenables

### Problèmes constatés
- Les dépendances explicites entre classes concrètes provoquent un couplage fort qui rend l'application peu flexible
- L'instanciation des composants de l'application par d'autres composants rend le code peu lisible et difficilement maintenable.

### Bonnes pratiques
Pour répondre aux objectifs et remédier aux problèmes, des principes s'imposent:
- Programmation par contrat (interfaces) => couplage faible, flexibilité
- Inversion de contrôle par injection de dépendances (IoC) => structuration du code, lisibilité, maintenabilité

### Références
[Principes SOLID (ROSSI en français)](https://fr.wikipedia.org/wiki/SOLID_%28informatique%29)
- Responsabilité unique -> réduire la complexité et améliorer la compréhension du code
- Ouverture à l'extension / Fermeture à la modification  -> évoluer sans risque de régression 
- Substitution de Liskov (par héritage) -> conserver la cohérence du programme lorsqu'on remplace une classe parente par une classe enfant
- Ségrégation d'interfaces : responsabilité unique des interfaces
- Inversion de dépendances : pas de dépendance sur des classes concrètes mais sur des abstractions (interfaces) -> flexibilité

### Solution
> Spring prétend être un conteneur IoC léger qui facilite la mise en place d'une architecture robuste selon les principes SOLID
