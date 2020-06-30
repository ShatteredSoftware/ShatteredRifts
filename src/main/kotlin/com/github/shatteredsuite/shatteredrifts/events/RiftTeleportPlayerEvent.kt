package com.github.shatteredsuite.shatteredrifts.events

import com.github.shatteredsuite.shatteredrifts.data.RiftLocation
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent

class RiftTeleportPlayerEvent(player: Player, val rift: RiftLocation) : PlayerTeleportEvent(player, player.location, rift.destination, TeleportCause.PLUGIN)