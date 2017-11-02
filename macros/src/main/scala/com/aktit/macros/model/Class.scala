package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 06/10/17
  */
case class Class(
	meta: Class.Meta
) extends Code
	with Method.Contains[Class]
	with Meta.Contains[Class.Meta]
	with Code.Name[Class]
	with Templ.Contains[Class]
{
	override def name: String = meta.tname.value

	override def withName(name: String): Class = copy(meta = meta.copy(tname = Type.Name(name)))

  def withConstructorParameter(param: Param): Class = withConstructorParameters(Seq(param))

  def withConstructorParameters(params: Seq[Param]): Class = withConstructorParameterss(Seq(params))

  def withConstructorParameterss(paramss: Seq[Seq[Param]]): Class = copy(
    meta = meta.copy(
      paramss = paramss.map(_.map(_.meta.param).toList).toList
    )
  )

	override def tree = q"..${meta.mods} class ${meta.tname}[..${meta.tparams}] ..${meta.ctorMods} (...${meta.paramss}) extends ${meta.template}"

	override def withTemplate(t: Template) = copy(meta = meta.copy(template = t))
}

object Class extends PartialParser[Class]
{
	case class Meta(
		mods: List[Mod],
		tname: Type.Name,
		tparams: List[Type.Param],
		ctorMods: List[Mod],
		paramss: List[List[Term.Param]],
		template: Template
	) extends com.aktit.macros.model.Meta with com.aktit.macros.model.Meta.Template

	override def parser = {
		case q"..$mods class $tname[..$tparams] ..$ctorMods (...$paramss) extends $template" =>
			Class(Meta(mods, tname, tparams, ctorMods, paramss, template))
	}

  def withName(name: String) = parser(q"class X {}").withName(name)
}