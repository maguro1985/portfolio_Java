# ---------- ビルド用 ----------
FROM gradle:8.14-jdk17 AS build

WORKDIR /app
COPY . .

RUN gradle build -x test

# ---------- 実行用 ----------
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]