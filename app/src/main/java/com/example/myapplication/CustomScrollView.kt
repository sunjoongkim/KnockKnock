import android.content.Context
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator
import android.widget.OverScroller
import android.widget.ScrollView
import java.lang.reflect.Field

class CustomScrollView : ScrollView {
    private var scrollSpeed = 13 // 조절할 스크롤 속도

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context) : super(context)

    override fun fling(velocityY: Int) {
        // 스크롤 속도를 조절하는 부분
        super.fling(velocityY / scrollSpeed)
    }

    fun setScrollSpeed(speed: Int) {
        try {
            val mScroller: Field = CustomScrollView::class.java.getDeclaredField("mScroller")
            mScroller.isAccessible = true
            val scroller = OverScroller(context, DecelerateInterpolator())
            mScroller.set(this, scroller)
            
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}