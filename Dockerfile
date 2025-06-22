# Etapa de build
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /app

# Copia todos os arquivos do projeto
COPY . .

# Corrige a permissão do mvnw
RUN chmod +x ./mvnw

# Executa o build do Maven dentro do container
RUN ./mvnw clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o JAR gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Expõe as portas (caso tenha nginx reverso ou outra estratégia de rede)
EXPOSE 8080
EXPOSE 80

# Comando de execução
ENTRYPOINT ["java", "-jar", "app.jar"]
