package it.cosenonjaviste.contact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.parceler.ParcelClass;

import javax.inject.Inject;

import it.cosenonjaviste.CoseNonJavisteApp;
import it.cosenonjaviste.R;
import it.cosenonjaviste.databinding.ContactBinding;
import it.cosenonjaviste.lib.mvp.LifeCycleFragment;

@ParcelClass(ContactModel.class)
public class ContactFragment extends LifeCycleFragment implements ContactView {

    @Inject ContactPresenter presenter;

    @Override protected void init() {
        CoseNonJavisteApp.createComponent(this,
                c -> DaggerContactComponent.builder().applicationComponent(c).build()
        ).inject(this);
        addListener(presenter);
        addInstanceStateListener(presenter);
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContactBinding binding = ContactBinding.inflate(inflater, container, false);
        binding.setModel(presenter.getModel());
        binding.sendButton.setOnClickListener(v -> presenter.send());
        return binding.getRoot();
    }

    @Override public void showSentMessage() {
        Snackbar.make(getView(), getResources().getString(R.string.message_sent), Snackbar.LENGTH_LONG).show();
    }

    @Override public void showSentError() {
        Snackbar.make(getView(), getResources().getString(R.string.error_sending_message), Snackbar.LENGTH_LONG).show();
    }
}
