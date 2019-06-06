FROM anapsix/alpine-java
MAINTAINER Marko Jevtovic <markojevtovic25@yahoo.com>
ADD target/registration-service-0.0.1-SNAPSHOT.jar registration-service.jar
ENTRYPOINT ["java", "-jar", "/registration-service.jar"]
EXPOSE 2222