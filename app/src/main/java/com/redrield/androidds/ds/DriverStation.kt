package com.redrield.androidds.ds

class DriverStation(team: Int, alliance: Alliance) {
    val ptr: Long

    init {
        ptr = new(team, alliance.ptr)
    }

    constructor(team: Int) : this(team, Alliance(Colour.Blue, 1))
    constructor() : this(0)

    val batteryVoltage: Float
        get() = _getBatteryVoltage(ptr)

    fun enable() {
        _enable(ptr)
    }

    fun disable() {
        _disable(ptr)
    }

    val enabled: Boolean
        get() = _isEnabled(ptr)

    fun estop() {
        _estop(ptr)
    }

    val estopped: Boolean
        get() = _isEstopped(ptr)

    var mode: Mode
        get() = TODO()
        set(value) = _setMode(ptr, value)

    var teamNumber: Int
        get() = _getTeamNumber(ptr)
        set(value) = _setTeamNumber(ptr, value)

    fun restartCode() {
        _restartCode(ptr)
    }

    fun restartRoboRIO() {
        _restartRIO(ptr)
    }

    fun setAlliance(alliance: Alliance) {
        _setAlliance(ptr, alliance.ptr)
    }

    fun setGameSpecificMessage(msg: String) {
        _setGSM(ptr, msg)
    }

    val connected: Boolean
        get() = _isConnected(ptr)

    val codeAlive: Boolean
        get() = _isCodeAlive(ptr)

    fun setTCPConsumer(consumer: (String) -> Unit) {
        _setTCPConsumer(ptr, consumer)
    }

    private external fun new(team: Int, alliance: Long): Long
    private external fun destroy(ptr: Long)
    private external fun _getBatteryVoltage(ptr: Long): Float
    private external fun _enable(ptr: Long)
    private external fun _disable(ptr: Long)
    private external fun _isEnabled(ptr: Long): Boolean
    private external fun _estop(ptr: Long)
    private external fun _isEstopped(ptr: Long): Boolean
    private external fun _getDSMode(ptr: Long)
    private external fun _getMode(ptr: Long)
    private external fun _setMode(ptr: Long, mode: Mode)
    private external fun _getTeamNumber(ptr: Long): Int
    private external fun _restartCode(ptr: Long)
    private external fun _restartRIO(ptr: Long)
    private external fun _setAlliance(ptr: Long, alliance: Long)
    private external fun _setGSM(ptr: Long, gsm: String)
    private external fun _setTeamNumber(ptr: Long, team: Int)
    private external fun _isConnected(ptr: Long): Boolean
    private external fun _isCodeAlive(ptr: Long): Boolean
    private external fun _setTCPConsumer(ptr: Long, callback: (String) -> Unit)
    //TODO: how the flying fuck do i transfer a function pointer
}