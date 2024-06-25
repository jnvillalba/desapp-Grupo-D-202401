package ar.edu.unq.desapp.grupod.backenddesappapi.utils;

import lombok.extern.slf4j.Slf4j;
import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CacheEventLogger implements CacheEventListener<Object, Object> {

    @Override
    public void onEvent(CacheEvent<?, ?> cacheEvent) {
        String oldValueString = cacheEvent.getOldValue() != null ? cacheEvent.getOldValue().toString() : null;
        String newValueString = cacheEvent.getNewValue() != null ? cacheEvent.getNewValue().toString() : null;
        var cacheEventKey = cacheEvent.getKey();
        String magenta = "\033[0;35m";
        String blue = "\033[0;34m";
        String resetColor = "\033[0m";
        String red = "\033[0;31m";
        String green = "\033[0;32m";

        String logMessage = magenta + ">>> UpdateCache" + resetColor + "\n" +
                "KEY: " + blue + cacheEventKey + resetColor + "\n" +
                "OLDVALUE: " + red + oldValueString + resetColor + "\n" +
                "NEWVALUE: " + green + newValueString + resetColor;

        // Imprimir el mensaje de log
        log.info(logMessage);
    }
}