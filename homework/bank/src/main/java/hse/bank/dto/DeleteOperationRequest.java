package hse.bank.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class DeleteOperationRequest {
    private final Long id;
}