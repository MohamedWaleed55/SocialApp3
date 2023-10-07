package com.gp.socialapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gp.socialapp.database.model.Post
import com.gp.socialapp.database.model.Reply
import com.gp.socialapp.database.model.relationship.PostWithReplies
import com.gp.socialapp.repository.ReplyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor(
    private val repository: ReplyRepository
) : ViewModel() {

    data class NestedReplyItem(
        val reply: Reply?,
        val replies: List<NestedReplyItem>
    )

    private fun buildNestedReplies(postId: Long, allReplies: List<Reply>): NestedReplyItem {
        val replies= allReplies.filter { it.postId == postId }
        val nestedRepliesList = buildNestedReplies(replies, null)
        return NestedReplyItem(null, replies = nestedRepliesList)
    }

    private fun buildNestedReplies(
        replies: List<Reply>,
        parentReplyId: Long?
    ): List<NestedReplyItem> {
        return replies
            .filter { it.parentReplyId == parentReplyId }
            .map { reply ->
                val nestedReplies = buildNestedReplies(replies, reply.id)
                NestedReplyItem(
                    reply = reply,
                    replies = nestedReplies
                )
            }
    }


    fun getPostwithReplies() {
        viewModelScope.launch {
            repository.getAllReplies().collect {
                val nestedReplies = buildNestedReplies(2, it)
                Log.d("TestViewModel", "getPostwithReplies: $nestedReplies")
            }
        }
    }

    fun insertdummy() {

        viewModelScope.launch(Dispatchers.IO) {

            val reply1 = Reply(
                postId = 1,
                parentReplyId = null,
                content = "Reply 1 for Post 1",
                upvotes = 5,
                downvotes = 1,
                depth = 0
            )
            val id1 = repository.insertReply(reply1)
            val reply2 = Reply(
                postId = 1,
                parentReplyId = id1,
                content = "Reply 2 for Post 1",
                upvotes = 8,
                downvotes = 2,
                depth = 0
            )
            val id2 = repository.insertReply(reply2)
            val reply3 = Reply(
                postId = 1,
                parentReplyId = id2,
                content = "Reply to Reply 1 for Post 1",
                upvotes = 1,
                downvotes = 1,
                depth = 1
            )
            val id3 = repository.insertReply(reply3)
            val reply4 = Reply(
                postId = 2,
                parentReplyId = id3,
                content = "Reply 1 for Post 2",
                upvotes = 7,
                downvotes = 2,
                depth = 0
            )
            val id4 = repository.insertReply(reply4)
            val reply5 = Reply(
                postId = 2,
                parentReplyId = id4,
                content = "Reply 2 for Post 2",
                upvotes = 9,
                downvotes = 2,
                depth = 0
            )
            val id5 = repository.insertReply(reply5)
            //crate nested replies for each post
            val reply6 = Reply(
                postId = 2,
                parentReplyId = id5,
                content = "Reply to Reply 1 for Post 2",
                upvotes = 1,
                downvotes = 1,
                depth = 1
            )
            val id6 = repository.insertReply(reply6)
            val reply7 = Reply(
                postId = 2,
                parentReplyId = id6,
                content = "Reply to Reply 2 for Post 2",
                upvotes = 1,
                downvotes = 1,
                depth = 2
            )
            val id7 = repository.insertReply(reply7)
            val reply8 = Reply(
                postId = 2,
                parentReplyId = id7,
                content = "Reply to Reply 1 for Post 2",
                upvotes = 1,
                downvotes = 1,
                depth = 1
            )
            val id8 = repository.insertReply(reply8)
            val reply9 = Reply(
                postId = 2,
                parentReplyId = id8,
                content = "Reply to Reply 2 for Post 2",
                upvotes = 1,
                downvotes = 1,
                depth = 2
            )
            val id9 = repository.insertReply(reply9)
            val reply10 = Reply(
                postId = 2,
                parentReplyId = id9,
                content = "Reply to Reply 3 for Post 2",
                upvotes = 1,
                downvotes = 1,
                depth = 3
            )
            val id10 = repository.insertReply(reply10)
            val reply11 = Reply(
                postId = 2,
                parentReplyId = id10,
                content = "Reply to Reply 4 for Post 2",
                upvotes = 1,
                downvotes = 1,
                depth = 4
            )
            val reply12 = Reply(
                postId = 2,
                parentReplyId = id5,
                content = "Reply to Reply 5 for Post 2",
                upvotes = 1,
                downvotes = 1,
                depth = 1
            )



            repository.insertReply(reply2)
            repository.insertReply(reply3)
            repository.insertReply(reply4)
            repository.insertReply(reply5)
            repository.insertReply(reply6)
            repository.insertReply(reply7)
            repository.insertReply(reply8)
            repository.insertReply(reply9)
            repository.insertReply(reply10)
            repository.insertReply(reply11)
            repository.insertReply(reply12)

        }


    }


}