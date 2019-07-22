package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
case class ValDefinedEx(meta: ValDefinedEx.Meta)
	extends ValEx
		with CodeEx.Name[ValDefinedEx]
		with TypeEx.Contains[ValDefinedEx]
{
	override def tree = q"..${meta.mods} val ..${meta.patsnel}: ${meta.tpeopt}  = ${meta.expr}"

	override def name = meta.patsnel.collectFirst {
		case n: Pat.Var => n.name.value
	}.get

	override def withName(name: String) = copy(
		meta = meta.copy(
			patsnel = List(Pat.Var(Term.Name(name)))
		)
	)

	override def `type` = TypeEx.apply(meta.tpeopt.get)

	override def withType(t: TypeEx) = copy(
		meta = meta.copy(
			tpeopt = Some(t.meta.tpe)
		)
	)

	override def withMods(mods: ModsEx) = copy(meta = meta.copy(mods = mods.meta.mods.toList))
}

object ValDefinedEx extends PartialParser[ValDefinedEx]
{

	case class Meta(mods: List[Mod], patsnel: List[scala.meta.Pat], tpeopt: Option[Type], expr: Term) extends MetaEx with MetaEx.Mods

	def fromSource(src: String): ValDefinedEx = parser(src.parse[Stat].get)

	override def parser = {
		case q"..$mods val ..$patsnel: $tpeopt = $expr" =>
			ValDefinedEx(Meta(mods, patsnel, tpeopt, expr))
	}

	trait Contains
	{
		def vals: Seq[ValDefinedEx]
	}

}
