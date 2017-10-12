package com.aktit.macros.model

/**
  * @author kostas.kougios
  *         Date: 12/10/17
  */
trait Meta

object Meta
{

	trait Contains[T <: Meta]
	{
		def meta: T
	}

	trait Template
	{
		def template: scala.meta.Template
	}

}