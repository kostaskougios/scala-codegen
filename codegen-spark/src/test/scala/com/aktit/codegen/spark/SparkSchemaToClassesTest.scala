package com.aktit.codegen.spark

import com.aktit.codegen.model.PackageEx
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  * @author kostas.kougios
  *         12/10/2019 - 22:20
  */
class SparkSchemaToClassesTest extends FunSuite
{
	test("simple schema") {
		SparkSchemaToClasses.createClasses(
			"my.code",
			"Simple",
			StructType(
				Seq(
					StructField("isOk", DataTypes.BooleanType)
				)
			)
		) should be(PackageEx.fromSource(
			"""
			  |package my.code
			  |
			  |case class Simple(isOk:Boolean)
			  |""".stripMargin))
	}

}
