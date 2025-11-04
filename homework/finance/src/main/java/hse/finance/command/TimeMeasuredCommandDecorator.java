package hse.finance.command;

public class TimeMeasuredCommandDecorator<T> implements Command<T> {
    private final Command<T> delegate;

    public TimeMeasuredCommandDecorator(Command<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public T execute() {
        long start = System.nanoTime();
        try {
            return delegate.execute();
        } finally {
            long end = System.nanoTime();
            long ms = (end - start) / 1_000_000;
            System.out.println("[TIME] " + delegate.name() + " took " + ms + " ms");
        }
    }

    @Override
    public String name() {
        return delegate.name();
    }
}

