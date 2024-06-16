FROM maven:3.8.6-eclipse-temurin-17 as builder
WORKDIR /opt/app
COPY pom.xml ./
COPY ./src ./src
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
EXPOSE 80
COPY --from=builder /opt/app/target/*.jar /opt/app/*.jar
COPY ./src/main/resources/schema.sql /opt/app/schema.sql
ENTRYPOINT ["java", "-jar", "/opt/app/*.jar"]