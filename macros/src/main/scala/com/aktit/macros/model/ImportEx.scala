package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 02/11/17
  */
case class ImportEx(meta: ImportEx.Meta) extends CodeEx
{
  override def tree = q"import ..${meta.importersnel}"
}

object ImportEx extends PartialParser[ImportEx]
{

  case class Meta(importersnel: List[Importer]) extends com.aktit.macros.model.MetaEx

  override def parser = {
    case q"import ..$importersnel" =>
      ImportEx(Meta(importersnel))
  }
}
