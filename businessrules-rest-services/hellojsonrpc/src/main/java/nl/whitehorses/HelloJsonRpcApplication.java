package nl.whitehorses;

import com.googlecode.jsonrpc4j.spring.AutoJsonRpcServiceImplExporter;
import nl.whitehorses.service.HelloWorldImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HelloJsonRpcApplication {

    private static final Logger logger = LoggerFactory.getLogger(HelloJsonRpcApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(HelloJsonRpcApplication.class, args);
        logger.debug("--Application Started--");
    }

    @Bean
    public static AutoJsonRpcServiceImplExporter autoJsonRpcServiceImplExporter() {
        return new AutoJsonRpcServiceImplExporter();
    }

    @Bean
    public static HelloWorldImpl helloWorldImpl() {
        return new HelloWorldImpl();
    }


}