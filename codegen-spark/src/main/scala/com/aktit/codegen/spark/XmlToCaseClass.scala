package com.aktit.codegen.spark

import scala.xml.{Elem, XML}

/**
  * @author kostas.kougios
  *         02/09/2019 - 21:04
  */
private class XmlToCaseClass(
	targetPackage: String,
	newClassName: String,
	xmlFileName: String,
	elementToVariableName: String => String
)
{
	def build = {
		val xml = XML.loadFile(xmlFileName)
		xml.child.collect {
			case e: Elem => deepScan(e)
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

trait Scanned
{
	def name: String
}

private case class ScannedClass(name: String, children: Seq[Scanned]) extends Scanned

private case class ScannedField(name: String) extends Scanned

object XmlToCaseClass
{
	def createClasses(
		targetPackage: String,
		newClassName: String,
		xmlFile: String,
		headerToVariableName: String => String = ColumnNameToFieldName.defaultColumnNameToFieldName
	) = new XmlToCaseClass(targetPackage, newClassName, xmlFile, headerToVariableName).build
}