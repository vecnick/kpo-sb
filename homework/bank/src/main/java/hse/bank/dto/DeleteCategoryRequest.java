package hse.bank.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class DeleteCategoryRequest {
    private final Long id;
}