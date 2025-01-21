# Taxi Stations Pipeline

Ce projet met en œuvre un pipeline distribué pour la gestion et la visualisation des données Open Data concernant les stations de taxi. Il utilise Apache Kafka, Spring Boot, et Flutter pour fournir un système complet, de l'ingestion des données jusqu'à leur affichage sur une carte interactive.

## **Fonctionnalités**
- **Ingestion des données Open Data** depuis le site [data.gouv.fr](https://www.data.gouv.fr).
- **Nettoyage des données** avec OpenRefine.
- **Service Producteur Kafka** pour envoyer les données dans un topic Kafka.
- **Service Consommateur Kafka** pour stocker les données dans une base MySQL ou H2.
- **Service Kafka Streams** pour regrouper et filtrer les données.
- **Application Flutter** pour afficher les données sur une carte Google Maps.

---

## **Architecture**
![Pipeline Architecture](https://via.placeholder.com/800x400?text=Pipeline+Architecture)

1. **Producteur Kafka** :
   - Envoie les données du fichier CSV dans le topic `taxi-stations.raw`.
2. **Kafka Streams** :
   - Lit les données brutes.
   - Produit dans :
     - `stations.grouped.by.status` (regroupement par statut).
     - `paris.stations` (stations filtrées pour Paris).
3. **Consommateur Kafka** :
   - Persiste les données transformées dans MySQL (ou H2 en cas d’échec).
   - Expose une API RESTful pour accéder aux données.
4. **Application Flutter** :
   - Consomme l’API et affiche les données sur Google Maps.

---

## **Prérequis**
1. **Kafka** : Installez un serveur Kafka local ([Documentation officielle](https://kafka.apache.org/documentation/)).
2. **Java 11+** : Nécessaire pour exécuter les services Spring Boot.
3. **Flutter** : Installez Flutter pour l’application mobile ([Documentation Flutter](https://flutter.dev/docs/get-started)).
4. **MySQL** : Configurez une base de données MySQL locale.

---

## **Installation**
### **1. Clonez le Dépôt**
```bash
git clone https://github.com/username/taxi-stations-pipeline.git
cd taxi-stations-pipeline
```

### **2. Lancez Kafka**
Assurez-vous que votre serveur Kafka est démarré.
```bash
bin/zookeeper-server-start.sh config/zookeeper.properties
bin/kafka-server-start.sh config/server.properties
```

### **3. Configurez MySQL**
Créez une base de données :
```sql
CREATE DATABASE taxi_db;
```

### **4. Démarrez les Services**
#### Producteur
```bash
cd producer
mvn spring-boot:run
```

#### Consommateur
```bash
cd consumer
mvn spring-boot:run
```

#### Kafka Streams
```bash
cd kafka-streams
mvn spring-boot:run
```

#### Application Flutter
```bash
cd flutter-app
.\runner.bat #Windows
./runner.sh  #Linux
```

---

## **Données**
### **Source**
Les données sont disponibles sur le site [data.gouv.fr](https://www.data.gouv.fr).

### **Nettoyage**
Le fichier `stations-taxi-cleaned.csv` contient les données nettoyées avec OpenRefine.

---

## **Endpoints API**
### **1. Liste des Stations**
- **URL** : `/api/stations`
- **Méthode** : GET

### **2. Recherche par ID**
- **URL** : `/api/stations/{id}`
- **Méthode** : GET

---

## **Contribuer**
Les contributions sont les bienvenues ! Veuillez suivre ces étapes :
1. Forkez le dépôt.
2. Créez une branche (`git checkout -b feature/ma-feature`).
3. Commitez vos modifications (`git commit -m 'Ajout d’une nouvelle fonctionnalité'`).
4. Poussez la branche (`git push origin feature/ma-feature`).
5. Ouvrez une Pull Request.

---

## **Licence**
Ce projet est sous licence MIT. Voir le fichier [LICENSE](LICENSE) pour plus de détails.

---

## **Auteurs**
- **Nom** : GAVI Holali David
- **GitHub** : https://github.com/g-holali-david
