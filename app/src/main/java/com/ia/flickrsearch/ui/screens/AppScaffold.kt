package com.ia.flickrsearch.ui.screens

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ia.flickrsearch.data.model.FlickrPhoto
import kotlinx.coroutines.FlowPreview
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Copyright (c) 2024
 * Todos os direitos reservados.
 *
 * Autor: Guilherme Ferrari Bréscia
 */
@Composable
fun AppScaffold() {
    val navController = rememberNavController()

    Scaffold {
        AppNavHost(
            navController = navController,
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
        )
    }
}

@OptIn(FlowPreview::class)
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screens.MAIN.route,
        modifier = modifier,
    ) {
        composable(Screens.MAIN.route) {
            MainScreen(
                onImageClick = { photo ->
                    val jsonPhoto = Uri.encode(Json.encodeToString(photo))
                    navController.navigate("${Screens.DETAIL.route}/$jsonPhoto")
                }
            )
        }
        composable(
            route = "${Screens.DETAIL.route}/{photoJson}",
            arguments = listOf(navArgument("photoJson") { type = NavType.StringType })
        ) { backStackEntry ->
            val photoJson = backStackEntry.arguments?.getString("photoJson")
            val photo = photoJson?.let { Json.decodeFromString<FlickrPhoto>(it) }
            photo?.let { DetailScreen(it) }
        }
    }
}