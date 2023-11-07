package com.swein.shjetpackcompose.examples.mviexampletest.member

import com.swein.shjetpackcompose.examples.mviexampletest.mviscaffold.Action

sealed class MemberAction: Action {
    data object Login: MemberAction()

    data object Favorite: MemberAction()

    data object Logout: MemberAction()
}