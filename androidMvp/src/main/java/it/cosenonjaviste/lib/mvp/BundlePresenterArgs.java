package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;
import android.text.TextUtils;

import org.parceler.Parcels;

import it.cosenonjaviste.mvp.base.args.PresenterArgs;
import it.cosenonjaviste.mvp.base.args.PresenterArgsFactory;

public class BundlePresenterArgs implements PresenterArgs {
    private Bundle args;

    public BundlePresenterArgs() {
        this(new Bundle());
    }

    public BundlePresenterArgs(Bundle args) {
        this.args = args != null ? args : new Bundle();
    }

    @Override public <T> T getObject(String key) {
        return Parcels.unwrap(args.getParcelable(key));
    }

    @Override public int getInt(String key) {
        Object value = args.get(key);
        if (value == null) {
            return 0;
        }
        if (value instanceof Integer) {
            return (int) value;
        }
        if (value instanceof String) {
            String s = (String) value;
            if (!TextUtils.isEmpty(s)) {
                return Integer.parseInt(s);
            }
        }
        return 0;
    }

    @Override public String getString(String key) {
        return args.getString(key);
    }

    @Override public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    @Override public boolean getBoolean(String key, boolean defaultValue) {
        Object value = args.get(key);
        if (value instanceof Boolean) {
            return (boolean) value;
        }
        if (value instanceof String) {
            String s = (String) value;
            if (!TextUtils.isEmpty(s)) {
                return Boolean.parseBoolean(s);
            }
        }
        return defaultValue;
    }

    @Override public PresenterArgs putObject(String key, Object value) {
        args.putParcelable(key, Parcels.wrap(value));
        return this;
    }

    @Override public PresenterArgs putInt(String key, int value) {
        args.putInt(key, value);
        return this;
    }

    @Override public PresenterArgs putString(String key, String value) {
        args.putString(key, value);
        return this;
    }

    @Override public PresenterArgs putBoolean(String key, boolean value) {
        args.putBoolean(key, value);
        return this;
    }

    public Bundle getBundle() {
        return args;
    }

    public static class Factory extends PresenterArgsFactory {
        @Override public PresenterArgs create() {
            return new BundlePresenterArgs();
        }
    }
}