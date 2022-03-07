// SPDX-License-Identifier: MIT
// Copyright (c) 2022 Lukas Zeller

package com.lz101010.chess.core
import com.lz101010.chess.data.Move
import com.lz101010.chess.data.PieceType
import com.lz101010.chess.data.Square
import org.assertj.core.api.Assertions.assertThat

import org.junit.jupiter.api.Test

class PositionEvaluatorTest {
    @Test
    fun detectCheck_passes() {
        val move = Move(PieceType.Q.asWhite, Square.F5, Square.F8)

        runAssertions("q2k4/8/8/5Q2/8/8/8/3K4 w - - 0 1", move, check = true)
    }

    @Test
    fun detectMate_passes() {
        val move = Move(PieceType.Q.asWhite, Square.E6, Square.D7)

        runAssertions("q2k4/8/3KQ3/8/8/8/8/8 w - - 0 1", move, check = true, mate = true)
    }

    @Test
    fun detectTechnicalMate_passes() {
        val move = Move(PieceType.Q.asWhite, Square.H7, Square.H8)

        runAssertions("k6q/2K4Q/8/8/8/8/8/8 w - - 0 1", move, check = true, technicalMate = true)
    }

    @Test
    fun detectTechnicalMateWithoutCheck_passes() {
        val move = Move(PieceType.K.asWhite, Square.F5, Square.F6)

        //runAssertions("8/1k6/8/3NBK2/8/8/8/8 w - - 0 1", move, technicalMate = true)
        runAssertions("8/1k6/8/4BK1B/8/8/8/8 w - - 0 1", move, technicalMate = true)
        runAssertions("8/1k6/8/3NNK2/8/8/8/8 w - - 0 1", move, technicalMate = true)
        runAssertions("8/1k6/8/4RK2/8/8/8/8 w - - 0 1", move, technicalMate = true)
        runAssertions("8/bk6/8/4QK2/8/8/8/8 w - - 0 1", move, technicalMate = true)
        runAssertions("8/nk6/8/4QK2/8/8/8/8 w - - 0 1", move, technicalMate = true)
    }

    @Test
    fun detectNotTechnicalMate_passes() {
        val move = Move(PieceType.K.asWhite, Square.F5, Square.F6)

        runAssertions("8/rk6/8/4BK1B/8/8/8/8 w - - 0 1", move, technicalMate = false)
        runAssertions("8/rk6/8/4NK2/8/8/8/8 w - - 0 1", move, technicalMate = false)
        runAssertions("8/qk6/8/4NK2/8/8/8/8 w - - 0 1", move, technicalMate = false)
        runAssertions("8/rk6/8/4BK2/8/8/8/8 w - - 0 1", move, technicalMate = false)
        runAssertions("8/qk6/8/4BK2/8/8/8/8 w - - 0 1", move, technicalMate = false)
    }

    @Test
    fun detectStaleMate_passes() {
        val move = Move(PieceType.Q.asWhite, Square.H6, Square.H7)

        runAssertions("k1K5/8/5p1Q/5P2/8/8/8/8 w - - 0 1", move, staleMate = true)
    }

    @Test
    fun detectTechnicalDraw_passes() {
        val move = Move(PieceType.K.asWhite, Square.G8, Square.H8)

        runAssertions("6Kq/4k3/8/8/8/8/8/8 w - - 0 1", move, technicalDraw = true)
    }


    @Test
    fun detectNotTechnicalDraw_passes() {
        val move = Move(PieceType.K.asWhite, Square.G8, Square.H8)

        runAssertions("6K1/rk6/8/4N3/8/8/8/8 w - - 0 1", move, technicalDraw = false)
        runAssertions("6K1/rk6/8/4R3/8/8/8/8 w - - 0 1", move, technicalDraw = false)
    }

    private fun runAssertions(
        fen: String,
        move: Move,
        check: Boolean = false,
        mate: Boolean = false,
        technicalMate: Boolean = false,
        staleMate: Boolean = false,
        technicalDraw: Boolean = false
    ) {
        val board = BoardGenerator.fromFen(fen)
        assertThat(PositionEvaluator.isCheck(board, move)).isEqualTo(check)
        assertThat(PositionEvaluator.isMate(board, move)).isEqualTo(mate)
        assertThat(PositionEvaluator.isTechnicalMate(board, move)).isEqualTo(technicalMate)
        assertThat(PositionEvaluator.isStaleMate(board, move)).isEqualTo(staleMate)
        assertThat(PositionEvaluator.isTechnicalDraw(board, move)).isEqualTo(technicalDraw)
    }
}