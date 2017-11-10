package com.aktit.sbt

import java.io.File

import com.aktit.codegen.model.PackageEx
import com.aktit.sbt.model.CompilationFile
import org.apache.commons.io.FileUtils

/**
  * @author kostas.kougios
  *         Date: 21/08/17
  */
object SbtService
{
    def withPackages(srcDir: String, enhancePackage: String): Seq[CompilationFile] = {
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

        files.map {
            file =>
                CompilationFile(
                    file,
                    PackageEx.fromFile(file)
                )
        }
    }

    def save(compilationFile: CompilationFile, targetDir: String): Unit = save(Seq(compilationFile), targetDir)

    def save(compilationFiles: Seq[CompilationFile], targetDir: String): Unit = for (cf <- compilationFiles) {
        val dir = cf.pckg.name.replace('.', '/')
        val target = new File(targetDir, dir)
        target.mkdirs()
        val out = new File(target, cf.file.getName)
        FileUtils.writeStringToFile(out, cf.pckg.syntax)
    }
}
