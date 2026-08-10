package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.AppDatabase
import com.example.data.ChatMessageEntity
import com.example.data.ClipCommentEntity
import com.example.data.ClipEntity
import com.example.data.FriendEntity
import com.example.data.GameEntity
import com.example.data.GamerRepository
import com.example.data.LeaderboardUser
import com.example.data.MissionEntity
import com.example.data.NotificationEntity
import com.example.data.TournamentEntity
import com.example.data.UserProfileEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val repository = GamerRepository(db.gamerDao())

    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val games: StateFlow<List<GameEntity>> = repository.allGames
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val clips: StateFlow<List<ClipEntity>> = repository.allClips
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val friends: StateFlow<List<FriendEntity>> = repository.allFriends
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val missions: StateFlow<List<MissionEntity>> = repository.allMissions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val tournaments: StateFlow<List<TournamentEntity>> = repository.allTournaments
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notifications: StateFlow<List<NotificationEntity>> = repository.allNotifications
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadNotificationsCount: StateFlow<Int> = repository.allNotifications
        .map { list -> list.count { !it.isRead } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // UI state
    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedGenre = MutableStateFlow("Todos")
    val selectedGenre: StateFlow<String> = _selectedGenre.asStateFlow()

    private val _selectedGameDetail = MutableStateFlow<GameEntity?>(null)
    val selectedGameDetail: StateFlow<GameEntity?> = _selectedGameDetail.asStateFlow()

    private val _showMissionsSheet = MutableStateFlow(false)
    val showMissionsSheet: StateFlow<Boolean> = _showMissionsSheet.asStateFlow()

    private val _showNotificationsSheet = MutableStateFlow(false)
    val showNotificationsSheet: StateFlow<Boolean> = _showNotificationsSheet.asStateFlow()

    private val _showFriendsChatSheet = MutableStateFlow(false)
    val showFriendsChatSheet: StateFlow<Boolean> = _showFriendsChatSheet.asStateFlow()

    private val _showPublishClipSheet = MutableStateFlow(false)
    val showPublishClipSheet: StateFlow<Boolean> = _showPublishClipSheet.asStateFlow()

    private val _selectedFriendForChat = MutableStateFlow<FriendEntity?>(null)
    val selectedFriendForChat: StateFlow<FriendEntity?> = _selectedFriendForChat.asStateFlow()

    private val _selectedClipForComments = MutableStateFlow<ClipEntity?>(null)
    val selectedClipForComments: StateFlow<ClipEntity?> = _selectedClipForComments.asStateFlow()

    private val _activeChatMessages = MutableStateFlow<List<ChatMessageEntity>>(emptyList())
    val activeChatMessages: StateFlow<List<ChatMessageEntity>> = _activeChatMessages.asStateFlow()

    private val _activeClipComments = MutableStateFlow<List<ClipCommentEntity>>(emptyList())
    val activeClipComments: StateFlow<List<ClipCommentEntity>> = _activeClipComments.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
        }
    }

    fun selectTab(index: Int) {
        _currentTab.value = index
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setSelectedGenre(genre: String) {
        _selectedGenre.value = genre
    }

    fun openGameDetail(game: GameEntity?) {
        _selectedGameDetail.value = game
    }

    fun toggleMissionsSheet(show: Boolean) {
        _showMissionsSheet.value = show
    }

    fun toggleNotificationsSheet(show: Boolean) {
        _showNotificationsSheet.value = show
        if (show) {
            viewModelScope.launch {
                repository.markAllNotificationsAsRead()
            }
        }
    }

    fun toggleFriendsChatSheet(show: Boolean) {
        _showFriendsChatSheet.value = show
    }

    fun togglePublishClipSheet(show: Boolean) {
        _showPublishClipSheet.value = show
    }

    fun selectFriendForChat(friend: FriendEntity?) {
        _selectedFriendForChat.value = friend
        if (friend != null) {
            viewModelScope.launch {
                repository.getChatMessages(friend.id).collect { messages ->
                    _activeChatMessages.value = messages
                }
            }
        } else {
            _activeChatMessages.value = emptyList()
        }
    }

    fun selectClipForComments(clip: ClipEntity?) {
        _selectedClipForComments.value = clip
        if (clip != null) {
            viewModelScope.launch {
                repository.getCommentsForClip(clip.id).collect { comments ->
                    _activeClipComments.value = comments
                }
            }
        } else {
            _activeClipComments.value = emptyList()
        }
    }

    fun toggleFavoriteGame(game: GameEntity) {
        viewModelScope.launch {
            repository.toggleFavoriteGame(game.id, game.isFavorite)
        }
    }

    fun toggleClipLike(clip: ClipEntity) {
        viewModelScope.launch {
            repository.toggleClipLike(clip.id, clip.isLiked, clip.likesCount)
        }
    }

    fun toggleFollowPlayer(playerTag: String, isFollowing: Boolean) {
        viewModelScope.launch {
            repository.toggleFollowPlayer(playerTag, isFollowing)
        }
    }

    fun postComment(clipId: String, text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.addCommentToClip(clipId, text)
        }
    }

    fun publishClip(caption: String, gameTitle: String) {
        if (caption.isBlank()) return
        viewModelScope.launch {
            repository.publishNewClip(caption, gameTitle)
            _showPublishClipSheet.value = false
        }
    }

    fun claimMission(missionId: String) {
        viewModelScope.launch {
            repository.claimMissionReward(missionId)
        }
    }

    fun toggleTournamentRegister(tournamentId: String, isRegistered: Boolean) {
        viewModelScope.launch {
            repository.toggleTournamentRegister(tournamentId, isRegistered)
        }
    }

    fun sendChatMessage(friendId: String, messageText: String) {
        if (messageText.isBlank()) return
        viewModelScope.launch {
            repository.sendChatMessage(friendId, messageText)
        }
    }

    fun addFriend(gamerTag: String, name: String) {
        if (gamerTag.isBlank()) return
        viewModelScope.launch {
            repository.addNewFriend(gamerTag, name)
        }
    }

    fun getLeaderboard(): List<LeaderboardUser> {
        return repository.getStaticLeaderboard(userProfile.value)
    }
}
