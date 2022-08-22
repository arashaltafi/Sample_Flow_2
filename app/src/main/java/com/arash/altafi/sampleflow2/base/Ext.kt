package com.arash.altafi.sampleflow2.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


fun <T> flowIO(
    block: suspend FlowCollector<T>.() -> Unit,
) = flow { block() }.flowOn(Dispatchers.IO)

fun <T> channelFlowIO(
    block: suspend ProducerScope<T>.() -> Unit,
) = channelFlow { block() }.flowOn(Dispatchers.IO)

fun <T> flowCompute(
    block: suspend FlowCollector<T>.() -> Unit,
) = flow { block() }.flowOn(Dispatchers.Default)

fun <T> flowMain(
    block: suspend FlowCollector<T>.() -> Unit,
) = flow { block() }.flowOn(Dispatchers.Main)