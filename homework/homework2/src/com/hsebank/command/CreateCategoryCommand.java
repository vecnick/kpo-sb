package com.hsebank.command;

import com.hsebank.domain.Category;
import com.hsebank.service.CategoryService;

public final class CreateCategoryCommand implements Command {
    private final CategoryService svc;
    private final Category.Type type;
    private final String name;

    public CreateCategoryCommand(CategoryService svc, Category.Type type, String name) {
        this.svc = svc;
        this.type = type;
        this.name = name;
    }

    public void execute() {
        svc.create(type, name);
    }
}