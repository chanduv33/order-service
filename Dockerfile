FROM azul/zulu-openjdk-alpine:11.0.4
COPY target/order-service-0.0.1-SNAPSHOT.jar order-service.jar
ENTRYPOINT ["ava","-jar","order-service.jar"]