package com.github.shatteredsuite.shatteredrifts.validators

import com.github.shatteredsuite.core.validation.ArgumentValidationException
import com.github.shatteredsuite.core.validation.Validator
import com.github.shatteredsuite.shatteredrifts.ShatteredRifts
import com.github.shatteredsuite.shatteredrifts.data.RiftLocation

object RiftLocationValidator : Validator<RiftLocation> {
    override fun validate(param: String): RiftLocation = ShatteredRifts.instance.riftManager.get(param)
            ?: throw ArgumentValidationException("Could not find a BindingType with id ${param}.",
                    ArgumentValidationException.ValidationErrorType.INVALID_FORMAT, "invalid-binding-type",
                    param, ShatteredRifts.instance.riftManager.getIds().joinToString())
}