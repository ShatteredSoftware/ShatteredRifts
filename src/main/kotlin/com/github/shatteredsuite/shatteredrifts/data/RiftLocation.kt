package com.github.shatteredsuite.shatteredrifts.data

import com.github.shatteredsuite.core.util.Identified
import com.github.shatteredsuite.shatteredrifts.ext.column
import com.github.shatteredsuite.shatteredrifts.ext.offset
import com.github.shatteredsuite.shatteredrifts.util.Vector2
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.Entity
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
    fun findEntities(): List<Entity> {
        return this.location.world?.getNearbyEntities(this.location, this.radius, this.height.toDouble(), this.radius)
        { it.location.column().distance(this.location.column()) <= this.radius }?.toList()
                ?: emptyList()
    }

    val placeholders: Map<String, String>
        get() = mapOf("id" to id, "radius" to radius.toString(),
                "height" to height.toString(), "timing" to timing.toString(),
                "duration" to duration.toString(), "particles" to particles.toString(),
                "particle-frequency" to particleFrequency.toString(),
                "ambient-particle" to ambientParticle.name, "active-particle" to activeParticle.name,
                "enabled" to enabled.toString())

    fun playAmbientParticles(gradient: Float) {
        val col = Vector2.ZERO
        val particleCount = (gradient * particles).roundToInt()
        for (i in 0..particleCount) {
            val vec = col.randomRadiusPoint(this.radius)
            val particleLocation = location.offset(vec)
            playParticle(ambientParticle, particleLocation)
        }
    }

    fun playActiveParticles() {
        val col = Vector2.ZERO
        for (i in 0..particles) {
            val vec = col.randomRadiusPoint(this.radius)
            val particleLocation = location.offset(vec)
            playParticle(activeParticle, particleLocation)
        }
    }

    private fun playParticle(particle: Particle, particleLocation: Location) {
        try {
            when (particle) {
                Particle.REDSTONE -> {
                    val data = Particle.DustOptions(Color.RED, 1f)
                    particleLocation.world!!.spawnParticle(particle, particleLocation, 1, data)
                }
                Particle.SPELL_MOB -> {
                    particleLocation.world!!.spawnParticle(particle, particleLocation, 1, 1.0, 0.0, 0.0, 1)
                }
                Particle.NOTE -> {
                    particleLocation.world!!.spawnParticle(particle, particleLocation, 1, 1.0, 0.0, 0.0, 1)
                }
                Particle.BLOCK_CRACK -> {
                    val type = particleLocation.block.type
                    val fixedType = if (type != Material.AIR || type != Material.CAVE_AIR || type != Material.VOID_AIR) {
                        Material.STONE
                    } else type
                    particleLocation.world!!.spawnParticle(particle, particleLocation, 1, fixedType)
                }
                Particle.BLOCK_DUST -> {
                    val type = particleLocation.block.type
                    val fixedType = if (type != Material.AIR || type != Material.CAVE_AIR || type != Material.VOID_AIR) {
                        Material.STONE
                    } else type
                    particleLocation.world!!.spawnParticle(particle, particleLocation, 1, fixedType.createBlockData())
                }
                else -> {
                    particleLocation.world!!.spawnParticle(particle, particleLocation, 1)
                }
            }
        } catch (ex: IllegalArgumentException) {
            return
        }
    }

    companion object {
        val DEFAULT = RiftLocation("default", Location(null, 0.0, 0.0, 0.0), Location(null, 0.0, 0.0, 0.0), 4.0, 2, 30, 5, 500, 10, Particle.PORTAL, Particle.PORTAL, false)
    }
}