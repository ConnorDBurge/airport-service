package com.foreflight.config.db;

import javax.sql.DataSource;
import java.sql.SQLException;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DatabaseLogger {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseLogger.class);

    private final DataSource dataSource;

    @EventListener(ApplicationReadyEvent.class)
    public void logDatabaseUrl() {
        try {
            String url = dataSource.getConnection().getMetaData().getURL();
            logger.info("Connected to database: {}", url);
        } catch (SQLException e) {
            logger.error("Could not log database URL", e);
        }
    }
}
