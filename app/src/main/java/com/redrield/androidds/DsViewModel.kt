package com.redrield.androidds

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.redrield.androidds.ds.DriverStation
import com.redrield.androidds.ds.MockDriverStation
import com.redrield.androidds.ds.RealDriverStation
import com.redrield.androidds.ds.Mode

class DsViewModel(private val savedStateHandle: SavedStateHandle, mock: Boolean) : ViewModel() {

    // Default args dont work but this does, :shrug:
    constructor(savedStateHandle: SavedStateHandle) : this(savedStateHandle, false)

    private val _tab = MutableLiveData<SelectedTab>()
    val selectedTab: LiveData<SelectedTab>
        get() = _tab

    private val ds: DriverStation

    private val _enabled = MutableLiveData<Boolean>()
    val enabled: LiveData<Boolean>
        get() = _enabled

    private val _estopped = MutableLiveData<Boolean>()
    val estopped: LiveData<Boolean>
        get() = _estopped

    private val _teamNumber = MutableLiveData<Int>()
    val teamNumber: LiveData<Int>
        get() = _teamNumber

    private val _mode = MutableLiveData<Mode>()
    val mode: LiveData<Mode>
        get() = _mode

    private val _batteryVoltage = MutableLiveData<Float>()
    val batteryVoltage: LiveData<Float>
        get() = _batteryVoltage

    init {
        ds = if(!mock) {
            RealDriverStation()
        } else {
            MockDriverStation()
        }

        savedStateHandle.get<DsState>("state")?.let {
            ds.teamNumber = it.teamNumber
            ds.mode = it.mode

            if(it.estopped) {
                ds.estop()
            }

            if(it.enabled) {
                ds.enable()
            }

            _teamNumber.postValue(it.teamNumber)
            _mode.postValue(it.mode)
            _estopped.postValue(it.estopped)
            _enabled.postValue(it.enabled)
            _batteryVoltage.postValue(it.batteryVoltage)
        }
    }

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set("state", DsState(enabled.value ?: false, mode.value ?: Mode.Autonomous, teamNumber.value ?: 0, estopped.value ?: false, batteryVoltage.value ?: 0f))
        ds.close()
    }

    fun estop() {
        ds.estop()
        _estopped.postValue(true)
    }

    fun enable() {
        if(!ds.enabled) {
            ds.enable()
            _enabled.postValue(true)
        }
    }

    fun disable() {
        if(ds.enabled) {
            ds.disable()
            _enabled.postValue(false)
        }
    }

    fun setMode(mode: Mode) {
        ds.mode = mode
        _mode.postValue(mode)
    }

    fun setTeamNumber(team: Int) {
        ds.teamNumber = team
        _teamNumber.postValue(team)
    }

    fun changeTab(tab: SelectedTab) {
        _tab.value = tab
    }

    enum class SelectedTab {
        Control,
        Config
    }
}