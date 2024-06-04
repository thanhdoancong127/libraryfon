# Sử dụng một image Maven để build các module
FROM maven:3.9.6-eclipse-temurin-21-jammy AS build

# Tạo thư mục /app trong container
RUN mkdir /app

WORKDIR /app

# Copy tất cả các file từ thư mục gốc của dự án vào thư mục /app của container
COPY . .

# Di chuyển tới thư mục gốc của module libraryfon và build
RUN mvn clean package -DskipTests -pl notification -am

# Sử dụng một image Java 21 làm base
FROM openjdk:21-jdk

# Đặt thư mục làm việc
WORKDIR /app

# Copy file .jar của từng module vào thư mục app
COPY --from=build /app/notification/target/notification-1.0.0-SNAPSHOT.jar /app/notification-1.0.0-SNAPSHOT.jar
COPY --from=build /app/common/target/common-1.0.0-SNAPSHOT.jar /app/common-1.0.0-SNAPSHOT.jar

# Khởi chạy ứng dụng Spring Boot khi container được khởi động
CMD ["java", "-jar", "notification-1.0.0-SNAPSHOT.jar"]