# 1️⃣ Utiliser une image contenant Maven + JDK 17 pour compiler le projet
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# 2️⃣ Définir le répertoire de travail
WORKDIR /app

# 3️⃣ Copier les fichiers du projet dans l'image
COPY . .

# 4️⃣ Compiler l’application avec Maven
RUN mvn clean package -DskipTests

# 5️⃣ Utiliser une image Tomcat allégée pour exécuter l’application
FROM tomcat:9.0.82-jdk17

# 6️⃣ Définir le répertoire de travail dans Tomcat
WORKDIR /usr/local/tomcat/webapps/

# 7️⃣ Copier le fichier WAR généré dans Tomcat
COPY --from=builder /app/target/hyban.war hyban.war

# 8️⃣ Exposer le port 8080
EXPOSE 8080

# 9️⃣ Démarrer Tomcat
CMD ["catalina.sh", "run"]
