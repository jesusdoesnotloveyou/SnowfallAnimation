package com.jdnly.androidlabs.lab11

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.appcompat.app.AppCompatActivity
import com.jdnly.androidlabs.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.random.Random

class Snowflake {
    var speed = Random.nextInt(8, 15)
    var yPos = 0.0f
    var xPos = .0f
    var radius = .0f
}

class Lab11 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lab11)
    }
}

class DrawSurface : SurfaceView, SurfaceHolder.Callback {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(
        context: Context, attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    override fun surfaceChanged(
        holder: SurfaceHolder, format: Int,
        width: Int, height: Int
    ) {}

    override fun surfaceCreated(holder: SurfaceHolder) {
        paint.color = Color.WHITE
        job = GlobalScope.launch {
            var canvas: Canvas?

            val snowflakeCount = 40
            val snowflakes = mutableListOf<Snowflake>()

            for (i in 0 until snowflakeCount) {
                val s = Snowflake()
                s.radius = Random.nextFloat() * 30.0f + 1.0f
                s.yPos = 0.0f - s.radius
                s.xPos = Random.nextDouble(
                    0.0 + s.radius,
                    1080.0 - s.radius
                ).toFloat()
                snowflakes.add(s)
            }

            while (true) {
                canvas = holder.lockCanvas(null)
                if (canvas != null) {
                    canvas.drawColor(Color.argb(255, 0, 192, 192))
                    snowflakes.forEach { s ->
                        canvas.drawCircle(
                            s.xPos,
                            s.yPos,
                            s.radius,
                            paint
                        )
                    }
                    holder.unlockCanvasAndPost(canvas)
                }
                // Перемещение снежинки
                snowflakes.forEach { s ->
                    s.yPos += s.speed
                    if (s.yPos > height - s.radius / 2) {
                        s.radius = Random.nextFloat() * 30.0f + 1.0f
                        s.yPos = 0.0f - s.radius
                        s.speed = Random.nextInt(8, 15)
                    }
                }
            }
        }
    }

    private var paint = Paint()
    private lateinit var job: Job

    override fun surfaceDestroyed(holder: SurfaceHolder) { job.cancel() }

    init { holder.addCallback(this) }
}
