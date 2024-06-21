package ar.edu.unq.desapp.grupod.backenddesappapi.configuration;

import ar.edu.unq.desapp.grupod.backenddesappapi.utils.aspects.LogExecutionTimeAspectAnnotation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

    @Bean
    public LogExecutionTimeAspectAnnotation logExecutionTimeAspectAnnotation() {
        return new LogExecutionTimeAspectAnnotation();
    }
}