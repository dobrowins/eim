package com.dobrowins.extremelyinconvenientmessenger.common

data class EimError(val message: String = "", val exception: RuntimeException? = null)