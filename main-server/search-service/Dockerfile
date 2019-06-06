FROM anapsix/alpine-java
MAINTAINER Marko Jevtovic <markojevtovic25@yahoo.com>
ADD target/search-service-0.0.1-SNAPSHOT.jar search-service.jar
ENTRYPOINT ["java", "-jar", "/search-service.jar"]
EXPOSE 2222