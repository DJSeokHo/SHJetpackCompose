package com.swein.shjetpackcompose.examples.mviexample.member.viewmodel

import com.swein.shjetpackcompose.examples.mviexample.member.action.FavoriteAction
import com.swein.shjetpackcompose.examples.mviexample.member.model.FavoriteState
import com.swein.shjetpackcompose.examples.mviexample.member.reducer.FavoriteReducer
import com.swein.shjetpackcompose.examples.mviexample.mvi.MVIViewModel

class FavoriteViewModel(
    reducer: FavoriteReducer = FavoriteReducer()
): MVIViewModel<FavoriteState, FavoriteAction>(
    reducer = reducer,
    stateInit = FavoriteState(false)
) {

    fun onFavorite() {
        emit(FavoriteAction.Favorite)
    }

    fun onUnFavorite() {
        emit(FavoriteAction.UnFavorite)
    }
}