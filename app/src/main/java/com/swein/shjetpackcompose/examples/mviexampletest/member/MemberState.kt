package com.swein.shjetpackcompose.examples.mviexampletest.member

import com.swein.shjetpackcompose.examples.mviexampletest.mviscaffold.State

sealed class MemberState: State {
    data object Loading: MemberState() // 每次都一样即可
    data object None: MemberState() // before login
    data class LoginSuccess(val data: MemberData): MemberState() // 登录信息不同，所以用 data class
    data object LogoutSuccess: MemberState() // 登录信息不同，所以用 data class
}
