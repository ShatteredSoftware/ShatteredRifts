package com.github.shatteredsuite.shatteredrifts

import com.github.shatteredsuite.core.ShatteredPlugin
import com.github.shatteredsuite.shatteredrifts.commands.BaseCommand
import com.github.shatteredsuite.shatteredrifts.config.ConfigManager
import com.github.shatteredsuite.shatteredrifts.data.LocationDeserializer
import com.github.shatteredsuite.shatteredrifts.data.LocationSerializer
import com.github.shatteredsuite.shatteredrifts.data.RiftManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.Bukkit
import org.bukkit.Location

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
        ConfigManager.writeRifts(this, riftManager.getAll())
    }

    fun updateStones() {
        for(stoneLocation in riftManager.getAll()) {
            // Add stones to timings if they're not in there already. Add one if they are.
            stoneTimings[stoneLocation.id] = stoneTimings[stoneLocation.id]?.plus(1) ?: 0
            // Play particles every 5 seconds.
            if(stoneTimings[stoneLocation.id]!! % stoneLocation.particleFrequency == 0L) {
                stoneLocation.playAmbientParticles(stoneTimings[stoneLocation.id]!!.toFloat() / (stoneLocation.timing.toFloat()))
            }
            // Teleport, play particles every second.
            if(stoneTimings[stoneLocation.id]!! > stoneLocation.timing) {
                stoneLocation.findPlayers().forEach { it.teleport(stoneLocation.destination) }
                stoneLocation.playActiveParticles()
            }
            // Reset timer.
            if(stoneTimings[stoneLocation.id]!! > stoneLocation.timing + stoneLocation.duration) {
                stoneTimings[stoneLocation.id] = 0
            }
        }
    }
}
