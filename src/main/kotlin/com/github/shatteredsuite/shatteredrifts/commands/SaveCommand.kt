package com.github.shatteredsuite.shatteredrifts.commands

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.shatteredrifts.ShatteredRifts

class SaveCommand(val instance: ShatteredRifts, parent: BaseCommand) :
        LeafCommand(instance, parent, "save", "shatteredrifts.command.create", "command.create") {
    override fun execute(ctx: CommandContext) {
        instance.saveContent()
        ctx.sendImportantMessage("saved", true)
    }
}