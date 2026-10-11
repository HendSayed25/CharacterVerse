package com.characterverse.presentation.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.characterverse.R
import com.characterverse.ui.theme.*

@Composable
fun StatusBadge(
    status: String,
    modifier: Modifier = Modifier
) {
    val aliveText = stringResource(R.string.filter_alive)
    val deadText = stringResource(R.string.filter_dead)

    val statusColor = when (status.lowercase()) {
        aliveText.lowercase() -> StatusAlive
        deadText.lowercase() -> StatusDead
        else -> StatusUnknown
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = BadgeBackground,
        border = BorderStroke(1.dp, statusColor.copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(statusColor)
            )
            Text(
                text = status.uppercase(),
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                ),
                color = statusColor
            )
        }
    }
}

@Composable
fun StatusDot(
    status: String,
    modifier: Modifier = Modifier
) {
    val aliveText = stringResource(R.string.filter_alive)
    val deadText = stringResource(R.string.filter_dead)

    val statusColor = when (status.lowercase()) {
        aliveText.lowercase() -> StatusAlive
        deadText.lowercase() -> StatusDead
        else -> StatusUnknown
    }

    Box(
        modifier = modifier
            .size(10.dp)
            .clip(CircleShape)
            .background(statusColor)
    )
}
