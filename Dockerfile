FROM openjdk:11
ADD target/stock.jar stock.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "stock.jar"]