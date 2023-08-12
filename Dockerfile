FROM amazoncorretto:17
COPY ./build/libs/bugtracking-0.0.1.jar .
CMD [ "java", "-jar", "bugtracking-0.0.1.jar" ]