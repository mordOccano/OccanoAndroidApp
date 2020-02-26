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
    var id: Int = 0,
//    @ColumnInfo(name = "imo")
//    @ColumnInfo(name = "name")
//    @ColumnInfo(name = "enginetype")
    var vessel: String? = "",
    var m_e: String? = "",
    var displacement_engine: String? = "",
    var Number_of_cylinders: Int? = 0,
    var Stroke_bore_ratio: String? = "",
    var Diameter_of_piston_in_cm: String? = "",
    var Concept: String? = "",
    var Design: String? = "",
    var IMO: String? = "",
    var AIS_Vessel_Type	: String? = "",
    var Year_Built: String? = "",
    var Length_Overall: String? = "",
    var Breadth_Extreme: String? = "",
    var Deadweight: String? = "",
    var Gross_Tonnage: String? = "",

    var isSelected: Boolean = false
):Parcelable{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Ship) return false

        if (id != other.id) return false
        if (vessel != other.vessel) return false
        if (m_e != other.m_e) return false
        if (displacement_engine != other.displacement_engine) return false
        if (Number_of_cylinders != other.Number_of_cylinders) return false
        if (Stroke_bore_ratio != other.Stroke_bore_ratio) return false
        if (Diameter_of_piston_in_cm != other.Diameter_of_piston_in_cm) return false
        if (Concept != other.Concept) return false
        if (Design != other.Design) return false
        if (IMO != other.IMO) return false
        if (AIS_Vessel_Type != other.AIS_Vessel_Type) return false
        if (Year_Built != other.Year_Built) return false
        if (Length_Overall != other.Length_Overall) return false
        if (Breadth_Extreme != other.Breadth_Extreme) return false
        if (Deadweight != other.Deadweight) return false
        if (Gross_Tonnage != other.Gross_Tonnage) return false
        if (isSelected != other.isSelected) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + (vessel?.hashCode() ?: 0)
        result = 31 * result + (m_e?.hashCode() ?: 0)
        result = 31 * result + (displacement_engine?.hashCode() ?: 0)
        result = 31 * result + (Number_of_cylinders?.hashCode() ?: 0)
        result = 31 * result + (Stroke_bore_ratio?.hashCode() ?: 0)
        result = 31 * result + (Diameter_of_piston_in_cm?.hashCode() ?: 0)
        result = 31 * result + (Concept?.hashCode() ?: 0)
        result = 31 * result + (Design?.hashCode() ?: 0)
        result = 31 * result + (IMO?.hashCode() ?: 0)
        result = 31 * result + (AIS_Vessel_Type?.hashCode() ?: 0)
        result = 31 * result + (Year_Built?.hashCode() ?: 0)
        result = 31 * result + (Length_Overall?.hashCode() ?: 0)
        result = 31 * result + (Breadth_Extreme?.hashCode() ?: 0)
        result = 31 * result + (Deadweight?.hashCode() ?: 0)
        result = 31 * result + (Gross_Tonnage?.hashCode() ?: 0)
        result = 31 * result + isSelected.hashCode()
        return result
    }

    override fun toString(): String {
        return "Ship(id=$id, vessel=$vessel, m_e=$m_e, displacement_engine=$displacement_engine, Number_of_cylinders=$Number_of_cylinders, Stroke_bore_ratio=$Stroke_bore_ratio, Diameter_of_piston_in_cm=$Diameter_of_piston_in_cm, Concept=$Concept, Design=$Design, IMO=$IMO, AIS_Vessel_Type=$AIS_Vessel_Type, Year_Built=$Year_Built, Length_Overall=$Length_Overall, Breadth_Extreme=$Breadth_Extreme, Deadweight=$Deadweight, Gross_Tonnage=$Gross_Tonnage, isSelected=$isSelected)"
    }


}
