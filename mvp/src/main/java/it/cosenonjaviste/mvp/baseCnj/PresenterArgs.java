package it.cosenonjaviste.mvp.baseCnj;

public interface PresenterArgs {
    <T> T getObject(String key);

    PresenterArgs putObject(String key, Object value);
}
