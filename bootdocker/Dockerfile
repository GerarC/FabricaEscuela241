FROM openjdk:17-alpine

COPY SITAS-0.0.1-SNAPSHOT.jar /usr/src/bootdocker/SITAS-0.0.1-SNAPSHOT.jar

WORKDIR /usr/src/bootdocker

EXPOSE 8099

ENTRYPOINT ["java", "-jar", "/usr/src/bootdocker/SITAS-0.0.1-SNAPSHOT.jar"]

