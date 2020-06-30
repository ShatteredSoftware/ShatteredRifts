package com.github.shatteredsuite.shatteredrifts.commands

import com.github.shatteredsuite.core.commands.BranchCommand
import com.github.shatteredsuite.shatteredrifts.ShatteredRifts

class BaseCommand(instance: ShatteredRifts) : BranchCommand(instance, null, "rifts", "shatteredrifts.command.base", "command.base") {
    init {
        registerSubcommand(CreateCommand(instance, this))
        registerSubcommand(EditCommand(instance, this))
        registerSubcommand(DeleteCommand(instance, this))
        registerSubcommand(SaveCommand(instance, this))
        registerSubcommand(LoadCommand(instance, this))
        registerSubcommand(VersionCommand(instance, this))
    }
}