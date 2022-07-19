package com.jorotayo.fl_datatracker.screens.welcomeScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.jorotayo.fl_datatracker.navigation.Screen
import com.jorotayo.fl_datatracker.screens.welcomeScreen.components.WelcomeScreenData
import com.jorotayo.fl_datatracker.ui.theme.BottomCardShape
import com.jorotayo.fl_datatracker.viewModels.WelcomeViewModel
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun WelcomeScreen(
    navController: NavHostController,
    viewModel: WelcomeViewModel,
    pages: List<WelcomeScreenData>
) {

    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            HorizontalPager(state = pagerState, count = pages.size) { page ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.primary),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {

                    Image(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .fillMaxHeight(0.6f),
                        imageVector = pages[page].image,
                        contentDescription = "Pager Image",
                        colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
                    )
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            Card(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                backgroundColor = MaterialTheme.colors.onPrimary,
                elevation = (8.dp),
                shape = BottomCardShape.large
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 32.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = pages[pagerState.currentPage].title,
                        fontSize = MaterialTheme.typography.h4.fontSize,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 40.dp)
                            .padding(top = 20.dp),
                        text = pages[pagerState.currentPage].description,
                        fontSize = MaterialTheme.typography.subtitle1.fontSize,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center
                    )

                    HorizontalPagerIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .weight(1f),
                        pagerState = pagerState
                    )
                    OnBoardingComplete(
                        modifier = Modifier.weight(1f),
                        pagerState = pagerState,
                        viewModel = viewModel,
                        lastIndex = pages.lastIndex
                    )
                    //Next Button Section
                    AnimatedVisibility(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 20.dp),
                        visible = pagerState.currentPage < pages.lastIndex,
                        enter = fadeIn(animationSpec = tween(durationMillis = 50))
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Button(
                                onClick = {
                                    scope.launch {
                                        pagerState.scrollToPage(pagerState.currentPage + 1)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = Color.White,
                                    backgroundColor = MaterialTheme.colors.primary
                                )
                            ) {
                                Text(text = "Next")
                            }
                        }
                    }

                    FinishButton(
                        modifier = Modifier.weight(1f),
                        pagerState = pagerState,
                        lastIndex = pages.lastIndex
                    ) {
                        navController.popBackStack()
                        navController.navigate(Screen.Home.route)
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnBoardingComplete(
    pagerState: PagerState,
    viewModel: WelcomeViewModel,
    modifier: Modifier,
    lastIndex: Int
) {
    AnimatedVisibility(
        modifier = Modifier.fillMaxWidth(),
        visible = pagerState.currentPage == lastIndex
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val checkedState = remember { mutableStateOf(false) }
            Text(text = "Don't show on next run")
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    viewModel.saveOnBoardingState()
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primary,
                    checkmarkColor = Color.White
                )
            )
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun FinishButton(
    modifier: Modifier,
    pagerState: PagerState,
    lastIndex: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == lastIndex
        ) {
            Button(
                modifier = Modifier,
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.primary
                )
            ) {
                Text(text = "Finish")
            }
        }
    }
}

//Previews
@Composable
@Preview(showBackground = true)
fun PreviewOnBoardingScreen() {
    /* OnBoardingScreen(
         navController = rememberNavController(),
         viewModel = hiltViewModel(),
         pages =
             List(
                 image = Icons.Default.DataExploration,
                 title = "Add Data",
                 description = "Click the floating action button to add new Data"
             )
     )*/

}