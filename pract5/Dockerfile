FROM bellsoft/liberica-openjre-alpine:22
COPY pract5/target/pract5-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "-parameters"]