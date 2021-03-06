## Branche 02_springcore_ioc_démarrage

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

## Branche 03_création_des_contrôleurs (principe de responsabilité unique)

### 1. Déplacement du code d'interaction avec les utilisateurs dans les contrôleurs

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

### 2. Diagramme de classes schématisé

Au final, les diagrammes ci-dessous font apparaître les dépendances entre composants de l'application

- **Diagramme de classes sur la fonctionnalité 'catalogue'**

![CatalogModel](diagram/CatalogModel.jpg)

- **Diagramme de classes sur la fonctionnalité 'devis'**

![DevisModel](diagram/DevisModel.jpg)


## Branche 04_spring_configuration_full_xml

### 1. Fichiers de type applicationContext.xml

La déclaration des classes concrètes à implémenter n'est plus faite dans notre application mais dans des fichiers de configuration.

C'est le framework Spring qui s'occupe d'instancier les classes et d'injecter les dépendances à partir de cette config.

On renseigne 2 fichiers de configuration dans `/src/main/resources` :
- `applicationContext-1.xml` : Configuration des classes d'implémentation relatives au contexte 1 (beans au sens Spring)
- `applicationContext-2.xml` : Configuration relative au contexte 2

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

A retenir :
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

### 3. Résultat

Le code java est épuré car la mécanique d'assemblage des composants est prise en charge par Spring.

La structure de l'application est clairement documentée dans les fichiers de configuration
- Avantages : 
	- Code lisible et structuré : facilite la maintenance
	- Application documentée : facilite la compréhension  
- Inconvénients :
	- Travail fastidieux de déclaration des beans dans les fichiers de configuration : procédé lourd pour les grosses applications
	- Forte dépendance de l'application vis-à-vis du framework Spring (notamment sur les classes de type `ApplicationContext`)

## Branche 05_réduire_la_config_xml_par_autowire

### 1. Au niveau d'un bean

On peut indiquer dans la déclaration d'un bean que l'on souhaite injecter ses dépendances par leur nom ou par leur type.

On utilise pour ceci l'attribut `autowire` dans la balise `<bean>`.

L'injection de dépendance se fait alors automatiquement sans avoir besoin de les spécifier explicitement.

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

On utilise pour ceci l'attribut `default-autowire="byName"` dans la balise `<beans>`.

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

## Branche 06_optimer_la_configuration_xml

### 1. Factoriser la config commune

La configuration commune est déplacée dans un fichier séparé **applicationContext.xml**.

Ce fichier est le point d'entrée de la config Spring pour toute notre application.

Les configs spécifiques à chaque contexte peuvent y être importées via la balise `<import resource="xxx"/>`.

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

Ces variables sont définies sous la forme **-D**xxx=yyy, avec: 
- xxx = nom de la variable
- yyy = valeur de la variable  

Les variables d'environnement peuvent être utilisées dans la config Spring :
- la valeur de la variable à utiliser est déclarée sous la forme ${xxx}

**Exemple**

Pour définir la valeur du contexte égale à 2
- Commande `java -Dcontexte=2 -classpath "..." net.jmb.tuto.spring.Application`

Ce contexte est utilisable dans le fichier de config `applicationContext.xml`.

Il permet l'import de la config spécifique au contexte

**src/main/resources/applicationContext.xml**

```xml
	<!-- 
	 	On importe la config spécifique au contexte en utilisant la variable d'environnement 
	 	${contexte} qui doit être fournie en ligne de commande ( -Dcontexte=2 par exemple )
	 -->
	<import resource="classpath:/applicationContext-${contexte}.xml"/>
```

### 3. Injecter des données de fichiers .properties

On peut stocker des données de configuration dans des fichiers properties (typiquement `application.properties`).

Ces données sont accessibles dans la config Spring :
- la balise `<context:property-placeholder location="???"/>` indique qu'on souhaite utiliser des propriétés de configuration.
- la valeur de la propriété est ensuite accessible sous la forme ${xxx}.

**Exemple**

Pour définir le pourcentage de remise des clients VIP égal à 15%

Fichier **src/main/resources/application.properties**

```properties
app.contexte2.remise = 15
```

Fichier **src/main/resources/applicationContext.xml**

```xml
	<!-- 
	 	On déclare accéder aux valeurs du fichier de propriétés
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


## Branche 07_injection_par_annotation

### 1. Balise `<context:annotation-config/>` dans applicationContext.xml  

Cette balise permet l'injection de dépendances par annotation dans les classes dépendantes.

```xml
   <!-- on a supprimé l'attribut default-autowire="byName" de la balise beans -->
    
    <!--
    	Permet l'injection par annotation @Autowired dans les classes ayant des dépendances sur les beans définis ici
    	Les getters et setters des classes dépendantes sont facultatifs
     -->
    <context:annotation-config/>    
```

### 2. Annotation @Autowired dans les classes dépendantes

Cette annotation se place au niveau d'un attribut, d'un setter ou d'un constructeur.

Elle indique au conteneur Spring d'injecter le bean ayant le même type que la propriété concernée.

> **Toutes les classes ayant des dépendances sur des beans gérés par le conteneur Spring doivent être annotées**

> Ou bien sinon il faut injecter les dépendances dans la config xml

Si on se réfère au diagramme de classes (Branche 03_création_des_contrôleurs), ces classes sont :
- `ArticleController`
- `CatalogAbstractService`
- `DevisController`
- `DevisSimpleService`
- `DevisRemiseService`

Exemple sur la classe **net.jmb.tuto.spring.controller.DevisController**

```java
public class DevisController {
	
	@Autowired
	DevisServiceInterface devisService;
```

### 3. Conclusion

Résultat :
- L'injection de dépendance est à nouveau documentée contrairement à l'autowiring xml
- Elle est déclarée dans le code
	- Avantage : lisible immédiatement lorsqu'on travaille sur les composants
	- Inconvénient : crée une dépendance forte sur le framework Spring 
	
Pour pallier l'inconvénient de la dépendance forte sur le framework Spring, il est possible d'utiliser à la place les annotations JEE standard `@Resource` ou `@Inject`.

L'annotation `@Resource` outre le fait d'être un standard JEE présente aussi l'avantage de pouvoir désigner par son nom le bean qu'on souhaite injecter.

Exemple sur la classe **net.jmb.tuto.spring.controller.ArticleController**

```java
public class ArticleController {
	
	@Resource(name = "catalogService")
	CatalogServiceInterface service;	
	...
}
```

## Branche 08_instanciation_par_annotation

### 1. Balise `<context:component-scan base-package="..."/>` dans applicationContext.xml  

Cette balise indique les packages qui doivent être scannés pour détecter et instancier les beans gérés par le conteneur Spring.

Elle rend inutile l'annotation `<context:annotation-config/>`

**applicationContext.xml**

```xml
   <!--
    	Permet la détection et l'instanciation des beans dans les packages considérés.
		Ces beans doivent être annotés pour être détectés
     -->
    <context:component-scan base-package="net.jmb.tuto.spring.controller" />
    
    <!-- 
	 	Plus besoin de déclarer dans la config xml les contrôleurs à instancier
	 -->
```

### 2. Annotation `@Component` et ses stéréotypes dans les classes à instancier

`@Component` est l'annotation générique qui permet aux classes annotées d'être auto détectées et instanciées si elles sont dans un package scanné

Cette annotation est dérivée en plusieurs stéréotypes qui représentent une spécialisation :
- `@Controller` pour les classes contrôleurs
- `@Service` pour les classes de services
- `@Repository` pour les classes d'accès aux données

Exemple : classe **ArticleController**

```java
@Controller
public class ArticleController {
	...
}
```

### 3. Organisation des classes à gérer dans le conteneur Spring

Dans les fichier `applicationContext-1.xml` et `applicationContext-2.xml`, on indique de scanner les packages contenant les services et les repositories

```xml
	<context:component-scan 
		base-package="
			net.jmb.tuto.spring.service, 
			net.jmb.tuto.spring.repository"
	/> 
```

Ensuite si toutes les classes relatives aux différents contextes sont annotées, on est confronté à un problème de doublon

Exemple : pour l'interface `DevisServiceInterface`, on a 2 classes qui ne peuvent pas être annotées en même temps :
- `DevisSimpleService`
- `DevisRemiseService`

La preuve en essayant.

------

> **Solution proposée** : 
> - Créer un package distinct pour chaque contexte spécifique
> - Regrouper dans ces packages distincts les classes annotées
> - Scanner uniquement le package correspondant au contexte voulu


**Arborescence des packages de configuration**

![ConfigTree](diagram/ConfigTree.png)

> Chaque classe annotée dans ces packages ne fait qu'étendre la classe spécifique à instancier

**Exemple : classe `ArticleRepository` pour le contexte 2** 

```java
@Repository
public class ArticleRepository extends ArticleDatabaseRepository implements ArticleRepositoryInterface {
}
```

**Configuration applicationContext-2.xml associé au contexte 2**

```xml
<context:component-scan 
		base-package="net.jmb.tuto.spring.config.contexte2"
	/> 
```

### 4. Conclusion

La configuration s'effectue presque intégralement en java : plus de verbiage xml.

Avec l'organisation en packages des beans annotés :
- Le couplage avec le framework se réduit uniquement à ces packages
- La structure de la config reste centralisée, lisible et maintenable


## Branche 09_spring_configuration_full_java

### 1. Déclarer les classes de configuration -> annotation `@Configuration`

Cette annotation placée sur une classe indique qu'il s'agit d'une classe de configuration Spring.

Les classes de configuration ont la même vocation que les fichiers de config xml et peuvent les remplacer ou coexister.
- Intanciation des classes concrètes
- Injection des dépendances
- Import de configuration externe Java ou XML
- Scan de packages pour auto détection des beans
- Accès aux variables d'environnement, 
- Accès aux fichiers properties,
- etc...

**Arborescence des packages de configuration**

![JavaConfigTree](diagram/JavaConfigTree.png)

**net.jmb.tuto.spring.config.ApplicationConfig**
> Le point d'entrée de la configuration pour toute l'application

```java
@Configuration
@ComponentScan
public class ApplicationConfig {
	
	@Bean
	public ClientController clientController() {
		ClientController clientController = new ClientController();
		return clientController;
	}
	@Bean
	public ArticleController articleController(CatalogServiceInterface catalogService) {
		ArticleController articleController = new ArticleController();
		articleController.setCatalogService(catalogService);
		return articleController;
	}
	@Bean
	public DevisController devisController(DevisServiceInterface devisService) {
		DevisController devisController = new DevisController();
		devisController.setDevisService(devisService);
		return devisController;
	}
}
```

**net.jmb.tuto.spring.config.contexteDefaut.ContexteDefautConfig**
> Configuration par défaut (valable aussi pour le contexte 1)

```java
@Configuration
@ConditionalOnProperty(value = "contexte", matchIfMissing = true, havingValue = "1")
public class ContexteDefautConfig {
	
	@Bean
	public CatalogServiceInterface catalogService(ArticleRepositoryInterface articleRepository) {
		CatalogBasicService catalogBasicService = new CatalogBasicService();
		catalogBasicService.setArticleRepository(articleRepository);
		return catalogBasicService;
	}
	@Bean
	public ArticleRepositoryInterface articleRepository() {
		return new ArticleMemoryRepository();
	}	
	@Bean
	public DevisServiceInterface devisService(CatalogServiceInterface catalogService) {
		DevisSimpleService devisSimpleService = new DevisSimpleService();
		devisSimpleService.setCatalogService(catalogService);
		return devisSimpleService;
	}
}
```

**net.jmb.tuto.spring.config.contexte2.Contexte2Config**
> Configuration valable uniquement pour le contexte 2

```java
@Configuration
@ConditionalOnProperty(name = "contexte", havingValue = "2")
@PropertySource(value = "classpath:/application.properties")
public class Contexte2Config {
	
	@Value("${app.contexte2.remise}")
	int remise = 10;
	
	@Bean
	public CatalogServiceInterface catalogService(ArticleRepositoryInterface articleRepository) {
		CatalogDetailService catalogDetailService = new CatalogDetailService();
		catalogDetailService.setArticleRepository(articleRepository);
		return catalogDetailService;
	}
	@Bean
	public ArticleRepositoryInterface articleRepository() {
		return new ArticleDatabaseRepository();
	}	
	@Bean
	public DevisServiceInterface devisService(CatalogServiceInterface catalogService, ClientServiceInterface clientService) {
		DevisRemiseService devisRemiseService = new DevisRemiseService();
		devisRemiseService.setCatalogService(catalogService);
		devisRemiseService.setClientService(clientService);
		devisRemiseService.setRemise(remise);
		return devisRemiseService;
	}	
	@Bean
	public ClientServiceInterface clientService() {
		ClientSimpleService clientSimpleService = new ClientSimpleService();
		return clientSimpleService;
	}
}
```

### 2. Charger le contexte Spring -> classe `@AnnotationConfigApplicationContext`

**net.jmb.tuto.spring.Application**

```java
public class Application {

	public static void main(String[] args) {

		// L'élement central de la config Spring est la classe ApplicationConfig
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		...
}
```

### 3. Sélectionner les packages à scanner (auto détection des beans) -> annotation `@ComponentScan`

Cette annotation se place sur une classe de configuration et spécifie les packages à scanner.
Si rien n'est fourni en paramètre, le package en cours et ses sous-packages sont scanné.

C'est le cas dans l'exemple ci-dessous :

```java
@Configuration
@ComponentScan
public class ApplicationConfig 
```

### 4. Instancier un objet géré dans le conteneur -> annotation `@Bean`

Cette annotation se place sur une méthode qui retourne un objet :
- L'objet en retour est placé dans le conteneur Spring et candidat pour l'injection en @Autowired
- Si la méthode a des paramètres, ils sont injectés automatiquement si leur type est une classe gérée par Spring.

Exemple :

```java
@Bean
public ArticleController articleController(CatalogServiceInterface catalogService) { ... }
```

### 5. Accéder à des variables et propriétés -> annotations `@PropertySource` et `@Value`

Les propriétés sont cherchées dans l'environnement Spring (interface `Environment`).
Cet environnement est alimenté au chargement du contexte Spring avec :
- les fichiers properties, 
- les variables d'environnement (JVM ou système),
- les ressources JNDI,
- les paramètres du ServletContext pour les webapp

Fonctionnement dans le cas d'un fichier de propriétés :
- **@PropertySource** se place sur une classe de config et spécifie le chemin vers le fichier de propriété 
- **@Value("${xxx}")** se place sur une variable (attribut ou paramètre) et lui affecte la valeur de la propriété
	- On passe en paramètre de cette annotation la chaîne "${xxx}" 
	- Avec xxx qui représente le nom de la propriété à lire 

Exemple :

```java
@Configuration
@PropertySource(value = "classpath:/application.properties")
public class Contexte2Config {	
	@Value("${app.contexte2.remise}")
	int remise = 10;	// 10 est la valeur par défaut 
```

### 6. Charger la config sous certaines conditions -> Annotation `@ConditionalOnProperty`

Cette annotation se place sur une classe de config ou une méthode.
Et selon où elle se situe :
- Elle ne charge la config que si la condition est respectée
- Elle n'exécute la méthode que si la condition est respectée

Comme ci-dessus la propriété est récupérée dans l'environnement Spring.

Exemple :

```java
@Configuration
@ConditionalOnProperty(value = "contexte", matchIfMissing = true, havingValue = "1")
public class ContexteDefautConfig { ... }
```

### 7. Quelques autres annotations de configuration utiles

- `@Import` : importe d'autres classes de configuration
- `@ImportResource` : importe des fichiers de configuration XML
- `@Profile` : conditionne le chargement de la config à la valeur du profil (propriété `spring.profiles.active` de l'environnement Spring)


## Branche 10_scopes

### 1. Considérations sur la portée (scope) des beans Spring

Par défaut, la portée des beans gérés par Spring est singleton.
C'est-à-dire que pour chaque classe, Spring  n'utilise qu'une seule instance d'objet dans toute l'application.

Cette portée est généralement adaptée car le conteneur Spring gère des classes de traitement.
- Les classes de traitement ne portent généralement pas d'attributs métiers mais uniquement des méthodes (ou des attributs de config qui ne varient plus une fois initialisés).
- Il n'y a donc aucun besoin d'avoir plusieurs instances de ces classes. Les instances multiples n'ont de sens que pour distinguer des états d'objets en fonction de leurs attributs.

> Cependant, il est parfois nécessaire de stocker des **données variables dans les objets gérés par Spring**.
> Dans ce cas, il faut impérativement modifier la portée : L'objet ne peut plus être de type singleton car l'affectation de valeurs à ses attributs peut provoquer des interférences.


### 2. Scope Singleton (par défaut)

Exemple de config sur l'interface `CatalogServiceInterface`

** ContexteDefautConfig **

```java
...
public class ContexteDefautConfig {
	
	@Bean
	@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON) // ou aucun scope : c'est pareil
	public CatalogServiceInterface catalogService(ArticleRepositoryInterface articleRepository) {
		CatalogBasicService catalogBasicService = new CatalogBasicService();
		catalogBasicService.setArticleRepository(articleRepository);
		return catalogBasicService;
	}
```

On rajoute un `sysout` dans les méthodes qui utilisent cette interface

** ArticleController.choisirArticles() **

```java
...
	public List<Integer> choisirArticles() {

		if (service != null) {			
			System.err.println("ArticleController.choisirArticles(): " + service); 
			...
```

** DevisSimpleService.calculerDevis() **

```java
	public Devis calculerDevis(String nomClient, List<Integer> numArticles) {
		...		
		// Calcul détail devis par article
		articlesEtQuantites.forEach((numArticle, quantite) -> {
			System.err.println("DevisSimpleService.calculerDevis(): " + catalogService);
			Article article = catalogService.chercherArticle(numArticle);
			...
```

A l'exécution, on constate que la référence de l'objet de type `CatalogServiceInterface` est toujours la même


### 3. Scope Prototype

Avec ce scope, un nouvel objet est instancié et retourné par Spring à chaque injection ou à chaque appel au conteneur

Exemple de config sur l'interface `CatalogServiceInterface`

** ContexteDefautConfig **

```java
public class ContexteDefautConfig {
	
	@Bean
	@Scope(
		scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE 
	)
	public CatalogServiceInterface catalogService(ArticleRepositoryInterface articleRepository) {
		CatalogBasicService catalogBasicService = new CatalogBasicService();
		catalogBasicService.setArticleRepository(articleRepository);
		return catalogBasicService;
	}
```

A l'exécution, on constate que la référence de l'objet de type `CatalogServiceInterface` :
- Est différente entre `ArticleController` et `DevisSimpleService`.
- Mais est identique à chaque utilisation à l'intérieur de `DevisSimpleService`.

> Problème : prototype injecté dans un singleton.

`DevisSimpleService` est un singleton et l'injection de `CatalogServiceInterface` n'est faire qu'une seule fois à sa création.
Chaque appel de cette interface à l'intérieur du singleton fera toujours référence à la même instance, ce qu'on ne souhaite pas.

### 4. Scope Prototype + proxyMode

Pour résoudre le problème du prototype à l'intérieur du singleton et obtenir une instance différente de `CatalogServiceInterface` à chaque appel, Spring propose l'option `proxyMode`

** ContexteDefautConfig **

```java
public class ContexteDefautConfig {
	
	@Bean
	@Scope(
		scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE 
		, proxyMode = ScopedProxyMode.INTERFACES
	)
	public CatalogServiceInterface catalogService(ArticleRepositoryInterface articleRepository) {
		CatalogBasicService catalogBasicService = new CatalogBasicService();
		catalogBasicService.setArticleRepository(articleRepository);
		return catalogBasicService;
	}
```

A l'exécution, on constate que la référence de l'objet de type `CatalogServiceInterface` est différente à chaque utilisation.