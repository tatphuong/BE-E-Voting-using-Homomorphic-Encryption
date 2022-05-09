# Run Stage
FROM adoptopenjdk/openjdk11
ENV ARTIFACT_NAME=spring-boot-started-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
    
WORKDIR $APP_HOME
COPY --from=Builder $APP_HOME/build/libs/$ARTIFACT_NAME .
    
EXPOSE 8080
ENTRYPOINT exec java -jar /build/libs/${ARTIFACT_NAME}