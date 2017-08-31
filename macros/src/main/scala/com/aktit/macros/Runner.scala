package com.aktit.macros

import java.io.File

import com.aktit.macros.model.Package

/**
  * @author kostas.kougios
  *         Date: 21/08/17
  */
object Runner extends App
{
  val srcDir = args(0)
  val enhancePackage = args(1)
  println(
    s"""
       |src dir: $srcDir
       |Enhance: $enhancePackage
       |""".stripMargin)

  val files = new File(srcDir, enhancePackage.replace('.', '/')).listFiles
  if (files.isEmpty) throw new IllegalArgumentException(s"no files found under $files")

  println(
    s"""
       |Files to enhance:
       |${files.mkString("\n")}
       |""".stripMargin)

	val parser = new Parser(files)
	val results = parser.parse
  println(results.collect {
    case p: Package =>
      p.traits
  })
}
