package it.cosenonjaviste.mvp.base;

import java.util.HashMap;

public class MapPresenterArgs implements PresenterArgs {
    private HashMap<String, Object> map = new HashMap<>();

    public MapPresenterArgs put(String key, Object value) {
        map.put(key, value);
        return this;
    }

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

    @Override public PresenterArgs putObject(String key, Object value) {
        map.put(key, value);
        return this;
    }

    @Override public PresenterArgs putInt(String key, int value) {
        map.put(key, value);
        return this;
    }
}
