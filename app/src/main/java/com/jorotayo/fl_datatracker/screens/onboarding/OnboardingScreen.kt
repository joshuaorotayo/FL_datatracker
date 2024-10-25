package com.jorotayo.fl_datatracker.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.jorotayo.fl_datatracker.R
import com.jorotayo.fl_datatracker.screens.onboarding.components.OnboardingEvent
import com.jorotayo.fl_datatracker.screens.onboarding.components.OnboardingScreenData
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.util.Dimen
import com.jorotayo.fl_datatracker.util.Dimen.sixtyPercent
import com.jorotayo.fl_datatracker.util.Dimen.xLarge
import com.jorotayo.fl_datatracker.util.Dimen.xxLarge
import com.jorotayo.fl_datatracker.util.Dimen.xxxLarge
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@DefaultPreviews
@Composable
fun PreviewOnBoardingScreen() {
    FL_DatatrackerTheme {
        OnboardingScreen(
            onBoardingEvent = {},
            finishOnboarding = {},
            pages = listOf(
                OnboardingScreenData(
                    image = Icons.Default.SwapHoriz,
                    title = "Kelly",
                    description = "Donell"
                ), OnboardingScreenData(
                    image = Icons.Default.SwapHoriz,
                    title = "Kelly",
                    description = "Donell"
                )
            )
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnboardingScreen(
    onBoardingEvent: (OnboardingEvent) -> Unit,
    finishOnboarding: () -> Unit,
    pages: List<OnboardingScreenData>,
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val darkTheme = isSystemInDarkTheme()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
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
                        .fillMaxHeight(0.4f),
                    imageVector = pages[page].image,
                    contentDescription = "Pager Image",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
                )
            }
            // here

            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                Card(
                    modifier = Modifier
                        .fillMaxHeight(sixtyPercent)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .clip(RoundedCornerShape(topStart = xLarge, topEnd = xLarge)),
                    backgroundColor = if (darkTheme) MaterialTheme.colors.background else MaterialTheme.colors.surface
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(top = xxLarge)
                    ) {
                        Spacer(modifier = Modifier.height(Dimen.large))
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = pages[pagerState.currentPage].title,
                            fontSize = MaterialTheme.typography.h4.fontSize,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.onSurface
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp)
                                .padding(top = 20.dp),
                            text = pages[pagerState.currentPage].description,
                            fontSize = MaterialTheme.typography.h6.fontSize,
                            fontWeight = FontWeight.Medium,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.onSurface
                        )

                        HorizontalPagerIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .weight(3f),
                            activeColor = MaterialTheme.colors.primary,
                            inactiveColor = MaterialTheme.colors.onSurface,
                            pagerState = pagerState
                        )
                        OnBoardingComplete(
                            modifier = Modifier.weight(1f),
                            pagerState = pagerState,
                            onboardingEvent = onBoardingEvent,
                            lastIndex = pages.lastIndex
                        )
                        // Next Button Section
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
                                    Text(stringResource(R.string.next_btn))
                                }
                            }
                        }

                        FinishButton(
                            modifier = Modifier.weight(1f),
                            pagerState = pagerState,
                            lastIndex = pages.lastIndex,
                            onClick = finishOnboarding
                        )
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
    onboardingEvent: (OnboardingEvent) -> Unit,
    modifier: Modifier,
    lastIndex: Int,
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
            Text(
                text = stringResource(R.string.hide_welcome_screen),
                color = MaterialTheme.colors.onSurface
            )
            Checkbox(
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    onboardingEvent(OnboardingEvent.SaveOnBoarding)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colors.primary,
                    uncheckedColor = Color.Black,
                    checkmarkColor = MaterialTheme.colors.onPrimary
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
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            visible = pagerState.currentPage == lastIndex
        ) {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colors.onPrimary,
                    backgroundColor = MaterialTheme.colors.primary
                )
            ) {
                Text(text = stringResource(R.string.finish_btn))
            }
        }
    }
}


@OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
@DefaultPreviews
@Composable
fun PreviewOnBoardingScreen2() {
    val randomString = LoremIpsum(50).values.toString()
    FL_DatatrackerTheme {
        OnboardingScreen2(
            onBoardingEvent = {},
            finishOnboarding = {},
            pages = listOf(
                OnboardingScreenData(
                    image = Icons.Default.SwapHoriz,
                    title = "Kelly Donell",
                    description = randomString
                ), OnboardingScreenData(
                    image = Icons.Default.SwapHoriz,
                    title = "Kelly",
                    description = "Donell"
                )
            )
        )
    }
}

@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun OnboardingScreen2(
    onBoardingEvent: (OnboardingEvent) -> Unit,
    finishOnboarding: () -> Unit,
    pages: List<OnboardingScreenData>,
) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val backgroundColour =
        if (isDarkMode()) MaterialTheme.colors.primary else MaterialTheme.colors.onSecondary
    val colorFilterColor =
        if (isDarkMode()) MaterialTheme.colors.background else MaterialTheme.colors.primary
    val textColor = Color.Black
    val buttonColours =
        if (isDarkMode()) MaterialTheme.colors.background else MaterialTheme.colors.primary

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColour)
    ) {
        HorizontalPager(state = pagerState, count = pages.size) { page ->
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(xxxLarge))
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(Dimen.fortyPercent),
//                        .background(shape = RoundedCornerShape(small), color = MaterialTheme.colors.background),
                    imageVector = pages[page].image,
                    contentDescription = "Pager Image",
                    colorFilter = ColorFilter.tint(colorFilterColor)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(top = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(xxLarge)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = pages[pagerState.currentPage].title,
                        fontSize = MaterialTheme.typography.h4.fontSize,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = textColor
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = pages[pagerState.currentPage].description,
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Center,
                        color = textColor
                    )

                    HorizontalPagerIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        activeColor = if (isDarkMode()) MaterialTheme.colors.onBackground else MaterialTheme.colors.primary,
                        inactiveColor = if (isDarkMode()) MaterialTheme.colors.background else Color.Black,
                        pagerState = pagerState
                    )
                    OnBoardingComplete(
                        modifier = Modifier,
                        pagerState = pagerState,
                        onboardingEvent = onBoardingEvent,
                        lastIndex = pages.lastIndex
                    )
                    // Next Button Section
                    AnimatedVisibility(
                        modifier = Modifier
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
                                    backgroundColor = buttonColours
                                )
                            ) {
                                Text(stringResource(R.string.next_btn))
                            }
                        }
                    }

                    FinishButton(
                        modifier = Modifier.weight(1f),
                        pagerState = pagerState,
                        lastIndex = pages.lastIndex,
                        onClick = finishOnboarding
                    )
                }
            }
        }
    }
}