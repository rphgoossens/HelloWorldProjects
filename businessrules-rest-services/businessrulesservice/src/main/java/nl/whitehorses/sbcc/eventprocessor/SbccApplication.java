package nl.whitehorses.sbcc.eventprocessor;

import com.googlecode.jsonrpc4j.spring.JsonServiceExporter;
import nl.whitehorses.sbcc.eventprocessor.api.EventProcessor;
import nl.whitehorses.sbcc.eventprocessor.impl.EventProcessorImpl;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@SpringBootApplication
public class SbccApplication {
    private static final Logger logger = LoggerFactory.getLogger(SbccApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SbccApplication.class, args);
        logger.debug("--Application Started--");
    }

    @Bean
    public EventProcessor eventProcessor() {
        return new EventProcessorImpl();
    }

    @Bean(name = "/rpc/business-rules-service")
    public JsonServiceExporter jsonServiceExporter() {
        JsonServiceExporter exporter = new JsonServiceExporter();
        exporter.setService(eventProcessor());
        exporter.setServiceInterface(EventProcessor.class);
        return exporter;
    }

}
