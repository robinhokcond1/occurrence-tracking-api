# Usar a imagem do OpenJDK 21
FROM openjdk:21-jdk

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR da aplicação para o container
COPY target/*.jar app.jar

# Expõe a porta da aplicação
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
