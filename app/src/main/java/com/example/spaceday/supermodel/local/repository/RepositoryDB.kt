package com.example.spaceday.supermodel.local.repository

import com.example.spaceday.supermodel.local.FavoriteDAO

interface RepositoryDB {
    fun getFavorite() : FavoriteDAO
}