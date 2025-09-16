start cmd /k ".\mvnw spring-boot:run -Dspring-boot.run.fork=false -Dspring-boot.run.jvmArguments=-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:5005"
