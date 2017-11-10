package com.aktit.macros.model

import com.aktit.macros.model

import scala.collection.immutable
import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 06/10/17
  */
case class ClassEx(
	meta: ClassEx.Meta
) extends CodeEx
	with MethodEx.Contains[ClassEx]
	with MetaEx.Contains
	with MetaEx.ContainsTypeParams[ClassEx]
	with CodeEx.Name[ClassEx]
	with TemplateEx.Contains[ClassEx]
{
	override def name: String = meta.tname.value

	override def withName(name: String): ClassEx = copy(meta = meta.copy(tname = scala.meta.Type.Name(name)))

	def constructorParameters: List[List[TermParamEx]] = meta.paramss.map(_.map(TermParamEx.apply))

	def withConstructorParameter(param: TermParamEx): ClassEx = withConstructorParameters(Seq(param))

	def withConstructorParameters(params: Seq[TermParamEx]): ClassEx = withConstructorParameterss(Seq(params))

	def withConstructorParameterss(paramss: Seq[Seq[TermParamEx]]): ClassEx = copy(
    meta = meta.copy(
      paramss = paramss.map(_.map(_.meta.param).toList).toList
    )
  )

	def extending: immutable.Seq[TypeEx] = templ.inits.map(_.tpe).map(TypeEx.apply)

    def withExtending(tpe: TypeEx): ClassEx = withExtending(Seq(tpe))

    def withExtending(types: Seq[TypeEx]): ClassEx = withTempl(templ.copy(inits = types.map(_.meta.tpe).map(tpe => Init(tpe, Name("invalid"), Nil)).toList))

	override def tree = q"..${meta.mods} class ${meta.tname}[..${meta.tparams}] ..${meta.ctorMods} (...${meta.paramss}) extends ${meta.template}"

	override def withTemplate(t: Template) = copy(meta = meta.copy(template = t))

	override def withTypeParams(params: Seq[TypeParamEx]) = copy(
    meta = meta.copy(
      tparams = params.map(_.meta.param).toList
    )
  )

    def toTermParam(paramName: String, tpesnel: Seq[TypeEx]) = TermParamEx {
		val tpe = t"${meta.tname}[..${tpesnel.map(_.meta.tpe).toList}]"
		param"${Name(paramName)} : ${Option(tpe)}"
	}

    def toType: TypeEx = TypeEx(t"${meta.tname}[..${typeParams.map(_.toType.meta.tpe).toList}]")

}

object ClassEx extends PartialParser[ClassEx]
{
	case class Meta(
		mods: List[Mod],
		tname: scala.meta.Type.Name,
		tparams: List[scala.meta.Type.Param],
		ctorMods: List[Mod],
		paramss: List[List[Term.Param]],
		template: Template
	) extends model.MetaEx with model.MetaEx.Template with model.MetaEx.TypeParams

	override def parser = {
		case q"..$mods class $tname[..$tparams] ..$ctorMods (...$paramss) extends $template" =>
			ClassEx(Meta(mods, tname, tparams, ctorMods, paramss, template))
	}

    def withName(name: String): ClassEx = parser(q"class X {}").withName(name)
}