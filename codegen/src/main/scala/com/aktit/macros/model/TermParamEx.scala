package com.aktit.macros.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 29/09/17
  */
case class TermParamEx(meta: TermParamEx.Meta) extends CodeEx
    with MetaEx.Contains
    with MetaEx.ContainsMods
    with CodeEx.Name[TermParamEx]
{
    override def name: String = meta.param.name.value

    override def tree = meta.param

    override def withName(name: String) = copy(
        meta = meta.copy(
            param = meta.param.copy(name = Name(name))
        )
    )

    def `type`: Option[TypeEx] = meta.param.decltpe.map(TypeEx.apply)

    def withType(tpe: String) = copy(
        meta = meta.copy(
            param = meta.param.copy(
                decltpe = Some(scala.meta.Type.Name(tpe))
            )
        )
    )

    def toVal: ValEx = {
        val p = meta.param
        val q = q"..${p.mods} val ..${List(Pat.Var(Term.Name(p.name.value)))}: ${p.decltpe.get}"
        ValEx.parser(q)
    }
}

object TermParamEx
{

    case class Meta(param: scala.meta.Term.Param) extends MetaEx with MetaEx.Mods
    {
        override def mods = param.mods
    }

    def parseString(code: String) = TermParamEx(Meta(code.parse[Term.Param].get))

    def apply(param: scala.meta.Term.Param): TermParamEx = TermParamEx(Meta(param))

    // creates a string as if these params are used in a method call, i.e. "(n)(m)" for two params n,m of some type
    def toString(parameters: Seq[Seq[TermParamEx]]) =
        if (parameters.isEmpty)
            ""
        else
            parameters.map(_.map(_.name).mkString(",")).mkString("(", ")(", ")")
}