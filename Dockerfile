# Sử dụng hình ảnh cơ sở với JDK
FROM openjdk:11-jre-slim
ENV APP_HOME=/usr/app/
# Tạo thư mục làm việc
WORKDIR $APP_HOME

COPY gradle $APP_HOME/gradle
# Sao chép tệp build.gradle và settings.gradle vào thư mục làm việc
COPY build.gradle settings.gradle $APP_HOME

# Sao chép tệp Gradle wrapper vào thư mục làm việc
COPY gradlew $APP_HOME

# Sao chép toàn bộ thư mục src vào thư mục làm việc
COPY src /usr/app/src

# Chạy lệnh Gradle để xây dựng ứng dụng
RUN ./gradlew clean build
ENV ARTIFACT_NAME=spring-boot-started-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME
COPY --from=Builder $APP_HOME/build/libs/$ARTIFACT_NAME .

ENTRYPOINT exec java -jar ${ARTIFACT_NAME}
