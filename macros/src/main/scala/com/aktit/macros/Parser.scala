package com.aktit.macros

import java.io.File

import com.aktit.macros.model.{N, Package, Trait}

import scala.meta._

/**
  * http://scalameta.org/tutorial/
  *
  * https://github.com/scalameta/scalameta/blob/master/notes/quasiquotes.md
  *
  * @author kostas.kougios
  *         Date: 21/08/17
  */
class Parser private(sources: Seq[String])
{
	def parse: Seq[N] = sources.flatMap(parseFile)

	private def parseFile(source: String) = {
		// see https://docs.scala-lang.org/overviews/quasiquotes/syntax-summary.html
		source.parse[Source] match {
			case Parsed.Success(tree) =>
				tree.children.map(parse)
			case Parsed.Error(_, _, details) =>
				throw details
		}
	}

	private def parse(child: Tree): N = child match {
		case tree@q"..$mods trait $tname[..$tparams] extends $template" =>
			Trait(tree, mods, tname, tparams, template)
		case tree@q"package $ref { ..$topstats }" =>
			Package(tree, ref, topstats.map(parse))
		case _ => throw new IllegalArgumentException(s"can't recognize\n$child")
	}
}

object Parser
{
	def files(files: Seq[File]): Parser = new Parser(
		files.map { file =>
			val src = scala.io.Source.fromFile(file, "UTF-8")
			try src.mkString finally src.close()
		}
	)

	def file(file: File): Parser = files(Seq(file))

}