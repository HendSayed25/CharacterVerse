package com.characterverse.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.characterverse.R
import com.characterverse.ui.theme.*

@Composable
fun StatusFilterChips(
    selectedStatus: String,
    onStatusSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val allLabel = stringResource(R.string.filter_all)
    val aliveLabel = stringResource(R.string.filter_alive)
    val deadLabel = stringResource(R.string.filter_dead)
    val unknownLabel = stringResource(R.string.filter_unknown)
    val rawUnknown = stringResource(R.string.filter_raw_unknown)

    val filterOptions = listOf(allLabel, aliveLabel, deadLabel, rawUnknown)

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(filterOptions) { status ->
            val isSelected = selectedStatus.equals(status, ignoreCase = true)
            val chipColor = when (status.lowercase()) {
                aliveLabel.lowercase() -> StatusAlive
                deadLabel.lowercase() -> StatusDead
                rawUnknown -> StatusUnknown
                else -> PortalCyan
            }

            FilterChip(
                selected = isSelected,
                onClick = { onStatusSelected(status) },
                label = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        if (status != allLabel) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(chipColor)
                            )
                        }
                        Text(
                            text = if (status == rawUnknown) unknownLabel else status,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        )
                    }
                },
                shape = RoundedCornerShape(20.dp),
                colors = FilterChipDefaults.filterChipColors(
                    containerColor = DarkSurface,
                    labelColor = TextSecondary,
                    selectedContainerColor = chipColor.copy(alpha = 0.25f),
                    selectedLabelColor = if (status == allLabel) PortalCyan else chipColor
                ),
                border = FilterChipDefaults.filterChipBorder(
                    borderColor = DarkCardBorder,
                    selectedBorderColor = chipColor,
                    enabled = true,
                    selected = isSelected
                )
            )
        }
    }
}
