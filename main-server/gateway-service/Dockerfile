FROM anapsix/alpine-java
MAINTAINER Marko Jevtovic <markojevtovic25@yahoo.com>
ADD target/gateway-service-0.0.1-SNAPSHOT.jar gateway-service.jar
ENTRYPOINT ["java", "-jar", "/gateway-service.jar"]
EXPOSE 8761