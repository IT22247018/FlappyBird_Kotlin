package com.example.flappybird_k

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.os.Handler
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class GameView(private val context: Context, attrs: AttributeSet?) : View(
    context, attrs
) {
    private val handler: Handler
    private val r: Runnable
    private var arrPipes: ArrayList<Pipe>? = null
    private var bird: Bird? = null
    private var sumpipe = 0
    private var distance = 0
    private var score: Int
    private var bestscore = 0
    var isStart: Boolean
    private var soundJump = 0
    private val volume = 0f
    private var loadedsound = false

    private var soundPool: SoundPool? = null

    init {
        val sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE)
        if (sp != null) {
            bestscore = sp.getInt("bestscore", 0)
        }
        score = 0
        isStart = false
        initBird()
        initPipe()
        handler = Handler()
        r = Runnable { invalidate() }

        if (Build.VERSION.SDK_INT >= 21) {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
            val builder = SoundPool.Builder()
            builder.setAudioAttributes(audioAttributes).setMaxStreams(5)
            soundPool = builder.build()
        } else {
            soundPool = SoundPool(5, AudioManager.STREAM_MUSIC, 0)
        }
        soundPool!!.setOnLoadCompleteListener { soundPool, sampleId, status ->
            loadedsound = true
        }
        soundJump = soundPool!!.load(context, R.raw.jump_02, 1)
    }


    private fun initPipe() {
        sumpipe = 6
        distance = 300 * Constants.SCREEN_HEIGHT / 1920
        arrPipes = ArrayList()
        for (i in 0 until sumpipe / 2) {
            if (i < sumpipe / 2) {
                arrPipes!!.add(
                    Pipe(
                        (Constants.SCREEN_WIDTH + i * ((Constants.SCREEN_WIDTH + 200 * Constants.SCREEN_WIDTH / 1080) / (sumpipe / 2))).toFloat(),
                        0f, 200 * Constants.SCREEN_WIDTH / 1080, Constants.SCREEN_HEIGHT / 2
                    )
                )
                arrPipes!![arrPipes!!.size - 1].bm =
                    BitmapFactory.decodeResource(this.resources, R.drawable.pipe2)
                arrPipes!![arrPipes!!.size - 1].randomY()
            } else {
                arrPipes!!.add(
                    Pipe(
                        arrPipes!![i - sumpipe / 2].x,
                        arrPipes!![i - sumpipe / 2].y
                                + arrPipes!![i - sumpipe / 2].height + distance,
                        200 * Constants.SCREEN_WIDTH / 1080,
                        Constants.SCREEN_HEIGHT / 2
                    )
                )
                arrPipes!![arrPipes!!.size - 1].bm =
                    BitmapFactory.decodeResource(this.resources, R.drawable.pipe1)
            }
        }
    }

    private fun initBird() {
        bird = Bird()
        bird!!.width = 100 * Constants.SCREEN_WIDTH / 1080
        bird!!.height = 100 * Constants.SCREEN_HEIGHT / 1920
        bird!!.x = (100 * Constants.SCREEN_WIDTH / 1080).toFloat()
        bird!!.y = (Constants.SCREEN_HEIGHT / 2 - bird!!.height / 2).toFloat()
        val arrbms = ArrayList<Bitmap>()
        arrbms.add(BitmapFactory.decodeResource(resources, R.drawable.bird1))
        arrbms.add(BitmapFactory.decodeResource(resources, R.drawable.bird2))
        bird!!.setArrbms(arrbms)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (isStart) {
            bird!!.draw(canvas)
            for (i in arrPipes!!.indices) {
                if (bird!!.getRect()
                        .intersect(arrPipes!![i].getRect()) || bird!!.y - bird!!.height < 0 || bird!!.y > Constants.SCREEN_HEIGHT
                ) {
                    Pipe.speed = 0
                    MainActivity.txt_score_over?.text = MainActivity.txt_score?.text
                    MainActivity.txt_best_score?.text = "Best Score: $bestscore"
                    MainActivity.txt_best_score?.visibility = View.VISIBLE
                    MainActivity.rl_game_over?.visibility = View.VISIBLE

                }
                if (bird!!.x + bird!!.width > arrPipes!![i].x + arrPipes!![i].width / 2 && bird!!.x + bird!!.width <= arrPipes!![i].x + arrPipes!![i].width / 2 + Pipe.speed && i < sumpipe / 2) {
                    score++
                    if (score > bestscore) {
                        bestscore = score
                        val sp = context.getSharedPreferences("gamesetting", Context.MODE_PRIVATE)
                        val editor = sp.edit()
                        editor.putInt("bestscore", bestscore)
                        editor.apply()
                    }
                    MainActivity.txt_score?.text = "" + score


                }
                if (arrPipes!![i].x < -arrPipes!![i].width) {
                    arrPipes!![i].x = Constants.SCREEN_WIDTH.toFloat()
                    if (i < sumpipe / 2) {
                        arrPipes!![i].randomY()
                    } else {
                        arrPipes!![i].y = (arrPipes!![i - sumpipe / 2].y
                                + arrPipes!![i - sumpipe / 2].height + distance)
                    }
                }
                arrPipes!![i].draw(canvas)
            }
        } else {
            if (bird!!.y > Constants.SCREEN_HEIGHT / 2) {
                bird!!.drop = (-15 * Constants.SCREEN_HEIGHT / 1920).toFloat()
            }
            bird!!.draw(canvas)
        }
        handler.postDelayed(r, 10)
    }

    //make the bird soar when touch screen
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            bird!!.drop = -15f
            if (loadedsound) {
                val streamId: Int =
                    this.soundPool!!.play(this.soundJump, 0.5.toFloat(), 0.5.toFloat(), 1, 0, 1f)
            }
        }
        return true
    }

    fun reset() {
        MainActivity.txt_score?.text = "0" // Using safe call (?.)
        score = 0
        initPipe()
        initBird()
    }

}
