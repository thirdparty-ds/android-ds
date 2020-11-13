package com.redrield.androidds.ds

class RealDriverStation(team: Int, alliance: Alliance): DriverStation, AutoCloseable {
    val ptr: Long

    init {
        ptr = new(team, alliance.ptr)
    }

    constructor(team: Int) : this(team, Alliance(Colour.Blue, 1))
    constructor() : this(0)

    override val batteryVoltage: Float
        get() = _getBatteryVoltage(ptr)

    override fun enable() {
        _enable(ptr)
    }

    override fun disable() {
        _disable(ptr)
    }

    override val enabled: Boolean
        get() = _isEnabled(ptr)

    override fun estop() {
        _estop(ptr)
    }

    override val estopped: Boolean
        get() = _isEstopped(ptr)

    override var mode: Mode
        get() = Mode.values()[_getMode(ptr)]
        set(value) = _setMode(ptr, value.ordinal)

    override var teamNumber: Int
        get() = _getTeamNumber(ptr)
        set(value) = _setTeamNumber(ptr, value)

    override fun restartCode() {
        _restartCode(ptr)
    }

    override fun restartRoboRIO() {
        _restartRIO(ptr)
    }

    override fun setAlliance(alliance: Alliance) {
        _setAlliance(ptr, alliance.ptr)
    }

    override fun setGameSpecificMessage(msg: String) {
        _setGSM(ptr, msg)
    }

    override val connected: Boolean
        get() = _isConnected(ptr)

    override val codeAlive: Boolean
        get() = _isCodeAlive(ptr)

    override fun setTCPConsumer(consumer: (String) -> Unit) {
        _setTCPConsumer(ptr, consumer)
    }

    override fun close() {
        this.destroy(ptr)
    }

    private external fun new(team: Int, alliance: Long): Long
    private external fun destroy(ptr: Long)
    private external fun _getBatteryVoltage(ptr: Long): Float
    private external fun _enable(ptr: Long)
    private external fun _disable(ptr: Long)
    private external fun _isEnabled(ptr: Long): Boolean
    private external fun _estop(ptr: Long)
    private external fun _isEstopped(ptr: Long): Boolean
    private external fun _getDSMode(ptr: Long): Int
    private external fun _getMode(ptr: Long): Int
    private external fun _setMode(ptr: Long, mode: Int)
    private external fun _getTeamNumber(ptr: Long): Int
    private external fun _restartCode(ptr: Long)
    private external fun _restartRIO(ptr: Long)
    private external fun _setAlliance(ptr: Long, alliance: Long)
    private external fun _setGSM(ptr: Long, gsm: String)
    private external fun _setTeamNumber(ptr: Long, team: Int)
    private external fun _isConnected(ptr: Long): Boolean
    private external fun _isCodeAlive(ptr: Long): Boolean
    private external fun _setTCPConsumer(ptr: Long, callback: (String) -> Unit)
}