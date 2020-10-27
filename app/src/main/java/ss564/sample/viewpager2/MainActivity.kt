package ss564.sample.viewpager2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.method.ScrollingMovementMethod
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val SLIDE_DELAY = 1000L
        private const val SLIDE_DURATION = 2500L
    }

    private val slideshowAdapter by lazy { SlideshowPagerAdapter(this) }

    private val timer = Timer()
    private val handler by lazy { Handler(Looper.getMainLooper()) }

    private var firstStart = false

    private val nextPosition: Int
        get() {
            return when {
                !firstStart -> {
                    firstStart = true
                    0
                }
                view_pager.currentItem == slideshowAdapter.itemCount - 1 -> 0
                else -> view_pager.currentItem + 1
            }
        }

    private var runningTask: TimerTask? = null

    private val pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            when (state) {
                ViewPager2.SCROLL_STATE_IDLE -> {
                    showLog("ViewPager state idle")
                    runSlideshowTimer()
                }
                ViewPager2.SCROLL_STATE_DRAGGING -> {
                    showLog("ViewPager state dragging")
                    cancelTimerTask()
                }
                else -> {

                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        view_pager.adapter = slideshowAdapter
        tv_logger.movementMethod = ScrollingMovementMethod()

    }

    override fun onResume() {
        super.onResume()
        runSlideshowTimer()
        view_pager.registerOnPageChangeCallback(pageChangeCallback)
    }

    private fun runSlideshowTimer() {
        if (runningTask == null) {
            showLog("\n==== [ Initialized Task ] ====\n")
            scheduleTimerTask(object : TimerTask() {
                override fun run() {
                    handler.post { updatePagerPosition() }
                }
            })
        }
    }

    private fun scheduleTimerTask(task: TimerTask) {
        showLog("Timer Schedule delay $SLIDE_DELAY, duration $SLIDE_DURATION")
        timer.schedule(task, SLIDE_DELAY, SLIDE_DURATION)
        runningTask = task
    }

    private fun cancelTimerTask() {
        showLog("\n==== [ Task Cancel ] ====\n")
        runningTask?.cancel()
        runningTask = null
    }

    private fun showLog(message: String) {
        tv_logger.text = when (tv_logger.text) {
            null -> message
            else -> tv_logger.text.toString() + "\n $message"
        }
    }

    override fun onPause() {
        super.onPause()
        view_pager.unregisterOnPageChangeCallback(pageChangeCallback)
        cancelTimerTask()
    }

    private fun updatePagerPosition() {
        showLog("Update pager called")
        if (runningTask == null) return
        val position = nextPosition
        showLog("Pager updated to $position")
        view_pager.setCurrentItem(position, true)
    }
}