package com.israis007.materialchip.ui

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.bumptech.glide.Glide
import com.israis007.materialchip.R
import com.israis007.materialchip.`object`.NO_COLOR
import com.israis007.materialchip.model.ChipSimpleAttrs
import com.israis007.materialchip.model.ChipTextGravity
import com.israis007.materialchip.tools.ViewTools

class ChipSimple @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var simpleAttrs: ChipSimpleAttrs
    private var simpleEvent: ChipSimpleEvent? = null

    init {
        context.withStyledAttributes(attrs, R.styleable.ChipSimple, defStyleAttr, R.style.ChipSimple){
            val reso = context.resources
            simpleAttrs = ChipSimpleAttrs(
                iconChipShow = getBoolean(R.styleable.ChipSimple_iconChipShow, true),
                iconChip = getDrawable(R.styleable.ChipSimple_iconChip) ?: ContextCompat.getDrawable(context, R.drawable.ic_done)!!,
                iconChipTint = getColor(R.styleable.ChipSimple_iconChipTint, NO_COLOR),
                iconChipGravity = ChipTextGravity.getGravity(
                    getInt(R.styleable.ChipSimple_iconChipGravity, reso.getInteger(R.integer.Gravity))
                ),
                textChip = getString(R.styleable.ChipSimple_textChip) ?: reso.getString(R.string.textExample),
                textChipColor =  getColor(R.styleable.ChipSimple_textChipColor, ContextCompat.getColor(context, R.color.text)),
                textChipSize = getDimension(R.styleable.ChipSimple_textChipSize, reso.getDimension(R.dimen.text)),
                textChipPadding = getDimension(R.styleable.ChipSimple_textChipPadding, reso.getDimension(R.dimen.padding)),
                textChipPaddingStart = getDimension(R.styleable.ChipSimple_textChipPaddingStart, reso.getDimension(R.dimen.padStart)),
                textChipPaddingTop = getDimension(R.styleable.ChipSimple_textChipPaddingTop, reso.getDimension(R.dimen.padTop)),
                textChipPaddingEnd = getDimension(R.styleable.ChipSimple_textChipPaddingEnd, reso.getDimension(R.dimen.padEnd)),
                textChipPaddingBottom = getDimension(R.styleable.ChipSimple_textChipPaddingBottom, reso.getDimension(R.dimen.padBottom)),
                backgroundChip = getColor(R.styleable.ChipSimple_backgroundChip, ContextCompat.getColor(context, R.color.background)),
                strokeWidth = getDimension(R.styleable.ChipSimple_strokeWidth, reso.getDimension(R.dimen.stroke)),
                strokeColor = getColor(R.styleable.ChipSimple_strokeColor, ContextCompat.getColor(context, R.color.stroke)),
                closable = getBoolean(R.styleable.ChipSimple_closable, false),
                closeIcon = getDrawable(R.styleable.ChipSimple_closeIcon) ?: ContextCompat.getDrawable(context, R.drawable.ic_close)!!,
                closeIconTint = getColor(R.styleable.ChipSimple_closeIconTint, NO_COLOR),
                closeIconChipGravity = ChipTextGravity.getGravity(
                    getInt(R.styleable.ChipSimple_closeIconChipGravity, reso.getInteger(R.integer.Gravity))
                )
            )
        }
        drawChip()
    }

    private fun drawChip(){
        val chipl = LayoutInflater.from(context).inflate(R.layout.simplechip, null, false)
        val cl_root = chipl.findViewById<ConstraintLayout>(R.id.simplechip_conslayout)
        val tv_text = chipl.findViewById<AppCompatTextView>(R.id.simplechip_text)
        val iv_icon = chipl.findViewById<ImageView>(R.id.simplechip_icon)
        val iv_iconClose = chipl.findViewById<ImageView>(R.id.simplechip_closeicon)

        /* Custom Text */
        tv_text.apply {
            text = simpleAttrs.textChip
            setTextColor(simpleAttrs.textChipColor)
            setTextSize(TypedValue.COMPLEX_UNIT_PX, simpleAttrs.textChipSize)
        }

        /* Custom Icons */
        val lap = iv_icon.layoutParams as ConstraintLayout.LayoutParams
        val lap_close = iv_iconClose.layoutParams as ConstraintLayout.LayoutParams

        lap.width = simpleAttrs.textChipSize.toInt()
        lap.height = simpleAttrs.textChipSize.toInt()
        lap_close.width = simpleAttrs.textChipSize.toInt()
        lap_close.height = simpleAttrs.textChipSize.toInt()

        iv_icon.layoutParams = lap
        iv_iconClose.layoutParams = lap_close

        if (simpleAttrs.iconChipTint != NO_COLOR)
            iv_icon.setColorFilter(simpleAttrs.iconChipTint!!, PorterDuff.Mode.SRC_IN)

        if (simpleAttrs.closeIconTint != NO_COLOR)
            iv_iconClose.setColorFilter(simpleAttrs.closeIconTint!!, PorterDuff.Mode.SRC_IN)

        /* Set icons */
        Glide.with(context).load(simpleAttrs.iconChip).fitCenter().circleCrop().error(R.drawable.ic_done).placeholder(R.drawable.ic_done).into(iv_icon)
        Glide.with(context).load(simpleAttrs.closeIcon).fitCenter().circleCrop().error(R.drawable.ic_close).placeholder(R.drawable.ic_close).into(iv_iconClose)

        iv_icon.visibility = if (simpleAttrs.iconChipShow) View.VISIBLE else View.GONE
        iv_iconClose.visibility = if (simpleAttrs.closable) View.VISIBLE else View.GONE

        val drawable = cl_root.background as GradientDrawable
        drawable.setColor(simpleAttrs.backgroundChip)
        drawable.setStroke(simpleAttrs.strokeWidth.toInt(), simpleAttrs.strokeColor)

        /* Add vies like gravity */
        cl_root.removeAllViews()

        val lp = tv_text.layoutParams as ConstraintLayout.LayoutParams

        when {
            simpleAttrs.iconChipGravity == ChipTextGravity.IconChipGravity.START && simpleAttrs.closeIconChipGravity == ChipTextGravity.IconChipGravity.START -> {
                lap.setMargins(lp.marginStart, 0, 0,0)
                lap.startToStart = 0
                lap.startToEnd = -1
                iv_icon.layoutParams = lap
                cl_root.addView(ViewTools.getViewWithoutParent(iv_icon))
                lap_close.setMargins(context.resources.getDimension(R.dimen.padStart).toInt(), 0,0,0)
                lap_close.startToEnd = iv_icon.id
                lap_close.endToEnd = -1
                iv_iconClose.layoutParams = lap_close
                cl_root.addView(ViewTools.getViewWithoutParent(iv_iconClose))
                if (simpleAttrs.textChipPadding != 0f)
                    lp.setMargins(simpleAttrs.textChipPadding.toInt(), simpleAttrs.textChipPadding.toInt(), simpleAttrs.textChipPadding.toInt(), simpleAttrs.textChipPadding.toInt())
                else
                    lp.setMargins(simpleAttrs.textChipPaddingStart.toInt(), simpleAttrs.textChipPaddingTop.toInt(), simpleAttrs.textChipPaddingEnd.toInt(), simpleAttrs.textChipPaddingBottom.toInt())
                lp.startToEnd = iv_iconClose.id
                lp.endToEnd = 0
                tv_text.layoutParams = lp
                cl_root.addView(ViewTools.getViewWithoutParent(tv_text))
            }
            simpleAttrs.iconChipGravity == ChipTextGravity.IconChipGravity.START && simpleAttrs.closeIconChipGravity == ChipTextGravity.IconChipGravity.END -> {
                lap.setMargins(lp.marginStart, 0, 0,0)
                lap.startToStart = 0
                lap.startToEnd = -1
                iv_icon.layoutParams = lap
                cl_root.addView(ViewTools.getViewWithoutParent(iv_icon))
                if (simpleAttrs.textChipPadding != 0f)
                    lp.setMargins(simpleAttrs.textChipPadding.toInt(), simpleAttrs.textChipPadding.toInt(), simpleAttrs.textChipPadding.toInt(), simpleAttrs.textChipPadding.toInt())
                else
                    lp.setMargins(simpleAttrs.textChipPaddingStart.toInt(), simpleAttrs.textChipPaddingTop.toInt(), simpleAttrs.textChipPaddingEnd.toInt(), simpleAttrs.textChipPaddingBottom.toInt())
                lp.startToStart = -1
                lp.startToEnd = iv_icon.id
                tv_text.layoutParams = lp
                cl_root.addView(ViewTools.getViewWithoutParent(tv_text))
                lap_close.setMargins(0, 0,context.resources.getDimension(R.dimen.padEnd).toInt(),0)
                lap_close.startToEnd = tv_text.id
                iv_iconClose.layoutParams = lap_close
                cl_root.addView(ViewTools.getViewWithoutParent(iv_iconClose))
            }
            simpleAttrs.iconChipGravity == ChipTextGravity.IconChipGravity.END && simpleAttrs.closeIconChipGravity == ChipTextGravity.IconChipGravity.END -> {
                if (simpleAttrs.textChipPadding != 0f)
                    lp.setMargins(simpleAttrs.textChipPadding.toInt(), simpleAttrs.textChipPadding.toInt(), simpleAttrs.textChipPadding.toInt(), simpleAttrs.textChipPadding.toInt())
                else
                    lp.setMargins(simpleAttrs.textChipPaddingStart.toInt(), simpleAttrs.textChipPaddingTop.toInt(), simpleAttrs.textChipPaddingEnd.toInt(), simpleAttrs.textChipPaddingBottom.toInt())
                tv_text.layoutParams = lp
                cl_root.addView(ViewTools.getViewWithoutParent(tv_text))
                cl_root.addView(ViewTools.getViewWithoutParent(iv_icon))
                cl_root.addView(ViewTools.getViewWithoutParent(iv_iconClose))
            }
            simpleAttrs.iconChipGravity == ChipTextGravity.IconChipGravity.END && simpleAttrs.closeIconChipGravity == ChipTextGravity.IconChipGravity.START -> {
                lap_close.setMargins(lp.marginStart, 0, 0,0)
                lap_close.startToStart = 0
                lap_close.endToEnd = -1
                iv_iconClose.layoutParams = lap_close
                cl_root.addView(ViewTools.getViewWithoutParent(iv_iconClose))
                if (simpleAttrs.textChipPadding != 0f)
                    lp.setMargins(simpleAttrs.textChipPadding.toInt(), simpleAttrs.textChipPadding.toInt(), simpleAttrs.textChipPadding.toInt(), simpleAttrs.textChipPadding.toInt())
                else
                    lp.setMargins(simpleAttrs.textChipPaddingStart.toInt(), simpleAttrs.textChipPaddingTop.toInt(), simpleAttrs.textChipPaddingEnd.toInt(), simpleAttrs.textChipPaddingBottom.toInt())
                lp.startToStart = -1
                lp.startToEnd = iv_iconClose.id
                tv_text.layoutParams = lp
                cl_root.addView(ViewTools.getViewWithoutParent(tv_text))
                lap.setMargins(0, 0,context.resources.getDimension(R.dimen.padEnd).toInt(),0)
                lap.startToEnd = tv_text.id
                lap.endToEnd = 0
                iv_icon.layoutParams = lap
                cl_root.addView(ViewTools.getViewWithoutParent(iv_icon))
            }
        }

        iv_iconClose.setOnClickListener {
            simpleEvent?.OnClose(this)
        }

        this@ChipSimple.addView(ViewTools.getViewWithoutParent(cl_root))
    }

    fun addChipSimpleEvent(event: ChipSimpleEvent){
        this.simpleEvent = event
    }

}