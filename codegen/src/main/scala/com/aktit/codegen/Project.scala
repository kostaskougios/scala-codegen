package com.aktit.codegen

import java.io.File

import com.aktit.codegen.model.PackageEx
import org.apache.commons.io.FileUtils

/**
  * @author kostas.kougios
  *         27/07/2019 - 08:14
  */
class Project private(targetSrcDir: String, srcDirs: Seq[String])
{
	private val parser = Parser()

	def toPackage(source: String): PackageEx = {
		val file = source.replace('.', '/') + ".scala"
		val candidateFiles = srcDirs.map(s => new File(s, file))
		val src = candidateFiles.find(_.exists)
			.getOrElse(throw new IllegalArgumentException(s"no source found : $source , looked up files ${candidateFiles.map(_.getAbsolutePath).mkString(",")}"))
		parser.file(src)
	}

	def save(pckg: PackageEx): Unit = {
		val targetDir = new File(targetSrcDir, pckg.name.replace('.', '/'))
		targetDir.mkdirs()
		val fileName = (pckg.classes ++ pckg.objects).head.name + ".scala"
		FileUtils.writeStringToFile(new File(targetDir, fileName), pckg.syntax)
	}

}

object Project
{
	def apply(targetSrcDir: String, srcDirs: String*) = new Project(targetSrcDir, srcDirs)
}