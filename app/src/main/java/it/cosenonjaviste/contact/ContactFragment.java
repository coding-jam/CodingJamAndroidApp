package it.cosenonjaviste.contact;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.ParcelClass;

import javax.inject.Inject;

import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.R;
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
        binding = DataBindingUtil.inflate(inflater, R.layout.contact, container, false);
        binding.setModel(presenter.getModel());
        return binding.getRoot();
    }

    @Override public Observable<Void> onSend() {
        return ViewObservable.clicks(binding.sendButton).map(e -> null);
    }

    @Override public Observable<String> name() {
        return WidgetObservable.text(binding.name).map(e -> e.text().toString());
    }

    @Override public Observable<String> subject() {
        return WidgetObservable.text(binding.subject).map(e -> e.text().toString());
    }

    @Override public Observable<String> email() {
        return WidgetObservable.text(binding.email).map(e -> e.text().toString());
    }

    @Override public Observable<String> message() {
        return WidgetObservable.text(binding.message).map(e -> e.text().toString());
    }
}
