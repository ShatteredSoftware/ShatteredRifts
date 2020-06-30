package com.github.shatteredsuite.shatteredrifts.commands.edit

import com.github.shatteredsuite.core.commands.ArgParser
import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.shatteredrifts.ShatteredRifts
import com.github.shatteredsuite.shatteredrifts.commands.EditCommand
import com.github.shatteredsuite.shatteredrifts.validators.RiftLocationValidator
import org.bukkit.entity.Player

class EditLocationCommand(val instance: ShatteredRifts, parent: EditCommand) :
        LeafCommand(instance, parent, "ambientParticle", "shatteredrifts.command.edit",
                "command.edit") {
    init {
        addAlias("loc")
    }

    override fun execute(ctx: CommandContext) {
        val oldRift = RiftLocationValidator.validate(ctx.args[0])
        val loc = if (ctx.sender is Player && ctx.args.size == 1) {
            (ctx.sender as Player).location
        } else if (ctx.sender is Player && ctx.args.size == 4) {
            ArgParser.validShortLocation(ctx.args, 1, ctx.sender as Player)
        } else ArgParser.validLocation(ctx.args, 1)
        val rift = oldRift.copy(location = loc)
        instance.riftManager.delete(oldRift.id)
        instance.riftManager.register(rift)
        ctx.contextMessages.putAll(rift.placeholders)
        ctx.contextMessages["key"] = label
        ctx.contextMessages["value"] = "${loc.x} ${loc.y} ${loc.z}"
        ctx.sendMessage("rift-edited", true)
    }

    override fun tabComplete(ctx: CommandContext): List<String> {
        if (ctx.args.isNotEmpty() && ctx.sender is Player) {
            return TabCompleters.completeLocationPlayer(ctx.args, 0, ctx.sender as Player)
        }
        return emptyList()
    }
}
