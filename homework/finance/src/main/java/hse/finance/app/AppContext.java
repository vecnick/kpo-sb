package hse.finance.app;

import hse.finance.domain.Factory;
import hse.finance.facade.AnalyticsFacade;
import hse.finance.facade.BankAccountFacade;
import hse.finance.facade.CategoryFacade;
import hse.finance.facade.OperationFacade;
import hse.finance.repository.BankAccountRepository;
import hse.finance.repository.CategoryRepository;
import hse.finance.repository.OperationRepository;
import hse.finance.repository.inmemory.InMemoryBankAccountRepository;
import hse.finance.repository.inmemory.InMemoryCategoryRepository;
import hse.finance.repository.inmemory.InMemoryOperationRepository;
import hse.finance.repository.proxy.CachingBankAccountRepository;
import hse.finance.repository.proxy.CachingCategoryRepository;
import hse.finance.repository.proxy.CachingOperationRepository;
import hse.finance.service.BalanceService;
import hse.finance.service.ExportService;
import hse.finance.service.ImportService;

public class AppContext {
    private final Factory factory = new Factory();

    private final BankAccountRepository bankAccountRepository;
    private final CategoryRepository categoryRepository;
    private final OperationRepository operationRepository;

    private final BankAccountFacade bankAccountFacade;
    private final CategoryFacade categoryFacade;
    private final OperationFacade operationFacade;
    private final AnalyticsFacade analyticsFacade;

    private final BalanceService balanceService;
    private final ExportService exportService;
    private final ImportService importService;

    public AppContext() {
        // underlying storage
        var baseAccRepo = new InMemoryBankAccountRepository();
        var baseCatRepo = new InMemoryCategoryRepository();
        var baseOpRepo = new InMemoryOperationRepository();
        // proxies (cache layer)
        this.bankAccountRepository = new CachingBankAccountRepository(baseAccRepo);
        this.categoryRepository = new CachingCategoryRepository(baseCatRepo);
        this.operationRepository = new CachingOperationRepository(baseOpRepo);

        // facades
        this.bankAccountFacade = new BankAccountFacade(bankAccountRepository, factory);
        this.categoryFacade = new CategoryFacade(categoryRepository, factory);
        this.operationFacade = new OperationFacade(operationRepository, bankAccountRepository, categoryRepository, factory);
        this.analyticsFacade = new AnalyticsFacade(operationRepository, categoryRepository);

        // services
        this.balanceService = new BalanceService(bankAccountRepository, operationRepository);
        this.exportService = new ExportService(bankAccountRepository, categoryRepository, operationRepository);
        this.importService = new ImportService(bankAccountRepository, categoryRepository, operationRepository, factory);
    }

    public Factory factory() {
        return factory;
    }

    public BankAccountFacade bankAccounts() {
        return bankAccountFacade;
    }

    public CategoryFacade categories() {
        return categoryFacade;
    }

    public OperationFacade operations() {
        return operationFacade;
    }

    public AnalyticsFacade analytics() {
        return analyticsFacade;
    }

    public BalanceService balance() {
        return balanceService;
    }

    public ExportService exportService() {
        return exportService;
    }

    public ImportService importService() {
        return importService;
    }
}

