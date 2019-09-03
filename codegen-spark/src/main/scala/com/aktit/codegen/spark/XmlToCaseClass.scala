package com.aktit.codegen.spark

import scala.xml.{Elem, XML}

/**
  * @author kostas.kougios
  *         02/09/2019 - 21:04
  */
private class XmlToCaseClass(
	targetPackage: String,
	newClassName: String,
	xml: Elem,
	elementToVariableName: String => String
)
{
	def build = {
		val allClasses = xml.child.collect {
			case e: Elem => deepScan(e)
		}.collect {
			case c: ScannedClass => c.classes
		}.flatten
		allClasses.groupBy(_.name).map {
			case (n, cl) =>
				ResultingClass(
					n,
					cl.flatMap(_.children)
						.groupBy(_.name)
						.map {
							case (_, s) => s.head match {
								case ScannedField(field) =>
									ResultingField(field, "String")
								case ScannedClass(clz, _) =>
									ResultingField(clz, clz)
							}
						}.toList
				)
		}
	}

	private def deepScan(n: Elem): Scanned = {
		val elems = n.child.collect {
			case e: Elem => e
		}
		if (elems.isEmpty)
			ScannedField(n.label)
		else ScannedClass(n.label, elems.map(deepScan))
	}

}

private sealed trait Scanned
{
	def name: String
}

private case class ScannedClass(name: String, children: Seq[Scanned]) extends Scanned
{
	def classes: Seq[ScannedClass] = this +: children.collect {
		case c: ScannedClass => c.classes
	}.flatten
}

private case class ScannedField(name: String) extends Scanned

case class ResultingClass(name: String, fields: Seq[ResultingField])

case class ResultingField(name: String, tpe: String)

object XmlToCaseClass
{
	def createClasses(
		targetPackage: String,
		newClassName: String,
		xmlFile: String,
		headerToVariableName: String => String = ColumnNameToFieldName.defaultColumnNameToFieldName
	) = {
		val xml = XML.loadFile(xmlFile)
		new XmlToCaseClass(targetPackage, newClassName, xml, headerToVariableName).build
	}
}