package com.dobrowins.extremelyinconvenientmessenger

data class EimError(val message: String = "", val retryFunc: suspend () -> Unit = {}) {
    constructor(t: Throwable) : this(message = t.message.orEmpty())
}