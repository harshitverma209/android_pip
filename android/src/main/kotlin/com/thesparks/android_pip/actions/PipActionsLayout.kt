package com.thesparks.android_pip.actions

import android.app.RemoteAction
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi

enum class PipActionsLayout(
    private val defaultActions: List<PipAction>
) {
    NONE(emptyList()),
    MEDIA(listOf(PipAction.PREVIOUS, PipAction.PAUSE, PipAction.NEXT)),
    MEDIA_ONLY_PAUSE(listOf(PipAction.PAUSE)),
    MEDIA_LIVE(listOf(PipAction.LIVE, PipAction.PAUSE)),
    MEDIA_WITH_SEEK_10(listOf(PipAction.REWIND, PipAction.PAUSE, PipAction.FORWARD));

    var actions: MutableList<PipAction> = defaultActions.toMutableList()
        private set

    @RequiresApi(Build.VERSION_CODES.O)
    fun remoteActions(context: Context): MutableList<RemoteAction> =
        remoteActions(context, actions)

    fun resetActions() {
        actions = defaultActions.toMutableList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun toggleToAfterAction(pipAction: PipAction) {
        pipAction.afterAction()?.let { afterAction ->
            val a = actions.firstOrNull{ it == pipAction }
            a?.let {
                val i = actions.indexOf(a)
                actions[i] = afterAction
            }
        }
    }

    companion object {

        @RequiresApi(Build.VERSION_CODES.O)
        fun remoteActions(context: Context, actions: MutableList<PipAction>): MutableList<RemoteAction> =
            actions.map { a -> a.toRemoteAction(context) }.toMutableList()

    }
}