package com.app.rewizor.data.repository

import com.app.rewizor.data.RewizorResult
import com.app.rewizor.data.model.Account

interface AccountRepository {
    var account: Account
    suspend fun recoverPassword(email: String): RewizorResult<Unit>
}