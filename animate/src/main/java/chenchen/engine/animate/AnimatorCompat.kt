package chenchen.engine.animate

import android.animation.Animator
import android.animation.ValueAnimator
import android.os.Build

/**
 * @author: chenchen
 * @since: 2023/11/15 9:41
 */
object AnimatorCompat {

    fun getTotalDuration(animator: Animator): Long = with(animator) {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            totalDuration
        } else {
            startDelay + duration * repeatCount(animator)
        }
    }

    fun getRepeatDuration(animator: Animator): Long = with(animator) {
        return duration * repeatCount(animator)
    }

    /**
     * 容易理解的次数
     * 原生动画次数默认为0，无限为-1，设置N次，执行N+1次
     * 把动画次数修正默认为1，设置N次就执行N次
     */
    fun repeatCount(animator: Animator): Int {
        if(animator !is ValueAnimator){
            return 1
        }
        if (animator.repeatCount < 0) return animator.repeatCount
        return animator.repeatCount + 1
    }
}