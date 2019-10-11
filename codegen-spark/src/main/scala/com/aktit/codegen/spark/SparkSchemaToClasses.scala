package com.aktit.codegen.spark

import com.aktit.codegen.model.{ClassEx, TermParamEx}
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}

/**
  * @author kostas.kougios
  *         11/10/2019 - 23:08
  */
class SparkSchemaToClasses(
	fieldToFieldName: StructField => String
)
{
	def toClass(className: String, schema: StructType) =
		ClassEx.withName(className)
			.withConstructorParameters(
				toConstructorParams(schema.fields)
			).withCaseClass

	def toConstructorParams(fields: Seq[StructField]) = fields.map {
		field =>
			val fieldName = fieldToFieldName(field)
			val tpe = field.dataType match {
				case DataTypes.BooleanType =>
			}
			TermParamEx.fromSource(s"$fieldName : $tpe")
	}
}
