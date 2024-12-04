# Etapa de build
FROM ubuntu:latest AS build

# Atualiza os repositórios de pacotes e instala o JDK 17 e Maven
RUN apt-get update && apt-get install -y openjdk-17-jdk maven

# Define o diretório de trabalho
WORKDIR /app

# Copia apenas o diretório do projeto para o contêiner
COPY breakpoint /app

# Realiza o build do projeto
RUN mvn clean package -DskipTests

# Etapa final para execução
FROM openjdk:17-jdk-slim

# Define a porta exposta
EXPOSE 8080

# Copia o JAR gerado na etapa anterior para o contêiner final
COPY --from=build /app/target/breakpoint-0.0.1-SNAPSHOT.jar app.jar

# Define o comando de inicialização do aplicativo
ENTRYPOINT ["java", "-jar", "app.jar"]