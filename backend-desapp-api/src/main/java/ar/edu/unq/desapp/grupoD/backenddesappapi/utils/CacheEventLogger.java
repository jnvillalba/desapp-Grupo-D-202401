package ar.edu.unq.desapp.grupod.backenddesappapi.utils;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

@Slf4j
public class CacheEventLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent( CacheEvent<?, ?> cacheEvent) {
        String oldValueString = cacheEvent.getOldValue() != null ? cacheEvent.getOldValue().toString() : null;
        String newValueString = cacheEvent.getNewValue() != null ? cacheEvent.getNewValue().toString() : null;
        log.info(">>> UpdateCache | KEY: " + cacheEvent.getKey() + " | OLDVALUE: " + oldValueString + " | NEWVALUE: " +newValueString);
    }
}