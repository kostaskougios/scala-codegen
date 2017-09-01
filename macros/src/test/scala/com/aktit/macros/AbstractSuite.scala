package com.aktit.macros

import java.io.File

import org.scalatest.{FunSuite, Matchers}

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
abstract class AbstractSuite extends FunSuite with Matchers
{
	protected def codeDirectory: File = {
		val f = new File("code")
		if (f.exists)
			f
		else new File(".")
	}

	protected def codeSourceFolder = SourceFolder(new File(codeDirectory, "src/main/scala"))
}
