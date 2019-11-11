package uz.islom.ui.fragment.tasbih

//class TasbihFragment : SwipeAbleFragment() {
//
//    companion object {
//        fun newInstance() = TasbihFragment()
//    }
//
//    override fun getSwipeBackView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,appTheme: Theme): View? {
//        return FrameLayout(inflater.context).apply {
//
//            addView(FrameLayout(context).apply {
//
//                addView(BaseImageButton(context).apply {
//                    id = R.id.idBackButton
//                    setButtonPadding(dp(16))
//                }, ViewGroup.LayoutParams(dp(56), dp(56)))
//
//                addView(BaseTextView(context).apply {
//                    id = R.id.idTextView
//                    gravity = Gravity.CENTER_VERTICAL
//                    text = string(R.string.tasbih)
//                    setTextSizeSp(18)
//                }, FrameLayout.LayoutParams(full, full).apply {
//                    leftMargin = dp(72)
//                    rightMargin = dp(16)
//                })
//
//            }, ViewGroup.LayoutParams(full, dp(56)))
//
//            addView(FrameLayout(context).apply {
//
//            }, FrameLayout.LayoutParams(full, full).apply {
//                topMargin = dp(56)
//            })
//
//            layoutParams = ViewGroup.LayoutParams(full, full)
//
//        }
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        view.findViewById<BaseImageButton>(R.id.idBackButton).apply {
//            setImageResources(R.drawable.ic_arrow_left, colour(R.color.black))
//            setOnClickListener {
//                fragmentManager?.popBackStack()
//            }
//        }
//
//    }
//
//}