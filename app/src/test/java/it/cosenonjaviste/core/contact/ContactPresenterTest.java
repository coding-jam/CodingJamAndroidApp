package it.cosenonjaviste.core.contact;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.R;
import it.cosenonjaviste.model.MailJetService;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactPresenterTest {

    @Mock MailJetService mailJetService;

    @InjectMocks ContactPresenter presenter;

    @Mock ContactView view;

    @Test
    public void testEmailError() {
        ContactModel model = presenter.initAndResume(view);

        model.name.set("aaa");
        model.email.set("aaa");
        model.message.set("aaa");
        presenter.send();

        assertThat(model.nameError.get()).isZero();
        assertThat(model.messageError.get()).isZero();
        assertThat(model.emailError.get()).isEqualTo(R.string.invalid_email);
    }

    @Test
    public void testSend() {
        when(mailJetService.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(null));

        ContactModel model = presenter.initAndResume(view);

        model.name.set("aaa");
        model.email.set("aaa@aaa.it");
        model.message.set("aaa");
        presenter.send();

        assertThat(model.nameError.get()).isZero();
        assertThat(model.messageError.get()).isZero();
        assertThat(model.emailError.get()).isZero();
        Mockito.verify(view).showSentMessage();
    }
}