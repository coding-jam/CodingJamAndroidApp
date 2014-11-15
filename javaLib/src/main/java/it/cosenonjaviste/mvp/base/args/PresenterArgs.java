package it.cosenonjaviste.mvp.base.args;

public interface PresenterArgs {
    PresenterArgs EMPTY = new PresenterArgs() {
        @Override public <T> T getObject(String key) {
            return null;
        }

        @Override public int getInt(String key) {
            return 0;
        }

        @Override public String getString(String key) {
            return null;
        }

        @Override public boolean getBoolean(String key) {
            return false;
        }

        @Override public boolean getBoolean(String key, boolean defaultValue) {
            return defaultValue;
        }

        @Override public PresenterArgs putObject(String key, Object value) {
            throw new UnsupportedOperationException();
        }

        @Override public PresenterArgs putInt(String key, int value) {
            throw new UnsupportedOperationException();
        }

        @Override public PresenterArgs putString(String key, String value) {
            throw new UnsupportedOperationException();
        }

        @Override public PresenterArgs putBoolean(String key, boolean value) {
            throw new UnsupportedOperationException();
        }
    };

    <T> T getObject(String key);

    int getInt(String key);

    String getString(String key);

    boolean getBoolean(String key);

    boolean getBoolean(String key, boolean defaultValue);

    PresenterArgs putObject(String key, Object value);

    PresenterArgs putInt(String key, int value);

    PresenterArgs putString(String key, String value);

    PresenterArgs putBoolean(String key, boolean value);
}
