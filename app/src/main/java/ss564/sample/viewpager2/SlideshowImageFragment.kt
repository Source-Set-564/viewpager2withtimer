package ss564.sample.viewpager2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.layout_slideshow_image.*

class SlideshowImageFragment : Fragment() {

    companion object {

        private const val KEY_IMAGE = "image"
        private const val INVALID = -1

        fun create(@DrawableRes imageRes: Int = INVALID): SlideshowImageFragment {
            return SlideshowImageFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_IMAGE, imageRes)
                }
            }
        }
    }

    private val imageResId by lazy { arguments?.getInt(KEY_IMAGE) ?: INVALID }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_slideshow_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when {
            imageResId != INVALID -> iv_art.setImageResource(imageResId)
        }
    }
}