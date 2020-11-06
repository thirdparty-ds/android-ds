package com.redrield.androidds.util

import kotlin.math.min

/**
 * This is a simple circular buffer so we don't need to "bucket brigade" copy old values.
 */
class CircularBuffer(size: Int) {
    var data = FloatArray(size)

    // Index of element at front of buffer
    private var front = 0

    // Number of elements used in buffer
    private var length = 0

    /**
     * Returns number of elements in buffer.
     *
     * @return number of elements in buffer
     */
    val size get() = length

    /**
     * Get value at front of buffer.
     *
     * @return value at front of buffer
     */
    val first: Float
        get() = data[front]

    /**
     * Get value at back of buffer.
     *
     * @return value at back of buffer
     */
    val last: Float
        get() =
            if (length == 0) {
                0f
            } else data[(front + length - 1) % data.size]

    /**
     * Push new value onto front of the buffer. The value at the back is overwritten if the buffer is
     * full.
     */
    fun addFirst(value: Float) {
        if (data.isEmpty()) {
            return
        }
        front = moduloDec(front)
        data[front] = value
        if (length < data.size) {
            length++
        }
    }

    /**
     * Push new value onto back of the buffer. The value at the front is overwritten if the buffer is
     * full.
     */
    fun addLast(value: Float) {
        if (data.isEmpty()) {
            return
        }
        data[(front + length) % data.size] = value
        if (length < data.size) {
            length++
        } else {
            // Increment front if buffer is full to maintain size
            front = moduloInc(front)
        }
    }

    /**
     * Pop value at front of buffer.
     *
     * @return value at front of buffer
     */
    fun removeFirst(): Float {
        // If there are no elements in the buffer, do nothing
        if (length == 0) {
            return 0f
        }
        val temp = data[front]
        front = moduloInc(front)
        length--
        return temp
    }

    /**
     * Pop value at back of buffer.
     */
    fun removeLast(): Float {
        // If there are no elements in the buffer, do nothing
        if (length == 0) {
            return 0f
        }
        length--
        return data[(front + length) % data.size]
    }

    /**
     * Resizes internal buffer to given size.
     *
     *
     * A new buffer is allocated because arrays are not resizable.
     */
    fun resize(size: Int) {
        val newBuffer = FloatArray(size)
        length = min(length, size)
        for (i in 0 until length) {
            newBuffer[i] = data[(front + i) % data.size]
        }
        data = newBuffer
        front = 0
    }

    /**
     * Sets internal buffer contents to zero.
     */
    fun clear() {
        for (i in data.indices) {
            data[i] = 0f
        }
        front = 0
        length = 0
    }

    /**
     * Get the element at the provided index relative to the start of the buffer.
     *
     * @return Element at index starting from front of buffer.
     */
    operator fun get(index: Int): Float {
        return data[(front + index) % data.size]
    }

    fun forEachIndexed(fn: (Int, Float) -> Unit) {
        for(i in 0 until size) {
            fn(i, data[(front + i) % size])
        }
    }

    /**
     * Increment an index modulo the length of the m_data buffer.
     */
    private fun moduloInc(index: Int): Int {
        return (index + 1) % data.size
    }

    /**
     * Decrement an index modulo the length of the m_data buffer.
     */
    private fun moduloDec(index: Int): Int {
        return if (index == 0) {
            data.size - 1
        } else {
            index - 1
        }
    }
}
