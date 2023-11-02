package com.swein.shjetpackcompose.examples.mviexample.member.action
import com.swein.shjetpackcompose.examples.mviexample.mvi.Action

sealed interface FavoriteAction: Action {
    data object Favorite: FavoriteAction
    data object UnFavorite: FavoriteAction
}