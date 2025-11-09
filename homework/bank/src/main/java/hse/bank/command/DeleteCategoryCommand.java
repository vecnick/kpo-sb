package hse.bank.command;

import hse.bank.dto.DeleteCategoryRequest;
import hse.bank.facade.FinanceFacade;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteCategoryCommand implements Command {
    private final FinanceFacade financeFacade;
    private final DeleteCategoryRequest request;

    @Override
    public void execute() {
        financeFacade.deleteCategory(request);
        System.out.println("Category deleted successfully: " + request.getId());
    }
}