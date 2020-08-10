FROM openjdk:11.0.8-slim-buster
#MYSQL
#ARG MYSQL_HOST
#ARG MYSQL_USER
#ARG MYSQL_PASSWORD
#ENV MYSQL_HOST = $MYSQL_HOST
#ENV MYSQL_USER = $MYSQL_USER
#ENV MYSQL_PASSWORD = $MYSQL_PASSWORD
#APP
#ARG APP_PORT
#ARG APP_HOST
#ENV APP_PORT = $APP_PORT
#ENV APP_HOST = $APP_HOST

RUN mkdir - p shortner

WORKDIR /shortner

COPY ./target/shortner-0.0.1.jar /shortner/app.jar

CMD java -jar -XX:+UnlockExperimentalVMOptions -Xmx512m -Xms512m -XX:MetaspaceSize=100m app.jar