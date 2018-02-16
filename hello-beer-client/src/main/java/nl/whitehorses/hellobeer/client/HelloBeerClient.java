package nl.whitehorses.hellobeer.client;

import nl.whitehorses.hellobeer.generated.client.api.BeerControllerApi;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class HelloBeerClient extends WebMvcConfigurerAdapter {
    public HelloBeerClient() {
    }

    public static void main(String[] args) {
        org.springframework.boot.SpringApplication.run(HelloBeerClient.class, args);
    }

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", "/beers");
    }

    @Bean
    public BeerControllerApi addBeerControllerApi() {
        return new BeerControllerApi();
    }
}