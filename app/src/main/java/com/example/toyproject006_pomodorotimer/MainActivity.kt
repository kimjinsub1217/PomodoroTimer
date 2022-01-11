package com.example.toyproject006_pomodorotimer

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private val remainMinutesTextView: TextView by lazy {
        findViewById(R.id.remainMinuteTextView)
    }

    private val remainSecondsTextView: TextView by lazy {
        findViewById(R.id.remainSecondsTextView)
    }

    private val seekbar: SeekBar by lazy {
        findViewById(R.id.seekBar)
    }

    private var currentCountDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindViews()
    }

    private fun bindViews() {
        seekbar.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if(fromUser) {
                        updateRemainTime(progress * 60 * 1000L)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    currentCountDownTimer?.cancel()
                    currentCountDownTimer = null
                }

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    seekBar ?: return
                    currentCountDownTimer = createCountDownTimer(seekBar.progress * 60 * 1000L)
                    currentCountDownTimer?.start()
                }


            }
        )
    }

//  카운트다운 메서드 : 몇 millisecond 뒤에 받을건지를 인자로 받게 됨
    private fun createCountDownTimer(initialMillis: Long) =
        object : CountDownTimer(initialMillis, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                updateRemainTime(millisUntilFinished)
                updateSeekBar(millisUntilFinished)

            }

            override fun onFinish() {
                updateRemainTime(0)
                updateSeekBar(0)
            }
        }

//  매 1초 마다 UI를 갱신해야 하기 때문에 분 과 초를 갱신하는 메서드
    @SuppressLint("SetTextI18n")
    private fun updateRemainTime(remainMillis: Long) {
        val remainSeconds = remainMillis / 1000
        remainMinutesTextView.text = "%02d".format(remainSeconds / 60)
        remainSecondsTextView.text = "%02d".format(remainSeconds % 60)
    }

//  SeekBar 1분이 지날때 마다 1칸씩 줄어드는 메서드
   private fun updateSeekBar(remainMillis: Long) {
        seekbar.progress = (remainMillis / 1000 / 60).toInt()
    }
}