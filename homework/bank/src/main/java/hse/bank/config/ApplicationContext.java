package hse.bank.config;

import hse.bank.command.*;
import hse.bank.dto.*;
import hse.bank.facade.FinanceFacade;
import hse.bank.factories.DomainFactory;
import hse.bank.io.export.JsonExportVisitor;
import hse.bank.io.importing.AbstractDataImporter;
import hse.bank.io.importing.JsonDataImporter;
import hse.bank.repository.*;
import hse.bank.repository.impl.*;
import hse.bank.service.*;
import hse.bank.service.impl.*;

import java.time.LocalDate;

public class ApplicationContext {

    private final BankAccountRepository bankAccountRepository;
    private final CategoryRepository categoryRepository;
    private final OperationRepository operationRepository;

    private final DomainFactory domainFactory;

    private final FinanceFacade financeFacade;

    public ApplicationContext() {
        this.bankAccountRepository = new BankAccountRepositoryImpl();
        this.categoryRepository = new CategoryRepositoryImpl();
        this.operationRepository = new OperationRepositoryImpl();

        AccountService accountService = new AccountServiceImpl(bankAccountRepository);
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
        OperationService operationService = new OperationServiceImpl(operationRepository, accountService, categoryService);
        AnalyticsService analyticsService = new AnalyticsServiceImpl(operationRepository);

        this.domainFactory = new DomainFactory();

        this.financeFacade = new FinanceFacade(
                accountService,
                categoryService,
                operationService,
                analyticsService
        );
    }

    public Command getCreateAccountCommand(CreateAccountRequest request) {
        return new CreateAccountCommand(financeFacade, request);
    }

    public Command getDeleteAccountCommand(DeleteAccountRequest request) {
        return new DeleteAccountCommand(financeFacade, request);
    }

    public Command getCreateCategoryCommand(CreateCategoryRequest request) {
        return new CreateCategoryCommand(financeFacade, request);
    }

    public Command getDeleteCategoryCommand(DeleteCategoryRequest request) {
        return new DeleteCategoryCommand(financeFacade, request);
    }

    public Command getAddOperationCommand(CreateOperationRequest request) {
        return new AddOperationCommand(financeFacade, domainFactory, request);
    }

    public Command getDeleteOperationCommand(DeleteOperationRequest request) {
        return new DeleteOperationCommand(financeFacade, request);
    }

    public Command getAnalyticsCommand(LocalDate from, LocalDate to) {
        return new GetAnalyticsCommand(financeFacade, from, to);
    }

    public Command getExportDataCommand(String filePath) {
        return new ExportDataCommand(
                bankAccountRepository,
                categoryRepository,
                operationRepository,
                new JsonExportVisitor(),
                filePath
        );
    }

    public Command getImportDataCommand(String filePath) {
        AbstractDataImporter importer = new JsonDataImporter(
                bankAccountRepository,
                categoryRepository,
                operationRepository
        );
        return new ImportDataCommand(importer, filePath);
    }
}