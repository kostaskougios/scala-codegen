package com.aktit.codegen.model

import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 01/09/17
  */
trait MethodEx extends CodeEx
    with CodeEx.Name[MethodEx]
    with MetaEx.Contains
    with MetaEx.ContainsMods
{
    def withReturnType(returnType: String): MethodEx

    def returnType: Option[TypeEx]

    def parameters: Seq[Seq[TermParamEx]]

    def withParameters(params: Seq[Seq[TermParamEx]]): MethodEx

    // converts this method to it's abstract (no impl) representation
    def toAbstract: DeclaredMethodEx

    // adds impl (or replaces the existing one)
    def withImplementation(code: String): DefinedMethodEx

    def withOverrides: MethodEx

    override def tree: Stat
}

object MethodEx extends PartialParser[MethodEx]
{
    override val parser = DeclaredMethodEx.parser.orElse(DefinedMethodEx.parser)

    def isMethod(t: Tree): Boolean = parser.isDefinedAt(t)

    trait Contains[T] extends TemplateEx.Contains[T]
    {
        def meta: MetaEx with MetaEx.Template

        def methods: Seq[MethodEx] = meta.template.children.collect(parser)

        def declaredMethods: Seq[DeclaredMethodEx] = methods.collect {
            case d: DeclaredMethodEx => d
        }

        def definedMethods: Seq[DefinedMethodEx] = methods.collect {
            case d: DefinedMethodEx => d
        }

        def withMethods(methods: Seq[MethodEx]): T = {
            withTemplateInner(
                Template(
                    meta.template.early,
                    meta.template.inits,
                    meta.template.self,
                    meta.template.stats.filterNot(isMethod) ++ methods.map(_.tree)
                )
            )
        }

    }

}
