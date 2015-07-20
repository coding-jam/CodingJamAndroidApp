package it.cosenonjaviste.lib.mvp;

public class MvpPresenter<M, V> implements LifeCycleListener<V>, InstanceStateListener {
    public static final String MODEL = "model";

    private V view;

    private M model;

    @Override public void resume(V view) {
        this.view = view;
        resume();
    }

    @Override public void pause() {
    }

    public void resume() {
    }

    @Override public void destroy() {
    }

    @Override public void detachView() {
        this.view = null;
    }

    public M createDefaultModel() {
        return null;
    }

    @Override public void saveState(ObjectSaver saver) {
        saver.save(MODEL, getModel());
    }

    @Override public void loadState(ObjectLoader loader) {
        model = loader.load(MODEL);
        if (model == null) {
            model = createDefaultModel();
        }
    }

    public final V getView() {
        return view;
    }

    public M getModel() {
        return model;
    }
}
