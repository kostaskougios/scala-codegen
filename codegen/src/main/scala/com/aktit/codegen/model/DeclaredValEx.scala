package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
case class DeclaredValEx private(meta: DeclaredValEx.Meta)
	extends ValEx
		with CodeEx.Name[DeclaredValEx]
		with TypeEx.Contains[DeclaredValEx]
{
	override def tree: Decl.Val = q"..${meta.mods} val ..${meta.patsnel}: ${meta.tpe}"

	override def name = meta.patsnel.collectFirst {
		case n: Pat.Var => n.name.value
	}.get

	override def withName(name: String) = copy(
		meta = meta.copy(
			patsnel = List(Pat.Var(Term.Name(name)))
		)
	)

	override def `type` = TypeEx.apply(meta.tpe)

	override def withType(t: TypeEx) = copy(
		meta = meta.copy(
			tpe = t.meta.tpe
		)
	)

	override def withMods(mods: ModsEx) = copy(meta = meta.copy(mods = mods.meta.mods.toList))

}

object DeclaredValEx extends PartialParser[DeclaredValEx]
{

	case class Meta(mods: List[Mod], patsnel: List[scala.meta.Pat], tpe: Type) extends MetaEx with MetaEx.Mods

	def fromSource(src: String): DeclaredValEx = parser(src.parse[Stat].get)

	def withName(name: String): DeclaredValEx = parser(q"val x : Unit").withName(name)

	override def parser = {
		case q"..$mods val ..$patsnel: $tpe" =>
			DeclaredValEx(Meta(mods, patsnel, tpe))
	}

	trait Contains
	{
		def vals: Seq[DeclaredValEx]
	}

}
