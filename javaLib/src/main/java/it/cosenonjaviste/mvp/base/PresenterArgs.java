package it.cosenonjaviste.mvp.base;

public interface PresenterArgs {

    PresenterArgs EMPTY = new PresenterArgs() {
        @Override public <T> T getObject(String key) {
            return null;
        }

        @Override public int getInt(String key) {
            return 0;
        }

        @Override public PresenterArgs putObject(String key, Object value) {
            throw new UnsupportedOperationException();
        }

        @Override public PresenterArgs putInt(String key, int value) {
            throw new UnsupportedOperationException();
        }
    };

    <T> T getObject(String key);

    int getInt(String key);

    PresenterArgs putObject(String key, Object value);

    PresenterArgs putInt(String key, int value);
}
