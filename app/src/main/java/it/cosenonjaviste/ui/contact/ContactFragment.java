package it.cosenonjaviste.ui.contact;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.contact.ContactViewModel;
import it.cosenonjaviste.databinding.ContactBinding;
import it.cosenonjaviste.mv2m.ViewModelFragment;
import it.cosenonjaviste.ui.CoseNonJavisteApp;

public class ContactFragment extends ViewModelFragment<ContactViewModel> {

    @Override protected ContactViewModel createViewModel() {
        return CoseNonJavisteApp.getComponent(this).getContactViewModel();
    }

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ContactBinding binding = ContactBinding.bind(inflater.inflate(R.layout.contact, container, false));
        binding.setViewModel(viewModel);
        return binding.getRoot();
    }
}
