package hse_bank.finance.config;

import hse_bank.finance.facade.*;
import hse_bank.finance.factories.DomainFactory;
import hse_bank.finance.repository.InMemoryRepository;
import hse_bank.finance.repository.Repository;
import hse_bank.finance.domain.*;

public class AppConfig {
    private final Repository<BankAccount> bankAccountRepository = new InMemoryRepository<>();
    private final Repository<Category> categoryRepository = new InMemoryRepository<>();
    private final Repository<Operation> operationRepository = new InMemoryRepository<>();
    private final DomainFactory domainFactory = new DomainFactory();

    public DomainFactory domainFactory() {
        return domainFactory;
    }

    public Repository<BankAccount> bankAccountRepository() {
        return bankAccountRepository;
    }

    public Repository<Category> categoryRepository() {
        return categoryRepository;
    }

    public Repository<Operation> operationRepository() {
        return operationRepository;
    }

    public BankAccountFacade bankAccountFacade() {
        return new BankAccountFacade(bankAccountRepository(), domainFactory());
    }

    public CategoryFacade categoryFacade() {
        return new CategoryFacade(categoryRepository(), domainFactory());
    }

    public OperationFacade operationFacade() {
        return new OperationFacade(operationRepository(), domainFactory(), bankAccountFacade());
    }

    public AnalyticsFacade analyticsFacade() {
        return new AnalyticsFacade(operationFacade());
    }
}