package com.aktit.codegen.spark

import com.aktit.codegen.model.TermParamEx
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}
import org.scalatest.FunSuite
import org.scalatest.Matchers._

/**
  * @author kostas.kougios
  *         12/10/2019 - 22:20
  */
class SparkSchemaToClassesTest extends FunSuite
{
	test("nullable boolean") {
		createClassGetFields(
			StructField("isOk", DataTypes.BooleanType, nullable = true)
		) should be(Seq(TermParamEx.fromSource("isOk : Option[Boolean]")))
	}

	test("not nullable boolean") {
		createClassGetFields(
			StructField("isOk", DataTypes.BooleanType, nullable = false)
		) should be(Seq(TermParamEx.fromSource("isOk : Boolean")))
	}

	def createClassGetFields(fields: StructField*) = SparkSchemaToClasses.createClasses(
		"my.code",
		"Simple",
		StructType(fields.toSeq)
	).classes.head.constructorParameters.head
}
