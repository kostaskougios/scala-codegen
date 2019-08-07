package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 02/11/17
  */
case class ImportEx private(meta: ImportEx.Meta) extends CodeEx
{
	def imports: Seq[Imported] = meta.importersnel.flatMap {
		i =>
			i.importees.map {
				case ie: Importee.Name =>
					TypeImport(i.ref.toString, ie.name.value)
				case ie: Importee.Rename =>
					TypeImportRenamed(i.ref.toString, ie.name.value, ie.rename.value)
				case _: Importee.Wildcard =>
					WildcardImport(i.ref.toString)
			}
	}

	override def tree = q"import ..${meta.importersnel}"
}

object ImportEx extends PartialParser[ImportEx]
{

	case class Meta(importersnel: List[Importer]) extends com.aktit.codegen.model.MetaEx

	override def parser = {
		case q"import ..$importersnel" =>
			ImportEx(Meta(importersnel))
	}

	def fromSource(code: String) = parser(code.parse[Stat].get)
}

trait Imported
{
	def packageName: String
}

case class TypeImport(packageName: String, typeName: String) extends Imported

case class TypeImportRenamed(packageName: String, typeName: String, renamedFrom: String) extends Imported

case class WildcardImport(packageName: String) extends Imported