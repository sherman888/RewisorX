package com.app.rewizor

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.app.rewizor.exstension.replaceFragment
import com.app.rewizor.ui.LoginFragment
import com.app.rewizor.ui.RecoverPasswordFragment
import com.app.rewizor.ui.RegistrationFragment
import com.app.rewizor.viewmodel.StartViewModel
import kotlinx.android.synthetic.main.activity_start.*
import org.koin.android.ext.android.inject

class StartActivity : AppCompatActivity() {
    private val viewModel: StartViewModel by inject()
    var toolbarTitle: String
        get() = start_toolbar.title.toString()
        set(value) {
            start_toolbar.title = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        viewModel.onViewCreated()
        setViewModel()
    }

    private fun setViewModel() {
        viewModel.screen.observe(this, Observer { view ->  onStartViewOpen(view) })
        viewModel.openMain.observe(this, Observer { if (it) openMainApp() })
    }

    private fun onStartViewOpen(view: StartViewModel.FRAGMENT) {
        when(view) {
            StartViewModel.FRAGMENT.LOGIN -> replaceFragment(fragment = LoginFragment())
            StartViewModel.FRAGMENT.REGISTRATION -> replaceFragment(fragment = RegistrationFragment())
            StartViewModel.FRAGMENT.RECOVER -> replaceFragment(fragment = RecoverPasswordFragment())
        }
    }

    private fun openMainApp() {
        Intent(this, MainActivity::class.java).also { startActivity(it) }
        finish()
    }
    
    companion object {
        const val FRAGMENT_CONTAINER = R.id.fragment_container
    }
}
