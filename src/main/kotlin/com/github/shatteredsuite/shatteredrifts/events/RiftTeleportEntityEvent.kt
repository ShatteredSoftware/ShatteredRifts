package com.github.shatteredsuite.shatteredrifts.events

import com.github.shatteredsuite.shatteredrifts.data.RiftLocation
import org.bukkit.entity.Entity
import org.bukkit.event.entity.EntityTeleportEvent

class RiftTeleportEntityEvent(entity: Entity, val rift: RiftLocation) : EntityTeleportEvent(entity, entity.location, rift.destination)