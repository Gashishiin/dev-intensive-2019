package ru.skillbranch.devintensive.extensions

import java.lang.StringBuilder

fun String.truncate(num: Int = 16): String {
    return if (this.trimEnd().length <= num) this.trimEnd()
    else {
        this.trimEnd().substring(0, num).trimEnd()+ "..."
    }
}

fun String.stripHtml():String {
    return this
        .replace(" +".toRegex(), " ")
        .replace("&amp;|&lt;|&gt;|&quot;|&apos;|&nbsp;".toRegex(),"")
        .replace("<[^>]*>".toRegex(),"")
}