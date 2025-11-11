package hse_bank.finance.commands;

public interface Command {
    void execute();
    void undo();
}