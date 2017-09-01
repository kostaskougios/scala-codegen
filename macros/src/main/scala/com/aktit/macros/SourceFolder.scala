package com.aktit.macros

import java.io.File

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
class SourceFolder private(dir: File)
{
	def parse(fullClassName: String) =
		Parser(new File(dir, s"${fullClassName.replace('.', '/')}.scala")).parse
}

object SourceFolder
{
	def apply(dir: File): SourceFolder = new SourceFolder(dir)
}