package it.cosenonjaviste.post;

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
import it.cosenonjaviste.CnjFragment;
import it.cosenonjaviste.R;
import it.cosenonjaviste.mvp.post.PostDetailModel;
import it.cosenonjaviste.mvp.post.PostDetailPresenter;
import it.cosenonjaviste.mvp.post.PostDetailView;

public class PostDetailFragment extends CnjFragment<PostDetailPresenter, PostDetailModel> implements PostDetailView {

    @Inject Provider<PostDetailPresenter> presenterProvider;

    @InjectView(R.id.web_view) WebView webView;

    @InjectView(R.id.progress) ProgressBar progressBar;

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
                if (url.equalsIgnoreCase("http://www.cosenonjaviste.it/wp-content/themes/flexform/style.css")) {
                    return getCssWebResourceResponseFromAsset();
                }
                if (url.startsWith("https://pbs.twimg.com/")
                        || url.startsWith("https://cdn.syndication.twimg.com/")
                        || url.startsWith("https://syndication.twitter.com")
                        || url.contains("platform.twitter.com/")
                        || url.startsWith("http://www.facebook.com/plugins/like_box.php")
                        || url.startsWith("https://fbcdn-profile-")
                        || url.contains("sharethis.com/")
                        || url.equals("http://www.cosenonjaviste.it/wp-content/uploads/2013/06/favicon.ico")
                        ) {
                    return null;
                }
                return super.shouldInterceptRequest(view, url);
            }

            private WebResourceResponse getCssWebResourceResponseFromAsset() {
                try {
                    return getUtf8EncodedCssWebResourceResponse(getActivity().getAssets().open("style.css"));
                } catch (IOException e) {
                    return null;
                }
            }

            @TargetApi(Build.VERSION_CODES.HONEYCOMB) private WebResourceResponse getUtf8EncodedCssWebResourceResponse(InputStream data) {
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

    @Override public void update(PostDetailModel model) {
        webView.loadUrl(model.getPost().getUrl());
    }
}
