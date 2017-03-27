package ru.jakimenko.genmesrab;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext()) {
            PropertyPlaceholderConfigurer propertyConfigurer = new PropertyPlaceholderConfigurer();
            propertyConfigurer.setLocation(new ClassPathResource("application.properties"));
            context.addBeanFactoryPostProcessor(propertyConfigurer);
            context.setConfigLocation("applicationContext.xml");
            context.refresh();
            System.out.println("Start generate messages into RabbitMQ");
            GenerateMessages generateMessages = context.getBean(GenerateMessages.class);
            generateMessages.generate();
        } catch (Exception e) {
            System.err.println(new StringBuilder()
                    .append("Error: ").append(e.getMessage())
                    .append(" Trace: ")
                    .append(Arrays.toString(e.getStackTrace())).toString());
        }
    }
}
