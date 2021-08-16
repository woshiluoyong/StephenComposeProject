package com.stephen.nb.test.compose.viewmodel

import androidx.lifecycle.ViewModel
import com.stephen.nb.test.compose.MyApplication
import kotlinx.coroutines.flow.MutableStateFlow

open class BaseViewModel : ViewModel() {
    val appTheme = MutableStateFlow(MyApplication.currentTheme)
    
    data class CommonString(var string: String)
    data class CommonBoolean(var boolean: Boolean)
}