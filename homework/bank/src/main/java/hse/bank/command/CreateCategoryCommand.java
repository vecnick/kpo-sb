package hse.bank.command;

import hse.bank.domain.Category;
import hse.bank.dto.CreateCategoryRequest;
import hse.bank.facade.FinanceFacade;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CreateCategoryCommand implements Command {
    private final FinanceFacade financeFacade;
    private final CreateCategoryRequest request;

    @Override
    public void execute() {
        Category category = financeFacade.createCategory(request);
        System.out.println("Category created successfully: " + category);
    }
}