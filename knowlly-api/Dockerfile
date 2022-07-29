FROM gradle:6.6.1-jdk11 as compile

WORKDIR /home/gradle/project

# Only copy dependency-related files
# COPY build.gradle settings.gradle /home/gradle/project/

# Only download dependencies
# Eat the expected build failure since no source code has been copied yet
# RUN gradle clean build --no-daemon > /dev/null 2>&1 || true
COPY build/libs/knowlly-api-0.0.1-SNAPSHOT.jar /home/gradle/project

ENTRYPOINT ["java","-jar", "-Xms600m", "-Xmx600m", "/home/gradle/project/knowlly-api-0.0.1-SNAPSHOT.jar"]
EXPOSE 8080
