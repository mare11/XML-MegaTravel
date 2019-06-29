FROM anapsix/alpine-java
MAINTAINER Marko Jevtovic <markojevtovic25@yahoo.com>
ADD target/user-service-0.0.1-SNAPSHOT.jar user-service.jar
#COPY lib/ /lib/
ENTRYPOINT ["java", "-jar", "/user-service.jar"]
EXPOSE 2222