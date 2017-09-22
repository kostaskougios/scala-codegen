package com.aktit.macros

import java.io.File

import com.aktit.macros.model.{N, Package}

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
	def parse(sources: Seq[String]): Seq[N] = sources.flatMap(parseSource)

	def files(files: Seq[File]): Seq[N] = parse(files.map { file =>
		val src = scala.io.Source.fromFile(file, "UTF-8")
		try src.mkString finally src.close()
	})

	def file(file: File): Seq[N] = files(Seq(file))

	def parseSource(source: String): Seq[N] = {
		// see https://docs.scala-lang.org/overviews/quasiquotes/syntax-summary.html
		source.parse[Source] match {
			case Parsed.Success(tree) =>
				tree.children.map(Package.parser)
			case Parsed.Error(_, _, details) =>
				throw details
		}
	}
}

object Parser
{
	def apply(): Parser = new Parser
}