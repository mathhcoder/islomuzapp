package uz.islom.ui.fragment.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import uz.islom.ui.fragment.BaseFragment
import uz.islom.ext.full
import uz.islom.ext.wrap
import uz.islom.model.dm.Theme

class AuthorizationFragment : BaseFragment() {

    companion object {
        fun newInstance() = AuthorizationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return LinearLayout(inflater.context).apply {

            orientation = LinearLayout.VERTICAL

            addView(TextInputLayout(context).apply {

                addView(TextInputEditText(context).apply {

                }, LinearLayout.LayoutParams(full, wrap))

            }, LinearLayout.LayoutParams(full, wrap))

            addView(TextInputLayout(context).apply {

                addView(TextInputEditText(context).apply {

                }, LinearLayout.LayoutParams(full, wrap))

            }, LinearLayout.LayoutParams(full, wrap))

            layoutParams = ViewGroup.LayoutParams(full, full)

        }
    }

}