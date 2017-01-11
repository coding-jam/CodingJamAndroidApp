package it.cosenonjaviste.ui.page;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Provider;

import it.codingjam.lifecyclebinder.LifeCycleBinder;
import it.codingjam.lifecyclebinder.RetainedObjectProvider;
import it.cosenonjaviste.R;
import it.cosenonjaviste.core.page.PageViewModel;
import it.cosenonjaviste.databinding.PostDetailBinding;
import it.cosenonjaviste.ui.CoseNonJavisteApp;

public class PageFragment extends Fragment {

    @RetainedObjectProvider("viewModel") @Inject Provider<PageViewModel> provider;

    PageViewModel viewModel;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        CoseNonJavisteApp.getComponent(this).inject(this);
        LifeCycleBinder.bind(this);
    }

    @SuppressLint("SetJavaScriptEnabled") @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PostDetailBinding binding = PostDetailBinding.bind(inflater.inflate(R.layout.post_detail, container, false));
        binding.setViewModel(viewModel);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(binding.toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WebSettings settings = binding.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        File externalFilesDir = getActivity().getExternalFilesDir(null);
        if (externalFilesDir != null) {
            settings.setAppCachePath(externalFilesDir.getAbsolutePath());
            settings.setAppCacheMaxSize(20 * 1024 * 1024);
            settings.setAppCacheEnabled(true);
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }

        binding.collapsingToolbar.setTitle("");

        binding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                webView.loadUrl(url);
                return true;
            }

            @Override public WebResourceResponse shouldInterceptRequest(WebView view11, String url) {
                if (url.contains("codingjam.it/wp-content/themes/bliss/style.css?ver=4.4.2")) {
                    return getCssWebResourceResponseFromAsset();
                }
                if (url.startsWith("https://pbs.twimg.com/")
                        || url.startsWith("https://cdn.syndication.twimg.com/")
                        || url.startsWith("https://syndication.twitter.com")
                        || url.contains("platform.twitter.com/")
                        || url.startsWith("http://www.facebook.com/plugins/like_box.php")
                        || url.startsWith("https://fbcdn-profile-")
                        || url.contains("sharethis.com/")
                        || url.contains("disquscdn.com/")
                        || url.contains("sharethis-gtm-pixel")
                        || url.contains("facebook.com/")
                        || url.contains("fbcdn.net/")
                        || url.contains("/contact-form-7/")
                        || url.contains("/catablog/")
                        || url.contains("/advanced-random-posts-widget/")
                        || url.contains("/yet-another-related-posts-plugin/")
                        || url.contains("/wp-social-seo-booster/")
                        || url.contains("/extended-categories-widget/")
                        || url.contains("google-analytics.com/")
                        || url.contains("cf_action=sync_comments")
                        || url.startsWith("http://www.cosenonjaviste.it/wp-includes/js/comment-reply.min.js")
                        || url.startsWith("https://fbstatic-a.akamaihd.net")
                        || url.startsWith("https://glitter.services.disqus.com")
                        || url.startsWith("http://connect.facebook.net")
                        || url.startsWith("http://disqus.com/")
                        || url.startsWith("https://referrer.disqus.com")
                        || url.startsWith("http://cosenonjaviste.disqus.com/")
                        || url.equals("http://www.cosenonjaviste.it/wp-content/uploads/2013/06/favicon.ico")
                        ) {
                    return new WebResourceResponse("", "", new ByteArrayInputStream(new byte[0]));
                }
                return super.shouldInterceptRequest(view11, url);
            }

            private WebResourceResponse getCssWebResourceResponseFromAsset() {
                try {
                    return new WebResourceResponse("text/css", "UTF-8", getActivity().getAssets().open("style.css"));
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            public void onPageFinished(WebView webView, String url) {
                super.onPageFinished(webView, url);
                viewModel.htmlLoaded();
            }
        });
        return binding.getRoot();
    }
}
