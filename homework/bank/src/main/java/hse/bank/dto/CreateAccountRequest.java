package hse.bank.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class CreateAccountRequest {
    private final String name;
}