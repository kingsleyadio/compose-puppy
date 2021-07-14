/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.NavActions
import com.example.androiddevchallenge.ui.NavDestination
import com.example.androiddevchallenge.ui.detail.PuppyDetails
import com.example.androiddevchallenge.ui.list.PuppyList
import com.example.androiddevchallenge.ui.list.PuppyListViewModel
import com.example.androiddevchallenge.ui.starter.Starter
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.google.accompanist.insets.ProvideWindowInsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injection.init(applicationContext)
        setContent {
            MyTheme {
                ProvideWindowInsets {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        NavGraph()
    }
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val navActions = remember(navController) { NavActions(navController) }
    val vm = viewModel<PuppyListViewModel>()

    NavHost(navController = navController, startDestination = NavDestination.Starter.route) {
        composable(NavDestination.Starter.route) {
            Starter(onExplore = navActions::goHome)
        }
        composable(NavDestination.PupList.route) {
            val pups by vm.puppies.collectAsState()

            PuppyList(
                puppies = pups,
                onClick = { navActions.openDetails(it.id) }
            )
        }
        composable(
            NavDestination.PupDetails.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            val pupId = entry.arguments!!.getInt("id")
            val puppy = vm.findPuppyById(pupId)
            if (puppy == null) {
                Text(text = "Puppy not found :(", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
            } else {
                PuppyDetails(
                    puppy,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
