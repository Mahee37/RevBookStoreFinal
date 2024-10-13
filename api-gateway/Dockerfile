from openjdk:17
EXPOSE 8080
ADD target/api-gateway-1.0-SNAPSHOT.jar apigateway.jar
ENTRYPOINT ["java","-jar","/apigateway.jar"]