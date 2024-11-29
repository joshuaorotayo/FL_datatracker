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
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.util.Dimen


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
fun PreviewFLLScreensDark() {
    FL_DatatrackerTheme {
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
            .padding(start = Dimen.small, top = Dimen.xxLarge, end = Dimen.small)
    )
    {
        Surface(
            modifier = Modifier
                .clip(RoundedCornerShape(Dimen.small))
                .fillMaxWidth()
                .height(100.dp),
            color = Color.Black,
            elevation = 3.dp,
            shape = RoundedCornerShape(
                Dimen.xSmall
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimen.xSmall)
            ) {
                Text(text = "Bacenta data", color = Color.DarkGray)
                Text(
                    modifier = Modifier.padding(top = Dimen.xxxSmall),
                    text = "100",
                    color = Color.White
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(Dimen.xSmall)
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
            .padding(top = Dimen.xxxLarge, bottom = Dimen.medium)
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(
                Dimen.xSmall
            )
        ) {
            item {
                for (i in 1..13) {
                    ItemExample(
                        backColour = backColourUi,
                        textColour = textColourUi,
                        textContent = "Item number $i"
                    )
                    Spacer(modifier = Modifier.height(Dimen.xxSmall))
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
            .height(Dimen.xLarge)
            .shadow(
                elevation = Dimen.xSmall
            ),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimen.small)
        ) {
            Text(
                modifier = Modifier.padding(
                    end = Dimen.medium,
                    top = Dimen.xxSmall,
                    bottom = Dimen.xxSmall
                ),
                text = "Page Name",
                color = textColour,
                fontSize = 14.sp
            )
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(
                        RoundedCornerShape(Dimen.small)
                    )
                    .background(MaterialTheme.colors.surface), elevation = Dimen.xSmall
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = Dimen.regular,
                        vertical = Dimen.xxSmall
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
                elevation = Dimen.large,
                shape = RoundedCornerShape(Dimen.regular, Dimen.regular, Dimen.zero, Dimen.zero)
            )
            .height(Dimen.xLarge),
        elevation = Dimen.medium
    ) {
        Row(
            modifier = Modifier
                .background(backColour)
                .padding(Dimen.small), Arrangement.SpaceAround
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
        elevation = if (backColour == Color.Black) 0.dp else Dimen.xxxxSmall
    ) {
        Text(
            modifier = Modifier.padding(horizontal = Dimen.regular, vertical = Dimen.xSmall),
            text = textContent,
            color = textColour
        )
    }
}
