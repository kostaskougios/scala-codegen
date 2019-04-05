package com.aktit.sbt.model

import java.io.File

import com.aktit.codegen.model.PackageEx

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
case class CompilationFile(
	file: File,
	pckg: PackageEx
)