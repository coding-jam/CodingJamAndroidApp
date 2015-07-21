package it.cosenonjaviste.lib.mvp;

public interface LifeCycleListener<V> {
    void resume(V view);

    void pause();

    void detachView();

    void destroy();

    void saveState(ObjectSaver saver);

    void loadState(ObjectLoader loader);

    interface ObjectSaver {
        void save(String key, Object value);
    }

    interface ObjectLoader {
        <T> T load(String key);
    }
}