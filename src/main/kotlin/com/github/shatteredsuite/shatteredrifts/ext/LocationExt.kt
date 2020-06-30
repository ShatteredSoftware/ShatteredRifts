package com.github.shatteredsuite.shatteredrifts.ext

import com.github.shatteredsuite.shatteredrifts.util.Vector2
import org.bukkit.Location

fun Location.column(): Vector2 {
    return Vector2(this.x, this.z)
}

fun Location.offset(vec: Vector2): Location {
    return Location(this.world, this.x + vec.x, this.y, this.z + vec.y, this.pitch, this.yaw)
}