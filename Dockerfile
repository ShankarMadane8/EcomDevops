#FROM openjdk:17
#EXPOSE 8181
#ADD target/springboot-k8s-demo.jar springboot-k8s-demo.jar
#ENTRYPOINT ["java","-jar","/springboot-k8s-demo.jar"]


#===================
#Render

FROM openjdk:17
WORKDIR /app
COPY target/springboot-k8s-demo.jar app.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "app.jar"]
