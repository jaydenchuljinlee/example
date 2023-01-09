FROM gcr.io/distroless/java:11
LABEL description="Echo IP Java Application"
EXPOSE 60432
ARG JAR_FILE=/build/libs/demo-0.0.1-SNAPSHOT.jar 
COPY ${JAR_FILE} /opt/app-in-image.jar
WORKDIR /opt
ENTRYPOINT ["java", "-jar", "app-in-image.jar"]
