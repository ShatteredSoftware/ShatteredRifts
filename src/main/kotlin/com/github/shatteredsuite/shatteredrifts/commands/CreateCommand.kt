package com.github.shatteredsuite.shatteredrifts.commands

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.shatteredrifts.ShatteredRifts
import com.github.shatteredsuite.shatteredrifts.data.RiftLocation

class CreateCommand(private val instance: ShatteredRifts, baseCommand: BaseCommand) :
        LeafCommand(instance, baseCommand, "create", "shatteredrifts.command.create", "command.create") {
    init {
        addAlias("c")
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 1)
    }

    override fun execute(ctx: CommandContext) {
        val id = ctx.args[0]
        val rift = RiftLocation.DEFAULT.copy(id = id)
        instance.riftManager.register(rift)
        ctx.contextMessages.putAll(rift.placeholders)
        ctx.sendMessage("rift-created", true)
    }
}
