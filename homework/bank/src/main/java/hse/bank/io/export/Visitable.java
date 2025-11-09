package hse.bank.io.export;

public interface Visitable {
    void accept(DataVisitor visitor);
}