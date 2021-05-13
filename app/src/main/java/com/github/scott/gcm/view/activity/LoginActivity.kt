package com.github.scott.gcm.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.scott.gcm.CommonUtil
import com.github.scott.gcm.R
import com.github.scott.gcm.data.viewmodel.LoginViewModel
import com.github.scott.gcm.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var client: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    private val GOOGLE_LOGIN = 10001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModel()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.viewModel = viewModel

        setGoogleLogin()
    }

    private fun initViewModel() {
        viewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application)).get(
                LoginViewModel::class.java
            )
        viewModel.moveMain.observe(this, Observer { email ->
            CommonUtil.savedUser(this, email)
            moveMainActivity()
        })
        viewModel.googleLogin.observe(this, Observer {
            startActivityForResult(client.signInIntent, GOOGLE_LOGIN)
        })
        viewModel.showToast.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.moveSignup.observe(this, Observer {
            startActivity(Intent(this, SignupAcitivity::class.java))
        })
    }

    private fun setGoogleLogin() {
        // Google Login
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        client = GoogleSignIn.getClient(this, options)

        // 인증 데이터
        auth = FirebaseAuth.getInstance()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_LOGIN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            var account: GoogleSignInAccount? = null
            try {
                account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed Google Login", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val email = auth.currentUser.email
                    val name = auth.currentUser.displayName

                    if (viewModel.isExistUser(email)) {
                        // sharedpreference에 저장하고 메인으로 이동
                        Log.e("Google 로그인", "email : $email   name : $name")
                        CommonUtil.savedUser(this, email)
                        moveMainActivity()
                    } else {
                        viewModel.email = email
                        viewModel.password = idToken
                        viewModel.name = name
                        viewModel.onClickSignup(true)
                    }

                } else {
                    //   update(null)
                }
            }
    }

    private fun moveMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}