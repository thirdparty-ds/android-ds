package com.redrield.androidds.ds

interface DriverStation {
    val batteryVoltage: Float
    val estopped: Boolean
    val enabled: Boolean
    var mode: Mode
    var teamNumber: Int
    val connected: Boolean
    val codeAlive: Boolean

    fun enable()
    fun disable()
    fun estop()
    fun restartCode()
    fun restartRoboRIO()
    fun setAlliance(alliance: Alliance)
    fun setGameSpecificMessage(msg: String)
    fun setTCPConsumer(consumer: (String) -> Unit)
    fun close()
}