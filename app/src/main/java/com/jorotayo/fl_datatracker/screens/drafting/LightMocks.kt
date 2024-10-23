package com.jorotayo.fl_datatracker.screens.drafting

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jorotayo.fl_datatracker.ui.theme.AppTheme
import com.jorotayo.fl_datatracker.ui.theme.AppTheme.dimens


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode"
)
@Composable
fun PreviewFLLScreens() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(240, 239, 239, 255))

        ) {
            LightColumnUI()
            LightRoundedTopBar(
                Modifier.align(Alignment.TopCenter),
                textColour = Color.Red.copy(alpha = 0.5f)
            )
            LightRoundedBottomBar(
                Modifier.align(Alignment.BottomCenter),
                textColour = Color.Red.copy(alpha = 0.5f),
                backColour = Color.White
            )
        }
    }
}

@Composable
fun LightColumnUI() {
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
            color = MaterialTheme.colors.background,
            shape = RoundedCornerShape(
                dimens.xSmall
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimens.xSmall)
            ) {
                Text(text = "Subheading Example", color = Color.Black, fontSize = 12.sp)
                Text(
                    modifier = Modifier.padding(top = dimens.xxxSmall),
                    text = "100",
                    color = Color.Gray
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
            Text(
                modifier = Modifier,
                text = "Heading Example",
                fontSize = 16.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                tint = Color.Red.copy(alpha = 0.5f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(dimens.small)
        ) {
            for (i in 0..1) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .weight(1f),
                    shape = RoundedCornerShape(dimens.small),
                    color = Color.White
                ) {

                }
            }
        }

    }
}


@Composable
fun LightRoundedTopBar(modifier: Modifier, textColour: Color) {
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
                .padding(dimens.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Page Name",
                color = textColour,
                fontSize = 14.sp,
                modifier = Modifier.padding(end = dimens.medium),
            )
            Surface(
                modifier = Modifier
                    .wrapContentSize(),
                color = Color(240, 239, 239, 255),
                shape = RoundedCornerShape(8.dp),
            ) {
                Row(modifier = Modifier) {
                    Text(
                        text = "Search Item",
                        color = textColour,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(
                            start = dimens.xSmall,
                            top = dimens.xxxSmall,
                            bottom = dimens.xxxSmall,
                            end = dimens.xxxLarge
                        ),
                    )
                }
            }
        }
    }
}

@Composable
fun LightRoundedBottomBar(modifier: Modifier, backColour: Color, textColour: Color) {
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


