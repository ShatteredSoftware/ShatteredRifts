package com.github.shatteredsuite.shatteredrifts.commands.edit

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.validation.Validators
import com.github.shatteredsuite.shatteredrifts.ShatteredRifts
import com.github.shatteredsuite.shatteredrifts.commands.EditCommand
import com.github.shatteredsuite.shatteredrifts.validators.RiftLocationValidator

class EditEnabledCommand(val instance: ShatteredRifts, parent: EditCommand) :
        LeafCommand(instance, parent, "enabled", "shatteredrifts.command.edit",
                "command.edit") {
    init {
        addAlias("on")
    }

    override fun execute(ctx: CommandContext) {
        val oldRift = RiftLocationValidator.validate(ctx.args[0])
        val enabled = Validators.booleanValidator.validate(ctx.args[1])
        val rift = oldRift.copy(enabled = enabled)
        instance.riftManager.delete(oldRift.id)
        instance.riftManager.register(rift)
        ctx.contextMessages.putAll(rift.placeholders)
        ctx.contextMessages["key"] = label
        ctx.contextMessages["value"] = enabled.toString()
        ctx.sendMessage("rift-edited", true)
    }

    override fun tabComplete(ctx: CommandContext): List<String> {
        if (ctx.args.size <= 1) {
            return TabCompleters.completeBoolean(ctx.args, 0)
        }
        return emptyList()
    }
}