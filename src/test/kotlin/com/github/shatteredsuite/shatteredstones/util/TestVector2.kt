package com.github.shatteredsuite.shatteredstones.util

import org.junit.Assert
import org.junit.Test
import kotlin.math.sqrt

class TestVector2 {
    private val vec0 = Vector2(0.0, 0.0)
    private val vec1 = Vector2(1.0, 1.0)
    private val vec2 = Vector2(2.0, 2.0)
    private val vec3 = Vector2(3.0, 3.0)

    @Test
    fun `test math`() {
        val vec6 = vec2 * vec3
        Assert.assertEquals(Vector2(6.0, 6.0), vec6)
        val zeroVec = vec0 * vec6
        Assert.assertEquals(vec0, zeroVec)
        Assert.assertEquals(vec2, vec1 * 2)
    }

    @Test
    fun `test magnitude`() {
        Assert.assertEquals(vec1.magnitude(), sqrt(2.0), 0.001)
    }

    @Test
    fun `test normalize`() {
        Assert.assertEquals(Vector2(1 / sqrt(2.0), 1 / sqrt(2.0)), vec1.normalize())
    }

    @Test
    fun `test offset`() {
        Assert.assertEquals(Vector2(2.0, 1.0), vec1.offsetX(1.0))
        Assert.assertEquals(Vector2(1.0, 2.0), vec1.offsetY(1.0))
        Assert.assertEquals(Vector2(2.0, 2.0), vec1.offset(1.0, 1.0))
    }

    @Test
    fun `test non-vector comparison`() {
        Assert.assertFalse(vec0 == Any())
    }

    @Test
    fun `test distance`() {
        Assert.assertEquals(vec0.distance(vec1), sqrt(2.0), 0.001)
    }

    @Test
    fun `test radius point`() {
        Assert.assertEquals(vec0.offsetX(2.0), vec0.radiusPoint(0.0, 2.0))
    }

    @Test
    fun `test random radius point`() {
        val radPoint = vec0.randomRadiusPoint(2.0)
        Assert.assertEquals(vec0.distance(radPoint), 2.0, 0.001)
    }
}