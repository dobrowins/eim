package com.dobrowins.extremelyinconvenientmessenger

interface Handles<EVENT> {
    fun onEvent(event: EVENT)
}