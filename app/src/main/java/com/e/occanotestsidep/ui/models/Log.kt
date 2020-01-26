package com.e.occanosidetest.models


import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "logs")
data class Log(
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0,

    @ColumnInfo(name = "log_kind")
    var kindOfSrcLog:Int = 0,

    @ColumnInfo(name = "log_content")
    var content: String? = ""
): Parcelable {

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return content?.hashCode() ?: 0
    }

}

