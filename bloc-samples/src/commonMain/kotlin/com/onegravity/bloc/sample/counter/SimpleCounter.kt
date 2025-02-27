package com.onegravity.bloc.sample.counter

import com.onegravity.bloc.bloc
import com.onegravity.bloc.context.BlocContext
import com.onegravity.bloc.utils.logger

object SimpleCounter {
    sealed class Action {
        data class Increment(val value: Int = 1): Action()
        data class Decrement(val value: Int = 1): Action()
    }

    fun bloc(context: BlocContext) =
        bloc<Int, Action>(context, 1) {
            thunkMatching<Action.Increment> { _, action, dispatch ->
                logger.w("thunk 1 started")
                dispatch(action)
                dispatch(action)
                logger.w("thunk 1 ended")
            }
            thunkMatching<Action.Increment> { _, action, dispatch ->
                logger.w("thunk 2 started")
                dispatch(action)
                logger.w("thunk 2 ended")
            }
            thunk { _, action, dispatch ->
                logger.w("thunk 3 started")
                dispatch(action)
                logger.w("thunk 3 ended")
            }
            reduce { state, action ->
                logger.w("reduce started")
                val result = when (action) {
                    is Action.Increment -> state + action.value
                    is Action.Decrement -> (state - action.value).coerceAtLeast(0)
                }
                logger.w("reduce ended")
                result
            }
        }

// shortest possible implementation
//    fun bloc(context: BlocContext) =
//        bloc<Int, Boolean>(context, 1) {
//            reduce { state, action -> state + if (action) 1 else -1 }
//        }

}
