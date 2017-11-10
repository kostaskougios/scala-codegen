package com.aktit.codegen.model

import scala.collection.immutable
import scala.meta._

/**
  * @author kostas.kougios
  *         Date: 09/10/17
  */
case class TemplateEx(meta: TemplateEx.Meta) extends CodeEx with ValEx.Contains with MetaEx.Contains
{
    def extending: immutable.Seq[TypeEx] = meta.inits.map(_.tpe).map(TypeEx.apply)

    def withExtending(types: Seq[TypeEx]) = copy(
        meta = meta.copy(
            inits = types.map(_.meta.tpe).map(tpe => Init(tpe, Name("invalid"), Nil)).toList
        )
    )

    override def tree: Template = template"{ ..${meta.earlyStats} } with ..${meta.inits} { ${meta.self} => ..${meta.stats} }"

    override def vals = meta.stats.collect(ValEx.parser)
}

object TemplateEx extends PartialParser[TemplateEx]
{

    case class Meta(
        earlyStats: List[Stat],
        inits: List[Init],
        self: Self,
        stats: List[Stat]
    ) extends MetaEx

    override def parser = {
        case template"{ ..$earlyStats } with ..$inits { $self => ..$stats }" =>
            TemplateEx(Meta(earlyStats, inits, self, stats))
    }

    trait Contains[S]
    {
        def meta: MetaEx with MetaEx.Template

        protected def withTemplateInner(t: Template): S

        def extending: immutable.Seq[TypeEx] = template.extending

        def template: TemplateEx = TemplateEx.parser(meta.template)

        def withTemplate(t: TemplateEx) = withTemplateInner(t.tree)

        def extendsTypes: immutable.Seq[TypeEx] = template.meta.inits.map(_.tpe).map(TypeEx.apply)
    }

}