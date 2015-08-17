package it.cosenonjaviste.lib.mvp;

public interface ObjectLoader {
    <T> T load(String key);
}
