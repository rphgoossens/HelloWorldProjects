package nl.whitehorses.sbcc.eventprocessor;

import com.googlecode.jsonrpc4j.spring.JsonServiceExporter;
import nl.whitehorses.sbcc.eventprocessor.service.EventProcessor;
import nl.whitehorses.sbcc.eventprocessor.service.EventProcessorImpl;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SbccApplication {
    private static final Logger logger = LoggerFactory.getLogger(SbccApplication.class);
    public static final String drlFile = "nl/whitehorses/rules/Rules.drl";

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


    @Bean
    public KieContainer kieContainer() {
        KieServices kieServices = KieServices.Factory.get();

        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(drlFile));
        KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem);
        kieBuilder.buildAll();
        KieModule kieModule = kieBuilder.getKieModule();

        return kieServices.newKieContainer(kieModule.getReleaseId());

    }
}
