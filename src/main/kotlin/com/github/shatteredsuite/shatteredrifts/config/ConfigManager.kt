package com.github.shatteredsuite.shatteredrifts.config

import com.github.shatteredsuite.shatteredrifts.ShatteredRifts
import com.github.shatteredsuite.shatteredrifts.data.RiftLocation
import com.google.gson.reflect.TypeToken
import java.io.*
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.function.Predicate
import java.util.stream.Collectors

object ConfigManager {
    @JvmStatic
    fun loadRifts(instance: ShatteredRifts) : List<RiftLocation> {
        if (!instance.dataFolder.exists()) {
            instance.dataFolder.mkdirs()
            writeRifts(instance, listOf(RiftLocation.DEFAULT))
        }
        val riftsFile = File(instance.dataFolder, "rifts.json")
        if(!riftsFile.exists()) {
            writeRifts(instance, listOf(RiftLocation.DEFAULT))
        }
        val type = object : TypeToken<ArrayList<RiftLocation>>() {}.type
        return try {
            instance.gson.fromJson(FileReader(riftsFile), type)
        } catch (e: FileNotFoundException) {
            ArrayList()
        }
    }

    @JvmStatic
    fun writeRifts(instance: ShatteredRifts, rifts: Iterable<RiftLocation>) {
        if (!instance.dataFolder.exists()) {
            instance.dataFolder.mkdirs()
        }
        val file = File(instance.dataFolder, "rifts.json")
        try {
            val configText = instance.gson.toJson(rifts)
            val writer: Writer = OutputStreamWriter(FileOutputStream(file), StandardCharsets.UTF_8)
            writer.write(configText)
            writer.close()
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }
}