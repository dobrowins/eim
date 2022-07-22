package com.dobrowins.extremelyinconvenientmessenger.common

import android.content.Context
import android.content.Intent
import com.dobrowins.extremelyinconvenientmessenger.R

interface ShareFragment {

    fun Context.showShareOptions(url: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, url)
        }
        Intent.createChooser(intent, getString(R.string.share_via_title)).run(::startActivity)
    }
}