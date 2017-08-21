package com.aktit.macros

import java.io.File

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 21/08/17
  */
class Enhancer(files: Seq[File])
{
  def enhance = files.map(enhanceFile)

  private def enhanceFile(file: File) = {
    val source = scala.io.Source.fromFile(file, "UTF-8").mkString

    // see https://docs.scala-lang.org/overviews/quasiquotes/syntax-summary.html
    source.parse[Source] match {
      case Parsed.Success(tree) =>
        tree match {
          case q"$mods trait $tpname[..$tparams] extends { ..$earlydefns } with ..$parents { $self => ..$stats }" =>
            println(s"A trait declaration with name $tpname")
          case q"package $ref { ..$topstats }" =>
            println(s"A package $ref")
          case _ => throw new IllegalArgumentException(s"can't recognize\n$tree")
        }
      case Parsed.Error(_, _, details) =>
        throw details
    }
  }
}
