package com.aktit.sbt

import sbt.Keys._
import sbt._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
object SbtPlugin extends AutoPlugin
{
    println("******************* Loading SbtPlugin")
    override lazy val projectSettings = Seq(commands += helloCommand)

    lazy val helloCommand =
        Command.command("hello") { (state: State) =>
            println("Hi!")
            state
        }

}
