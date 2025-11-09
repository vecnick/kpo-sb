package hse.bank.io.export;

import hse.bank.domain.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class JsonExportVisitor implements DataVisitor {
    private final JsonObject root = new JsonObject();
    private final JsonArray accounts = new JsonArray();
    private final JsonArray categories = new JsonArray();
    private final JsonArray operations = new JsonArray();
    private final Gson gson;

    public JsonExportVisitor() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        root.add("accounts", accounts);
        root.add("categories", categories);
        root.add("operations", operations);
    }

    @Override
    public void visit(BankAccount account) {

        accounts.add(gson.toJsonTree(account));
    }

    @Override
    public void visit(Category category) {
        categories.add(gson.toJsonTree(category));
    }

    @Override
    public void visit(Operation operation) {
        operations.add(gson.toJsonTree(operation));
    }

    @Override
    public String getResult() {
        return gson.toJson(root);
    }
}