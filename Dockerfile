FROM openjdk
EXPOSE 8600
VOLUME /app
COPY /app/application.yml ./
# ENV JAVA_OPTS =''
ENTRYPOINT [ "java" , "-jar" ,"/app/elasticcore.jar"]