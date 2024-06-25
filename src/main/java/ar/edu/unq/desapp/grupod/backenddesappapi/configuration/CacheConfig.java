package ar.edu.unq.desapp.grupod.backenddesappapi.configuration;

import ar.edu.unq.desapp.grupod.backenddesappapi.model.dto.BinancePriceDTO;
import ar.edu.unq.desapp.grupod.backenddesappapi.utils.CacheEventLogger;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.event.CacheEventListener;
import org.ehcache.event.EventType;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager getCacheManager() {

        CachingProvider provider = Caching.getCachingProvider();
        CacheManager cacheManager = provider.getCacheManager();
        CacheConfigurationBuilder<String, List<BinancePriceDTO>> listConfigBuilder =
                CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                String.class, (Class<List<BinancePriceDTO>>) (Class<?>) List.class,
                                ResourcePoolsBuilder.heap(2).offheap(10, MemoryUnit.MB))
                        .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(60)));
        
        CacheEventListenerConfigurationBuilder asynchronousListener = CacheEventListenerConfigurationBuilder
                .newEventListenerConfiguration(new CacheEventLogger()
                        , EventType.CREATED, EventType.EXPIRED).unordered().asynchronous();
        if (cacheManager.getCache("cryptoCache") == null) {
            cacheManager.createCache("cryptoCache",
                    Eh107Configuration.fromEhcacheCacheConfiguration(listConfigBuilder.withService(asynchronousListener)));
        }
        return cacheManager;
    }

}