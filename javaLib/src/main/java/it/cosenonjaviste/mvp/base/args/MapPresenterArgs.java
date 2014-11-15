package it.cosenonjaviste.mvp.base.args;

import java.util.HashMap;

public class MapPresenterArgs implements PresenterArgs {
    private HashMap<String, Object> map = new HashMap<>();

    @Override public <T> T getObject(String key) {
        return (T) map.get(key);
    }

    @Override public int getInt(String key) {
        Integer integer = (Integer) map.get(key);
        if (integer != null) {
            return integer;
        } else {
            return 0;
        }
    }

    @Override public String getString(String key) {
        return (String) map.get(key);
    }

    @Override public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    @Override public boolean getBoolean(String key, boolean defaultValue) {
        Object v = map.get(key);
        if (v == null) {
            return defaultValue;
        }
        return (Boolean) v;
    }

    @Override public PresenterArgs putObject(String key, Object value) {
        map.put(key, value);
        return this;
    }

    @Override public PresenterArgs putInt(String key, int value) {
        map.put(key, value);
        return this;
    }

    @Override public PresenterArgs putString(String key, String value) {
        map.put(key, value);
        return this;
    }

    @Override public PresenterArgs putBoolean(String key, boolean value) {
        map.put(key, value);
        return this;
    }
}
