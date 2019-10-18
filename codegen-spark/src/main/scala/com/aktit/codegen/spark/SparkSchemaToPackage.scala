package com.aktit.codegen.spark

import com.aktit.codegen.model.{ClassEx, PackageEx, TermParamEx}
import org.apache.spark.sql.types.{DataTypes, StructField, StructType}

/**
  * @author kostas.kougios
  *         11/10/2019 - 23:08
  */
class SparkSchemaToPackage private(
	targetPackage: String,
	fieldToFieldName: StructField => String,
	fieldToClassName: StructField => String
)
{
	def toPackage(topClassName: String, schema: StructType) =
		PackageEx.withName(targetPackage)
			.withClasses(toClass(topClassName, schema))

	private def toClass(className: String, schema: StructType): Seq[ClassEx] = {
		val scanned = toConstructorParams(schema.fields)
		val c = ClassEx.withName(className)
			.withConstructorParameters(
				scanned.map(_.toParam)
			).withCaseClass
		c +: scanned.collect {
			case sc: Class => sc.classEx
		}.flatten
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

private case class Class(fieldName: String, classEx: Seq[ClassEx], field: StructField) extends Scanned
{
	def primary = classEx.head
	override def toParam = {
		val t = if (field.nullable) s"Option[${primary.name}]" else primary.name
		TermParamEx.fromSource(s"$fieldName : $t")
	}
}

object SparkSchemaToPackage
{
	def createPackage(
		targetPackage: String,
		topClassName: String,
		schema: StructType,
		fieldToFieldName: StructField => String = Naming.defaultFieldToFieldName,
		fieldToClassName: StructField => String = Naming.defaultFieldToClassName
	) = new SparkSchemaToPackage(targetPackage, fieldToFieldName, fieldToClassName)
		.toPackage(topClassName, schema)
}