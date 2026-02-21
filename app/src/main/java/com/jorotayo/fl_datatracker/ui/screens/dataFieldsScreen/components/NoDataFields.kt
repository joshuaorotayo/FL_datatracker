package com.jorotayo.fl_datatracker.ui.screens.dataFieldsScreen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jorotayo.fl_datatracker.ui.DefaultPreviews
import com.jorotayo.fl_datatracker.ui.theme.FL_DatatrackerThemeNew
import com.jorotayo.fl_datatracker.ui.util.Dimensions

/**
 * MODERNIZED NO DATA FIELD EMPTY STATE
 *
 * Improvements:
 * - Material 3 components and styling
 * - Better visual hierarchy
 * - Clear call-to-action
 * - Consistent burgundy theme
 * - Proper spacing (4dp grid)
 * - More welcoming and helpful design
 */

@DefaultPreviews
@Composable
fun PreviewNoDataField() {
    FL_DatatrackerThemeNew {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NoDataFieldScreen(
                onAddFieldClick = {}
            )
        }
    }
}

@Composable
fun NoDataFieldScreen(
    modifier: Modifier = Modifier,
    onAddFieldClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.spacingMedium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(
            defaultElevation = Dimensions.elevationSmall
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.spacingXXLarge),
            verticalArrangement = Arrangement.spacedBy(Dimensions.spacingLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Large icon
            Surface(
                shape = MaterialTheme.shapes.extraLarge,
                color = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.CreateNewFolder,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(Dimensions.spacingLarge)
                        .size(Dimensions.iconXLarge)
                )
            }

            // Text content
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimensions.spacingXSmall),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No Data Fields Yet",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Create your first data field to start organizing your information",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = Dimensions.spacingMedium)
                )
            }

            // Call to action button
            Button(
                onClick = onAddFieldClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.componentButton),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(Dimensions.iconSmall)
                )
                Spacer(modifier = Modifier.width(Dimensions.spacingXSmall))
                Text(
                    "Add Your First Field",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            // Optional: Quick tips
            Divider(
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(vertical = Dimensions.spacingSmall)
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(Dimensions.spacingXSmall),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Quick Tips:",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                QuickTipItem(
                    text = "Choose from text, date, time, boolean, and more field types"
                )
                QuickTipItem(
                    text = "Add custom hints to guide data entry"
                )
                QuickTipItem(
                    text = "Toggle fields active or inactive anytime"
                )
            }
        }
    }
}

@Composable
private fun QuickTipItem(
    text: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(Dimensions.spacingXSmall),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 2.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
    }
}


@DefaultPreviews
@Composable
fun PreviewNoDataFieldMinimal() {
    FL_DatatrackerThemeNew {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NoDataFieldMinimal(
                onAddFieldClick = {}
            )
        }
    }
}

/**
 * ALTERNATIVE VERSION - Minimal Design
 *
 * Use this if you prefer a simpler, more compact empty state
 */
@Composable
fun NoDataFieldMinimal(
    modifier: Modifier = Modifier,
    onAddFieldClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.spacingMedium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.spacingXLarge),
            verticalArrangement = Arrangement.spacedBy(Dimensions.spacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon
            Icon(
                imageVector = Icons.Default.AddBox,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(64.dp)
            )

            // Text
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimensions.spacingXXSmall),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No Data Fields",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Add a field to get started",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            // Button
            Button(
                onClick = onAddFieldClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    modifier = Modifier.size(Dimensions.iconSmall)
                )
                Spacer(modifier = Modifier.width(Dimensions.spacingXSmall))
                Text("Add Field")
            }
        }
    }
}

@DefaultPreviews
@Composable
fun PreviewNoDataFieldWithFabHint() {
    FL_DatatrackerThemeNew {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NoDataFieldWithFabHint()
        }
    }
}

/**
 * ALTERNATIVE VERSION - Inline with FAB Hint
 *
 * Use this if you have a FAB and want to guide users to it
 */
@Composable
fun NoDataFieldWithFabHint(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(Dimensions.spacingMedium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.spacingXLarge),
            verticalArrangement = Arrangement.spacedBy(Dimensions.spacingMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icon
            Icon(
                imageVector = Icons.Default.CreateNewFolder,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(72.dp)
            )

            // Text
            Column(
                verticalArrangement = Arrangement.spacedBy(Dimensions.spacingXXSmall),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No Data Fields Yet",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "Tap the + button below to create your first data field",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }

            // Visual hint pointing down
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(Dimensions.spacingXXSmall)
            ) {
                Text(
                    text = "↓",
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Text(
                        text = "Add Field",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(
                            horizontal = Dimensions.spacingSmall,
                            vertical = Dimensions.spacingXXSmall
                        )
                    )
                }
            }
        }
    }
}

/**
 * COLOR SCHEME USED:
 *
 * Primary (Burgundy): #8B1A1A
 * - Icon tint
 * - Button background
 * - Quick tips bullets
 * - "Quick Tips:" label
 *
 * SurfaceVariant: Light gray (#F5F5F5) / Burgundy-tinted dark (#3A2F35)
 * - Card background
 *
 * PrimaryContainer: Light pink (#FFCDD2) / Deep burgundy (#93000A)
 * - Icon badge background
 *
 * OnSurface: Dark (#1C1B1F)
 * - Main heading
 *
 * OnSurfaceVariant: Medium gray (#49454F)
 * - Body text
 * - Tips text
 *
 * SPACING (4dp grid):
 * - Card padding: spacingXXLarge (32dp)
 * - Between major sections: spacingLarge (24dp)
 * - Between text elements: spacingXSmall (8dp)
 * - Icon size: iconXLarge (48dp)
 * - Button height: componentButton (50dp)
 *
 * TYPOGRAPHY:
 * - Main heading: headlineMedium (28sp)
 * - Body text: bodyMedium (14sp)
 * - Tips text: bodySmall (12sp)
 * - Button text: labelLarge (14sp, SemiBold)
 * - Tips heading: labelLarge (14sp, SemiBold)
 *
 * USAGE IN YOUR SCREEN:
 *
 * In ImprovedDataFieldsScreen, show this when dataFields is empty:
 *
 * if (dataFields.isEmpty()) {
 *     item {
 *         NoDataField(
 *             onAddFieldClick = onAddField
 *         )
 *     }
 * } else {
 *     items(dataFields) { field ->
 *         DataFieldCard(...)
 *     }
 * }
 */