// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.data

data class Move(
    val from: Square,
    val to: Square,
    val promotion: PieceType? = null
) {
    override fun toString(): String {
        return "$from$to" + (promotion ?: "")
    }
}
