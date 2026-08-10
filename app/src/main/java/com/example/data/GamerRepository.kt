package com.example.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class GamerRepository(private val dao: GamerDao) {

    val userProfile: Flow<UserProfileEntity?> = dao.getUserProfile()
    val allGames: Flow<List<GameEntity>> = dao.getAllGames()
    val allClips: Flow<List<ClipEntity>> = dao.getAllClips()
    val allFriends: Flow<List<FriendEntity>> = dao.getAllFriends()
    val allMissions: Flow<List<MissionEntity>> = dao.getAllMissions()
    val allTournaments: Flow<List<TournamentEntity>> = dao.getAllTournaments()
    val allNotifications: Flow<List<NotificationEntity>> = dao.getAllNotifications()

    fun getCommentsForClip(clipId: String): Flow<List<ClipCommentEntity>> = dao.getCommentsForClip(clipId)
    fun getChatMessages(friendId: String): Flow<List<ChatMessageEntity>> = dao.getChatMessages(friendId)

    suspend fun seedInitialDataIfEmpty() {
        val existingProfile = dao.getUserProfileSync()
        if (existingProfile == null) {
            // Seed User
            dao.insertOrUpdateUserProfile(
                UserProfileEntity(
                    id = 1,
                    gamerTag = "Vortex_Alex",
                    name = "Alex 'Vortex' Cruz",
                    avatarResName = "img_gamer_avatar_1786386297606",
                    level = 48,
                    currentXp = 8400,
                    maxXp = 10000,
                    rankTitle = "Leyenda",
                    rankTier = "LEYENDA",
                    rankPoints = 10890,
                    coins = 2450,
                    wins = 342,
                    losses = 89,
                    totalMatches = 431,
                    bio = "Pro Gamer FPS & Battle Royale 🎮 Streamer en GamerZone | Top 2 Global | Squad Captain ⚡"
                )
            )

            // Seed Games
            dao.insertGames(
                listOf(
                    GameEntity(
                        id = "game_1",
                        title = "Cyberpunk Nexus 2099",
                        genre = "Action RPG / Sci-Fi",
                        rating = 4.9f,
                        activePlayers = "1.8M",
                        coverImageResName = "img_game_cyberpunk_1786386315750",
                        description = "Explora una metrópolis futurista llena de aumentos cibernéticos, armas de plasma y batallas territoriales en tiempo real.",
                        isFavorite = true,
                        platforms = "PC, PS5, Xbox Series X"
                    ),
                    GameEntity(
                        id = "game_2",
                        title = "Apex Strikers Arena",
                        genre = "Battle Royale / FPS",
                        rating = 4.8f,
                        activePlayers = "2.4M",
                        coverImageResName = "img_game_battleroyale_1786386324854",
                        description = "Compite en escuadrones de 3 leyendas con habilidades tácticas únicas. Demuestra quién domina la arena.",
                        isFavorite = true,
                        platforms = "PC, Console, Mobile"
                    ),
                    GameEntity(
                        id = "game_3",
                        title = "Valorant Omega",
                        genre = "Tactical FPS",
                        rating = 4.9f,
                        activePlayers = "3.1M",
                        coverImageResName = "img_game_cyberpunk_1786386315750",
                        description = "Shooter táctico 5v5 impulsado por habilidades de agentes y puntería de precisión millimétrica.",
                        isFavorite = false,
                        platforms = "PC, PS5"
                    ),
                    GameEntity(
                        id = "game_4",
                        title = "Elden Souls Remastered",
                        genre = "Dark Fantasy RPG",
                        rating = 4.9f,
                        activePlayers = "1.1M",
                        coverImageResName = "img_game_battleroyale_1786386324854",
                        description = "Enfréntate a jefes colosales en un reino devastado por la magia antigua y batallas PvP hardcore.",
                        isFavorite = false,
                        platforms = "PC, Console"
                    ),
                    GameEntity(
                        id = "game_5",
                        title = "Rocket Surge League",
                        genre = "Deportes / Vehículos",
                        rating = 4.6f,
                        activePlayers = "950K",
                        coverImageResName = "img_game_cyberpunk_1786386315750",
                        description = "Fútbol acrobático con coches propulsados por cohetes turbo. Acción vertiginosa y torneos eSports.",
                        isFavorite = false,
                        platforms = "Multiplataforma"
                    )
                )
            )

            // Seed Clips
            dao.insertClips(
                listOf(
                    ClipEntity(
                        id = "clip_1",
                        playerTag = "ShadowKing",
                        playerAvatarResName = "img_gamer_avatar_1786386297606",
                        gameTitle = "Cyberpunk Nexus 2099",
                        caption = "¡Increíble 1v4 clutches en la final del torneo! ⚡🔥 ¿Qué opinan de esta jugada?",
                        videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_1mb.mp4",
                        thumbnailResName = "img_game_cyberpunk_1786386315750",
                        likesCount = 1420,
                        commentsCount = 89,
                        sharesCount = 210,
                        isLiked = true,
                        isFollowing = true,
                        timestamp = "Hace 2 horas"
                    ),
                    ClipEntity(
                        id = "clip_2",
                        playerTag = "CyberQueen",
                        playerAvatarResName = "img_gamer_avatar_1786386297606",
                        gameTitle = "Apex Strikers Arena",
                        caption = "Nos robamos el Gold Shield en el último segundo del círculo final! 🎯👑",
                        videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_1mb.mp4",
                        thumbnailResName = "img_game_battleroyale_1786386324854",
                        likesCount = 980,
                        commentsCount = 42,
                        sharesCount = 115,
                        isLiked = false,
                        isFollowing = false,
                        timestamp = "Hace 5 horas"
                    ),
                    ClipEntity(
                        id = "clip_3",
                        playerTag = "Vortex_Alex",
                        playerAvatarResName = "img_gamer_avatar_1786386297606",
                        gameTitle = "Valorant Omega",
                        caption = "Headshot a través del humo para ganar la ronda de clasificación Rango Leyenda! 💥",
                        videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_1mb.mp4",
                        thumbnailResName = "img_tournament_banner_1786386306089",
                        likesCount = 2340,
                        commentsCount = 156,
                        sharesCount = 410,
                        isLiked = true,
                        isFollowing = true,
                        timestamp = "Ayer"
                    )
                )
            )

            // Seed Clip Comments
            dao.insertComment(
                ClipCommentEntity(
                    clipId = "clip_1",
                    authorName = "CyberQueen",
                    authorAvatarResName = "img_gamer_avatar_1786386297606",
                    commentText = "¡Locura de jugada! Eres una bestia Shadow 👑🔥",
                    timeAgo = "Hace 1h"
                )
            )
            dao.insertComment(
                ClipCommentEntity(
                    clipId = "clip_1",
                    authorName = "NovaStrike",
                    authorAvatarResName = "img_gamer_avatar_1786386297606",
                    commentText = "Ese reflex shot con la sniper estuvo insano 🎯",
                    timeAgo = "Hace 45m"
                )
            )

            // Seed Friends
            dao.insertFriends(
                listOf(
                    FriendEntity(
                        id = "friend_1",
                        name = "ShadowKing",
                        gamerTag = "ShadowKing",
                        avatarResName = "img_gamer_avatar_1786386297606",
                        isOnline = true,
                        statusText = "En partida • Cyberpunk Nexus",
                        level = 50,
                        rank = "LEYENDA"
                    ),
                    FriendEntity(
                        id = "friend_2",
                        name = "Elena 'CyberQueen'",
                        gamerTag = "CyberQueen",
                        avatarResName = "img_gamer_avatar_1786386297606",
                        isOnline = true,
                        statusText = "En lobby • Apex Strikers",
                        level = 45,
                        rank = "MAESTRO"
                    ),
                    FriendEntity(
                        id = "friend_3",
                        name = "Mateo 'NovaStrike'",
                        gamerTag = "NovaStrike",
                        avatarResName = "img_gamer_avatar_1786386297606",
                        isOnline = false,
                        statusText = "Desconectado (Hace 2h)",
                        level = 39,
                        rank = "DIAMANTE"
                    ),
                    FriendEntity(
                        id = "friend_4",
                        name = "Lucas 'Phantom'",
                        gamerTag = "PhantomRider",
                        avatarResName = "img_gamer_avatar_1786386297606",
                        isOnline = true,
                        statusText = "En línea",
                        level = 32,
                        rank = "PLATINO"
                    )
                )
            )

            // Seed Initial Chat Messages
            dao.insertChatMessage(
                ChatMessageEntity(
                    friendId = "friend_1",
                    senderName = "ShadowKing",
                    messageText = "¡Hola Vortex! ¿Jugamos el torneo de esta tarde?",
                    time = "14:20",
                    isFromUser = false
                )
            )
            dao.insertChatMessage(
                ChatMessageEntity(
                    friendId = "friend_1",
                    senderName = "Vortex_Alex",
                    messageText = "¡De una! Ya estoy armado para la semifinal 🔥",
                    time = "14:22",
                    isFromUser = true
                )
            )

            // Seed Missions
            dao.insertMissions(
                listOf(
                    MissionEntity(
                        id = "m_1",
                        title = "Juega 3 partidas",
                        description = "Completa 3 partidas competitivas en cualquier juego.",
                        rewardXp = 300,
                        rewardCoins = 150,
                        currentProgress = 3,
                        maxProgress = 3,
                        isCompleted = true,
                        isClaimed = false,
                        isDaily = true
                    ),
                    MissionEntity(
                        id = "m_2",
                        title = "Gana 2 partidas",
                        description = "Consigue 2 victorias en modo Ranked.",
                        rewardXp = 500,
                        rewardCoins = 250,
                        currentProgress = 1,
                        maxProgress = 2,
                        isCompleted = false,
                        isClaimed = false,
                        isDaily = true
                    ),
                    MissionEntity(
                        id = "m_3",
                        title = "Publica un clip gaming",
                        description = "Sube un video con tu mejor jugada a la sección Clips.",
                        rewardXp = 400,
                        rewardCoins = 200,
                        currentProgress = 1,
                        maxProgress = 1,
                        isCompleted = true,
                        isClaimed = false,
                        isDaily = true
                    ),
                    MissionEntity(
                        id = "m_4",
                        title = "Consigue 10 likes en tu clip",
                        description = "Alcanza 10 me gusta en cualquiera de tus publicaciones.",
                        rewardXp = 800,
                        rewardCoins = 500,
                        currentProgress = 8,
                        maxProgress = 10,
                        isCompleted = false,
                        isClaimed = false,
                        isDaily = false
                    ),
                    MissionEntity(
                        id = "m_5",
                        title = "Conéctate con 3 amigos",
                        description = "Envía un mensaje o juega en escuadrón con tus amigos.",
                        rewardXp = 600,
                        rewardCoins = 300,
                        currentProgress = 2,
                        maxProgress = 3,
                        isCompleted = false,
                        isClaimed = false,
                        isDaily = false
                    )
                )
            )

            // Seed Tournaments
            dao.insertTournaments(
                listOf(
                    TournamentEntity(
                        id = "t_1",
                        title = "GamerZone World Championship 2026",
                        gameTitle = "Apex Strikers Arena",
                        prizePool = "$50,000 USD",
                        registeredPlayers = 214,
                        maxPlayers = 256,
                        startDate = "Hoy, 18:00 UTC",
                        bannerResName = "img_tournament_banner_1786386306089",
                        isRegistered = true
                    ),
                    TournamentEntity(
                        id = "t_2",
                        title = "Night City Cyber Showdown",
                        gameTitle = "Cyberpunk Nexus 2099",
                        prizePool = "$15,000 USD",
                        registeredPlayers = 58,
                        maxPlayers = 64,
                        startDate = "Mañana, 20:00 UTC",
                        bannerResName = "img_game_cyberpunk_1786386315750",
                        isRegistered = false
                    ),
                    TournamentEntity(
                        id = "t_3",
                        title = "Valorant Tactical Masters",
                        gameTitle = "Valorant Omega",
                        prizePool = "$25,000 USD",
                        registeredPlayers = 110,
                        maxPlayers = 128,
                        startDate = "En 3 días",
                        bannerResName = "img_game_battleroyale_1786386324854",
                        isRegistered = false
                    )
                )
            )

            // Seed Notifications
            dao.insertNotifications(
                listOf(
                    NotificationEntity(
                        title = "Amigo Conectado 🟢",
                        message = "ShadowKing se ha conectado y entró a partida en Cyberpunk Nexus.",
                        timeAgo = "Hace 10 min",
                        type = "friend",
                        isRead = false
                    ),
                    NotificationEntity(
                        title = "¡Nuevo Like en tu Clip! ❤️",
                        message = "A CyberQueen le ha gustado tu clip 'Headshot a través del humo'.",
                        timeAgo = "Hace 25 min",
                        type = "like",
                        isRead = false
                    ),
                    NotificationEntity(
                        title = "Torneo Próximo 🏆",
                        message = "El torneo GamerZone World Championship arranca en 1 hora. ¡Prepárate!",
                        timeAgo = "Hace 1 hora",
                        type = "tournament",
                        isRead = false
                    ),
                    NotificationEntity(
                        title = "¡Ascenso de Rango! 🎉",
                        message = "¡Felicidades! Has alcanzado el rango supremo LEYENDA con 10,890 Puntos.",
                        timeAgo = "Hace 1 día",
                        type = "rank",
                        isRead = true
                    )
                )
            )
        }
    }

    // Interactive Functions

    suspend fun toggleFavoriteGame(gameId: String, currentFav: Boolean) {
        dao.updateGameFavorite(gameId, !currentFav)
    }

    suspend fun toggleClipLike(clipId: String, isLiked: Boolean, currentCount: Int) {
        val newCount = if (isLiked) currentCount - 1 else currentCount + 1
        dao.updateClipLike(clipId, !isLiked, newCount.coerceAtLeast(0))
        if (!isLiked) {
            dao.insertNotification(
                NotificationEntity(
                    title = "¡Like registrado! ❤️",
                    message = "Has le diste me gusta al clip gaming.",
                    timeAgo = "Ahora",
                    type = "like",
                    isRead = false
                )
            )
        }
    }

    suspend fun toggleFollowPlayer(playerTag: String, isFollowing: Boolean) {
        dao.updatePlayerFollow(playerTag, !isFollowing)
    }

    suspend fun addCommentToClip(clipId: String, text: String) {
        val user = dao.getUserProfileSync() ?: return
        dao.insertComment(
            ClipCommentEntity(
                clipId = clipId,
                authorName = user.gamerTag,
                authorAvatarResName = user.avatarResName,
                commentText = text,
                timeAgo = "Ahora"
            )
        )
        dao.incrementClipComments(clipId)
    }

    suspend fun publishNewClip(caption: String, gameTitle: String) {
        val user = dao.getUserProfileSync() ?: return
        val newClip = ClipEntity(
            id = "clip_${System.currentTimeMillis()}",
            playerTag = user.gamerTag,
            playerAvatarResName = user.avatarResName,
            gameTitle = gameTitle,
            caption = caption,
            videoUrl = "https://sample-videos.com/video321/mp4/720/big_buck_bunny_720p_1mb.mp4",
            thumbnailResName = "img_tournament_banner_1786386306089",
            likesCount = 1,
            commentsCount = 0,
            sharesCount = 0,
            isLiked = true,
            isFollowing = true,
            timestamp = "Ahora mismo"
        )
        dao.insertClip(newClip)

        // Give XP and coins for publishing clip
        addXpAndCoins(user, 300, 150)

        dao.insertNotification(
            NotificationEntity(
                title = "Clip Publicado 🎥",
                message = "Tu clip '$caption' ya está visible para la comunidad. +300 XP",
                timeAgo = "Ahora",
                type = "like",
                isRead = false
            )
        )
    }

    suspend fun claimMissionReward(missionId: String) {
        val missions = dao.getAllMissions().first()
        val mission = missions.find { it.id == missionId } ?: return
        if (mission.isCompleted && !mission.isClaimed) {
            dao.claimMission(missionId)
            val user = dao.getUserProfileSync() ?: return
            addXpAndCoins(user, mission.rewardXp, mission.rewardCoins)

            dao.insertNotification(
                NotificationEntity(
                    title = "¡Recompensa Reclamada! 🏅",
                    message = "Ganaste +${mission.rewardXp} XP y +${mission.rewardCoins} Monedas por '${mission.title}'.",
                    timeAgo = "Ahora",
                    type = "rank",
                    isRead = false
                )
            )
        }
    }

    private suspend fun addXpAndCoins(user: UserProfileEntity, xpToAdd: Int, coinsToAdd: Int) {
        var newXp = user.currentXp + xpToAdd
        var newLevel = user.level
        var maxXp = user.maxXp
        if (newXp >= maxXp) {
            newXp -= maxXp
            newLevel += 1
            maxXp = (maxXp * 1.15f).toInt()
        }
        val updated = user.copy(
            level = newLevel,
            currentXp = newXp,
            maxXp = maxXp,
            coins = user.coins + coinsToAdd
        )
        dao.insertOrUpdateUserProfile(updated)
    }

    suspend fun toggleTournamentRegister(tournamentId: String, currentlyRegistered: Boolean) {
        val delta = if (currentlyRegistered) -1 else 1
        dao.updateTournamentRegistration(tournamentId, !currentlyRegistered, delta)
        val text = if (!currentlyRegistered) "Inscripto exitosamente" else "Inscripción cancelada"
        dao.insertNotification(
            NotificationEntity(
                title = "Torneo Actualizado 🏆",
                message = "$text en el torneo.",
                timeAgo = "Ahora",
                type = "tournament",
                isRead = false
            )
        )
    }

    suspend fun sendChatMessage(friendId: String, text: String) {
        val user = dao.getUserProfileSync() ?: return
        dao.insertChatMessage(
            ChatMessageEntity(
                friendId = friendId,
                senderName = user.gamerTag,
                messageText = text,
                time = "Ahora",
                isFromUser = true
            )
        )
    }

    suspend fun addNewFriend(gamerTag: String, name: String) {
        val newFriend = FriendEntity(
            id = "friend_${System.currentTimeMillis()}",
            name = name.ifBlank { gamerTag },
            gamerTag = if (gamerTag.startsWith("@")) gamerTag else "@$gamerTag",
            avatarResName = "img_gamer_avatar_1786386297606",
            isOnline = true,
            statusText = "En línea • GamerZone",
            level = (10..50).random(),
            rank = listOf("PLATINO", "DIAMANTE", "MAESTRO").random()
        )
        dao.insertFriend(newFriend)
        dao.insertNotification(
            NotificationEntity(
                title = "Amigo Añadido 👥",
                message = "Has agregado a ${newFriend.gamerTag} a tu lista de amigos.",
                timeAgo = "Ahora",
                type = "friend",
                isRead = false
            )
        )
    }

    suspend fun markAllNotificationsAsRead() {
        dao.markAllNotificationsAsRead()
    }

    fun getStaticLeaderboard(currentUser: UserProfileEntity?): List<LeaderboardUser> {
        val myPts = currentUser?.rankPoints ?: 10890
        val myLevel = currentUser?.level ?: 48
        val myWins = currentUser?.wins ?: 342

        return listOf(
            LeaderboardUser(1, "ShadowKing", "@ShadowKing", 50, 12450, 512, "LEYENDA", "img_gamer_avatar_1786386297606"),
            LeaderboardUser(2, currentUser?.name ?: "Alex 'Vortex'", "@${currentUser?.gamerTag ?: "Vortex_Alex"}", myLevel, myPts, myWins, currentUser?.rankTier ?: "LEYENDA", "img_gamer_avatar_1786386297606", isCurrentUser = true),
            LeaderboardUser(3, "Elena 'CyberQueen'", "@CyberQueen", 45, 9800, 298, "MAESTRO", "img_gamer_avatar_1786386297606"),
            LeaderboardUser(4, "Mateo 'NovaStrike'", "@NovaStrike", 39, 8200, 240, "DIAMANTE", "img_gamer_avatar_1786386297606"),
            LeaderboardUser(5, "Lucas 'Phantom'", "@PhantomRider", 32, 6400, 180, "PLATINO", "img_gamer_avatar_1786386297606"),
            LeaderboardUser(6, "Sofia 'Blaze'", "@BlazeMaster", 28, 4900, 130, "ORO", "img_gamer_avatar_1786386297606"),
            LeaderboardUser(7, "Carlos 'Neon'", "@NeonHunter", 21, 3100, 85, "PLATA", "img_gamer_avatar_1786386297606"),
            LeaderboardUser(8, "Kevin 'Rookie'", "@RookieSniper", 14, 1200, 25, "BRONCE", "img_gamer_avatar_1786386297606")
        )
    }
}
