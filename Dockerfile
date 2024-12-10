FROM openjdk:17
COPY build/libs/popolog-interview-service.jar popolog-interview-service.jar
ENTRYPOINT ["java", "-jar", "/popolog-interview-service.jar"]

# Docker run 명령을 통해 다른 인자를 지정할 경우 덮어쓸 수 있음
CMD ["--server.port=9082"]