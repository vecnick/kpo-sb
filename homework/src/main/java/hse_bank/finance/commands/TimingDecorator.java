package hse_bank.finance.commands;

public class TimingDecorator implements Command {
    private final Command command;

    public TimingDecorator(Command command) {
        this.command = command;
    }

    @Override
    public void execute() {
        long startTime = System.currentTimeMillis();
        command.execute();
        long endTime = System.currentTimeMillis();
        System.out.println("Command executed in: " + (endTime - startTime) + "ms");
    }

    @Override
    public void undo() {
        command.undo();
    }
}