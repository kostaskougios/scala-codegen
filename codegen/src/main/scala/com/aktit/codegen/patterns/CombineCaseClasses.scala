package com.aktit.codegen.patterns

import com.aktit.codegen.model.{ClassEx, ModsEx}

/**
  * @author kostas.kougios
  *         07/07/19 - 21:14
  */
object CombineCaseClasses
{
	def combine(newClassName: String, classes: ClassEx*) = {
		val vals = classes.flatMap(_.vals)

		ClassEx.withName(newClassName)
			.withConstructorParameters(vals.map(v => v.toTermParamEx.withMods(ModsEx.empty)))
			.withCaseClass
	}
}
