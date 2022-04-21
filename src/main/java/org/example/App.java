package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.configuration.MyConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) throws JsonProcessingException {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        Communication communication = context.getBean("communication", Communication.class);
        communication.getAllUsersEntity();

    }


}
