package org.archguard.core.internal

import org.jetbrains.annotations.Nullable

interface Element {
    fun getName(): String?

    @Nullable
    fun getDescription(): String?
}

interface HierarchicalElement : Element {
    val parent: HierarchicalElement?
    val children: List<out HierarchicalElement?>?
}