

## Branche 11_spring_webapp_initialisation


### 1. Création du projet maven 

Le projet peut être créé à partir de [Spring Initializr](https://start.spring.io/) en spécifiant la dépendance sur **Spring Web**.
Le projet généré est une application Spring Boot.

> A noter
- Dépendance sur `spring-boot-starter-web` qui ramène toutes les librairies utiles pour créer une webapp.
- Packaging de type `war` pour produire une application web

**Fichier pom.xml**

```xml
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.4.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>net.jmb.tuto.spring</groupId>
	<artifactId>02-spring-webmvc</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>spring-web</name>
	<description>Demo project for Spring Webmvc</description>
	<packaging>war</packaging>

	<properties>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
	</dependencies>
```

### 2. Création d'une page d'accueil

Afin de tester le fonctionnement, on crée une page d'accueil.
Par défaut Spring Boot cherche les pages statiques dans le répertoire `/src/main/resources/static`.
C'est dans ce répertoire qu'il nous faut créer la page

**/src/main/resources/static/index.html**

```html
<!DOCTYPE html>
<html>
<head>
<meta content="text/html;UTF-8">
<title>Gestion devis</title>

<body style="padding: 20px;">
	<div>
		Bonjour à vous mes collègues ...
	</div>	
</body>
</html>
```

### 3. Exécution de l'application

L'application Spring Boot a été générée dans le package `net.jmb.tuto.spring`.
Il suffit de l'exécuter comme une application java standard.

L'autoconfiguration Spring Boot : 
- embarque un serveur d'application tomcat.
- et confère à l'application la nature d'application web

Ceci rend la page d'accueil accessible par navigateur web à l'adresse [http://localhost:8080/](http://localhost:8080/)


 