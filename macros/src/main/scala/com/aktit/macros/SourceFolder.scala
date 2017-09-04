package com.aktit.macros

import java.io.File

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
class SourceFolder private(dir: File)
{
	private val p = Parser()

	def parse(fullClassName: String) =
		p.file(new File(dir, s"${fullClassName.replace('.', '/')}.scala"))
}

object SourceFolder
{
	def apply(dir: File): SourceFolder = new SourceFolder(dir)
}