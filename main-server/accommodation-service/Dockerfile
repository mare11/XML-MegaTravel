FROM anapsix/alpine-java
MAINTAINER Marko Jevtovic <markojevtovic25@yahoo.com>
ADD target/accommodation-service-0.0.1-SNAPSHOT.jar accommodation-service.jar
ENTRYPOINT ["java", "-jar", "/accommodation-service.jar"]
EXPOSE 2222