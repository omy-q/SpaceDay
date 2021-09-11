package com.example.spaceday.supermodel.local.repository

import com.example.spaceday.supermodel.local.DataBase
import com.example.spaceday.supermodel.local.FavoriteDAO

class RepositoryDBImpl(private val db :DataBase) : RepositoryDB{
    override fun getFavorite(): FavoriteDAO =db.favoriteDAO()
}