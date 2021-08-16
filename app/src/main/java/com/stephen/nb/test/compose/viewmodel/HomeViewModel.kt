package com.stephen.nb.test.compose.viewmodel

import com.stephen.nb.test.compose.MyApplication
import kotlinx.coroutines.flow.MutableSharedFlow

class HomeViewModel : BaseViewModel() {
    val loadApiDataState = MutableSharedFlow<Boolean>()//注意：MutableSharedFlow可以发射相同的值

    fun switchToNextTheme() {
        val nextTheme = appTheme.value.nextTheme()
        MyApplication.currentTheme = nextTheme
        appTheme.value = nextTheme
    }

    data class HomeDrawerViewState(
        val switchToNextTheme: () -> Unit,
        val updateProfile: (faceUrl: String, nickname: String, signature: String) -> Unit,
        val logout: () -> Unit,
    )
}