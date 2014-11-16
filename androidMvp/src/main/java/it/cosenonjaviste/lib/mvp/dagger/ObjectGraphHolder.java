package it.cosenonjaviste.lib.mvp.dagger;

public class ObjectGraphHolder<T> {

    private T objectGraph;

    private ObjectGraphCreator objectGraphCreator;

    private OnCreateListener<T> onCreateListener;

    public T getObjectGraph(DaggerApplication application) {
        if (objectGraph == null) {
            objectGraph = (T) objectGraphCreator.create(application);
            if (onCreateListener != null) {
                onCreateListener.onCreate(objectGraph);
            }
        }
        return objectGraph;
    }

    public void setObjectGraphCreator(ObjectGraphCreator objectGraphCreator) {
        if (objectGraphCreator == null) {
            this.objectGraphCreator = objectGraphCreator;
        }
    }

    public static void forceObjectGraphCreator(ObjectGraphCreator objectGraphCreator) {
//        ObjectGraphHolder.objectGraphCreator = objectGraphCreator;
//        objectGraph = null;
    }

    public static void setOnCreateListener(OnCreateListener onCreateListener) {
//        ObjectGraphHolder.onCreateListener = onCreateListener;
    }

    public interface OnCreateListener<T> {
        void onCreate(T objectGraph);
    }

    public static void inject(DaggerApplication app, Object obj) {
//        ObjectGraphHolder.getObjectGraph(app).inject(obj);
    }
}