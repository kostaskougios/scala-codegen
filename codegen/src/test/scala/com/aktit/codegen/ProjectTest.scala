package com.aktit.codegen

import java.io.File

import com.aktit.codegen.model.{ClassEx, PackageEx}

/**
  * @author kostas.kougios
  *         27/07/2019 - 08:21
  */
class ProjectTest extends AbstractSuite
{
	test("src to package") {
		val p = Project("", "codegen/test-files")
		val pckg = p.toPackage("com.mycompany.project.MyClass1")
		pckg.classes.head.name should be("MyClass1")
	}

	test("save") {
		val srcDir = s"$newRandomTmpDir/src"
		val project = Project(srcDir, "")
		val pckg = PackageEx.withName("com.aktit").withClasses(Seq(ClassEx.withName("MyClass")))
		project.save(pckg)
		new File(srcDir + "/com/aktit/MyClass.scala").exists should be(true)
	}
}
