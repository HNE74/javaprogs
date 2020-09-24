sudo docker run -e JPDA_ADDRESS=8000 -e "JAVA_TOOL_OPTIONS=-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n" -p 8080:8080 -p 8000:8000 -t springio/gs-spring-boot-docker
