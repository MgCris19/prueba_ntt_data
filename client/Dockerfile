FROM maven:3.8.4-openjdk-17 AS maven
LABEL MAINTAINER="Cris1907"

WORKDIR /usr/src/app
COPY . /usr/src/app
# Compile and package the application to an executable JAR

RUN mvn clean package -DskipTests

# For Java 17,
FROM maven:3.8.4-openjdk-17

ARG JAR_FILE=client-0.0.1-SNAPSHOT.jar

WORKDIR /opt/app

# Copy the spring-boot-api-tutorial.jar from the maven stage to the /opt/app directory of the current stage.
COPY --from=maven /usr/src/app/target/${JAR_FILE} /opt/app/

ENTRYPOINT ["java","-jar","client-0.0.1-SNAPSHOT.jar"]