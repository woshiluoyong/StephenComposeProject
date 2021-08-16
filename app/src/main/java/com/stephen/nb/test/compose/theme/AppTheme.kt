package com.stephen.nb.test.compose.theme

enum class AppTheme(val type: Int) {
    Light(0), Dark(1), Pink(2);

    fun isDarkTheme(): Boolean = this == Dark

    fun nextTheme(): AppTheme = when (this) {
        Light -> Dark
        Dark -> Pink
        Pink -> Light
    }
}