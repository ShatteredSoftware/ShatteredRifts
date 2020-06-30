package com.github.shatteredsuite.shatteredrifts.commands

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.shatteredrifts.ShatteredRifts

class LoadCommand(val instance: ShatteredRifts, parent: BaseCommand) :
        LeafCommand(instance, parent, "load", "shatteredrifts.command.create", "command.create") {
    override fun execute(ctx: CommandContext) {
        instance.loadContent()
        ctx.sendImportantMessage("loaded", true)
    }
}