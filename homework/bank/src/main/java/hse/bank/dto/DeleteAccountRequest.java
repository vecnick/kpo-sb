package hse.bank.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class DeleteAccountRequest {
    private final Long id;
}