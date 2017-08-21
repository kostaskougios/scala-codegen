package com.aktit.macros

import java.io.File

import scala.io.Source

/**
  * @author kostas.kougios
  *         Date: 21/08/17
  */
class Enhancer(files: Seq[File])
{
  def enhance = files.map(enhanceFile)

  private def enhanceFile(file: File) = {
    val source = Source.fromFile(file, "UTF-8").mkString
    println(source)
  }
}
