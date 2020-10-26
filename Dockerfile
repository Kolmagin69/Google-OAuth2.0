FROM maven:3-openjdk-8
COPY . usr/app
WORKDIR usr/app
RUN mvn clean install
ENTRYPOINT ["java", "-jar", "target/google.oauth-1.jar"]

