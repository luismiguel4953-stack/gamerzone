package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GamerDao {

    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile WHERE id = 1")
    suspend fun getUserProfileSync(): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUserProfile(user: UserProfileEntity)

    // Games
    @Query("SELECT * FROM games")
    fun getAllGames(): Flow<List<GameEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<GameEntity>)

    @Query("UPDATE games SET isFavorite = :isFavorite WHERE id = :gameId")
    suspend fun updateGameFavorite(gameId: String, isFavorite: Boolean)

    // Clips
    @Query("SELECT * FROM clips")
    fun getAllClips(): Flow<List<ClipEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClip(clip: ClipEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClips(clips: List<ClipEntity>)

    @Query("UPDATE clips SET isLiked = :isLiked, likesCount = :newCount WHERE id = :clipId")
    suspend fun updateClipLike(clipId: String, isLiked: Boolean, newCount: Int)

    @Query("UPDATE clips SET isFollowing = :isFollowing WHERE playerTag = :playerTag")
    suspend fun updatePlayerFollow(playerTag: String, isFollowing: Boolean)

    @Query("UPDATE clips SET commentsCount = commentsCount + 1 WHERE id = :clipId")
    suspend fun incrementClipComments(clipId: String)

    // Clip Comments
    @Query("SELECT * FROM clip_comments WHERE clipId = :clipId ORDER BY id DESC")
    fun getCommentsForClip(clipId: String): Flow<List<ClipCommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: ClipCommentEntity)

    // Friends
    @Query("SELECT * FROM friends")
    fun getAllFriends(): Flow<List<FriendEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriends(friends: List<FriendEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriend(friend: FriendEntity)

    // Chat Messages
    @Query("SELECT * FROM chat_messages WHERE friendId = :friendId ORDER BY id ASC")
    fun getChatMessages(friendId: String): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChatMessage(message: ChatMessageEntity)

    // Missions
    @Query("SELECT * FROM missions")
    fun getAllMissions(): Flow<List<MissionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMissions(missions: List<MissionEntity>)

    @Query("UPDATE missions SET isClaimed = 1 WHERE id = :missionId")
    suspend fun claimMission(missionId: String)

    @Query("UPDATE missions SET currentProgress = :progress, isCompleted = :isCompleted WHERE id = :missionId")
    suspend fun updateMissionProgress(missionId: String, progress: Int, isCompleted: Boolean)

    // Tournaments
    @Query("SELECT * FROM tournaments")
    fun getAllTournaments(): Flow<List<TournamentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTournaments(tournaments: List<TournamentEntity>)

    @Query("UPDATE tournaments SET isRegistered = :isRegistered, registeredPlayers = registeredPlayers + :delta WHERE id = :tournamentId")
    suspend fun updateTournamentRegistration(tournamentId: String, isRegistered: Boolean, delta: Int)

    // Notifications
    @Query("SELECT * FROM notifications ORDER BY id DESC")
    fun getAllNotifications(): Flow<List<NotificationEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("UPDATE notifications SET isRead = 1")
    suspend fun markAllNotificationsAsRead()
}
