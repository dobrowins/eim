package com.dobrowins.extremelyinconvenientmessenger.common

interface Handles<EVENT> {

    fun onEvent(event: EVENT)
}