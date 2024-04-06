FROM openjdk:21

EXPOSE 8080

ADD target/cloud-service-diploma-0.0.1-SNAPSHOT.jar cloud-service-diploma.jar

ENTRYPOINT ["java","-jar","/cloud-service-diploma.jar"]