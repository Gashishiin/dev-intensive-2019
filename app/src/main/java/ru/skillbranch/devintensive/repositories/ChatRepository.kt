package ru.skillbranch.devintensive.repositories

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.data.managers.CacheManager
import ru.skillbranch.devintensive.extensions.shortFormat
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.models.data.ChatType

object ChatRepository {
    private val chats = CacheManager.loadChats()
    private lateinit var archiveChatItem: ChatItem

    fun loadChats(): MutableLiveData<List<Chat>> {
        return chats
    }

    fun find(chatId: String): Chat? {
        val ind = chats.value!!.indexOfFirst { it.id == chatId }
        return chats.value!!.getOrNull(ind)
    }

    fun update(chat: Chat) {
        val copy = chats.value!!.toMutableList()
        val ind = chats.value!!.indexOfFirst { it.id == chat.id }
        if (ind == -1) return
        copy[ind] = chat
        chats.value = copy
    }

    fun loadArchive(): ChatItem {
        updateArchiveItem()
        return archiveChatItem
    }

    fun updateArchiveItem() {
        val archivedChats = getArchivedChats()
        val messageCount = archivedChats.sumBy { it.messages.count{!it.isReaded} }
        val lastChat = if (archivedChats.isEmpty()) null else archivedChats.sortedBy { it.lastMessageDate() }.last()
        archiveChatItem = ChatItem("-1",
            null,
            "",
            "Архив",
            lastChat?.lastMessageShort()?.first ?: "",
            messageCount,
            lastChat?.lastMessageDate()?.shortFormat(),
            false,
            ChatType.ARCHIVE,
            lastChat?.lastMessageShort()?.second ?: "")
    }

    private fun getArchivedChats() : List<Chat> {
        return chats.value!!.filter{it.isArchived}
    }
}