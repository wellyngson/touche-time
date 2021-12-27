package br.touchetime.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import br.touchetime.R
import br.touchetime.databinding.EditFightCharacteristicsBinding
import com.airbnb.paris.annotations.Attr
import com.airbnb.paris.annotations.Styleable
import com.airbnb.paris.extensions.style

@Styleable("EditFightCharacteristics")
class EditFightCharacteristics @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val viewBindingComponent = EditFightCharacteristicsBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        style {
            attrs
        }
    }

    @Attr(R.styleable.EditFightCharacteristics_titleEditFight)
    fun setTitle(title: String) {
        viewBindingComponent.title.text = title
    }

    @Attr(R.styleable.EditFightCharacteristics_text)
    fun setText(text: String) {
        viewBindingComponent.text.text = text
    }
}
