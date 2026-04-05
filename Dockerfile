# ---------- ビルド用 ----------
FROM gradle:7.6-jdk17 AS build

WORKDIR /app
COPY . .

# jar作成
RUN gradle build -x test

# ---------- 実行用 ----------
FROM openjdk:17-jdk-slim

WORKDIR /app

# buildステージからjarコピー
COPY --from=build /app/build/libs/*.jar app.jar

# RenderはPORT環境変数を使う
ENV PORT=8080

EXPOSE 8080

# 起動
ENTRYPOINT ["java","-jar","app.jar"]