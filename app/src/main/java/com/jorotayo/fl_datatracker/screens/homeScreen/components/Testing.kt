package com.jorotayo.fl_datatracker.screens.homeScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign.Companion.End
import androidx.compose.ui.text.style.TextAlign.Companion.Start
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.ui.DefaultDualPreview
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerTheme
import com.jorotayo.fl_datatracker.ui.theme.isDarkMode
import com.jorotayo.fl_datatracker.ui.theme.subtitleTextColour
import com.jorotayo.fl_datatracker.util.Dimen
import com.jorotayo.fl_datatracker.util.Dimen.small
import com.jorotayo.fl_datatracker.util.Dimen.xxSmall
import com.jorotayo.fl_datatracker.util.Dimen.xxxLarge
import com.jorotayo.fl_datatracker.util.Dimen.xxxSmall

@DefaultDualPreview
@Composable
fun PreviewTesting() {
    FL_DatatrackerTheme {
        MembersPanel(Modifier.padding(horizontal = Dimen.xSmall), totalCount = 200)
    }
}

@Composable
fun MembersPanel(modifier: Modifier, totalCount: Int) {
    Box(modifier.wrapContentSize()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(185.dp)
                .clip(RoundedCornerShape(small)), elevation = xxSmall
//                .background(MaterialTheme.colors.surface)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = small, top = small, end = small)
                    .fillMaxSize()
                    .align(Alignment.TopCenter)
                    .clip(RoundedCornerShape(small)),
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    Modifier
                        .padding(xxxSmall),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Content(Modifier.weight(3f), "Sheep")
                    Spacer(modifier = Modifier.weight(0.5f))
                    Content(Modifier.weight(3f), "Deer")
                    Spacer(modifier = Modifier.weight(0.5f))
                    Content(Modifier.weight(3f), "Goat")
                }

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = xxSmall, top = xxSmall),
                    text = "Total Members: $totalCount",
                    textAlign = Start,
                    style = MaterialTheme.typography.h6
                )
            }
        }
    }
}

@Composable
fun Content(
    modifier: Modifier,
    textString: String
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(small))
            .size(xxxLarge + small),
        backgroundColor = if (isDarkMode()) MaterialTheme.colors.surface else MaterialTheme.colors.background,
        elevation = xxSmall
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(xxSmall),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(0.8f),
                imageVector = Icons.Default.Face,
                contentDescription = "Cardstuff"
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = textString,
                color = MaterialTheme.colors.subtitleTextColour,
                textAlign = End
            )
        }
    }
}