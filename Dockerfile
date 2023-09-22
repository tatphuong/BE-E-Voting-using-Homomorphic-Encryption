# Sử dụng hình ảnh cơ sở với JDK để xây dựng ứng dụng
FROM openjdk:17-jdk-slim AS builder

ENV APP_HOME=/usr/app/
# Tạo thư mục làm việc
WORKDIR $APP_HOME

# Sao chép các tệp Gradle vào thư mục làm việc
COPY gradle $APP_HOME/gradle
COPY build.gradle settings.gradle $APP_HOME

# Sao chép tệp Gradle wrapper vào thư mục làm việc và cấp quyền thực thi
COPY gradlew $APP_HOME
RUN chmod +x $APP_HOME/gradlew

# Sao chép toàn bộ thư mục src vào thư mục làm việc
COPY src $APP_HOME/src

# Chạy lệnh Gradle để xây dựng ứng dụng
RUN ./gradlew clean build

# Bắt đầu giai đoạn mới và đặt tên hình ảnh thành lowercase
FROM openjdk:17-jdk-slim
ENV ARTIFACT_NAME=spring-boot-started-0.0.1-SNAPSHOT.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

# Sao chép file JAR từ giai đoạn trước (builder stage) vào giai đoạn hiện tại
COPY --from=builder $APP_HOME/build/libs/$ARTIFACT_NAME .

# Chạy ứng dụng
ENTRYPOINT exec java -jar $ARTIFACT_NAME
