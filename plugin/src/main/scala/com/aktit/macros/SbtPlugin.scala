package com.aktit.macros

import sbt._

/**
  * @author kostas.kougios
  *         Date: 10/11/17
  */
object SbtPlugin extends AutoPlugin
{
    lazy val helloCommand =
        Command.command("hello") { (state: State) =>
            println("Hi!")
            state
        }

}
