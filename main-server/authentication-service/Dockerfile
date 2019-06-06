FROM anapsix/alpine-java
MAINTAINER Marko Jevtovic <markojevtovic25@yahoo.com>
ADD target/authentication-service-0.0.1-SNAPSHOT.jar authentication-service.jar
ENTRYPOINT ["java", "-jar", "/authentication-service.jar"]
EXPOSE 2222