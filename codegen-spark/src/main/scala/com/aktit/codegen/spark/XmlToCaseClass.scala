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
		xml.child.collect {
			case e: Elem => deepScan(e)
		}.collect {
			case c: ScannedClass =>
				c.classes
		}.flatten
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

trait Scanned
{
	def name: String
}

case class ScannedClass(name: String, children: Seq[Scanned]) extends Scanned
{
	def classes: Seq[ScannedClass] = this +: children.collect {
		case c: ScannedClass => c.classes
	}.flatten
}

case class ScannedField(name: String) extends Scanned

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