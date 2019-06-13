FROM anapsix/alpine-java
MAINTAINER Marko Jevtovic <markojevtovic25@yahoo.com>
ADD target/exist-db-service-0.0.1-SNAPSHOT.jar exist-db-service.jar
ENTRYPOINT ["java", "-jar", "/exist-db-service.jar"]
EXPOSE 8087