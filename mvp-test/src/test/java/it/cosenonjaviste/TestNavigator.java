package it.cosenonjaviste;

public class TestNavigator {

}
//        implements Navigator {
//
//    private Object lastModel;
//
//    private RxMvpPresenter<?> lastPresenter;
//
//    private String lastOpenedUrl;
//
//    @Inject
//    public TestNavigator() {
//    }
//
//    @Override public void show(ContextBinder contextBinder, Class<? extends RxMvpPresenter<?>> presenterClass, Action1<PresenterArgs> argsAction) {
//
//        lastPresenter = createPresenter(presenterClass);
//        lastModel = PresenterTestUtils.init(lastPresenter, getArgs(argsAction), null);
//    }
//
//    @Override public <T> T createFragment(Class<? extends RxMvpPresenter<?>> presenterClass, Action1<PresenterArgs> argsAction) {
//        show(null, presenterClass, argsAction);
//        return null;
//    }
//
//    @Override public void open(String url) {
//        lastOpenedUrl = url;
//    }
//
//    private MapPresenterArgs getArgs(Action1<PresenterArgs> argsAction) {
//        MapPresenterArgs args = new MapPresenterArgs();
//        if (argsAction != null) {
//            argsAction.call(args);
//        }
//        return args;
//    }
//
//    private RxMvpPresenter<?> createPresenter(Class<? extends RxMvpPresenter<?>> presenterClass) {
//        RxMvpPresenter<?> presenter;
//        try {
//            presenter = presenterClass.newInstance();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        return presenter;
//    }
//
//    public <M> M getLastModel() {
//        return (M) lastModel;
//    }
//
//    public RxMvpPresenter<?> getLastPresenter() {
//        return lastPresenter;
//    }
//
//    public String getLastOpenedUrl() {
//        return lastOpenedUrl;
//    }
//}
