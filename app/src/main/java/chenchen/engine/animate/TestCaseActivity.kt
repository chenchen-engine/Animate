@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE", "CanBeParameter")

package chenchen.engine.animate

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import chenchen.engine.animate.demo.databinding.ActivityTestCaseBinding

/**
 * @author: chenchen
 * @since: 2024/5/28 15:34
 *
 * testList:
 * [ ] 测试没有动画时AnimateScope是否可以正常运行。开始、结束回调是否正常回调
 * [ ] 测试重复次数是否生效。正向的或者翻转的开始、结束、进度、重复回调是否正常
 *
 * bugList:
 * [ ] reverse无法对repeat生效
 * [ ] rootAnimate设置了startDelay对计算currentPlayTime异常，因为startDelay也算进了totalDuration
 *    需要减去startDelay，目前遇到的问题是repeatCount多计算了startDelay的时间
 */
class TestCaseActivity : AppCompatActivity() {
    private val TAG = "TestCaseActivity"
    private val binding by lazy { ActivityTestCaseBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btn.setOnClickListener {
            runCase()
        }
    }

    private fun runCase() {
        testEmptyAnimate()
        testRepeatCount()
        testDelayAnimate()
        testNextSubAnimateScope()
    }

    /**
     * 测试执行空的动画
     */
    fun testEmptyAnimate() {
        animateScope { }
    }

    /**
     * 测试重复次数
     */
    fun testRepeatCount() {
        val animScope = animateScope(false) {
            repeatCount = 3
            animateArgb(0xFFE6E6E6.toInt(), 0xFF1DE261.toInt(), 0xFFE6E6E6.toInt()) {
                duration = 1000

            }
            animateFloat(0.5f, 1.5f, 0.5f) {
                duration = 1000

            }
            animateArgb(Color.WHITE, 0xFFF8FEFA.toInt(), Color.WHITE) {
                duration = 1000
            }
            onRepeat { animateNode, count, totalCount ->
                Log.d(TAG, "testRepeatCount: count:$count, totalCount:$totalCount")
            }
            onUpdate { animateNode, value, playTime ->
                Log.d(TAG, "testRepeatCount: playTime:$playTime")
            }
        }
        animScope.start(600)
    }

    fun testDelayAnimate() = with(binding) {
        animateScope {
            delay(200) next animateAlpha(tv2, 0.6f, 1f) { duration = 300 }
        }
    }

    fun testNextSubAnimateScope() = with(binding) {
        animateScope {
            animateScaleY(tv1, 0.6f, 1f) {
                duration = 2000
            } next subAnimateScope {
                animateScaleX(tv1, 0.5f, 1f){
                    duration = 2000
                }
            }
        }
    }
}