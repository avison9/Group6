FROM openjdk:latest
COPY ./target/group6-0.1.0.4.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "group6-0.1.0.4.jar"]