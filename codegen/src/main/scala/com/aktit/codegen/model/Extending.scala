package com.aktit.codegen.model

/**
  * @author kostas.kougios
  *         06/08/2019 - 22:41
  */
trait Extending[A] extends TemplateEx.Contains[A]
{
	def withExtending(tpe: TypeEx): A = withExtending(Seq(tpe))

	def withExtending(types: Seq[TypeEx]): A = withTemplate(template.withExtending(types))

}
