FROM anapsix/alpine-java
MAINTAINER Marko Jevtovic <markojevtovic25@yahoo.com>
ADD target/reservation-service-0.0.1-SNAPSHOT.jar reservation-service.jar
ENTRYPOINT ["java", "-jar", "/reservation-service.jar"]
EXPOSE 2222