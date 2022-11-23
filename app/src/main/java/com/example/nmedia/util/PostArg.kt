package com.example.nmedia.util

import android.os.Bundle
import com.example.nmedia.Post
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object PostArg: ReadWriteProperty<Bundle, Post?> {
    override fun setValue(thisRef: Bundle, property: KProperty<*>, value: Post?) {
        thisRef.putParcelable("post", value)
    }

    override fun getValue(thisRef: Bundle, property: KProperty<*>): Post? {
        return thisRef.getParcelable("post")
    }
}