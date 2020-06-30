package com.github.shatteredsuite.shatteredrifts

import com.github.shatteredsuite.core.ShatteredPlugin
import com.github.shatteredsuite.shatteredrifts.commands.BaseCommand
import com.github.shatteredsuite.shatteredrifts.config.ConfigManager
import com.github.shatteredsuite.shatteredrifts.data.LocationDeserializer
import com.github.shatteredsuite.shatteredrifts.data.LocationSerializer
import com.github.shatteredsuite.shatteredrifts.data.RiftLocation
import com.github.shatteredsuite.shatteredrifts.data.RiftManager
import com.github.shatteredsuite.shatteredrifts.events.RiftTeleportEntityEvent
import com.github.shatteredsuite.shatteredrifts.events.RiftTeleportPlayerEvent
import com.github.shatteredsuite.shatteredrifts.ext.offset
import com.github.shatteredsuite.shatteredrifts.util.Vector2
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

class ShatteredRifts : ShatteredPlugin() {
    companion object {
        lateinit var instance: ShatteredRifts
            private set
    }

    var riftManager = RiftManager()
    var stoneTimings = mutableMapOf<String, Long>()
    val gson: Gson

    init {
        instance = this
        this.createMessages = true
        this.bStatsId = 8042
        val builder = GsonBuilder()
        builder.registerTypeAdapter(Location::class.java, LocationDeserializer())
        builder.registerTypeAdapter(Location::class.java, LocationSerializer(this))
        gson = builder.setPrettyPrinting().create()
    }

    override fun postEnable() {
        loadContent()
        getCommand("rifts")!!.setExecutor(BaseCommand(this))
    }

    override fun preDisable() {
        saveContent()
    }

    override fun onFirstTick() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, {
            this.updateStones()
        }, 20L, 20L)
    }

    fun loadContent() {

        val rifts = ConfigManager.loadRifts(this)
        for (rift in rifts) {
            riftManager.register(rift)
        }
    }

    fun saveContent() {
        ConfigManager.writeRifts(this, riftManager.getAll().toList())
    }

    fun updateStones() {
        for (stoneLocation in riftManager.getAll()) {
            if (!stoneLocation.enabled) {
                continue
            }
            // Add stones to timings if they're not in there already. Add one if they are.
            stoneTimings[stoneLocation.id] = stoneTimings[stoneLocation.id]?.plus(1) ?: 0
            // Teleport, play particles every second.
            if (stoneTimings[stoneLocation.id]!! > stoneLocation.timing) {
                val entities = stoneLocation.findEntities()
                for (entity in entities) {
                    teleportEntity(entity, stoneLocation)
                }
                stoneLocation.playActiveParticles()
            }
            // Play particles every 5 seconds.
            else if (stoneTimings[stoneLocation.id]!! % stoneLocation.particleFrequency == 0L) {
                stoneLocation.playAmbientParticles(stoneTimings[stoneLocation.id]!!.toFloat() / (stoneLocation.timing.toFloat()))
            }
            // Reset timer.
            if (stoneTimings[stoneLocation.id]!! > stoneLocation.timing + stoneLocation.duration) {
                stoneTimings[stoneLocation.id] = 0
            }
        }
    }

    private fun teleportEntity(entity: Entity, riftLocation: RiftLocation) {
        val cancelled = if (entity is Player) {
            val event = RiftTeleportPlayerEvent(entity, riftLocation)
            Bukkit.getPluginManager().callEvent(event)
            event.isCancelled
        } else {
            val event = RiftTeleportEntityEvent(entity, riftLocation)
            Bukkit.getPluginManager().callEvent(event)
            event.isCancelled
        }
        if (cancelled) {
            return
        }
        entity.teleport(riftLocation.destination
                .offset(Vector2(entity.location.x - riftLocation.location.x,
                        entity.location.y - riftLocation.location.y)))
    }
}
