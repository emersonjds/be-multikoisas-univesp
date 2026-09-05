# Estágio 1: Construção com Maven e compilador do Java 25
FROM maven:3.9.6-eclipse-temurin-25 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

# O comando agora gerará um Fat JAR graças ao plugin adicionado
RUN mvn clean package -DskipTests

# Estágio 2: Ambiente leve de execução (JRE 25)
FROM eclipse-temurin:25-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080

# ENTRYPOINT blindado contra picos de memória (OOMKilled)
ENTRYPOINT ["java", "-Xmx300m", "-Xms150m", "-jar", "app.jar"]