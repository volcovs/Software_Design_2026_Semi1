# Build
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY gradlew build.gradle settings.gradle ./
COPY gradle gradle
RUN chmod +x gradlew
COPY src src
RUN ./gradlew bootJar --no-daemon -x test
RUN JAR_FILE=$(ls build/libs/*.jar | grep -v '\-plain\.jar$' | head -1) && \
    test -f "$JAR_FILE" && cp "$JAR_FILE" /app/app.jar

# Run
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/app.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
