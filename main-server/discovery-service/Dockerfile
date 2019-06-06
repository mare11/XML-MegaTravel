FROM anapsix/alpine-java
MAINTAINER Marko Jevtovic <markojevtovic25@yahoo.com>
ADD target/discovery-service-0.0.1-SNAPSHOT.jar discovery-service.jar
ENTRYPOINT ["java", "-jar", "/discovery-service.jar"]
EXPOSE 8761