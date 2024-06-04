# Sử dụng một image Maven để build các module
FROM maven:3.8.4 AS build

# Tạo thư mục /app trong container
RUN mkdir /app

# Copy tất cả các file từ thư mục gốc của dự án vào thư mục /app của container
COPY ../ /app

# Di chuyển tới thư mục gốc của module libraryfon và build
WORKDIR /app/libraryfon
RUN mvn clean package -DskipTests -pl book -am

# Sử dụng một image Java 21 làm base
FROM openjdk:21-jdk

WORKDIR /app

# Copy file .jar của từng module vào thư mục app
COPY ./libraryfon/book/target/book-1.0.0-SNAPSHOT.jar /app
COPY ./libraryfon/common/target/common-1.0.0-SNAPSHOT.jar /app

ENTRYPOINT [ "rm -rf", "/app/libraryfon" ]

# Khởi chạy ứng dụng Spring Boot khi container được khởi động
CMD ["java", "-jar", "book-1.0.0-SNAPSHOT.jar"]
