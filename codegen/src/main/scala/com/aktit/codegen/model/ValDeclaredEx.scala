package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
case class ValDeclaredEx(meta: ValDeclaredEx.Meta)
	extends ValEx
		with CodeEx.Name[ValDeclaredEx]
		with TypeEx.Contains[ValDeclaredEx]
{
	override def tree = q"..${meta.mods} val ..${meta.patsnel}: ${meta.tpe}"

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

object ValDeclaredEx extends PartialParser[ValDeclaredEx]
{

	case class Meta(mods: List[Mod], patsnel: List[scala.meta.Pat], tpe: Type) extends MetaEx with MetaEx.Mods

	def fromSource(src: String): ValDeclaredEx = parser(src.parse[Stat].get)

	override def parser = {
		case q"..$mods val ..$patsnel: $tpe" =>
			ValDeclaredEx(Meta(mods, patsnel, tpe))
	}

	trait Contains
	{
		def vals: Seq[ValDeclaredEx]
	}

}
