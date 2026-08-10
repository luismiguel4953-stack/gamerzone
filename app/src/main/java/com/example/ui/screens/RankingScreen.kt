package com.example.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkspacePremium
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LeaderboardUser
import com.example.data.UserProfileEntity
import com.example.ui.components.getDrawableResId
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.GoldColor
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonGreen
import com.example.ui.theme.NeonMagenta
import com.example.ui.theme.NeonYellow
import com.example.ui.theme.RankBronze
import com.example.ui.theme.RankDiamante
import com.example.ui.theme.RankLeyenda
import com.example.ui.theme.RankMaestro
import com.example.ui.theme.RankOro
import com.example.ui.theme.RankPlata
import com.example.ui.theme.RankPlatino
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun RankingScreen(
    user: UserProfileEntity?,
    leaderboard: List<LeaderboardUser>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val rankTiersList = listOf(
        "BRONCE" to RankBronze,
        "PLATA" to RankPlata,
        "ORO" to RankOro,
        "PLATINO" to RankPlatino,
        "DIAMANTE" to RankDiamante,
        "MAESTRO" to RankMaestro,
        "LEYENDA" to RankLeyenda
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title Header
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.EmojiEvents, contentDescription = null, tint = GoldColor)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Sistema Competitivo Global",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Black,
                        color = TextPrimary
                    )
                )
            }
        }

        // Current Player Rank Banner
        item {
            val userRankTier = user?.rankTier ?: "LEYENDA"
            val userPoints = user?.rankPoints ?: 10890
            val userWins = user?.wins ?: 342

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        width = 1.dp,
                        brush = Brush.horizontalGradient(listOf(GoldColor, NeonCyan)),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .testTag("user_rank_banner"),
                colors = CardDefaults.cardColors(containerColor = DarkSurface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .clip(CircleShape)
                                    .background(GoldColor.copy(alpha = 0.2f))
                                    .border(2.dp, GoldColor, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.WorkspacePremium,
                                    contentDescription = null,
                                    tint = GoldColor,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            Column {
                                Text(
                                    text = user?.name ?: "Alex 'Vortex'",
                                    fontWeight = FontWeight.Black,
                                    color = TextPrimary,
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = "Rango $userRankTier • Posición #2 Global",
                                    color = NeonCyan,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "$userPoints Pts",
                                fontWeight = FontWeight.Black,
                                color = GoldColor,
                                fontSize = 16.sp
                            )
                            Text(
                                text = "$userWins Victorias",
                                color = NeonGreen,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Rank Progress to Next Tier
                    Text(
                        text = "Progreso hacia Leyenda Supremo: 10,890 / 12,000 Pts",
                        color = TextSecondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    LinearProgressIndicator(
                        progress = { 0.88f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(3.dp)),
                        color = GoldColor,
                        trackColor = DarkSurfaceVariant
                    )
                }
            }
        }

        // Rank Tiers Breakdown Chips
        item {
            Column {
                Text(
                    text = "Rangos Competitivos:",
                    color = TextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(rankTiersList) { (tierName, tierColor) ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(DarkSurfaceVariant)
                                .border(1.dp, tierColor.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.MilitaryTech,
                                    contentDescription = null,
                                    tint = tierColor,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = tierName,
                                    color = tierColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // Leaderboard List
        item {
            Text(
                text = "Tabla de Clasificación Global",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            )
        }

        items(leaderboard) { player ->
            val isCurrent = player.isCurrentUser
            val avatarRes = getDrawableResId(context, player.avatarResName)
            val rankColor = when (player.rankTier) {
                "LEYENDA" -> RankLeyenda
                "MAESTRO" -> RankMaestro
                "DIAMANTE" -> RankDiamante
                "PLATINO" -> RankPlatino
                "ORO" -> RankOro
                "PLATA" -> RankPlata
                else -> RankBronze
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        width = if (isCurrent) 2.dp else 1.dp,
                        color = if (isCurrent) NeonCyan else DarkCardBorder,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .testTag("leaderboard_item_${player.position}"),
                colors = CardDefaults.cardColors(
                    containerColor = if (isCurrent) DarkSurfaceVariant else DarkSurface
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Crown / Rank Position Badge
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(
                                    when (player.position) {
                                        1 -> GoldColor
                                        2 -> Color(0xFFC0C0C0)
                                        3 -> Color(0xFFCD7F32)
                                        else -> DarkSurfaceVariant
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "#${player.position}",
                                color = if (player.position <= 3) Color.Black else TextPrimary,
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Image(
                            painter = painterResource(id = avatarRes),
                            contentDescription = player.name,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = player.name,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCurrent) NeonCyan else TextPrimary,
                                    fontSize = 14.sp
                                )
                                if (isCurrent) {
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "(TÚ)",
                                        color = NeonCyan,
                                        fontWeight = FontWeight.ExtraBold,
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(2.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(rankColor.copy(alpha = 0.2f))
                                        .padding(horizontal = 6.dp, vertical = 1.dp)
                                ) {
                                    Text(
                                        text = player.rankTier,
                                        color = rankColor,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "NVL ${player.level} • ${player.wins} Victorias",
                                    color = TextMuted,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${player.points}",
                            fontWeight = FontWeight.Black,
                            color = GoldColor,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "Puntos",
                            color = TextMuted,
                            fontSize = 10.sp
                        )
                    }
                }
            }
        }
    }
}
