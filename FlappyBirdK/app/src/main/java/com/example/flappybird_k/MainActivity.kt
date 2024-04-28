package com.example.flappybird_k

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var gv: GameView? = null
    private var mediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_BLUR_BEHIND
        )
        val dm = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(dm)
        Constants.SCREEN_HEIGHT = dm.heightPixels
        Constants.SCREEN_WIDTH = dm.widthPixels
        setContentView(R.layout.activity_main)
        txt_score = findViewById(R.id.txt_score)
        txt_best_score = findViewById(R.id.txt_best_score)
        txt_score_over = findViewById(R.id.txt_score_over)
        rl_game_over = findViewById(R.id.rl_game_over)
        btn_start = findViewById(R.id.btn_start)
        gv = findViewById(R.id.gv)
        txt_name=findViewById(R.id.imageView)
        btn_start?.setOnClickListener(View.OnClickListener {
            gv?.isStart = true
            txt_score?.visibility = View.VISIBLE
            btn_start?.visibility = View.INVISIBLE
            txt_name?.visibility = View.INVISIBLE
        })


        (btn_start as? Button)?.setOnClickListener(View.OnClickListener {
            (gv as? GameView)?.isStart = true
            (txt_score as? TextView)?.visibility = View.VISIBLE
            (btn_start as? Button)?.visibility = View.VISIBLE
            (btn_start as? Button)?.visibility = View.VISIBLE
        })

        // Declare mediaPlayer as a nullable MediaPlayer
        var mediaPlayer: MediaPlayer? = null

        // Initialize mediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.sillychipsong)
        mediaPlayer?.setLooping(true)

    // Start mediaPlayer if it's not null
        mediaPlayer?.start()

        val btn_info = findViewById<ImageButton>(R.id.btn_info)
        btn_info.setOnClickListener(View.OnClickListener { // Create an Intent to start SecondActivity
            val intent = Intent(this@MainActivity, info::class.java)
            startActivity(intent)
        })

        val exitButton = findViewById<ImageButton>(R.id.btn_exit)
        exitButton.setOnClickListener {
            finish() // exit game
        }


    }

    override fun onResume() {
        super.onResume()
        mediaPlayer?.start()
    }


    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    companion object {
        var txt_score: TextView? = null
        var txt_best_score: TextView? = null
        var txt_score_over: TextView? = null
        var rl_game_over: RelativeLayout? = null
        var btn_start: Button? = null
        var txt_name: ImageView? =null

    }

}
