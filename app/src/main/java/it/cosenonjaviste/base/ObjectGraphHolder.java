package it.cosenonjaviste.base;

import dagger.ObjectGraph;

public class ObjectGraphHolder {
    private static ObjectGraph objectGraph;

    private static ObjectGraphCreator objectGraphCreator;

    private static OnCreateListener onCreateListener;

    public static ObjectGraph getObjectGraph(DaggerApplication application) {
        if (objectGraph == null) {
            objectGraph = objectGraphCreator.create(application);
            if (onCreateListener != null) {
                onCreateListener.onCreate(objectGraph);
            }
        }
        return objectGraph;
    }

    public static void setObjectGraphCreator(ObjectGraphCreator objectGraphCreator) {
        if (ObjectGraphHolder.objectGraphCreator == null) {
            ObjectGraphHolder.objectGraphCreator = objectGraphCreator;
        }
    }

    public static void forceObjectGraphCreator(ObjectGraphCreator objectGraphCreator) {
        ObjectGraphHolder.objectGraphCreator = objectGraphCreator;
        objectGraph = null;
    }

    public static void setOnCreateListener(OnCreateListener onCreateListener) {
        ObjectGraphHolder.onCreateListener = onCreateListener;
    }

    public interface OnCreateListener {
        void onCreate(ObjectGraph objectGraph);
    }

    public static void inject(DaggerApplication app, Object obj) {
        ObjectGraphHolder.getObjectGraph(app).inject(obj);
    }
}