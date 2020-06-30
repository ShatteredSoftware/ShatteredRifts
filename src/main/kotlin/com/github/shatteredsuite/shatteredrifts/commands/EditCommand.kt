package com.github.shatteredsuite.shatteredrifts.commands

import com.github.shatteredsuite.core.commands.ParameterizedBranchCommand
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.shatteredrifts.ShatteredRifts
import com.github.shatteredsuite.shatteredrifts.commands.edit.*
import java.time.Duration

class EditCommand(val instance: ShatteredRifts, parent: BaseCommand) :
        ParameterizedBranchCommand(instance, parent, "edit", "shatteredrifts.command.edit",
                "command.edit.base") {
    init {
        addAlias("e")
        registerSubcommand(EditAmbientParticleCommand(instance, this))
        registerSubcommand(EditActiveParticleCommand(instance, this))
        registerSubcommand(EditDestinationCommand(instance, this))
        registerSubcommand(EditDurationCommand(instance, this))
        registerSubcommand(EditEnabledCommand(instance, this))
        registerSubcommand(EditHeightCommand(instance, this))
        registerSubcommand(EditLocationCommand(instance, this))
        registerSubcommand(EditParticleFrequencyCommand(instance, this))
        registerSubcommand(EditRadiusCommand(instance, this))
        registerSubcommand(EditTimingCommand(instance, this))
    }

    override fun provideCompletions(ctx: CommandContext?): List<String> {
        return instance.riftManager.getIds().toList()
    }
}
