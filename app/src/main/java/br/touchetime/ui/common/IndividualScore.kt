package br.touchetime.ui.common

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import br.touchetime.R
import br.touchetime.databinding.IndividualScoreBinding
import com.airbnb.paris.annotations.Attr
import com.airbnb.paris.annotations.Styleable
import com.airbnb.paris.extensions.style

@Styleable("IndividualScore")
class IndividualScore @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    val viewBindingComponent = IndividualScoreBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        style(attrs)
    }

    @Attr(R.styleable.IndividualScore_name)
    fun setName(name: String) {
        viewBindingComponent.name.text = name
    }

    @Attr(R.styleable.IndividualScore_score)
    fun setScore(score: String) {
        viewBindingComponent.score.text = score
    }

    @Attr(R.styleable.IndividualScore_foul)
    fun setFoul(foul: String) {
        viewBindingComponent.foul.text = foul
    }

    @Attr(R.styleable.IndividualScore_backgroundComponent)
    fun setBackgroundComponent(backgroundComponent: Drawable?) {
        viewBindingComponent.container.background = backgroundComponent
    }

    @Attr(R.styleable.IndividualScore_backgroundComponentsAddRemove)
    fun setBackgroundComponentsAddRemove(backgroundComponent: Drawable?) {
        viewBindingComponent.apply {
            addScore.background = backgroundComponent
            removeScore.background = backgroundComponent
        }
    }
}
