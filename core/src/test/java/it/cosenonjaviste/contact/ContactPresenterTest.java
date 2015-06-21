package it.cosenonjaviste.contact;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import it.cosenonjaviste.mvp.ViewMock;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ContactPresenterTest {
    @InjectMocks ContactPresenter presenter;

    private ViewMock<ContactView> view = new ViewMock<>(ContactView.class);

    @Test
    public void testValidate() {
        ContactModel model = view.initAndResume(new ContactModel(), presenter);

        view.text("aaa").name();
        view.click().onSend();

        assertNotNull(model.subjectError.get());
    }
}