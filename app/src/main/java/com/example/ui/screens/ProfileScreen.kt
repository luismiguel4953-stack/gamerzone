package com.example.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MilitaryTech
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.data.ClipEntity
import com.example.data.GameEntity
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
import com.example.ui.theme.NeonPurple
import com.example.ui.theme.NeonYellow
import com.example.ui.theme.RankLeyenda
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ProfileScreen(
    user: UserProfileEntity?,
    favoriteGames: List<GameEntity>,
    userClips: List<ClipEntity>,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val avatarRes = getDrawableResId(context, user?.avatarResName)
    var selectedTabIndex by remember { mutableStateOf(0) }

    val achievements = listOf(
        "Primera Sangre 🩸" to "Consigue tu primera baja en Ranked",
        "Pentakill Master ⚡" to "Elimina a 5 enemigos en menos de 10s",
        "Clip Viral 🎥" to "Alcanza 1,000 me gusta en un clip",
        "Campeón Global 🏆" to "Gana un torneo oficial GamerZone",
        "Coleccionista 🎮" to "Añade 5 juegos a tus favoritos",
        "Escuadrón Alfa 👥" to "Juega 50 partidas con tu grupo de amigos"
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 16.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Profile Header Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .border(
                        width = 1.dp,
                        brush = Brush.horizontalGradient(listOf(NeonCyan, NeonMagenta)),
                        shape = RoundedCornerShape(24.dp)
                    )
                    .testTag("profile_header_card"),
                colors = CardDefaults.cardColors(containerColor = DarkSurface)
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Avatar with Level Ring
                    Box(contentAlignment = Alignment.Center) {
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(Brush.linearGradient(listOf(NeonCyan, NeonMagenta)))
                                .padding(3.dp)
                        ) {
                            Image(
                                painter = painterResource(id = avatarRes),
                                contentDescription = user?.name,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        }

                        // Rank Badge Overlay
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .clip(RoundedCornerShape(12.dp))
                                .background(RankLeyenda)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = user?.rankTier ?: "LEYENDA",
                                color = Color.Black,
                                fontWeight = FontWeight.Black,
                                fontSize = 10.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = user?.name ?: "Alex 'Vortex' Cruz",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = TextPrimary
                        )
                    )

                    Text(
                        text = "@${user?.gamerTag ?: "Vortex_Alex"}",
                        color = NeonCyan,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = user?.bio ?: "Pro Gamer FPS & Battle Royale 🎮 Streamer en GamerZone",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Level & XP Bar
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(DarkSurfaceVariant)
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Nivel ${user?.level ?: 48}",
                                color = NeonYellow,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                            Text(
                                text = "XP ${user?.currentXp ?: 8400} / ${user?.maxXp ?: 10000}",
                                color = NeonCyan,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        val progress = ((user?.currentXp ?: 8400).toFloat() / (user?.maxXp ?: 10000).toFloat()).coerceIn(0f, 1f)
                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(6.dp)
                                .clip(RoundedCornerShape(3.dp)),
                            color = NeonCyan,
                            trackColor = DarkBackground
                        )
                    }
                }
            }
        }

        // Stats Overview Row (Victorias, Derrotas, Win Rate, Puntos)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val wins = user?.wins ?: 342
                val losses = user?.losses ?: 89
                val total = wins + losses
                val winRate = if (total > 0) ((wins.toFloat() / total.toFloat()) * 100).toInt() else 0

                StatBox(title = "Victorias", value = "$wins", color = NeonGreen, modifier = Modifier.weight(1f))
                StatBox(title = "Derrotas", value = "$losses", color = NeonMagenta, modifier = Modifier.weight(1f))
                StatBox(title = "Win Rate", value = "$winRate%", color = NeonCyan, modifier = Modifier.weight(1f))
                StatBox(title = "GamerCoins", value = "${user?.coins ?: 2450}", color = GoldColor, modifier = Modifier.weight(1f))
            }
        }

        // Tabs Row
        item {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = DarkSurface,
                contentColor = NeonCyan,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = NeonCyan
                    )
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, DarkCardBorder, RoundedCornerShape(12.dp))
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Favoritos", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Clips", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
                Tab(
                    selected = selectedTabIndex == 2,
                    onClick = { selectedTabIndex = 2 },
                    text = { Text("Insignias", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
                )
            }
        }

        // Tab Content
        when (selectedTabIndex) {
            0 -> {
                // Favorite Games
                if (favoriteGames.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Aún no tienes videojuegos favoritos guardados", color = TextSecondary)
                        }
                    }
                } else {
                    items(favoriteGames) { game ->
                        val thumbRes = getDrawableResId(context, game.coverImageResName)
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .border(1.dp, DarkCardBorder, RoundedCornerShape(14.dp)),
                            colors = CardDefaults.cardColors(containerColor = DarkSurface)
                        ) {
                            Row(
                                modifier = Modifier.padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = thumbRes),
                                    contentDescription = game.title,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(10.dp)),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(game.title, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 14.sp)
                                    Text(game.genre, color = TextSecondary, fontSize = 11.sp)
                                }
                                Icon(imageVector = Icons.Default.Favorite, contentDescription = null, tint = NeonMagenta)
                            }
                        }
                    }
                }
            }
            1 -> {
                // User Published Clips
                items(userClips) { clip ->
                    val thumbRes = getDrawableResId(context, clip.thumbnailResName)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.dp, DarkCardBorder, RoundedCornerShape(14.dp)),
                        colors = CardDefaults.cardColors(containerColor = DarkSurface)
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = thumbRes),
                                contentDescription = clip.caption,
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(clip.caption, fontWeight = FontWeight.Bold, color = TextPrimary, fontSize = 13.sp, maxLines = 1)
                                Spacer(modifier = Modifier.height(2.dp))
                                Text("${clip.gameTitle} • ${clip.likesCount} Likes", color = NeonCyan, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
            2 -> {
                // Badges & Achievements
                items(achievements) { (title, desc) ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.dp, GoldColor.copy(alpha = 0.3f), RoundedCornerShape(14.dp)),
                        colors = CardDefaults.cardColors(containerColor = DarkSurfaceVariant)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(GoldColor.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(imageVector = Icons.Default.MilitaryTech, contentDescription = null, tint = GoldColor)
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(title, fontWeight = FontWeight.Bold, color = GoldColor, fontSize = 13.sp)
                                Text(desc, color = TextSecondary, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StatBox(
    title: String,
    value: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(DarkSurface)
            .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
            .padding(vertical = 10.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, fontWeight = FontWeight.Black, color = color, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(2.dp))
            Text(title, color = TextMuted, fontSize = 10.sp, fontWeight = FontWeight.Medium)
        }
    }
}
