package it.cosenonjaviste.core.contact;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.core.model.MailJetService;
import it.cosenonjaviste.core.mvp.ViewMock;
import rx.Observable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactPresenterTest {

    @Mock MailJetService mailJetService;

    @InjectMocks it.cosenonjaviste.core.contact.ContactPresenter presenter;

    private ViewMock<it.cosenonjaviste.core.contact.ContactView> view = new ViewMock<>(ContactView.class);

    @Test
    public void testEmailError() {
        it.cosenonjaviste.core.contact.ContactModel model = view.initAndResume(presenter);

        model.name.set("aaa");
        model.email.set("aaa");
        model.message.set("aaa");
        presenter.send();

        assertThat(model.nameError.get()).isNull();
        assertThat(model.messageError.get()).isNull();
        assertThat(model.emailError.get()).isEqualTo(ValidationError.INVALID_EMAIL);
    }

    @Test
    public void testSend() {
        when(mailJetService.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(null));

        ContactModel model = view.initAndResume(presenter);

        model.name.set("aaa");
        model.email.set("aaa@aaa.it");
        model.message.set("aaa");
        presenter.send();

        assertThat(model.nameError.get()).isNull();
        assertThat(model.messageError.get()).isNull();
        assertThat(model.emailError.get()).isNull();
        view.verify().showSentMessage();
    }
}