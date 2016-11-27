package it.cosenonjaviste.ui.contact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;
import javax.inject.Provider;

import it.codingjam.lifecyclebinder.LifeCycleBinder;
import it.codingjam.lifecyclebinder.RetainedObjectProvider;
import it.cosenonjaviste.R;
import it.cosenonjaviste.core.contact.ContactViewModel;
import it.cosenonjaviste.databinding.ContactBinding;
import it.cosenonjaviste.ui.CoseNonJavisteApp;
import it.cosenonjaviste.ui.MessageManager;
import rx.Subscription;

public class ContactFragment extends Fragment {

    @RetainedObjectProvider("viewModel") @Inject Provider<ContactViewModel> provider;

    ContactViewModel viewModel;

    @Inject MessageManager messageManager;

    private Subscription subscription;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        CoseNonJavisteApp.getComponent(this).inject(this);
        LifeCycleBinder.bind(this);

        subscription = viewModel.messageEvents.subscribe(
                message -> messageManager.showMessage(getActivity(), message));
    }

    @Override public void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContactBinding binding = ContactBinding.bind(inflater.inflate(R.layout.contact, container, false));
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}
