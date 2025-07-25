# Etapa 1: Build da aplicação
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia o código-fonte e faz o build do JAR
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: Imagem final com JDK mais leve
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o JAR gerado da etapa anterior
COPY --from=builder /app/target/*.jar medsilv-api.jar

# Expõe a porta usada pela aplicação
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "medsilv-api.jar"]
