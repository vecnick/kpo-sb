package hse.bank.dto;

import hse.bank.enums.OperationType;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class CreateCategoryRequest {
    private final String name;
    private final OperationType type;
}