package com.aktit.codegen

import java.io.File

import com.aktit.codegen.model.PackageEx

import scala.meta._

/**
  * http://scalameta.org/tutorial/
  *
  * https://github.com/scalameta/scalameta/blob/master/notes/quasiquotes.md
  *
  * @author kostas.kougios
  *         Date: 21/08/17
  */
class Parser
{
	def files(files: Seq[File]): Seq[PackageEx] = parse(files.map { file =>
		val src = scala.io.Source.fromFile(file, "UTF-8")
		try src.mkString finally src.close()
	})

	def file(file: File): PackageEx = files(Seq(file)).head
	def file(fileName: String): PackageEx = file(new File(fileName))

	def parse(sources: Seq[String]): Seq[PackageEx] = sources.flatMap(parseSource)
	def parseSource(source: String): Seq[PackageEx] = {
		// see https://docs.scala-lang.org/overviews/quasiquotes/syntax-summary.html
		source.parse[Source] match {
			case Parsed.Success(tree) =>
				tree.children.map(PackageEx.parser)
			case Parsed.Error(_, _, details) =>
				throw details
		}
	}
}

object Parser
{
	def apply(): Parser = new Parser
}