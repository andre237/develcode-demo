FROM maven:3-jdk-11
COPY . /usr/src/app
WORKDIR /usr/src/app
RUN ["mvn", "clean", "install"]
ENTRYPOINT ["java", "-jar", "target/example-0.0.1.jar"]
