package com.dev.touyou.tictactoe

import android.content.res.ColorStateList
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    val PLAYER_COUNT: Int = 2
    val PLAYER_IMAGES: Array<Int> = arrayOf(R.drawable.ic_batsu, R.drawable.ic_maru)

    var mTurn: Int? = null
    var mGameBoard: Array<Int>? = null
    var mBoardButtons: Array<ImageButton?>? = null
    var mPlayerTextView: TextView? = null
    var mWinnerTextView: TextView? = null
    var resetButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPlayerTextView = findViewById(R.id.playerText) as TextView
        mWinnerTextView = findViewById(R.id.winnerText) as TextView
        resetButton = findViewById(R.id.button) as Button

        mBoardButtons = arrayOfNulls(9)
        val buttonIds = arrayOf(R.id.imageButton1, R.id.imageButton2, R.id.imageButton3,
                R.id.imageButton4, R.id.imageButton5, R.id.imageButton6, R.id.imageButton7,
                R.id.imageButton8, R.id.imageButton9)
        for (i in buttonIds.indices) {
            mBoardButtons!![i] = findViewById(buttonIds[i]) as ImageButton
            mBoardButtons!![i]?.setTag(i)
        }

        init()
        setPlayer()
    }

    fun init() {
        mTurn = 1
        mGameBoard = Array(9, {i -> -1})
        for (i in 0 .. 8) {
            mBoardButtons!![i]?.setImageBitmap(null)
            print(i)
        }
        mWinnerTextView?.visibility = View.GONE
        resetButton?.visibility = View.GONE
    }

    fun setPlayer() {
        if (mTurn?.mod(2) == 0) {
            mPlayerTextView?.setText("Player: x(2)")
        } else {
            mPlayerTextView?.setText("Player: o(1)")
        }
    }

    fun tapMark(v: View) {
        if (mWinnerTextView?.visibility == View.VISIBLE) return
        val buttonNumber = v.tag as Int
        if (mGameBoard!![buttonNumber] == -1) {
            mBoardButtons!![buttonNumber]?.setImageResource(PLAYER_IMAGES[mTurn?.mod(2)!!])
            mGameBoard!![buttonNumber] = mTurn?.mod(2)!!
            val judge = judgeGame()
            if (judge != -1) {
                when(judge) {
                    0 -> {
                        mWinnerTextView?.setText("Game End\nPlayer: x(2)\nWin")
                        mWinnerTextView?.setTextColor(Color.BLUE)
                    }
                    1 -> {
                        mWinnerTextView?.setText("Game End\nPlayer: o(1)\nWin")
                        mWinnerTextView?.setTextColor(Color.RED)
                    }
                }
                mWinnerTextView?.visibility = View.VISIBLE
                resetButton?.visibility = View.VISIBLE
            } else {
                if (mTurn?.compareTo(mGameBoard?.size as Int)!! >= 0) {
                    mWinnerTextView?.setText("Game End\nDraw")
                    mWinnerTextView?.setTextColor(Color.YELLOW)
                    mWinnerTextView?.visibility = View.VISIBLE
                    resetButton?.visibility = View.VISIBLE
                }
                mTurn = mTurn!! + 1
                setPlayer()
            }
        }
    }

    fun reset(v: View) {
        init()
        setPlayer()
    }

    /*
        0 1 2
        3 4 5
        6 7 8
     */

    val judgeArray: Array<Array<Int>> = arrayOf(
            // 縦
            arrayOf(0, 3, 6),
            arrayOf(1, 4, 7),
            arrayOf(2, 5, 8),
            // 横
            arrayOf(0, 1, 2),
            arrayOf(3, 4, 5),
            arrayOf(6, 7, 8),
            // 斜め
            arrayOf(0, 4, 8),
            arrayOf(2, 4, 6)
    )

    fun judgeGame() : Int {
        for (i in judgeArray.indices) {
            if (mGameBoard!![judgeArray[i][0]]==mGameBoard!![judgeArray[i][1]] &&
                    mGameBoard!![judgeArray[i][1]]==mGameBoard!![judgeArray[i][2]] &&
                    mGameBoard!![judgeArray[i][0]]!=-1) {
                return mGameBoard!![judgeArray[i][0]]
            }
        }
        return -1
    }
}
