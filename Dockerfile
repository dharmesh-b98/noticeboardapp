FROM maven:3.9.9-eclipse-temurin-23 AS builder

LABEL MAINTAINER="Dharmesh"
LABEL description="SSF ASSESSMENT NOTICEBOARD APP"
LABEL name="NOTICEBOARDAPP"

WORKDIR /src

COPY mvnw .
COPY pom.xml .

COPY .mvn .mvn
COPY src src

RUN chmod a+x mvnw && /src/mvnw package -Dmaven.test.skip=true

FROM maven:3.9.9-eclipse-temurin-23

WORKDIR /app

COPY --from=builder /src/target/noticeboard-0.0.1-SNAPSHOT.jar app.jar

ENV SERVER_PORT=8080
ENV APPLICATION_HOST_URL=https://publishing-production-d35a.up.railway.app

EXPOSE ${SERVER_PORT}

HEALTHCHECK --interval=60s --start-period=120s \
   CMD curl -s -f http://localhost:${SERVER_PORT}/status || exit 1

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar