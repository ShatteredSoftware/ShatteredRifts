package com.github.shatteredsuite.shatteredrifts.commands.edit

import com.github.shatteredsuite.core.commands.ArgParser
import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.validation.ChoiceValidator
import com.github.shatteredsuite.core.validation.Validators
import com.github.shatteredsuite.shatteredrifts.ShatteredRifts
import com.github.shatteredsuite.shatteredrifts.commands.EditCommand
import com.github.shatteredsuite.shatteredrifts.validators.RiftLocationValidator
import org.bukkit.Particle
import org.bukkit.entity.Player

class EditRadiusCommand(val instance: ShatteredRifts, parent: EditCommand) :
        LeafCommand(instance, parent, "radius", "shatteredrifts.command.edit",
                "command.edit") {
    init {
        addAlias("r")
    }

    override fun execute(ctx: CommandContext) {
        val oldRift = RiftLocationValidator.validate(ctx.args[0])
        val radius = Validators.doubleValidator.validate(ctx.args[1])
        val rift = oldRift.copy(radius = radius)
        instance.riftManager.delete(oldRift.id)
        instance.riftManager.register(rift)
        ctx.contextMessages.putAll(rift.placeholders)
        ctx.contextMessages["key"] = label
        ctx.contextMessages["value"] = radius.toString()
        ctx.sendMessage("rift-edited", true)
    }

    override fun tabComplete(ctx: CommandContext): List<String> {
        if(ctx.args.size <= 1) {
            return TabCompleters.completeOdds(ctx.args, 0, 5)
        }
        return emptyList()
    }
}