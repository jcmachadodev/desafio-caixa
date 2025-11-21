
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app


COPY pom.xml .

RUN mvn dependency:go-offline


COPY src src


RUN mvn clean package -DskipTests


FROM eclipse-temurin:21-jre

WORKDIR /app


COPY --from=build /app/target/quarkus-app/lib/ /app/lib/
COPY --from=build /app/target/quarkus-app/*.jar /app/
COPY --from=build /app/target/quarkus-app/app/ /app/app/
COPY --from=build /app/target/quarkus-app/quarkus/ /app/quarkus/


ENV LANG='pt_BR.UTF-8'
ENV LANGUAGE='pt_BR:pt'


EXPOSE 8080


CMD ["java", "-Duser.language=pt", "-Duser.country=BR", "-jar", "/app/quarkus-run.jar"]