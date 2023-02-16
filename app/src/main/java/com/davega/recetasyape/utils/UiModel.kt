package com.davega.recetasyape.utils

interface UiModel

open class UiAwareModel : UiModel {
    var isRedelivered: Boolean = false
}