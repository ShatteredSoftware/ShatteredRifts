package com.github.shatteredsuite.shatteredrifts.commands.edit

import com.github.shatteredsuite.core.commands.LeafCommand
import com.github.shatteredsuite.core.commands.TabCompleters
import com.github.shatteredsuite.core.commands.predicates.CommandContext
import com.github.shatteredsuite.core.validation.ChoiceValidator
import com.github.shatteredsuite.shatteredrifts.ShatteredRifts
import com.github.shatteredsuite.shatteredrifts.commands.EditCommand
import com.github.shatteredsuite.shatteredrifts.validators.RiftLocationValidator
import org.bukkit.Particle

class EditActiveParticleCommand(val instance: ShatteredRifts, parent: EditCommand) :
        LeafCommand(instance, parent, "activeParticle", "shatteredrifts.command.edit",
                "command.edit") {
    init {
        addAlias("active")
    }

    private val particleValidator = ChoiceValidator(Particle.values().map { it.name })

    override fun execute(ctx: CommandContext) {
        val oldRift = RiftLocationValidator.validate(ctx.args[0])
        val particleName = particleValidator.validate(ctx.args[1])
        val particle: Particle = Particle.valueOf(particleName)
        val rift = oldRift.copy(activeParticle = particle)
        instance.riftManager.delete(oldRift.id)
        instance.riftManager.register(rift)
        ctx.contextMessages.putAll(rift.placeholders)
        ctx.contextMessages["key"] = label
        ctx.contextMessages["value"] = particle.name
        ctx.sendMessage("rift-edited", true)
    }

    override fun tabComplete(ctx: CommandContext): List<String> {
        if (ctx.args.size == 1) {
            return TabCompleters.completeFromOptions(ctx.args, 1, Particle.values().map { it.name })
        }
        return emptyList()
    }
}