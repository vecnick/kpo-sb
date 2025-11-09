package hse.bank.repository;

import hse.bank.domain.Operation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface OperationRepository {
    Operation save(Operation operation);
    Optional<Operation> findById(Long id);
    List<Operation> findAll();
    List<Operation> findByDateBetween(LocalDate from, LocalDate to);
    void deleteById(Long id);
    void clear();
}