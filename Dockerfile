FROM openjdk:17
COPY build/libs/popolog-interview-service.jar popolog-interview-service.jar
ENTRYPOINT ["java", "-jar", "/popolog-interview-service.jar"]