package hse.bank.io.importing;

import hse.bank.domain.*;
import hse.bank.repository.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class JsonDataImporter extends AbstractDataImporter {
    private final Gson gson = new Gson();

    public JsonDataImporter(BankAccountRepository bankAccountRepository,
                            CategoryRepository categoryRepository,
                            OperationRepository operationRepository) {
        super(bankAccountRepository, categoryRepository, operationRepository);
    }

    @Override
    protected ParsedData parseContent(String content) {
        JsonObject root = gson.fromJson(content, JsonObject.class);

        Type accountListType = new TypeToken<List<BankAccount>>() {}.getType();
        Type categoryListType = new TypeToken<List<Category>>() {}.getType();
        Type operationListType = new TypeToken<List<Operation>>() {}.getType();

        List<BankAccount> accounts = gson.fromJson(root.get("accounts"), accountListType);
        List<Category> categories = gson.fromJson(root.get("categories"), categoryListType);
        List<Operation> operations = gson.fromJson(root.get("operations"), operationListType);

        return new ParsedData(accounts, categories, operations);
    }
}