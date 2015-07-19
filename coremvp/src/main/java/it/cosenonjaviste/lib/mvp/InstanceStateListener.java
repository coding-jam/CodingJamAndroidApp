package it.cosenonjaviste.lib.mvp;

public interface InstanceStateListener {
    void saveState(ObjectSaver saver);

    void loadState(ObjectLoader loader);

    interface ObjectSaver {
        void save(String key, Object value);
    }

    interface ObjectLoader {
        <T> T load(String key);
    }
}