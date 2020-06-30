package com.github.shatteredsuite.shatteredrifts.commands

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.shatteredrifts.ShatteredRifts

class SaveCommand(val instance: ShatteredRifts, parent: BaseCommand) :
        LeafCommand(instance, parent, "create", "shatteredrifts.command.create", "command.create") {
    init {
        addAlias("c")
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 1)
    }

    override fun execute(ctx: CommandContext) {
        instance.saveContent()
        ctx.sendImportantMessage("saved", true)
    }
}