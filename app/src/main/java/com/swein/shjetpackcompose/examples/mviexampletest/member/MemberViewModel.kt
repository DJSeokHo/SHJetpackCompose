package com.swein.shjetpackcompose.examples.mviexampletest.member

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.swein.shjetpackcompose.examples.mviexampletest.mviscaffold.MVIViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class MemberViewModel: MVIViewModel<MemberAction, MemberState, MemberEffect>() {

    override fun onAction(action: MemberAction, currentState: MemberState) {

        when (action) {

            MemberAction.Login -> {
                login()
            }

            MemberAction.Logout -> {
                logout()
            }

            MemberAction.Favorite -> {

                val data = (currentState as MemberState.LoginSuccess).data
                favorite(data)
            }
        }
    }

    override fun initialState(): MemberState {
        return MemberState.None
    }

    private fun login() {

        MemberService.login()
            .onStart {
                emitState(MemberState.Loading)
            }
            .catch { excetpion ->
                emitEffect(MemberEffect.OnToast(excetpion.message ?: "error"))
            }
            .onEach { memberData: MemberData ->
                emitEffect(MemberEffect.OnToast("login success"))
                emitState(MemberState.LoginSuccess(memberData))
            }
            .launchIn(viewModelScope)
    }

    private fun favorite(currentData: MemberData) {

       MemberService.favorite(currentData)
           .catch { exception ->
               emitEffect(MemberEffect.OnToast(exception.message ?: "error"))
           }
           .onEach { memberData: MemberData ->
               emitState(MemberState.LoginSuccess(memberData))
           }
           .launchIn(viewModelScope)
    }

    private fun logout() {
        MemberService.logout()
            .onStart {
                emitState(MemberState.Loading)
            }
            .catch { exception ->
                emitEffect(MemberEffect.OnToast(exception.message ?: "error"))
            }
            .onEach {
                emitEffect(MemberEffect.OnToast("logout success"))
                emitState(MemberState.LogoutSuccess)
            }
            .launchIn(viewModelScope)
    }
}

object MemberService {
    fun login(): Flow<MemberData> {

        Log.d("???", "login")
        return flow {

            Log.d("???", "delay 1000")

            delay(1000)

            Log.d("???", "flow")

//            throw Exception("no data")

            val data = MemberData(
                profileUrl = "https://dl.dropboxusercontent.com/scl/fi/8nql9cebebg5tbhipyt2n/coding_with_cat_icon.jpeg?rlkey=8osp6elc8sicy2nt2vgepm6dt&dl=0",
                nickname = "Coding with cat",
                favorite = false
            )
            emit(data)

        }.flowOn(Dispatchers.IO)
    }

    fun favorite(currentData: MemberData): Flow<MemberData> {

        return flow {
            val newData = currentData.copy(favorite = !currentData.favorite)
            emit(newData)

        }.flowOn(Dispatchers.IO)

    }

    fun logout(): Flow<Any?> {

        return flow {

            delay(500)

            Log.d("???", "flow")

            emit(null)

        }.flowOn(Dispatchers.IO)
    }
}

