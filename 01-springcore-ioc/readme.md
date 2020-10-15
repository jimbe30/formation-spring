## Commit : 02_démarrage projet Spring

### 1. Initialisation 

> **Spring initializr** à l'adresse [https://start.spring.io/](https://start.spring.io/)

Renseigner le formulaire
- Projet maven
- Langage Java
- SpringBoot version 2.3.4
- Métadonnées maven

```
	- Group:     net.jmb.tuto.spring
	- Articfact: 01-spingcore-ioc
	- Package:   net.jmb.tuto.spring
	- Packaging: Jar
	- Java:      8
```

Une fois le projet généré, le dézipper dans le répertoire de formation.
Puis l'importer en tant que projet maven

### 2. Projet maven

Maven est un outil de build (construction) des projets java.
Il a été développé par la fondation Apache dans le but de standardiser et d'automatiser le process de fabrication.
- Compilation, tests unitaires, déploiement : précieux pour l'intégration continue.
- S'appuie sur un fichier de configuration pom.xml qui a la particularité de recenser les dépendances vis-à-vis des librairies requises.
- La gestion des dépendances utilise un système de dépôts locaux et distants.
- Dépôt distant de référence : [Central Repository](https://mvnrepository.com/repos/central)
- Guide utilisateur de référence : [Maven Getting Started](http://maven.apache.org/guides/getting-started/index.html) 

> **Le projet généré par Spring initializr est un projet maven qui référence toutes les dépendances vers les librairies nécessaires à l'utilisation du framework**
> Il s'agit d'un template (modèle) d'application SpringBoot préconfigurée et prête à l'emploi 

## Commit : 03_création des contrôleurs (principe de responsabilité unique)

> Le code d'interaction avec les utilisateurs est déplacé dans les contrôleurs

Dans la classe **`Application.java`** ne figure plus que le code responsable du flot d'exécution
- Instanciation des classes d'implémentation en fonction du contexte
- Mise en relation des objets par injection de dépendances

**net.jmb.tuto.spring.Application**

```java
	...
	// On identifie le client
	ClientController clientController = new ClientController();
	Client client = clientController.identifierClient();

	// On propose un choix dans la liste des articles disponibles
	ArticleController articleController = new ArticleController();
	articleController.setCatalogService(catalogService);
	List<Integer> numArticles = articleController.choisirArticles();

	// On renvoie le devis correspondant
	DevisController devisController = new DevisController();
	devisController.setDevisService(devisService);
	devisController.afficherDevis(client, numArticles);
	...
```

## Commit : 04_Conteneur Spring - configuration full xml

### 1. Fichiers de type applicationContext.xml

La déclaration des classes concrètes à implémenter n'est plus faite dans notre application mais dans des fichiers de configuration.
C'est le framework Spring qui s'occupe d'instancier les classes et d'injecter les dépendances à partir de cette config.

On renseigne 2 fichiers de configuration dans `/src/main/resources` :
- `applicationContext-1.xml` : Configuration des classes d'implémentation relatives au contexte 1 (beans au sens Spring)
- `applicationContext-2.xml` : Configuration relative au contexte 2


- Peu importe l'ordre dans lequel sont déclarés les beans, Spring en fait son affaire
- Il faut renseigner le nom qualifié de la classe 
- Chaque bean instancié dans le conteneur a un identifiant qui doit être unique (il est déduit du nom de la classe s'il n'est pas explicitement renseigné)
- Chaque bean peut ainsi être injecté dans les beans dépendants 
  On utilise pour ceci l'attribut `ref` dans les balises `<constructor-arg>` ou `<property>`

**/src/main/resources/applicationContext-1.xml**

```xml
	<bean id="articleRepository" class="net.jmb.tuto.spring.repository.ArticleMemoryRepository"/>

	<bean id="catalogService" class="net.jmb.tuto.spring.service.CatalogBasicService">
		<constructor-arg ref="articleRepository"/>
	</bean>
	
	<bean id="devisService" class="net.jmb.tuto.spring.service.DevisSimpleService">
		<constructor-arg ref="catalogService"/>
	</bean>
```

**/src/main/resources/applicationContext-2.xml**

```xml
	<bean id="articleRepository" class="net.jmb.tuto.spring.repository.ArticleDatabaseRepository"/>
	
	<bean id="catalogService" class="net.jmb.tuto.spring.service.CatalogDetailService">
		<constructor-arg ref="articleRepository"/>
	</bean>
	
	<bean id="clientService" class="net.jmb.tuto.spring.service.ClientSimpleService" />
	
	<bean id="devisService" class="net.jmb.tuto.spring.service.DevisRemiseService">
		<constructor-arg ref="catalogService"/>
		<constructor-arg ref="clientService"/>
	</bean>
```

**Configuration commune aux 2 contextes: les contrôleurs**

```xml
	<bean class="net.jmb.tuto.spring.controller.ClientController"/>
	
	<bean class="net.jmb.tuto.spring.controller.ArticleController">
		<property name="catalogService" ref="catalogService"/>
	</bean>
	
	<bean class="net.jmb.tuto.spring.controller.DevisController">
		<property name="devisService" ref="devisService"/>
	</bean>
```
- Peu importe l'ordre dans lequel sont déclarés les beans, Spring en fait son affaire
- Il faut renseigner le nom qualifié de la classe 
- Chaque bean instancié dans le conteneur a un identifiant qui doit être unique (il est déduit du nom de la classe s'il n'est pas explicitement renseigné)
- Chaque bean peut ainsi être injecté dans les beans dépendants 
  On utilise pour ceci l'attribut `ref` dans les balises `<constructor-arg>` ou `<property>`

### 2. Application.java

Notre application ne s'occupe plus ni d'instancier les objets, ni d'injecter leurs dépendances.
Elles se contente de récupérer les objets dont elle a besoin pour effectuer ses traitement à partir du conteneur Spring.
Ces objets sont en nombre considérablement réduit :
- Dans notre exemple, il s'agit uniquement des contrôleurs
- Tous les services et repositories nécessaires à leur fonctionnement ont été injectés par Spring à partir de la configuration

**net.jmb.tuto.spring.Application**

```java
	// On détermine quel est le fichier de configuration du conteneur Spring en fonction du contexte
	ApplicationContext applicationContext = null;		
	switch (contexte) {
		case 1:
			applicationContext = new ClassPathXmlApplicationContext("applicationContext-1.xml");
			break;
		case 2:
			applicationContext = new ClassPathXmlApplicationContext("applicationContext-2.xml");
				break;
	}
	...
	// Depuis le conteneur Spring, on ne récupère que les contrôleurs responsables des interactions avec l'utilisateur
	ClientController clientController = applicationContext.getBean(ClientController.class);
	ArticleController articleController = applicationContext.getBean(ArticleController.class);
	DevisController devisController = applicationContext.getBean(DevisController.class);
			
	// On identifie le client
	Client client = clientController.identifierClient();
	// On propose un choix dans la liste des articles disponibles
	List<Integer> numArticles = articleController.choisirArticles();
	// On renvoie le devis correspondant
	devisController.afficherDevis(client, numArticles);
```

### 3. Bilan

Le code java est épuré car la mécanique d'assemblage des composants est prise en charge par Spring
La structure de l'application est clairement documentée dans les fichiers de configuration
- Avantages : 
	- Code lisible et structuré : facilite la maintenance
	- Application documentée : facilite la compréhension  
- Inconvénients :
	- Travail fastidieux de déclaration des beans dans les fichiers de configuration : procédé lourd pour les grosses applications
	- Forte dépendance de l'application vis-à-vis du framework Spring (notamment sur les classes de type `ApplicationContext`)

## Commit : 05_Réduire la config xml - Injection par 'autowire'

### 1. Au niveau d'un bean

On peut indiquer dans la déclaration d'un bean que l'on souhaite injecter ses dépendances par leur nom ou par leur type
On utilise pour ceci l'attribut `autowire` dans la balise `<bean>`
L'injection de dépendance se fait alors automatiquement sans avoir besoin de les spécifier explicitement

> **L'autowiring ne fonctionne qu'avec les setters et pas les constructeurs** : il faut donc un constructeur sans argument pour les beans considérés

**/src/main/resources/applicationContext-1.xml**

```xml
	...

	<bean id="articleRepository" class="net.jmb.tuto.spring.repository.ArticleMemoryRepository"/>	
	<bean id="catalogService" class="net.jmb.tuto.spring.service.CatalogBasicService" autowire="byName"/>
	<bean id="devisService" class="net.jmb.tuto.spring.service.DevisSimpleService" autowire="byName"/>
	
```

### 2. Au niveau global

 On peut indiquer pour tous les beans que l'on souhaite injecter leur dépendances par leur nom ou par leur type.
 On utilise pour ceci l'attribut `default-autowire="byName"` dans la balise `<beans>`

**/src/main/resources/applicationContext-2.xml**

```xml
<beans
   ...
   default-autowire="byName">
	
	<bean id="articleRepository" class="net.jmb.tuto.spring.repository.ArticleDatabaseRepository"/>	
	<bean id="catalogService" class="net.jmb.tuto.spring.service.CatalogDetailService"/>
	<bean id="clientService" class="net.jmb.tuto.spring.service.ClientSimpleService" />	
	<bean id="devisService" class="net.jmb.tuto.spring.service.DevisRemiseService" />
	...
</beans>	
```

### 3. Résultat

- Beaucoup moins de xml à écrire et à lire : moins contraignant
- Mais l'application est moins compréhensible puisque les dépendances ne sont pas décrites : plus difficile à maintenir

## Commit : 06_Optimer la configuration xml

### 1. Factoriser la config commune

La configuration commune est déplacée dans un fichier séparé.
Elle peut ensuite être importée via la balise `<import resource="xxx"/>`

Nouveau fichier **src/main/resources/applicationContext.xml**

```xml
	<!-- 
	 	Configuration commune des contrôleurs valables quel que soit le contexte
	 -->	 
	<bean class="net.jmb.tuto.spring.controller.ClientController"/>
	<bean class="net.jmb.tuto.spring.controller.ArticleController"/>
	<bean class="net.jmb.tuto.spring.controller.DevisController"/>
```

### 2. Injecter des variables d'environnement

On peut valoriser des variables d'environnement dans la commande d'exécution du programme.
Ces variables sont définies sous la forme **-D**xxx=yyy.

Avec: 
- xxx = nom de la variable
- yyy = valeur de la variable  

Ces variables peuvent être ensuite utilisées dans la config Spring :
- la balise `<context:property-placeholder/>` indique qu'on souhaite utiliser des variables
- la valeur de la variable est sous la forme ${xxx}

**Exemple**

Pour définir la valeur du contexte égale à 2
- Commande `java -Dcontexte=2 -classpath "..." net.jmb.tuto.spring.Application`

Ce contexte est utilisable dans le fichier de config `applicationContext.xml`.
Il permet l'import de la config spécifique au contexte

**src/main/resources/applicationContext.xml**

```xml
	<!-- 
	 	On déclare vouloir accéder aux variables d'environnement
	 -->
	<context:property-placeholder/>	
	
	<!-- 
	 	On importe la config spécifique au contexte en utilisant la variable d'environnement 
	 	${contexte} qui doit être fournie en ligne de commande ( -Dcontexte=2 par exemple )
	 -->
	<import resource="classpath:/applicationContext-${contexte}.xml"/>
```

### 3. Injecter des données de fichiers .properties

On peut stocker des données de configuration dans des fichiers properties (typiquement `application.properties`).
Ces données sont ensuite accessibles dans la config Spring sous la forme ${xxx}

**Exemple**

Pour définir le pourcentage de remise des clients VIP égal à 15%

Fichier **src/main/resources/application.properties**

```properties
app.contexte2.remise = 15
```

Fichier **src/main/resources/applicationContext.xml**

```xml
	<!-- 
	 	On déclare vouloir accéder aux variables d'environnement et aux properties
	 -->
   <context:property-placeholder location="classpath:/application.properties"/>
```

Fichier **src/main/resources/applicationContext-2.xml**

```xml

	<!-- 
	 	Le taux de remise défini dans application.properties est accédé avec sa clé ${app.contexte2.remise}
	 	et injecté dans la propriété 'remise' de la classe DevisRemiseService
	 -->
	<bean id="devisService" class="net.jmb.tuto.spring.service.DevisRemiseService">
		<property name="remise" value="${app.contexte2.remise}"/>
	</bean>
```

### 4. Résultat

- La classe `Application.java` est considérablement allégée : elle n'a plus à s'occuper de déterminer la config en fonction du contexte.
- La configuration est mieux structurée : factorisation des classes communes
- Les valeurs ne sont plus définies en dur dans les programmes mais injectées à partir de fichiers de propriétés

