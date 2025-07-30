FROM eclipse-temurin:17-jre-alpine
WORKDIR /shoppit-0.0.1-SNAPSHOT
COPY build/libs/shoppit-0.0.1-SNAPSHOT.jar shoppit-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "shoppit-0.0.1-SNAPSHOT.jar"]
