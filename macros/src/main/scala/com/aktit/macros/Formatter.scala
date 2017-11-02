package com.aktit.macros

/**
  * @author kostas.kougios
  *         Date: 02/11/17
  */
class Formatter
{
  def format(code: String): String = Parser().parseSource(code).map(_.syntax).mkString("\n")
}

object Formatter
{
  private val formatter = new Formatter

  def format(code: String) = formatter.format(code)
}
