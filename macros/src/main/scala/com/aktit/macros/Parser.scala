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
class Parser(files: Seq[File])
{
	def parse: Seq[N] = files.flatMap(parseFile)

	private def parseFile(file: File) = {
    val source = scala.io.Source.fromFile(file, "UTF-8").mkString

    // see https://docs.scala-lang.org/overviews/quasiquotes/syntax-summary.html
    source.parse[Source] match {
      case Parsed.Success(tree) =>
		  tree.children.map(Parser.parse)
      case Parsed.Error(_, _, details) =>
        throw details
    }
  }

}

object Parser
{
	def parse(child: Tree): N = child match {
    case q"..$mods trait $tname[..$tparams] extends $template" =>
      new Trait(mods, tname, tparams, template)
    case q"package $ref { ..$topstats }" =>
		new Package(ref, topstats.map(parse))
    case _ => throw new IllegalArgumentException(s"can't recognize\n$child")
  }
}