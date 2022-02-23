package com.onegravity.knot

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CoroutineKnotState<S : State>(initialState: S) : KnotState<S> {

    private val _state = MutableStateFlow(initialState)

    override val state: StateFlow<S> = _state

    internal suspend fun changeState(newState: suspend (S) -> S) {
        _state.emit(newState(_state.value))
    }
}