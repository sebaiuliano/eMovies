package com.siuliano.emovies.extensions

import androidx.fragment.app.Fragment
import com.siuliano.emovies.ui.main.MainActivity

fun Fragment.showToolbar(visible: Boolean) {
    (requireActivity() as? MainActivity)?.supportActionBar?.run {
        if (visible) show() else hide()
    }
}