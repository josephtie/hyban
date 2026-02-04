# 1️⃣ Étape de compilation avec Maven et JDK 17
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# 2️⃣ Définir le répertoire de travail
WORKDIR /app

# 3️⃣ Copier les fichiers du projet dans l'image
COPY . .

# 4️⃣ Télécharger les dépendances Maven en avance pour optimiser la build
RUN mvn dependency:go-offline -B

# 5️⃣ Compiler l’application et générer le fichier WAR
RUN mvn clean package -DskipTests

# 6️⃣ Utiliser une image Tomcat avec JDK 17 pour l’exécution
FROM tomcat:9.0.82-jdk17

# 7️⃣ Définir le répertoire de travail dans Tomcat
WORKDIR /usr/local/tomcat/webapps/

# 8️⃣ Copier le fichier WAR généré dans Tomcat
COPY --from=builder /app/target/*.war hyban.war

# 9️⃣ Exposer le port 8080
EXPOSE 8080


# 🔟 Démarrer Tomcat
CMD ["catalina.sh", "run"]
