package it.cosenonjaviste.lib.mvp;

import android.os.Bundle;

import org.parceler.Parcels;

import it.cosenonjaviste.mvp.base.PresenterArgs;

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
        return args.getInt(key);
    }

    @Override public PresenterArgs putObject(String key, Object value) {
        args.putParcelable(key, Parcels.wrap(value));
        return this;
    }

    @Override public PresenterArgs putInt(String key, int value) {
        args.putInt(key, value);
        return this;
    }

    public Bundle getBundle() {
        return args;
    }
}
