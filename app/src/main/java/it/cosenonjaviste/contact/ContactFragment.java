package it.cosenonjaviste.contact;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import org.parceler.ParcelClass;

import javax.inject.Inject;

import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.databinding.ContactBinding;
import it.cosenonjaviste.lib.mvp.RxMvpFragment;
import rx.Observable;
import rx.android.view.ViewObservable;
import rx.android.widget.WidgetObservable;

@ParcelClass(ContactModel.class)
public class ContactFragment extends RxMvpFragment implements ContactView {

    @Inject ContactPresenter presenter;
    private ContactBinding binding;

    @Override protected void init() {
        CoseNonJavisteApp.createComponent(this,
                c -> DaggerContactComponent.builder().applicationComponent(c).build()
        ).inject(this);
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = ContactBinding.inflate(inflater, container, false);
        binding.setModel(presenter.getModel());
        return binding.getRoot();
    }

    @Override public Observable<Void> onSend() {
        return observeClick(binding.sendButton);
    }

    @Override public Observable<String> name() {
        return observeText(binding.name);
    }

    @Override public Observable<String> email() {
        return observeText(binding.email);
    }

    @Override public Observable<String> message() {
        return observeText(binding.message);
    }

    @NonNull private Observable<Void> observeClick(View view) {
        return ViewObservable.clicks(view).map(e -> null);
    }

    @NonNull private Observable<String> observeText(EditText editText) {
        return WidgetObservable.text(editText).map(e -> e.text().toString());
    }

    @Override public void startSending() {

    }

    @Override public void showSentMessage() {

    }

    @Override public void showSentError() {

    }
}
