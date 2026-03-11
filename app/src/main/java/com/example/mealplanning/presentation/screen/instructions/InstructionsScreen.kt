package com.example.mealplanning.presentation.screen.instructions

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.mealplanning.presentation.navigation.composable.NavBottomBar
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.collections.map
import kotlin.coroutines.coroutineContext

@Composable
fun InstructionsScreen(
    modifier: Modifier = Modifier,
    recipeId: Int,
    viewModel: InstructionsViewModel = hiltViewModel(
        creationCallback = { factory: InstructionsViewModel.Factory ->
            factory.create(recipeId)
        }
    ),
) {
    val state = viewModel.state.collectAsState()
    val currentState = state.value

    val scopeScreen = rememberCoroutineScope()

    when (currentState) {
        InstructionsViewModel.InstructionsState.Finished -> {

        }

        InstructionsViewModel.InstructionsState.Initial -> {

        }

        is InstructionsViewModel.InstructionsState.ShowingRecipe -> {
            Scaffold(
                modifier = modifier
                    .fillMaxSize(),
                bottomBar = { NavBottomBar() }
            ) { innerPadding ->
                val instructions = currentState.instructions
                var sumSteps = 0
                instructions.forEach { it.forEach { _ -> sumSteps++ } }
                Log.d("TEST1", "$sumSteps")
                val pagerState = rememberPagerState(pageCount = { sumSteps })
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    SecondaryTabRow(selectedTabIndex = pagerState.currentPage) {
                        var stepNumber = 0
                        instructions.forEachIndexed { indexInstructionsList, listStep ->
                           stepNumber++
                            Log.d("TEST2","$stepNumber")
                            listStep.forEachIndexed { indexStepLists, step ->
                                stepNumber ++
                                Tab(
                                    selected = indexStepLists + indexInstructionsList == pagerState.currentPage,
                                    onClick = {
                                        scopeScreen.launch {
                                            pagerState.animateScrollToPage(indexStepLists)
                                        }
                                    },
                                    text = {
                                        Log.d("TEST2","$stepNumber")
                                        Text("$stepNumber")
                                    }
                                )
                            }
                        }
                    }
                    HorizontalPager(state = pagerState) { page ->
                        instructions.forEach { listStep ->
                            listStep.forEachIndexed { stepNumber, step ->
                                if (page == stepNumber) {
                                    Text(
                                        modifier = Modifier.fillMaxSize(),
                                        text = step.step
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}