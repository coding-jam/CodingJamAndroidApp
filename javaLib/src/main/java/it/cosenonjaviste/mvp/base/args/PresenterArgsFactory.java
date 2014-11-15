package it.cosenonjaviste.mvp.base.args;

public abstract class PresenterArgsFactory {
    public abstract PresenterArgs create();

    public <T> PresenterArgs create(ArgsPopulator<T> argsPopulator, T param) {
        PresenterArgs args = create();
        return argsPopulator.populate(args, param);
    }

    public interface ArgsPopulator<T> {
        PresenterArgs populate(PresenterArgs args, T param);
    }
}