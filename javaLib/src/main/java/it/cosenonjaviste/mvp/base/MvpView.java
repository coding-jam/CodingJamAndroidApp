package it.cosenonjaviste.mvp.base;

public interface MvpView<M> {
    void update(M model);

    <MM> void open(Class<? extends MvpView<MM>> viewClass, MM model);
}
