package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/09/17
  */
case class Param(meta: Param.Meta) extends Code
  with Meta.Contains[Param.Meta]
  with Code.Name[Param]
{
  override def name: String = meta.param.name.value

  override def tree = meta.param

  override def withName(name: String) = copy(
    meta = meta.copy(
      param = meta.param.copy(name = Name(name))
    )
  )

  def withType(tpe: String) = copy(
    meta = meta.copy(
      param = meta.param.copy(
        decltpe = Some(scala.meta.Type.Name(tpe))
      )
    )
  )
}

object Param
{

  case class Meta(param: Term.Param) extends com.aktit.macros.model.Meta

  def parseString(code: String) = Param(Meta(code.parse[Term.Param].get))

  // creates a string as if these params are used in a method call, i.e. "(n)(m)" for two params n,m of some type
  def toString(parameters: Seq[Seq[Param]]) =
    if (parameters.isEmpty)
      ""
    else
      parameters.map(_.map(_.name).mkString(",")).mkString("(", ")(", ")")
}