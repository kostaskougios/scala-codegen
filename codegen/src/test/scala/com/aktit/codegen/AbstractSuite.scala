package com.aktit.codegen

import java.util.UUID

import org.apache.commons.io.FileUtils
import org.scalatest.{FunSuite, Matchers}

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
abstract class AbstractSuite extends FunSuite with Matchers
{
	def tmpDir = FileUtils.getTempDirectoryPath

	def newRandomTmpDir = s"/$tmpDir/${UUID.randomUUID}"
}