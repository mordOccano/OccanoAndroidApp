package com.e.occanosidetest.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Parcelize
//@Entity(tableName = "statuses")

data class Status(

//    @PrimaryKey(autoGenerate = true)
    private var statusId: Int = 0,

//    @ColumnInfo(name = "mainTitle")
    private var statusMainTitle: String? = null,

//    @ColumnInfo(name = "subTitle")
    private var statusSubTitle: String? = null,

//    @ColumnInfo(name = "lessContent")
    private var statusLessContent: String? = null,

//    @ColumnInfo(name = "moreContent")
    private var statusMoreContent: String? = null,

//    @ColumnInfo(name = "KindOfDanger")
    private var statusKindOfDanger:Int = 0,

//    @ColumnInfo(name = "kindOfAcknowledge")
    private var kindOfAcknowledge:Int = 0,

//    @ColumnInfo(name = "numberOfStatus")
    private var numberOfstatus:Int = 0,

//    @ColumnInfo(name = "timeStampOfStatus")
    private var timeStampOfstatus: String? = null
) : Parcelable
{

    fun Status(id: Int, statusMainTitle: String, statusLessContent: String, statusMoreContent: String, statusKindOfDanger: Int, kindOfAcknowledge: Int,numberOfstatus: Int,timeStampOfstatus: String) {
    this.statusId = id
    this.statusMainTitle = statusMainTitle
    this.statusLessContent = statusLessContent
      this.statusMoreContent =statusMoreContent
      this.statusKindOfDanger =statusKindOfDanger
      this.kindOfAcknowledge =kindOfAcknowledge
      this.numberOfstatus =numberOfstatus
      this.timeStampOfstatus =timeStampOfstatus
    }

    @Ignore
    fun Status() {
    }
    fun getStatusId(): Int {
        return this.statusId
    }

    fun setId(id: Int) {
        this.statusId = id
    }

    fun getStatusMainTitle(): String? {
        return this.statusMainTitle
    }

    fun setStatusMainTitle(statusMainTitle: String) {
        this.statusMainTitle = statusMainTitle
    }

    fun getStatusSubTitle(): String? {
        return this.statusSubTitle
    }

    fun setStatusSubTitle(statusSubTitle: String) {
        this.statusSubTitle = statusSubTitle
    }

    fun getStatusLessContent(): String? {
        return this.statusLessContent
    }

    fun setStatusLessContent(statusLessContent: String) {
        this.statusLessContent = statusLessContent
    }

    fun getStatusMoreContent(): String? {
        return this.statusMoreContent
    }

    fun setStatusMoreContent(statusMoreContent: String) {
        this.statusMoreContent = statusMoreContent
    }

    fun getStatusKindOfDanger(): Int? {
        return this.statusKindOfDanger
    }

    fun setStatusKindOfDanger(statusKindOfDanger: Int) {
        this.statusKindOfDanger = statusKindOfDanger
    }

    fun getKindOfAcknowledge(): Int? {
        return this.kindOfAcknowledge
    }

    fun setKindOfAcknowledge(kindOfAcknowledge: Int) {
        this.kindOfAcknowledge = kindOfAcknowledge
    }

    fun getNumberOfstatus(): Int? {
        return this.kindOfAcknowledge
    }

    fun setNumberOfstatus(numberOfstatus: Int) {
        this.numberOfstatus = numberOfstatus
    }


    fun getTimeStampOfstatus(): String? {
        return this.timeStampOfstatus
    }

    fun setTimeStampOfstatus(timeStampOfstatus: String) {
        this.timeStampOfstatus = timeStampOfstatus
    }
}



