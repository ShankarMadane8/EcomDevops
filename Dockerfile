#FROM openjdk:17
#EXPOSE 8181
#ADD target/springboot-k8s-demo.jar springboot-k8s-demo.jar
#ENTRYPOINT ["java","-jar","/springboot-k8s-demo.jar"]


#===================
#Render

#
# Build stage
#
FROM maven:3.8.5-openjdk-17 AS build

COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim

COPY --from=build /target/springboot-k8s-demo.jar demo.jar

EXPOSE 8182
ENTRYPOINT ["java", "-jar", "demo.jar"]
