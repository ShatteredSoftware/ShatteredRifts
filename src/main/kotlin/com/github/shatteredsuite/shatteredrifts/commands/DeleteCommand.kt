package com.github.shatteredsuite.shatteredrifts.commands

import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.WrappedCommand
import com.github.shatteredsuite.core.commands.predicates.ArgumentMinimumPredicate
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.commands.responses.CancelResponse
import com.github.shatteredsuite.core.validation.ChoiceValidator
import com.github.shatteredsuite.shatteredrifts.ShatteredRifts

class DeleteCommand(private val instance: ShatteredRifts, baseCommand: BaseCommand) :
        WrappedCommand(instance, baseCommand, "delete", "shatteredrifts.command.delete",
                "commands.delete") {

    init {
        addAlias("d")
        contextPredicates["args"] = ArgumentMinimumPredicate(CancelResponse(this.helpPath), 1)
    }

    override fun execute(ctx: CommandContext) {
        val choiceValidator = ChoiceValidator(instance.riftManager.getIds().toList())
        val id = choiceValidator.validate(ctx.args[0])
        val rift = instance.riftManager.get(id)
        instance.riftManager.delete(id)
        ctx.contextMessages.putAll(rift!!.placeholders)
        ctx.sendMessage("rift-deleted", true)
    }

    override fun onTabComplete(ctx: CommandContext): List<String> {
        if(ctx.args.size <= 1) {
            return TabCompleters.completeFromOptions(ctx.args, ctx.args.lastIndex,
                    instance.riftManager.getIds().toList())
        }
        return emptyList()
    }
}
