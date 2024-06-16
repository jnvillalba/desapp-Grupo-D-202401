package ar.edu.unq.desapp.grupod.backenddesappapi.configuration;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

import javax.cache.Caching;
import javax.cache.spi.CachingProvider;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CacheManager cacheManager() {
        return new JCacheCacheManager(ehCacheManager());
    }

    @Bean
    public javax.cache.CacheManager ehCacheManager() {
        CachingProvider provider = Caching.getCachingProvider();
        return provider.getCacheManager();
    }
}