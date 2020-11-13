package com.redrield.androidds.state

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.redrield.androidds.ds.DriverStation
import com.redrield.androidds.ds.MockDriverStation
import com.redrield.androidds.ds.RealDriverStation
import com.redrield.androidds.ds.Mode
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.TimeUnit

class DsViewModel(private val savedStateHandle: SavedStateHandle, mock: Boolean) : ViewModel() {

    private val updateScheduler = ScheduledThreadPoolExecutor(1)

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

    val batteryState = BatteryState(1.0/50.0, 5.0)

    private val _connected = MutableLiveData<Boolean>()
    val connected: LiveData<Boolean>
        get() = _connected

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

        }
    }

    override fun onCleared() {
        super.onCleared()
        savedStateHandle.set("state", DsState(enabled.value ?: false, mode.value ?: Mode.Autonomous, teamNumber.value ?: 0, estopped.value ?: false))
        ds.close()
    }

    fun estop() {
        ds.estop()
    }

    fun enable() {
        if(!ds.enabled) {
            ds.enable()
        }
    }

    fun disable() {
        if(ds.enabled) {
            ds.disable()
        }
    }

    fun setMode(mode: Mode) {
        ds.mode = mode
    }

    fun setTeamNumber(team: Int) {
        ds.teamNumber = team
    }

    fun changeTab(tab: SelectedTab) {
        _tab.value = tab
    }

    init {
        updateScheduler.scheduleAtFixedRate(this::updateFromDS, 0, 20, TimeUnit.MILLISECONDS)
    }

    private fun updateFromDS() {
        if(_connected.value != true && ds.connected) {
            _connected.postValue(true)
        } else if(_connected.value != false && !ds.connected) {
            _connected.postValue(false)
        }

        if(ds.connected) {
            batteryState.push(ds.batteryVoltage)
            _enabled.postValue(ds.enabled)
            _mode.postValue(ds.mode)
            _teamNumber.postValue(ds.teamNumber)
            _estopped.postValue(ds.estopped)
        }
    }

    enum class SelectedTab {
        Control,
        Config
    }
}