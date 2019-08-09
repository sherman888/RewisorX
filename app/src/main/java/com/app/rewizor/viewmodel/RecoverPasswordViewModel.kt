package com.app.rewizor.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.rewizor.data.RewizorError
import com.app.rewizor.data.repository.AccountRepository
import com.app.rewizor.exstension.isNullOrNorChars
import com.app.rewizor.viewmodel.livedata.SingleLiveEvent

class RecoverPasswordViewModel(
    private val accountRepository: AccountRepository
) : BaseViewModel() {

    private val insertedEmail: MutableLiveData<String> = MutableLiveData()

    private val invalidInput: MutableLiveData<String> = SingleLiveEvent()
    val invalidInputLiveData: LiveData<String> get() = invalidInput

    private val passwordRecovered: MutableLiveData<Boolean> = MutableLiveData()
    val passwordRecoveredLiveData: LiveData<Boolean> = passwordRecovered

    private val recoverFailedInfo: MutableLiveData<String> = MutableLiveData()
    val recoverFailedInfoLiveData: LiveData<String> get() = recoverFailedInfo

    fun onEmailInserted(email: String) {
        insertedEmail.value = email
    }

    fun onRecoverClicked() {
        if (!isValid()) return
        with(asyncProvider) {
            startSuspend {
                val result = executeBackGroundTask { accountRepository.recoverPassword(insertedEmail.value!!) }
                when { result.isError -> recoverFailed(result.error!!)
                       else -> recoverSuccess() }
            }
        }

    }

    private fun recoverFailed(error: RewizorError) {
        recoverFailedInfo.value = error.message
    }

    private fun recoverSuccess() {
        passwordRecovered.value = true
    }

    private fun isValid(): Boolean =
        insertedEmail.value.isNullOrNorChars {
            invalidInput.value = INCORRECT_EMAIL
        }


    override fun onViewCreated() {

    }

    companion object {
        const val INCORRECT_EMAIL = "Введите имейл в формате example@email.com"
    }
}
