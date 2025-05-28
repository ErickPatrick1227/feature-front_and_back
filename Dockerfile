# Use uma imagem base com Java 17
FROM eclipse-temurin:17-jre-alpine

# Copie o JAR compilado para dentro do container
COPY target/CRUD-java-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta que sua aplicação usa
EXPOSE 8081

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]
