package it.cosenonjaviste.core.contact

import it.cosenonjaviste.R
import it.cosenonjaviste.core.CnjJUnitDaggerRule
import it.cosenonjaviste.core.Navigator
import it.cosenonjaviste.daggermock.InjectFromComponent
import it.cosenonjaviste.model.MailJetService
import it.cosenonjaviste.ui.contact.ContactFragment
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.mockito.Matchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import rx.Observable

class ContactViewModelTest {

    @get:Rule val daggerRule = CnjJUnitDaggerRule()

    @Mock lateinit var mailJetService: MailJetService

    @Mock lateinit var navigator: Navigator

    @InjectFromComponent(ContactFragment::class) lateinit var viewModel: ContactViewModel

    @Test
    fun testEmailError() {
        val model = viewModel.initAndResume()

        compileForm(model, "aaa", "aaa", "aaa")
        viewModel.send()

        checkErrors(model, 0, R.string.invalid_email, 0)
    }

    @Test
    fun testMandatoryFields() {
        val model = viewModel.initAndResume()

        compileForm(model, "", null, "")
        viewModel.send()

        checkErrors(model, R.string.mandatory_field, R.string.mandatory_field, R.string.mandatory_field)
    }

    @Test
    fun testSend() {
        `when`(mailJetService.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(null))

        val model = viewModel.initAndResume()

        compileForm(model, "aaa", "aaa@aaa.it", "aaabbb")
        viewModel.send()

        checkErrors(model, 0, 0, 0)
        verify<Navigator>(navigator).showMessage(R.string.message_sent)
    }

    @Test
    fun testSendError() {
        `when`(mailJetService.sendEmail(anyString(), anyString(), anyString(), anyString()))
                .thenReturn(Observable.error(Exception("aaa")))

        val model = viewModel.initAndResume()

        compileForm(model, "aaa", "aaa@aaa.it", "aaabbb")
        viewModel.send()

        checkErrors(model, 0, 0, 0)
        verify<Navigator>(navigator).showMessage(R.string.error_sending_message)
    }

    private fun compileForm(model: ContactModel, name: String, email: String?, message: String) {
        model.name.set(name)
        model.email.set(email)
        model.message.set(message)
    }


    private fun checkErrors(model: ContactModel, name: Int, email: Int, message: Int) {
        assertThat(model.nameError.get()).isEqualTo(name)
        assertThat(model.emailError.get()).isEqualTo(email)
        assertThat(model.messageError.get()).isEqualTo(message)
    }
}