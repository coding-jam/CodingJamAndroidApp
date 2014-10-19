package it.cosenonjaviste;

import android.app.Activity;

import org.parceler.ParcelClass;
import org.parceler.ParcelClasses;

import it.cosenonjaviste.mvp.PostListModel;
import it.cosenonjaviste.testableandroidapps.model.Author;
import it.cosenonjaviste.testableandroidapps.model.Post;

@ParcelClasses({@ParcelClass(Post.class), @ParcelClass(Author.class), @ParcelClass(PostListModel.class)})
public class Dashboard extends Activity {

//    @InjectView(R.id.list) ListView listView;
//
//    @InjectView(R.id.progress) View progress;
//
//    @InjectView(R.id.reload) View reload;
//
//    @Inject Provider<PostListPresenter> presenterProvider;
//
//    @Inject ShareHelper shareHelper;
//
//    private PostAdapter postAdapter;
//
//    private CompositeSubscription subscriptions = new CompositeSubscription();
//
//    @Override protected void onCreate(Bundle savedInstanceState) {
//        ObjectGraph appObjectGraph = ObjectGraphHolder.getObjectGraph(getApplication());
//        ObjectGraph activityObjectGraph = appObjectGraph.plus(new ActivityModule(this));
//        activityObjectGraph.inject(this);
//
//        super.onCreate(savedInstanceState);
//
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        postAdapter = new PostAdapter(this);
//
//        listView.setAdapter(postAdapter);
//    }
//
//    @Override protected void subscribeToModelUpdates(Observable<ModelEvent<PostListModel>> updates) {
//        Observable<ModelEvent<PostListModel>> mainEvents = updates.filter(ModelEvent::isExtraEmpty);
//        subscriptions.add(
//                mainEvents
//                        .ofType(StartLoadingModelEvent.class)
//                        .subscribe(e -> showProgress())
//        );
//        subscriptions.add(
//                mainEvents
//                        .ofType(EndModelEvent.class)
//                        .map(EndModelEvent::getModel)
//                        .cast(PostListModel.class)
//                        .subscribe(this::updateView)
//        );
//
//        subscriptions.add(
//                updates
//                        .ofType(ErrorModelEvent.class)
//                        .map(ErrorModelEvent::getThrowable)
//                        .subscribe(t -> Toast.makeText(Dashboard.this, t.getMessage(), Toast.LENGTH_LONG).show())
//        );
//    }
//
//    @Override public void onStop() {
//        super.onStop();
//        subscriptions.unsubscribe();
//        subscriptions = new CompositeSubscription();
//    }
//
//    @Override protected Navigator getNavigator() {
//        return null;
//    }
//
//    @OnItemClick(R.id.list) void shareItem(int position) {
////        Repo repo = repoAdapter.getItem(position);
////        presenter.toggleStar(repo);
////        shareHelper.share(repo.getName(), repo.getName() + " " + repo.getUrl());
//    }
//
//    @Override protected int getLayoutId() {
//        return R.layout.activity_main;
//    }
//
//    @Override protected PostListPresenter createPresenter() {
//        return presenterProvider.get();
//    }
//
//    public void showProgress() {
//        progress.setVisibility(View.VISIBLE);
//        reload.setVisibility(View.GONE);
//        listView.setVisibility(View.GONE);
//    }
//
//    public void updateView(PostListModel model) {
//        if (model.isReloadVisible()) {
//            reload.setVisibility(View.VISIBLE);
//            progress.setVisibility(View.GONE);
//        } else {
//            listView.setVisibility(View.VISIBLE);
//            progress.setVisibility(View.GONE);
//            reload.setVisibility(View.GONE);
//        }
//        postAdapter.reloadData(model.getPosts());
//    }
//
//    @OnClick({R.id.reload}) void executeSearch() {
//        presenter.listPosts();
//    }
//
//    @Module(injects = Dashboard.class, addsTo = AppModule.class)
//    public static class ActivityModule {
//        private FragmentActivity activity;
//
//        public ActivityModule(FragmentActivity activity) {
//            this.activity = activity;
//        }
//
//        @Provides @Singleton public FragmentActivity provideActivity() {
//            return activity;
//        }
//
//        @Provides @Singleton PostListPresenter providePresenter(RepoService repoService) {
//            return new PostListPresenter(repoService);
//        }
//    }
}
