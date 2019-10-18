package com.aktit.codegen.spark

import com.aktit.codegen.model.TermParamEx
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  * @author kostas.kougios
  *         12/10/2019 - 22:20
  */
class SparkSchemaToPackageTest extends FunSuite
{
	test("nullable boolean") {
		createPackageGetFieldsOfFirstClass(
			StructField("isOk", DataTypes.BooleanType, nullable = true)
		) should be(Seq(TermParamEx.fromSource("isOk : Option[Boolean]")))
	}

	test("not nullable boolean") {
		createPackageGetFieldsOfFirstClass(
			StructField("isOk", DataTypes.BooleanType, nullable = false)
		) should be(Seq(TermParamEx.fromSource("isOk : Boolean")))
	}

	test("struct field") {
		val p = createPackage(
			StructField(
				"isOk",
				StructType(Seq(
					StructField("isOk", DataTypes.BooleanType, nullable = false)
				)),
				nullable = false)
		)
		p.classes.map(_.name) should be(Seq("Simple", "IsOk"))
		p.classes.find(_.name == "Simple").get.constructorParameters.flatten should be(Seq(TermParamEx.fromSource("isOk : IsOk")))
		p.classes.find(_.name == "IsOk").get.constructorParameters.flatten should be(Seq(TermParamEx.fromSource("isOk : IsOk")))
	}

	def createPackage(fields: StructField*) = SparkSchemaToPackage.createPackage(
		"my.code",
		"Simple",
		StructType(fields.toSeq)
	)

	def createPackageGetFieldsOfFirstClass(fields: StructField*) = createPackage(fields: _*).
		classes.head.constructorParameters.head
}
