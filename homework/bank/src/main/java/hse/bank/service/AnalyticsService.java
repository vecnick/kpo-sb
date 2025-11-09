package hse.bank.service;

import hse.bank.dto.AnalyticsReport;

import java.time.LocalDate;

public interface AnalyticsService {
    AnalyticsReport calculateAnalytics(LocalDate from, LocalDate to);
}