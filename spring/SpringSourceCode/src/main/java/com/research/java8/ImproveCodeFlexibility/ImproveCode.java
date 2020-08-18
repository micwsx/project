package com.research.java8.ImproveCodeFlexibility;

import java.util.function.Supplier;

public class ImproveCode {

    public static void main(String[] args) {
        Logger logger = new Logger();
        if (logger.getLogLevel() == LogLevel.FINAL) {
            logger.log("Problem: <Here>");
        }
        logger.log(LogLevel.FINAL, "Problem");
        logger.log(LogLevel.FINAL, () -> "Problem");
    }
}

enum LogLevel {FINAL, NORMAL, ERROR}

class Logger {

    private LogLevel logLevel;

    public Logger(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public Logger() {
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void log(String message) {
        System.out.println(message);
    }

    public void log(LogLevel logLevel, String message) {
        if (this.logLevel == logLevel) {
            System.out.println(message);
        }
    }

    public void log(LogLevel logLevel, Supplier<String> supplier) {
        if (this.logLevel == logLevel) {
            System.out.println(supplier.get());
        }
    }
}
