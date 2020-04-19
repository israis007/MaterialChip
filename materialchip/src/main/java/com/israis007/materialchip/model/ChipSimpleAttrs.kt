package com.israis007.materialchip.model

import android.graphics.drawable.Drawable

data class ChipSimpleAttrs(
    val iconChipShow: Boolean,
    val iconChip: Drawable,
    val iconChipTint: Int?,
    val iconChipGravity: ChipTextGravity.IconChipGravity,
    var textChip: String,
    val textChipColor: Int,
    val textChipSize: Float,
    val textChipPadding: Float,
    val textChipPaddingStart: Float,
    val textChipPaddingTop: Float,
    val textChipPaddingEnd: Float,
    val textChipPaddingBottom: Float,
    val backgroundChip: Int,
    val strokeWidth: Float,
    val strokeColor: Int,
    val closable: Boolean,
    val closeIcon: Drawable,
    val closeIconTint: Int?,
    val closeIconChipGravity: ChipTextGravity.IconChipGravity
) {
}