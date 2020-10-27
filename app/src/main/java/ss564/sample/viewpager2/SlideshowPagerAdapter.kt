package ss564.sample.viewpager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SlideshowPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private val IMAGES = arrayOf(
        R.drawable.art_1,
        R.drawable.art_2,
        R.drawable.art_3,
        R.drawable.art_4,
        R.drawable.art_5,
    )

    override fun getItemCount(): Int = IMAGES.size

    override fun createFragment(position: Int): Fragment {
        return SlideshowImageFragment.create(IMAGES[position])
    }
}