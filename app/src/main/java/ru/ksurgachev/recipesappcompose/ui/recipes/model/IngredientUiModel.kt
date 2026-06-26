package ru.ksurgachev.recipesappcompose.ui.recipes.model

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import ru.ksurgachev.recipesappcompose.data.model.IngredientDto

@Immutable
data class IngredientUiModel(
    val name: String,
    val quantity: String,
    val unitOfMeasure: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(quantity)
        parcel.writeString(unitOfMeasure)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<IngredientUiModel> {
            override fun createFromParcel(parcel: Parcel): IngredientUiModel = IngredientUiModel(parcel)
            override fun newArray(size: Int): Array<IngredientUiModel?> = arrayOfNulls(size)
        }
    }
}

fun IngredientDto.toUiModel() = IngredientUiModel(
    name = description,
    quantity = quantity,
    unitOfMeasure = unitOfMeasure
)