package com.example.mealplanning.presentation.screen.instructions

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SecondaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.example.mealplanning.domain.entity.instructions.Step
import com.example.mealplanning.presentation.navigation.composable.NavBottomBar
import kotlinx.coroutines.launch

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
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(48.dp),
                    color = MaterialTheme.colorScheme.primary
                )
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
                val pagerState = rememberPagerState(pageCount = { sumSteps })
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    SecondaryScrollableTabRow(selectedTabIndex = pagerState.currentPage) {
                        var stepNumber = 0
                        instructions.forEachIndexed { indexInstructionsList, listStep ->
                            listStep.forEachIndexed { indexStepLists, step ->
                                val currentStepNumber = stepNumber
                                Tab(
                                    selected = indexStepLists + indexInstructionsList == pagerState.currentPage,
                                    onClick = {
                                        scopeScreen.launch {
                                            pagerState.animateScrollToPage(currentStepNumber)
                                        }
                                    },
                                    text = {
                                        Text("${stepNumber + 1}")
                                    }
                                )
                                stepNumber++
                            }
                        }
                    }
                    HorizontalPager(state = pagerState) { page ->
                        val allInstructions = instructions.flatMap { it }
                        allInstructions.forEachIndexed { stepNumber, step ->
                            val currentStep = step
                            if (page == stepNumber) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(all = 16.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        currentStep.equipment.takeIf { it.isNotEmpty() }
                                            ?.let {
                                                Column(Modifier.weight(1f)) {
                                                    Text(
                                                        text = "Equipment",
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                    HorizontalDivider()
                                                    EquipmentListWithImages(step = currentStep)
                                                }
                                                Spacer(modifier = Modifier.width(16.dp))
                                            }

                                        currentStep.ingredients.takeIf { it.isNotEmpty() }
                                            ?.let {
                                                Column(Modifier.weight(1f)) {
                                                    Text(
                                                        text = "Ingredient",
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                    HorizontalDivider()
                                                    IngredientsListWithImages(step = currentStep)
                                                }
                                            }
                                    }
                                    Spacer(modifier = Modifier.height(32.dp))
                                    Text(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        text = currentStep.step
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

@Composable
fun EquipmentListWithImages(
    modifier: Modifier = Modifier,
    step: Step,
) {
    step.equipment.forEach {


        Row(
            modifier = modifier
                .padding(top = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .heightIn(max = 64.dp),
                    model = it.image,
                    contentDescription = "Equipment"
                )
            }
            Box(
                modifier = Modifier
                    .weight(3f)
            ) {
                Text(
                    text = "${it.name} ${it.temperature}"
                )
            }
        }

    }
}

@Composable
fun IngredientsListWithImages(
    modifier: Modifier = Modifier,
    step: Step,
) {
    step.ingredients.forEach {
        Row(
            modifier = modifier
                .padding(top = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .heightIn(max = 48.dp),
                    model = "https://img.spoonacular.com/ingredients_100x100/${it.imageUri}",
                    contentDescription = "Equipment"
                )
            }
            Box(
                modifier = Modifier
                    .weight(3f)
            ) {
                Text(
                    text = it.content
                )
            }
        }
    }
}