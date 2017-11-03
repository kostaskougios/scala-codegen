package com.aktit.macros.model

import com.aktit.macros.model

import scala.collection.immutable
import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 06/10/17
  */
case class Class(
	meta: Class.Meta
) extends Code
	with Method.Contains[Class]
	with Meta.Contains
  with Meta.ContainsTypeParams[Class]
	with Code.Name[Class]
	with Templ.Contains[Class]
{
	override def name: String = meta.tname.value

	override def withName(name: String): Class = copy(meta = meta.copy(tname = scala.meta.Type.Name(name)))

	def withConstructorParameter(param: TermParam): Class = withConstructorParameters(Seq(param))

	def withConstructorParameters(params: Seq[TermParam]): Class = withConstructorParameterss(Seq(params))

	def withConstructorParameterss(paramss: Seq[Seq[TermParam]]): Class = copy(
    meta = meta.copy(
      paramss = paramss.map(_.map(_.meta.param).toList).toList
    )
  )

	def extending: immutable.Seq[Type] = templ.inits.map(_.tpe).map(Type.apply)

	def withExtending(types: Seq[Type]) = withTempl(templ.copy(inits = types.map(_.meta.tpe).map(tpe => Init(tpe, Name("invalid"), Nil)).toList))

	override def tree = q"..${meta.mods} class ${meta.tname}[..${meta.tparams}] ..${meta.ctorMods} (...${meta.paramss}) extends ${meta.template}"

	override def withTemplate(t: Template) = copy(meta = meta.copy(template = t))

  override def withTypeParams(params: Seq[TypeParam]) = copy(
    meta = meta.copy(
      tparams = params.map(_.meta.param).toList
    )
  )

	def toTermParam(paramName: String) = {
		val tpe = t"${meta.tname}"
		param"${Name(paramName)} : ${Option(tpe)}"
	}

}

object Class extends PartialParser[Class]
{
	case class Meta(
		mods: List[Mod],
		tname: scala.meta.Type.Name,
		tparams: List[scala.meta.Type.Param],
		ctorMods: List[Mod],
		paramss: List[List[Term.Param]],
		template: Template
	) extends model.Meta with model.Meta.Template with model.Meta.TypeParams

	override def parser = {
		case q"..$mods class $tname[..$tparams] ..$ctorMods (...$paramss) extends $template" =>
			Class(Meta(mods, tname, tparams, ctorMods, paramss, template))
	}

  def withName(name: String) = parser(q"class X {}").withName(name)
}