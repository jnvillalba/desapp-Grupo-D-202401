package ar.edu.unq.desapp.grupod.backenddesappapi.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;

public class CacheEventLogger
        implements CacheEventListener<Object, Object> {

    protected final Log logger = LogFactory.getLog(getClass());


    @Override
    public void onEvent(
            CacheEvent<? extends Object, ? extends Object> cacheEvent) {
        logger.info(">>> UpdateCache | KEY: " + cacheEvent.getKey() + " | OLDVALUE: " + cacheEvent.getOldValue() + " | NEWVALUE: " +cacheEvent.getNewValue());
    }
}