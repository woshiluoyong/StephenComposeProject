package com.stephen.nb.test.compose.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow

class LineViewModel : BaseViewModel() {
    val clickState = MutableStateFlow(CommonString(""))
}