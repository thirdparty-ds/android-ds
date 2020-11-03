package com.redrield.androidds.ds


class MockDriverStation : DriverStation {
    override val batteryVoltage: Float
        get() = 0f

    override val estopped: Boolean
        get() = false

    override val enabled: Boolean
        get() = false

    override var mode: Mode
        get() = Mode.Autonomous
        set(value) {}

    override var teamNumber: Int
        get() = 420
        set(value) {}

    override val connected: Boolean
        get() = true

    override val codeAlive: Boolean
        get() = true

    override fun enable() {
    }

    override fun disable() {
    }

    override fun estop() {
    }

    override fun restartCode() {
    }

    override fun restartRoboRIO() {
    }

    override fun setAlliance(alliance: Alliance) {
    }

    override fun setGameSpecificMessage(msg: String) {
    }

    override fun setTCPConsumer(consumer: (String) -> Unit) {
    }

    override fun close() {

    }

}