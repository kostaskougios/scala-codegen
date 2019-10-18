package com.aktit.codegen.spark

import com.aktit.codegen.model.{ClassEx, PackageEx, TermParamEx}
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}

/**
  * @author kostas.kougios
  *         11/10/2019 - 23:08
  */
class SparkSchemaToClasses private(
	targetPackage: String,
	fieldToFieldName: StructField => String,
	fieldToClassName: StructField => String
)
{
	def toPackage(topClassName: String, schema: StructType) =
		PackageEx.withName(targetPackage)
			.withClass(toClass(topClassName, schema))

	def toClass(className: String, schema: StructType): ClassEx = {
		val scanned = toConstructorParams(schema.fields)
		ClassEx.withName(className)
			.withConstructorParameters(
				scanned.map(_.toParam)
			).withCaseClass
	}

	private def toConstructorParams(fields: Seq[StructField]): Seq[Scanned] = fields.map {
		field =>
			val fieldName = fieldToFieldName(field)
			field.dataType match {
				case DataTypes.BooleanType =>
					Type(fieldName, "Boolean", field)
				case s: StructType =>
					Class(fieldName, toClass(fieldToClassName(field), s), field)

			}
	}
}

private trait Scanned
{
	def toParam: TermParamEx
}

private case class Type(fieldName: String, name: String, field: StructField) extends Scanned
{
	override def toParam = {
		val t = if (field.nullable) s"Option[${name}]" else name
		TermParamEx.fromSource(s"$fieldName : $t")
	}
}

private case class Class(fieldName: String, c: ClassEx, field: StructField) extends Scanned
{
	override def toParam = {
		val t = if (field.nullable) s"Option[${c.name}]" else c.name
		TermParamEx.fromSource(s"$fieldName : $t")
	}
}

object SparkSchemaToClasses
{
	def createClasses(
		targetPackage: String,
		topClassName: String,
		schema: StructType,
		fieldToFieldName: StructField => String = Naming.defaultFieldToFieldName,
		fieldToClassName: StructField => String = Naming.defaultFieldToClassName
	) = new SparkSchemaToClasses(targetPackage, fieldToFieldName, fieldToClassName)
		.toPackage(topClassName, schema)
}