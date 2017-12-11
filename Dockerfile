FROM openjdk:8-jre-alpine

WORKDIR /app

ADD ./api/target/lairs-api-1.0-SNAPSHOT.jar /app

EXPOSE 8081

CMD ["java", "-jar", "lairs-api-1.0-SNAPSHOT.jar"]