package com.jorotayo.fl_datatracker.screens.drafting

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun PreviewFLLScreensDark() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            DarkColumnUI()
            DarkRoundedTopBar(
                Modifier.align(Alignment.TopCenter),
                textColour = Color.Red.copy(alpha = 0.5f)
            )
            DarkRoundedBottomBar(
                Modifier.align(Alignment.BottomCenter),
                textColour = Color.Red.copy(alpha = 0.5f),
                backColour = Color.Black
            )
        }
    }
}

@Composable
fun DarkColumnUI() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = dimens.small, top = dimens.xxLarge, end = dimens.small)
    )
    {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(dimens.small))
                .fillMaxWidth()
                .height(100.dp),
            color = Color.Black,
            elevation = 3.dp,
            shape = RoundedCornerShape(dimens.xSmall),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimens.xSmall)
            ) {
                Text(text = "Bacenta data", color = Color.DarkGray)
                Text(
                    modifier = Modifier.padding(top = dimens.xxxSmall),
                    text = "100",
                    color = Color.White
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(dimens.xSmall)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(modifier = Modifier, text = "All the Data", fontSize = 14.sp, color = Color.White)
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                tint = Color.Red.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun CentreScrollerUI(backColourUi: Color, textColourUi: Color) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimens.xxxLarge, bottom = dimens.medium)
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(
                dimens.xSmall
            )
        ) {
            item {
                for (i in 1..13) {
                    ItemExample(
                        backColour = backColourUi,
                        textColour = textColourUi,
                        textContent = "Item number $i"
                    )
                    Spacer(modifier = Modifier.height(dimens.xxSmall))
                }
            }
        }
    }
}

@Composable
fun DarkRoundedTopBar(modifier: Modifier, textColour: Color) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(dimens.xLarge)
            .shadow(
                elevation = dimens.xSmall
            ),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimens.small)
        ) {
            Text(
                modifier = Modifier.padding(
                    end = dimens.medium,
                    top = dimens.xxSmall,
                    bottom = dimens.xxSmall
                ),
                text = "Page Name",
                color = textColour,
                fontSize = 14.sp
            )
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(
                        RoundedCornerShape(dimens.small)
                    )
                    .background(MaterialTheme.colors.surface), elevation = dimens.xSmall
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = dimens.regular,
                        vertical = dimens.xxSmall
                    ),
                    text = "Search Item",
                    color = textColour,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun DarkRoundedBottomBar(modifier: Modifier, backColour: Color, textColour: Color) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = dimens.large,
                shape = RoundedCornerShape(dimens.regular, dimens.regular, dimens.zero, dimens.zero)
            )
            .height(dimens.xLarge),
        elevation = dimens.medium
    ) {
        Row(
            modifier = Modifier
                .background(backColour)
                .padding(dimens.small), Arrangement.SpaceAround
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu Icon",
                tint = textColour
            )
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu Icon",
                tint = textColour
            )
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu Icon",
                tint = textColour
            )
        }
    }
}

@Composable
fun ItemExample(backColour: Color, textColour: Color, textContent: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(backColour),
        elevation = if (backColour == Color.Black) 0.dp else dimens.xxxxSmall
    ) {
        Text(
            modifier = Modifier.padding(horizontal = dimens.regular, vertical = dimens.xSmall),
            text = textContent,
            color = textColour
        )
    }
}
