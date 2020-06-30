package com.github.shatteredsuite.shatteredstones.util

import kotlin.math.*
import kotlin.random.Random

data class Vector2(val x: Double, val y: Double) {
    operator fun times(scalar: Int) : Vector2 {
        return Vector2(x*scalar.toDouble(), y*scalar.toDouble())
    }

    operator fun times(scalar: Double) : Vector2 {
        return Vector2(x*scalar, y*scalar)
    }

    operator fun times(other: Vector2) : Vector2 {
        return Vector2(other.x * x, other.y * y)
    }

    fun offsetX(offset: Double): Vector2 {
        return Vector2(x + offset, y)
    }

    fun offsetY(offset: Double) : Vector2 {
        return Vector2(x, y + offset)
    }

    fun offset(offsetX: Double, offsetY: Double): Vector2 {
        return Vector2(x + offsetX, y + offsetY)
    }

    operator fun plus(vec: Vector2): Vector2 {
        return offset(vec.x, vec.y)
    }

    operator fun div(other: Double) : Vector2 {
        return Vector2(this.x / other, this.y / other)
    }

    fun magnitude() : Double {
        return sqrt((this.x).pow(2) + (this.y).pow(2))
    }

    fun normalize() : Vector2 {
        return this * (1 / magnitude())
    }

    infix fun dot(other: Vector2) : Double {
        return this.x * other.x + this.y * other.y
    }

    override operator fun equals(other: Any?) : Boolean {
        if(other is Vector2) {
            return this.x == other.x && this.y == other.y
        }
        return false
    }

    fun radiusPoint(point: Double, radius: Int) : Vector2 {
        val y = sin(point)
        val x = cos(point)
        val vec = Vector2(x, y) * radius
        return this + vec
    }

    fun randomRadiusPoint(radius: Int) : Vector2 {
        return radiusPoint(randomRadian(), radius)
    }

    private fun randomRadian() : Double {
        return Random.nextFloat() * PI
    }

    fun distance(point2: Vector2): Double {
        return sqrt((point2.x - this.x).pow(2) + (point2.y - this.y).pow(2))
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}