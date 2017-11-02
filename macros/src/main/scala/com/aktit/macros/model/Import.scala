package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 02/11/17
  */
case class Import(meta: Import.Meta) extends Code
{
  override def tree = q"import ..${meta.importersnel}"
}

object Import extends PartialParser[Import]
{

  case class Meta(importersnel: List[Importer]) extends com.aktit.macros.model.Meta

  override def parser = {
    case q"import ..$importersnel" =>
      Import(Meta(importersnel))
  }
}
