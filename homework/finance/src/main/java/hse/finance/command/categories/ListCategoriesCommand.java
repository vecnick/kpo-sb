package hse.finance.command.categories;

import hse.finance.command.Command;
import hse.finance.facade.CategoryFacade;

public class ListCategoriesCommand implements Command<Void> {
    private final CategoryFacade facade;

    public ListCategoriesCommand(CategoryFacade facade) { this.facade = facade; }

    @Override
    public Void execute() {
        facade.list().forEach(System.out::println);
        return null;
    }

    @Override
    public String name() { return "ListCategories"; }
}

