# Estágio 1: Construção com Maven e compilador do Java 25
FROM maven:3.9.16-amazoncorretto-25-al2023 AS build
WORKDIR /app

#copia o projeto inteiro
COPY . .

# O comando agora gerará um Fat JAR graças ao plugin adicionado
# O X é o log e o -Dskip... é ignorar testes
RUN mvn clean package -X -DskipTests

# Estágio 2: Ambiente leve de execução (JRE 25)
# amazoncorretto:25-al2023-headfull para caso precise manipular imagens ou gráficos.
FROM amazoncorretto:25-al2023-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar multikoisas-univesp.jar
EXPOSE 8080

# ENTRYPOINT blindado contra picos de memória (OOMKilled)
# Render tem 512MB, XMX é o máximo de memória e XMS é o mínimo para JVM
ENTRYPOINT ["java", "-Xmx415m", "-Xms250m", "-jar", "multikoisas-univesp.jar"]