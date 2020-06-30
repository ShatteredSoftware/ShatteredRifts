package com.github.shatteredsuite.shatteredrifts.data

import com.github.shatteredsuite.core.util.Identified
import com.github.shatteredsuite.shatteredrifts.ext.column
import com.github.shatteredsuite.shatteredrifts.ext.offset
import com.github.shatteredsuite.shatteredrifts.util.Vector2
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Particle
import org.bukkit.entity.Player
import java.util.stream.Collectors
import kotlin.math.roundToInt

/**
 *
 * @param location The location this stone should consider its "center"
 * @param destination Where this stone should teleport people to.
 * @param radius The radius that this stone should teleport people from.
 * @param height The number of blocks high this stone should look for players in.
 * @param timing The number of ticks between teleports.
 * @param particles The number of particles to spawn at the highest point along the gradient.
 * @param duration The number of seconds this will teleport for.
 * @param enabled Whether this rift is enabled.
 */
data class RiftLocation(override val id: String, val location: Location, val destination: Location,
                        val radius: Double, val height: Int, val timing: Int, val duration: Int,
                        val particles: Int, val particleFrequency: Int, val ambientParticle: Particle,
                        val activeParticle: Particle, val enabled: Boolean)
    : Identified {
    fun findPlayers(): List<Player> {
        return Bukkit.getOnlinePlayers().parallelStream().filter {
            it.location.world == location.world
                && location.column().distance(it.location.column()) <= radius
                && it.location.blockY in location.blockY..(location.blockY + height)
        }.collect(Collectors.toList())
    }

    fun playAmbientParticles(gradient: Float) {
        val col = Vector2.ZERO
        val particleCount = (gradient * particles).roundToInt()
        for (i in 0..particleCount) {
            val vec = col.randomRadiusPoint(this.radius)
            val particleLocation = location.offset(vec)
            particleLocation.world!!.spawnParticle(ambientParticle, particleLocation, 1)
        }
    }

    fun playActiveParticles() {
        val col = Vector2.ZERO
        for (i in 0..particles) {
            val vec = col.randomRadiusPoint(this.radius)
            val particleLocation = location.offset(vec)
            particleLocation.world!!.spawnParticle(activeParticle, particleLocation, 1)
        }
    }

    companion object {
        val DEFAULT = RiftLocation("changeme", Location(null, 0.0, 0.0, 0.0), Location(null, 0.0, 0.0, 0.0), 4.0, 2, 30, 5, 500, 10, Particle.PORTAL, Particle.PORTAL, false)
    }
}