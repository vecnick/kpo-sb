package com.hsebank.command;


public final class TimedCommandDecorator implements Command {
    private final Command inner;
    private final com.hsebank.metrics.MetricsCollector collector;


    public TimedCommandDecorator(Command inner, com.hsebank.metrics.MetricsCollector collector) {
        this.inner = inner;
        this.collector = collector;
    }


    public void execute() {
        long start = System.nanoTime();
        inner.execute();
        long end = System.nanoTime();
        collector.record(end - start);
    }
}