package com.hsebank.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class MetricsCollector {
    private final List<Long> list = new ArrayList<>();
    public void record(long nanos) { list.add(nanos); }
    public List<Long> getAll() { return Collections.unmodifiableList(list); }
}