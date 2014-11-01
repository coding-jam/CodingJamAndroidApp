package it.cosenonjaviste.mvp.base;

public abstract class MvpConfigFactory {

    public MvpConfig<?, ?, ?> createConfig(String configClass) {
        return createConfig(getType(configClass));
    }

    private Class<MvpConfig<?, ?, ?>> getType(String className) {
        try {
            return (Class<MvpConfig<?, ?, ?>>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public abstract MvpConfig<?, ?, ?> createConfig(Class<? extends MvpConfig<?, ?, ?>> type);

}
