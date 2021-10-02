FROM gradle:6-jdk11-openj9 as builder
WORKDIR /app
COPY . .
RUN ./gradlew build --stacktrace

FROM adoptopenjdk/openjdk11-openj9:alpine-slim
WORKDIR /app
COPY --from=builder /app/build/libs/pizzaapp.jar .
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java"]
CMD ["-Xtune:virtualized", "-XX:+UseContainerSupport", "-XX:MaxDirectMemorySize=128m", "-Dfile.encoding=UTF-8", "-jar", "pizzaapp.jar"]
EXPOSE 8080
