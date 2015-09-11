package it.cosenonjaviste.ui.contact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.contact.ContactView;
import it.cosenonjaviste.core.contact.ContactViewModel;
import it.cosenonjaviste.databinding.ContactBinding;
import it.cosenonjaviste.mv2m.ViewModelFragment;
import it.cosenonjaviste.ui.CoseNonJavisteApp;

public class ContactFragment extends ViewModelFragment<ContactViewModel> implements ContactView {

    @Override protected ContactViewModel createViewModel() {
        return CoseNonJavisteApp.getComponent(this).getContactViewModel();
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContactBinding binding = ContactBinding.bind(inflater.inflate(R.layout.contact, container, false));
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }

    @Override public void showSentMessage() {
        Snackbar.make(getView(), getResources().getString(R.string.message_sent), Snackbar.LENGTH_LONG).show();
    }

    @Override public void showSentError() {
        Snackbar.make(getView(), getResources().getString(R.string.error_sending_message), Snackbar.LENGTH_LONG).show();
    }
}
