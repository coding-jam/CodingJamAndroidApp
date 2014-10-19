package it.cosenonjaviste;

import android.app.Application;

import dagger.ObjectGraph;
import it.cosenonjaviste.base.ObjectGraphHolder;

public class CoseNonJavisteApp extends Application {

    @Override public void onCreate() {
        super.onCreate();
        ObjectGraphHolder.setObjectGraphCreator(app -> ObjectGraph.create(new AppModule(app)));
    }
}
