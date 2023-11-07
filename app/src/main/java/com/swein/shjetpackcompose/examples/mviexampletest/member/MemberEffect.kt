package com.swein.shjetpackcompose.examples.mviexampletest.member

import com.swein.shjetpackcompose.examples.mviexampletest.mviscaffold.Effect

sealed class MemberEffect: Effect {

    data class OnToast(val message: String): MemberEffect()
}