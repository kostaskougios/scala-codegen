package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
case class DefinedValEx private(meta: DefinedValEx.Meta)
	extends ValEx
		with CodeEx.Name[DefinedValEx]
		with TypeEx.Contains[DefinedValEx]
{
	override def tree: Defn.Val = q"..${meta.mods} val ..${meta.patsnel}: ${meta.tpeopt}  = ${meta.expr}"

	override def name = meta.patsnel.collectFirst {
		case n: Pat.Var => n.name.value
	}.get

	override def withName(name: String) = copy(
		meta = meta.copy(
			patsnel = List(Pat.Var(Term.Name(name)))
		)
	)

	override def `type` = TypeEx.apply(meta.tpeopt.getOrElse(throw new IllegalArgumentException(s"can't find type for val $name , type is probably not declared for this val")))

	override def withType(t: TypeEx) = copy(
		meta = meta.copy(
			tpeopt = Some(t.meta.tpe)
		)
	)

	def withExpression(code: String) = copy(
		meta = meta.copy(
			expr = code.parse[Term].get
		)
	)

	override def withMods(mods: ModsEx) = copy(meta = meta.copy(mods = mods.meta.mods.toList))
}

object DefinedValEx extends PartialParser[DefinedValEx]
{

	case class Meta(mods: List[Mod], patsnel: List[scala.meta.Pat], tpeopt: Option[Type], expr: Term) extends MetaEx with MetaEx.Mods

	def fromSource(src: String): DefinedValEx = parser(src.parse[Stat].get)

	def withName(name: String): DefinedValEx = parser(q"val x = ???").withName(name)

	override def parser = {
		case q"..$mods val ..$patsnel: $tpeopt = $expr " =>
			DefinedValEx(Meta(mods, patsnel, tpeopt, expr))
	}

	trait Contains
	{
		def vals: Seq[DefinedValEx]
	}

}
