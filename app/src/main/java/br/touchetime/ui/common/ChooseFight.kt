package br.touchetime.ui.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import br.touchetime.R
import br.touchetime.databinding.ChooseFightBinding
import com.airbnb.paris.annotations.Attr
import com.airbnb.paris.annotations.Styleable
import com.airbnb.paris.extensions.style

@Styleable("ChooseFight")
class ChooseFight @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val viewBindingComponent = ChooseFightBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        style {
            attrs
        }
    }

    @Attr(R.styleable.ChooseFight_title)
    fun setTitle(title: String) {
        viewBindingComponent.title.text = title
    }

    @Attr(R.styleable.ChooseFight_description)
    fun setDescription(description: String) {
        viewBindingComponent.description.text = description
    }

    @Attr(R.styleable.ChooseFight_icon)
    fun setIcon(icon: Drawable?) {
        viewBindingComponent.icon.setImageDrawable(icon)
    }
}
