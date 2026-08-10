package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.CommentsDialog
import com.example.ui.components.FriendsChatDialog
import com.example.ui.components.GameDetailDialog
import com.example.ui.components.GamerBottomNavBar
import com.example.ui.components.GamerHeader
import com.example.ui.components.MissionsDialog
import com.example.ui.components.NotificationsDialog
import com.example.ui.components.PublishClipDialog
import com.example.ui.screens.ClipsScreen
import com.example.ui.screens.GamesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.RankingScreen
import com.example.ui.theme.DarkBackground
import com.example.ui.theme.GamerZoneTheme
import com.example.ui.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GamerZoneTheme {
                GamerZoneApp()
            }
        }
    }
}

@Composable
fun GamerZoneApp(viewModel: MainViewModel = viewModel()) {
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val games by viewModel.games.collectAsStateWithLifecycle()
    val clips by viewModel.clips.collectAsStateWithLifecycle()
    val friends by viewModel.friends.collectAsStateWithLifecycle()
    val missions by viewModel.missions.collectAsStateWithLifecycle()
    val tournaments by viewModel.tournaments.collectAsStateWithLifecycle()
    val notifications by viewModel.notifications.collectAsStateWithLifecycle()
    val unreadNotificationsCount by viewModel.unreadNotificationsCount.collectAsStateWithLifecycle()

    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedGenre by viewModel.selectedGenre.collectAsStateWithLifecycle()
    val selectedGameDetail by viewModel.selectedGameDetail.collectAsStateWithLifecycle()

    val showMissionsSheet by viewModel.showMissionsSheet.collectAsStateWithLifecycle()
    val showNotificationsSheet by viewModel.showNotificationsSheet.collectAsStateWithLifecycle()
    val showFriendsChatSheet by viewModel.showFriendsChatSheet.collectAsStateWithLifecycle()
    val showPublishClipSheet by viewModel.showPublishClipSheet.collectAsStateWithLifecycle()

    val selectedFriendForChat by viewModel.selectedFriendForChat.collectAsStateWithLifecycle()
    val activeChatMessages by viewModel.activeChatMessages.collectAsStateWithLifecycle()

    val selectedClipForComments by viewModel.selectedClipForComments.collectAsStateWithLifecycle()
    val activeClipComments by viewModel.activeClipComments.collectAsStateWithLifecycle()

    val leaderboard = viewModel.getLeaderboard()

    Scaffold(
        topBar = {
            GamerHeader(
                user = userProfile,
                unreadNotificationsCount = unreadNotificationsCount,
                onOpenNotifications = { viewModel.toggleNotificationsSheet(true) },
                onOpenMissions = { viewModel.toggleMissionsSheet(true) },
                onOpenFriends = { viewModel.toggleFriendsChatSheet(true) }
            )
        },
        bottomBar = {
            GamerBottomNavBar(
                selectedTab = currentTab,
                onTabSelected = { viewModel.selectTab(it) }
            )
        },
        containerColor = DarkBackground,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "screen_transition"
            ) { tabIndex ->
                when (tabIndex) {
                    0 -> HomeScreen(
                        user = userProfile,
                        games = games,
                        tournaments = tournaments,
                        clips = clips,
                        leaderboard = leaderboard,
                        onGameClick = { viewModel.openGameDetail(it) },
                        onToggleFavoriteGame = { viewModel.toggleFavoriteGame(it) },
                        onNavigateToTab = { viewModel.selectTab(it) },
                        onToggleTournamentRegister = { id, reg -> viewModel.toggleTournamentRegister(id, reg) },
                        onOpenMissions = { viewModel.toggleMissionsSheet(true) },
                        onOpenFriends = { viewModel.toggleFriendsChatSheet(true) }
                    )
                    1 -> GamesScreen(
                        games = games,
                        searchQuery = searchQuery,
                        selectedGenre = selectedGenre,
                        onSearchChange = { viewModel.setSearchQuery(it) },
                        onGenreSelect = { viewModel.setSelectedGenre(it) },
                        onGameClick = { viewModel.openGameDetail(it) },
                        onToggleFavorite = { viewModel.toggleFavoriteGame(it) }
                    )
                    2 -> RankingScreen(
                        user = userProfile,
                        leaderboard = leaderboard
                    )
                    3 -> ClipsScreen(
                        clips = clips,
                        onLikeClip = { viewModel.toggleClipLike(it) },
                        onFollowPlayer = { tag, isFollowing -> viewModel.toggleFollowPlayer(tag, isFollowing) },
                        onOpenComments = { viewModel.selectClipForComments(it) },
                        onOpenPublishClip = { viewModel.togglePublishClipSheet(true) }
                    )
                    4 -> ProfileScreen(
                        user = userProfile,
                        favoriteGames = games.filter { it.isFavorite },
                        userClips = clips.filter { it.playerTag == (userProfile?.gamerTag ?: "Vortex_Alex") }
                    )
                }
            }
        }

        // Dialogs & Sheets Overlays
        selectedGameDetail?.let { game ->
            GameDetailDialog(
                game = game,
                onDismiss = { viewModel.openGameDetail(null) },
                onToggleFavorite = { viewModel.toggleFavoriteGame(it) }
            )
        }

        if (showMissionsSheet) {
            MissionsDialog(
                missions = missions,
                onClaimMission = { viewModel.claimMission(it) },
                onDismiss = { viewModel.toggleMissionsSheet(false) }
            )
        }

        if (showNotificationsSheet) {
            NotificationsDialog(
                notifications = notifications,
                onDismiss = { viewModel.toggleNotificationsSheet(false) }
            )
        }

        if (showFriendsChatSheet) {
            FriendsChatDialog(
                friends = friends,
                selectedFriend = selectedFriendForChat,
                messages = activeChatMessages,
                onSelectFriend = { viewModel.selectFriendForChat(it) },
                onSendMessage = { friendId, text -> viewModel.sendChatMessage(friendId, text) },
                onAddFriend = { tag, name -> viewModel.addFriend(tag, name) },
                onDismiss = { viewModel.toggleFriendsChatSheet(false) }
            )
        }

        if (showPublishClipSheet) {
            PublishClipDialog(
                games = games,
                onPublish = { caption, gameTitle -> viewModel.publishClip(caption, gameTitle) },
                onDismiss = { viewModel.togglePublishClipSheet(false) }
            )
        }

        selectedClipForComments?.let { clip ->
            CommentsDialog(
                clip = clip,
                comments = activeClipComments,
                onPostComment = { clipId, text -> viewModel.postComment(clipId, text) },
                onDismiss = { viewModel.selectClipForComments(null) }
            )
        }
    }
}
