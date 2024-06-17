package ar.edu.unq.desapp.grupod.backenddesappapi.configuration;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.BinancePriceDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.utils.CacheEventLogger;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.event.CacheEventListener;
import org.ehcache.event.EventType;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.time.Duration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager getCacheManager() {

        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();

        CacheConfigurationBuilder<String, BinancePriceDTO> configurationBuilder =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                String.class, BinancePriceDTO.class,
                                ResourcePoolsBuilder.heap(2)
                                        .offheap(10, MemoryUnit.MB))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(60)));

        CacheEventListenerConfigurationBuilder asynchronousListener = CacheEventListenerConfigurationBuilder
                .newEventListenerConfiguration(new CacheEventLogger()
                        , EventType.CREATED, EventType.EXPIRED).unordered().asynchronous();
        if(cacheManager.getCache("cryptoCache") == null) {
            cacheManager.createCache("cryptoCache",
                    Eh107Configuration.fromEhcacheCacheConfiguration(configurationBuilder.withService(asynchronousListener)));
        }
        return cacheManager;
    }

    @Bean
    public CacheEventListener<Object, Object> cacheEventLogger() {
        return new CacheEventLogger();
    }

}