package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 02/11/17
  */
case class ImportEx(meta: ImportEx.Meta) extends CodeEx
{
	def imports: Seq[Imported] = meta.importersnel.flatMap {
		i =>
			i.importees.map {
				case ie: Importee.Name =>
					TypeImport(i.ref.toString, ie.name.value)
				case ie: Importee.Rename =>
					TypeImportRenamed(i.ref.toString, ie.name.value, ie.rename.value)
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
}

trait Imported
{
	def packageName: String

	def typeName: String
}

case class TypeImport(packageName: String, typeName: String) extends Imported

case class TypeImportRenamed(packageName: String, typeName: String, renamedFrom: String) extends Imported