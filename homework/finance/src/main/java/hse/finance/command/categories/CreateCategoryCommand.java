package hse.finance.command.categories;

import hse.finance.command.Command;
import hse.finance.domain.CategoryType;
import hse.finance.facade.CategoryFacade;

public class CreateCategoryCommand implements Command<Void> {
    private final CategoryFacade facade;
    private final CategoryType type;
    private final String name;

    public CreateCategoryCommand(CategoryFacade facade, CategoryType type, String name) {
        this.facade = facade;
        this.type = type;
        this.name = name;
    }

    @Override
    public Void execute() {
        facade.create(type, name);
        System.out.println("Category created.");
        return null;
    }

    @Override
    public String name() { return "CreateCategory"; }
}
