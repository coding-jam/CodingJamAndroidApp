package it.cosenonjaviste;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Provider;

import butterknife.InjectView;
import it.cosenonjaviste.mvp.PostDetailModel;
import it.cosenonjaviste.mvp.PostDetailPresenter;
import it.cosenonjaviste.mvp.base.events.ModelEvent;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

public class PostDetailFragment extends CnjFragment<PostDetailPresenter, PostDetailModel> {

    @Inject Provider<PostDetailPresenter> presenterProvider;

    @InjectView(R.id.web_view) WebView webView;

    @InjectView(R.id.progress) ProgressBar progressBar;

    @Override protected void subscribeToModelUpdates(Observable<ModelEvent<PostDetailModel>> modelUpdates, CompositeSubscription subscription) {
        subscription.add(
                modelUpdates
                        .map(ModelEvent::getModel)
                        .subscribe(model -> {
                            webView.loadUrl(model.getPost().getUrl());
//                            textView.setText(model.getPost().getTitle());
                        })
        );
    }

    @Override protected PostDetailPresenter createPresenter() {
        return presenterProvider.get();
    }

    @Override protected void initView(View view) {
        super.initView(view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB) @Override public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//                http://www.cosenonjaviste.it/wp-content/themes/flexform/style.css
                if (url.equalsIgnoreCase("http://www.cosenonjaviste.it/wp-content/themes/flexform/style.css")) {
                    return getCssWebResourceResponseFromAsset();
                }
                System.out.println(url);
                return super.shouldInterceptRequest(view, url);
            }

            private WebResourceResponse getCssWebResourceResponseFromAsset() {
                try {
                    return getUtf8EncodedCssWebResourceResponse(getActivity().getAssets().open("style.css"));
                } catch (IOException e) {
                    return null;
                }
            }

            private WebResourceResponse getUtf8EncodedCssWebResourceResponse(InputStream data) {
                return new WebResourceResponse("text/css", "UTF-8", data);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override protected int getLayoutId() {
        return R.layout.post_detail;
    }
}
