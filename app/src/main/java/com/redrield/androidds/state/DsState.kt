package com.redrield.androidds.state

import android.os.Parcelable
import com.redrield.androidds.ds.Mode
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DsState(
    val enabled: Boolean,
    val mode: Mode,
    val teamNumber: Int,
    val estopped: Boolean,
) : Parcelable {
    constructor() : this(false, Mode.Autonomous, 0, false)
}
