package com.e.occanotestsidep.ui.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
//@Entity(tableName = "ships")
class Ship  (
//    @PrimaryKey(autoGenerate = true)
    var id: Int,
//    @ColumnInfo(name = "imo")
    var imo: String?,
//    @ColumnInfo(name = "name")
    var name: String?,
//    @ColumnInfo(name = "enginetype")
    var engineType: String?,
    var isSelected: Boolean
):Parcelable{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ship) return false

        if (id != other.id) return false
        if (imo != other.imo) return false
        if (name != other.name) return false
        if (engineType != other.engineType) return false
        if (isSelected != other.isSelected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (imo?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (engineType?.hashCode() ?: 0)
        result = 31 * result + isSelected.hashCode()
        return result
    }
}
