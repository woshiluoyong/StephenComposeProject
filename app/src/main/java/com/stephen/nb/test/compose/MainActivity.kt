package com.stephen.nb.test.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.view.WindowCompat
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.stephen.nb.test.compose.theme.MyAppTheme
import com.stephen.nb.test.compose.viewscreen.HomeScreen
import com.stephen.nb.test.compose.viewscreen.LineScreen
import com.stephen.nb.test.compose.viewscreen.SplashScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyAppTheme {
                NavigationRouterConfig(this)
            }
        }
    }
}

@Composable
fun NavigationRouterConfig(activity: ComponentActivity) {//导航路由配置
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = RouteScreen.ScreenForSplash.route) {
        composable(RouteScreen.ScreenForSplash.route) {
            SplashScreen(navController = navController)
        }
        composable(RouteScreen.ScreenForHome.route) {
            HomeScreen(activity = activity, navController = navController)
        }
        composable(RouteScreen.ScreenForLine().route, arguments = listOf(navArgument("dataSize") {
            type = NavType.IntType
            defaultValue = 5
        })) {
            LineScreen(navController = navController, dataSize = RouteScreen.ScreenForLine.getArgument(it))
        }
    }
}

sealed class RouteScreen(val route: String) {
    object ScreenForSplash: RouteScreen(route = "splashScreen")
    object ScreenForHome: RouteScreen(route = "homeScreen")
    class ScreenForLine(dataSize: Int? = null): RouteScreen(route = generateRoute(dataSize = dataSize)){
        companion object{
            fun generateRoute(dataSize: Int?): String = "lineScreen/${dataSize ?: "{dataSize}"}"

            fun getArgument(entry: NavBackStackEntry): Int = entry.arguments?.getInt("dataSize") ?: 10
        }
    }
}