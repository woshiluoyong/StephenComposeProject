package com.stephen.nb.test.compose.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SplashViewModel : BaseViewModel() {
    val splashState = MutableStateFlow(SplashScreenState(showLoading = false, alreadyEnter = false))//注意：MutableStateFlow不能发射相同的值

    fun startEnter() {
        this.splashState.value = SplashScreenState(showLoading = true, alreadyEnter = false)
        viewModelScope.launch(Dispatchers.Main) {
            delay(1 * 1000)
            splashState.value = SplashScreenState(showLoading = false, alreadyEnter = true)
        }
    }

    data class SplashScreenState(
        val showLoading: Boolean,
        val alreadyEnter: Boolean
    )
}