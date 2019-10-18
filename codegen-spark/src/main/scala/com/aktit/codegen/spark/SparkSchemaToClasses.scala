package com.aktit.codegen.spark

import com.aktit.codegen.model.{ClassEx, PackageEx, TermParamEx}
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}

/**
  * @author kostas.kougios
  *         11/10/2019 - 23:08
  */
class SparkSchemaToClasses private(
	targetPackage: String,
	fieldToFieldName: StructField => String
)
{
	def toPackage(topClassName: String, schema: StructType) =
		PackageEx.withName(targetPackage)
			.withClass(toClass(topClassName, schema))

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
					"Boolean"
				//				case DataTypes.StructType(fields) =>

			}
			val t = if (field.nullable) s"Option[$tpe]" else tpe
			TermParamEx.fromSource(s"$fieldName : $t")
	}
}

object SparkSchemaToClasses
{
	def createClasses(
		targetPackage: String,
		topClassName: String,
		schema: StructType,
		fieldToFieldName: StructField => String = ColumnToFieldName.defaultColumnNameToFieldName
	) = new SparkSchemaToClasses(targetPackage, fieldToFieldName)
		.toPackage(topClassName, schema)
}