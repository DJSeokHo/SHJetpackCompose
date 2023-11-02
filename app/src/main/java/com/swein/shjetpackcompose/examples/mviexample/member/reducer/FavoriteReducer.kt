package com.swein.shjetpackcompose.examples.mviexample.member.reducer

import com.swein.shjetpackcompose.examples.mviexample.member.action.FavoriteAction
import com.swein.shjetpackcompose.examples.mviexample.member.model.FavoriteState
import com.swein.shjetpackcompose.examples.mviexample.mvi.Reducer


class FavoriteReducer: Reducer<FavoriteState, FavoriteAction> {

    override fun reduce(state: FavoriteState, action: FavoriteAction): FavoriteState {

        return when (action) {
            FavoriteAction.Favorite -> state.copy(favorite = true)
            FavoriteAction.UnFavorite -> state.copy(favorite = false)
        }

    }

}