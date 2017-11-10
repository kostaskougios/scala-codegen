package com.aktit.sbt

import java.io.File

import com.aktit.codegen.Parser

/**
  * @author kostas.kougios
  *         Date: 21/08/17
  */
object SbtService
{
    def withPackages(srcDir: String, enhancePackage: String) = {
        println(
            s"""
               			   		   |src dir: $srcDir
               			   		   |Enhance: $enhancePackage
               			   		   |""".stripMargin)

        val files = new File(srcDir, enhancePackage.replace('.', '/')).listFiles.filter(_.getName.endsWith(".scala"))
        if (files.isEmpty) throw new IllegalArgumentException(s"no files found $srcDir , package $enhancePackage")

        println(
            s"""
               			   		   |Files to enhance:
               			   		   |${files.mkString("\n")}
               			   		   |""".stripMargin)

        val parser = new Parser
        parser.files(files)
    }
}
