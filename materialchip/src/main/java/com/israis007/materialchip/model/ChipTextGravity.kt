package com.israis007.materialchip.model

class ChipTextGravity {

    companion object {

        fun getGravity(value: Int): IconChipGravity =
            when (value){
                0 -> IconChipGravity.START
                else -> IconChipGravity.END
            }
    }

    enum class IconChipGravity{
        START,
        END
    }


}