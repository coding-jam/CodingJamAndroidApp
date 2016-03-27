package it.cosenonjaviste.core.contact;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.R;
import it.cosenonjaviste.core.MessageManager;
import it.cosenonjaviste.model.MailJetService;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactViewModelTest {

    @Mock MailJetService mailJetService;

    @InjectMocks ContactViewModel viewModel;

    @Mock MessageManager messageManager;

    @Test
    public void testParcelable() {
//        ContactModel model = new ContactModel();
//        ParcelableTester.check(model, ContactModel.CREATOR);
    }

    @Test
    public void testEmailError() {
        ContactModel model = viewModel.initAndResume();

        compileForm(model, "aaa", "aaa", "aaa");
        viewModel.send();

        checkErrors(model, 0, R.string.invalid_email, 0);
    }

    @Test
    public void testMandatoryFields() {
        ContactModel model = viewModel.initAndResume();

        compileForm(model, "", null, "");
        viewModel.send();

        checkErrors(model, R.string.mandatory_field, R.string.mandatory_field, R.string.mandatory_field);
    }

    @Test
    public void testSend() {
        when(mailJetService.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(null));

        ContactModel model = viewModel.initAndResume();

        compileForm(model, "aaa", "aaa@aaa.it", "aaabbb");
        viewModel.send();

        checkErrors(model, 0, 0, 0);
        verify(messageManager).showMessage(any(), eq(R.string.message_sent));
    }

    @Test
    public void testSendError() {
        when(mailJetService.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.error(new Exception("aaa")));

        ContactModel model = viewModel.initAndResume();

        compileForm(model, "aaa", "aaa@aaa.it", "aaabbb");
        viewModel.send();

        checkErrors(model, 0, 0, 0);
        verify(messageManager).showMessage(any(), eq(R.string.error_sending_message));
    }

    private void compileForm(ContactModel model, String name, String email, String message) {
        model.name.set(name);
        model.email.set(email);
        model.message.set(message);
    }


    private void checkErrors(ContactModel model, int name, int email, int message) {
        assertThat(model.nameError.get()).isEqualTo(name);
        assertThat(model.emailError.get()).isEqualTo(email);
        assertThat(model.messageError.get()).isEqualTo(message);
    }
}