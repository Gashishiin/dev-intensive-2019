package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository

class MainViewModel : ViewModel() {
    private val chatRepository = ChatRepository
    private val chats = Transformations.map(chatRepository.loadChats()){chats ->
        val chatItems = chats.filter { !it.isArchived }
            .map { it.toChatItem() }.toMutableList()

        if(chats.any { it.isArchived }) {
            chatItems += getArchiveChatItem()
        }

        return@map chatItems.sortedBy { it.id.toInt() }
    }

    private fun getArchiveChatItem(): ChatItem {
        return chatRepository.loadArchive()
    }

    private val query = mutableLiveData("")

    fun getChatData() : LiveData<List<ChatItem>> {
        val result = MediatorLiveData<List<ChatItem>>()

        val filterF = {
            val queryStr = query.value!!
            val searchedChats = chats.value!!

            result.value = if(queryStr.isEmpty()) searchedChats
            else searchedChats.filter{it.title.contains(queryStr, true )}
        }

        result.addSource(chats){filterF.invoke()}
        result.addSource(query){filterF.invoke()}
        return result
    }

    /*fun addItems() {
        val newItems = DataGenerator.generateChatsWithOffset(chats.value!!.size, 5).map { it.toChatItem() }
        val copy = chats.value!!.toMutableList()
        copy.addAll(newItems)
        chats.value = copy.sortedBy { it.id.toInt() }
    }
*/
    fun addToArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = true))

    }

    fun restoreFromArchive(chatId: String) {
        val chat = chatRepository.find(chatId)
        chat ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }

    fun handleSearchQuery(text: String) {
        query.value = text
    }
}