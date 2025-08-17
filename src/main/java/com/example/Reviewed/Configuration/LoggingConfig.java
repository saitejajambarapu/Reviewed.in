package com.example.Reviewed.Configuration;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import co.elastic.logging.logback.EcsEncoder;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import co.elastic.logging.logback.EcsEncoder;

@Configuration
public class LoggingConfig {
    @Bean
    public ApplicationRunner configureLogging() {
        return args -> {
            LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

            // ========== Console appender (optional) ==========
            PatternLayoutEncoder consoleEncoder = new PatternLayoutEncoder();
            consoleEncoder.setContext(context);
            consoleEncoder.setPattern("%d{yyyy-MM-dd HH:mm:ss} %-5level [%thread] %logger{36} - %msg%n");
            consoleEncoder.start();

            ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
            consoleAppender.setContext(context);
            consoleAppender.setName("CONSOLE");
            consoleAppender.setEncoder(consoleEncoder);
            consoleAppender.start();

            // ========== Rolling JSON file appender ==========
            RollingFileAppender<ILoggingEvent> jsonFileAppender = new RollingFileAppender<>();
            jsonFileAppender.setContext(context);
            jsonFileAppender.setName("JSON_FILE");

            // Path to the latest log file
            jsonFileAppender.setFile("logs/ecs-log.json");

            // ECS JSON encoder
            EcsEncoder ecsEncoder = new EcsEncoder();
            ecsEncoder.setContext(context);
            ecsEncoder.setServiceName("my-spring-app");  // Customize this
            ecsEncoder.start();
            jsonFileAppender.setEncoder(ecsEncoder);

            // Rolling policy (daily rotation)
            TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = new TimeBasedRollingPolicy<>();
            rollingPolicy.setContext(context);
            rollingPolicy.setParent(jsonFileAppender);
            rollingPolicy.setFileNamePattern("logs/ecs-log-%d{yyyy-MM-dd}.json");  // Daily file
            rollingPolicy.setMaxHistory(30); // Keep logs for 30 days
            rollingPolicy.start();

            jsonFileAppender.setRollingPolicy(rollingPolicy);
            jsonFileAppender.start();

            // ========== Attach appenders ==========
            ch.qos.logback.classic.Logger rootLogger = context.getLogger("ROOT");
            rootLogger.detachAndStopAllAppenders();
            rootLogger.addAppender(consoleAppender);       // Optional
            rootLogger.addAppender(jsonFileAppender);      // Main JSON logger
        };
    }
}
