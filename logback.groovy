logger('jxbrowser-troubleshooting', TRACE)

appender('CONSOLE', ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{HH:mm:ss.SSS} %thread [%level] %logger - %msg%n"
    }
}

root(INFO, ['CONSOLE'])
