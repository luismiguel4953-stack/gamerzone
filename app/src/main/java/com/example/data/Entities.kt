package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val gamerTag: String,
    val name: String,
    val avatarResName: String,
    val level: Int,
    val currentXp: Int,
    val maxXp: Int,
    val rankTitle: String, // e.g. "Leyenda", "Diamante", "Maestro"
    val rankTier: String,  // e.g. "LEYENDA", "DIAMANTE", "PLATINO"
    val rankPoints: Int,
    val coins: Int,
    val wins: Int,
    val losses: Int,
    val totalMatches: Int,
    val bio: String,
    val achievementsCount: Int = 12
)

@Entity(tableName = "games")
data class GameEntity(
    @PrimaryKey val id: String,
    val title: String,
    val genre: String,
    val rating: Float,
    val activePlayers: String,
    val coverImageResName: String,
    val description: String,
    val isFavorite: Boolean = false,
    val platforms: String = "PC, Console, Mobile"
)

@Entity(tableName = "clips")
data class ClipEntity(
    @PrimaryKey val id: String,
    val playerTag: String,
    val playerAvatarResName: String,
    val gameTitle: String,
    val caption: String,
    val videoUrl: String,
    val thumbnailResName: String,
    val likesCount: Int,
    val commentsCount: Int,
    val sharesCount: Int,
    val isLiked: Boolean = false,
    val isFollowing: Boolean = false,
    val timestamp: String
)

@Entity(tableName = "clip_comments")
data class ClipCommentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val clipId: String,
    val authorName: String,
    val authorAvatarResName: String,
    val commentText: String,
    val timeAgo: String
)

@Entity(tableName = "friends")
data class FriendEntity(
    @PrimaryKey val id: String,
    val name: String,
    val gamerTag: String,
    val avatarResName: String,
    val isOnline: Boolean,
    val statusText: String, // e.g. "En partida - Cyberpunk 2077" or "En línea"
    val level: Int,
    val rank: String
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val friendId: String,
    val senderName: String,
    val messageText: String,
    val time: String,
    val isFromUser: Boolean
)

@Entity(tableName = "missions")
data class MissionEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val rewardXp: Int,
    val rewardCoins: Int,
    val currentProgress: Int,
    val maxProgress: Int,
    val isCompleted: Boolean,
    val isClaimed: Boolean,
    val isDaily: Boolean // true = daily, false = weekly
)

@Entity(tableName = "tournaments")
data class TournamentEntity(
    @PrimaryKey val id: String,
    val title: String,
    val gameTitle: String,
    val prizePool: String,
    val registeredPlayers: Int,
    val maxPlayers: Int,
    val startDate: String,
    val bannerResName: String,
    val isRegistered: Boolean = false
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val message: String,
    val timeAgo: String,
    val type: String, // "friend", "like", "comment", "tournament", "rank"
    val isRead: Boolean = false
)

data class LeaderboardUser(
    val position: Int,
    val name: String,
    val gamerTag: String,
    val level: Int,
    val points: Int,
    val wins: Int,
    val rankTier: String,
    val avatarResName: String,
    val isCurrentUser: Boolean = false
)
