package hse.finance.export;

public interface Visitable {
    void accept(ExportVisitor visitor);
}

