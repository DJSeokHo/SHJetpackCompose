package com.swein.shjetpackcompose.examples.mviexample.member.model

import com.swein.shjetpackcompose.examples.mviexample.mvi.State

data class FavoriteState(
    val favorite: Boolean
): State
