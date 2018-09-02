FROM java:8
ADD /target/github-client-0.0.1-SNAPSHOT.jar //
ENTRYPOINT ["java", "-jar", "/github-client-0.0.1-SNAPSHOT.jar"]
