package ru.ksurgachev.recipesappcompose.ui.recipes.model

import android.os.Parcel
import android.os.Parcelable
import androidx.compose.runtime.Immutable
import ru.ksurgachev.recipesappcompose.Constants
import ru.ksurgachev.recipesappcompose.data.model.RecipeDto

@Immutable
data class RecipeUiModel(
    val id: Int,
    val title: String,
    val ingredients: List<IngredientUiModel>,
    val method: List<String>,
    val imageUrl: String,
    val isFavorite: Boolean
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.createTypedArrayList(IngredientUiModel.CREATOR) ?: emptyList(),
        parcel.createStringArrayList() ?: emptyList(),
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeTypedList(ingredients)
        parcel.writeStringList(method)
        parcel.writeString(imageUrl)
        parcel.writeByte(if (isFavorite) 1 else 0)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<RecipeUiModel> {
            override fun createFromParcel(parcel: Parcel): RecipeUiModel = RecipeUiModel(parcel)
            override fun newArray(size: Int): Array<RecipeUiModel?> = arrayOfNulls(size)
        }
    }
}

fun RecipeDto.toUiModel() = RecipeUiModel(
    id = id,
    title = title,
    ingredients = ingredients.map { it.toUiModel() },
    method = method,
    imageUrl = if (imageUrl.startsWith("http")) imageUrl else Constants.ASSETS_URI_PREFIX + imageUrl,
    isFavorite = false
)