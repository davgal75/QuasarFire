FROM openjdk:8-alpine
ADD target/FireOperation-0.0.1-SNAPSHOT.jar /usr/share/app.jar
ADD satelite.json /satelite.json
ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/app.jar"]