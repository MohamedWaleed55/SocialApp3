package com.gp.socialapp.di

import com.gp.socialapp.repository.PostRepository
import com.gp.socialapp.repository.PostRepositoryImpl
import com.gp.socialapp.repository.ReplyRepository
import com.gp.socialapp.repository.ReplyRepositoryImpl
import com.gp.socialapp.source.local.PostLocalDataSource
import com.gp.socialapp.source.local.ReplyLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideReplyRepository(replyLocalDataSource: ReplyLocalDataSource): ReplyRepository {
        return ReplyRepositoryImpl(replyLocalDataSource)
    }
    @Provides
    fun providePostRepository(postLocalDataSource: PostLocalDataSource)
    : PostRepository = PostRepositoryImpl(postLocalDataSource)
}