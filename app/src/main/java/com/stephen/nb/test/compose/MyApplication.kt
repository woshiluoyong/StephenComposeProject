package com.stephen.nb.test.compose

import android.app.Application
import com.stephen.nb.test.compose.common.CoilImageLoader
import com.stephen.nb.test.compose.theme.AppTheme

class MyApplication: Application() {
    companion object{
        lateinit var appContext: MyApplication

        var currentTheme = AppTheme.Light
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
        CoilImageLoader.initImageLoader(this)
    }
}