FROM openjdk:17
EXPOSE 8181
ADD target/springboot-k8s-demo.jar springboot-k8s-demo.jar
ENTRYPOINT ["java","-jar","/springboot-k8s-demo.jar"]
