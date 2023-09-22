# Sử dụng hình ảnh cơ sở với JDK để xây dựng ứng dụng
FROM openjdk:11-jre-slim AS builder
ENV APP_HOME=/usr/app/
# Tạo thư mục làm việc
WORKDIR $APP_HOME

COPY gradle $APP_HOME/gradle
# Sao chép tệp build.gradle và settings.gradle vào thư mục làm việc
COPY build.gradle settings.gradle $APP_HOME

# Sao chép tệp Gradle wrapper vào thư mục làm việc
COPY gradlew $APP_HOME

# Cấp quyền thực thi cho tệp Gradlew
RUN chmod +x $APP_HOME/gradlew

# Sao chép toàn bộ thư mục src vào thư mục làm việc
COPY src /usr/app/src

# Chạy lệnh Gradle để xây dựng ứng dụng
RUN ./gradlew clean build

# Bắt đầu giai đoạn mới và đặt tên hình ảnh thành lowercase
FROM openjdk:11-jre-slim
ENV ARTIFACT_NAME=spring-boot-started-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

# Sao chép file JAR từ giai đoạn trước (builder stage) vào giai đoạn hiện tại
COPY --from=builder $APP_HOME/build/libs/$ARTIFACT_NAME .

ENTRYPOINT exec java -jar $ARTIFACT_NAME
