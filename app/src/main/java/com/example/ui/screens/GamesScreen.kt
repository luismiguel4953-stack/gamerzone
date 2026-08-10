package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.GameEntity
import com.example.ui.components.GameCard
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.DarkCardBorder
import com.example.ui.theme.DarkSurfaceVariant
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun GamesScreen(
    games: List<GameEntity>,
    searchQuery: String,
    selectedGenre: String,
    onSearchChange: (String) -> Unit,
    onGenreSelect: (String) -> Unit,
    onGameClick: (GameEntity) -> Unit,
    onToggleFavorite: (GameEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val genres = listOf("Todos", "Action RPG", "Battle Royale", "FPS", "RPG", "Deportes")

    val filteredGames = games.filter { game ->
        val matchesQuery = game.title.contains(searchQuery, ignoreCase = true) ||
                game.genre.contains(searchQuery, ignoreCase = true)
        val matchesGenre = selectedGenre == "Todos" || game.genre.contains(selectedGenre, ignoreCase = true)
        matchesQuery && matchesGenre
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Title Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.SportsEsports, contentDescription = null, tint = NeonCyan)
            Spacer(modifier = Modifier.padding(start = 8.dp))
            Text(
                text = "Catálogo de Videojuegos",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Black,
                    color = TextPrimary
                )
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Search Input Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            placeholder = { Text("Buscar por título o género...", color = TextMuted, fontSize = 13.sp) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Buscar", tint = NeonCyan)
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("games_search_field"),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = DarkCardBorder,
                focusedContainerColor = DarkSurfaceVariant,
                unfocusedContainerColor = DarkSurfaceVariant,
                focusedTextColor = TextPrimary,
                unfocusedTextColor = TextPrimary
            ),
            shape = RoundedCornerShape(14.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Genre Chips Filter Row
        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(genres) { genre ->
                val isSelected = selectedGenre == genre
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isSelected) NeonCyan else DarkSurfaceVariant)
                        .border(
                            1.dp,
                            if (isSelected) NeonCyan else DarkCardBorder,
                            RoundedCornerShape(12.dp)
                        )
                        .clickable { onGenreSelect(genre) }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                        .testTag("genre_chip_$genre")
                ) {
                    Text(
                        text = genre,
                        color = if (isSelected) Color.Black else TextSecondary,
                        fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Games Grid
        if (filteredGames.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No se encontraron videojuegos",
                    color = TextSecondary,
                    fontSize = 14.sp
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 24.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(filteredGames) { game ->
                    GameCard(
                        game = game,
                        onGameClick = onGameClick,
                        onToggleFavorite = onToggleFavorite
                    )
                }
            }
        }
    }
}
