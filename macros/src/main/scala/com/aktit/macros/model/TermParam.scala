package com.aktit.macros.model

import com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/09/17
  */
case class TermParam(meta: TermParam.Meta) extends Code
  with Meta.Contains
  with Code.Name[TermParam]
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

object TermParam
{

  case class Meta(param: scala.meta.Term.Param) extends model.Meta

  def parseString(code: String) = TermParam(Meta(code.parse[Term.Param].get))

  def apply(param: scala.meta.Term.Param): TermParam = TermParam(Meta(param))
  // creates a string as if these params are used in a method call, i.e. "(n)(m)" for two params n,m of some type
  def toString(parameters: Seq[Seq[TermParam]]) =
    if (parameters.isEmpty)
      ""
    else
      parameters.map(_.map(_.name).mkString(",")).mkString("(", ")(", ")")
}