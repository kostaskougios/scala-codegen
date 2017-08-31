package com.aktit.macros

import java.io.File

import com.aktit.macros.model.{ N, Package, Trait }

import scala.meta._

/**
  * http://scalameta.org/tutorial/
  *
  * https://github.com/scalameta/scalameta/blob/master/notes/quasiquotes.md
  *
  * @author kostas.kougios
  *         Date: 21/08/17
  */
class Enhancer(files: Seq[File])
{
  def enhance: Seq[N] = files.flatMap(enhanceFile)

  private def enhanceFile(file: File) = {
    val source = scala.io.Source.fromFile(file, "UTF-8").mkString

    // see https://docs.scala-lang.org/overviews/quasiquotes/syntax-summary.html
    source.parse[Source] match {
      case Parsed.Success(tree) =>
        tree.children.map(Enhancer.extract)
      case Parsed.Error(_, _, details) =>
        throw details
    }
  }

}

object Enhancer
{
  def extract(child: Tree): N = child match {
    case q"..$mods trait $tname[..$tparams] extends $template" =>
      new Trait(mods, tname, tparams, template)
    case q"package $ref { ..$topstats }" =>
      new Package(ref, topstats.map(extract))
    case _ => throw new IllegalArgumentException(s"can't recognize\n$child")
  }
}