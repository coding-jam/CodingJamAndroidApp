package it.cosenonjaviste.lib.mvp.dagger;

import android.app.Application;


public abstract class DaggerApplication<T> extends Application {

    private static Object component;

    public static Object oldComponent;

    private static ObjectGraphCreator<?> objectGraphCreator;

    @Override public void onCreate() {
//        setObjectGraphCreator(new ObjectGraphCreator() {
//            @Override public T create(DaggerApplication app) {
//                return (T) ComponentBuilder.build(getComponentClass(), getDependencies());
//            }
//        });
        super.onCreate();
    }

    public T getComponent() {
        if (component == null) {
            component = getObjectGraphCreator().create(this);
        }
        return (T) component;
    }

    public abstract Object[] getDependencies();

    public abstract Class<T> getComponentClass();

    public static void forceObjectGraphCreator(ObjectGraphCreator<?> objectGraphCreator) {
        System.out.println("forceObjectGraphCreator");
        DaggerApplication.objectGraphCreator = objectGraphCreator;
        oldComponent = component;
        component = null;
//        if (component != null) {
//
//        }
    }

    private ObjectGraphCreator<T> getObjectGraphCreator() {
        System.out.println("getObjectGraphCreator " + objectGraphCreator);
        if (objectGraphCreator == null) {
            return new ObjectGraphCreator<T>() {
                @Override public T create(DaggerApplication app) {
                    return ComponentBuilder.build(getComponentClass(), getDependencies());
                }
            };
        }
        return (ObjectGraphCreator<T>) objectGraphCreator;
    }
}
