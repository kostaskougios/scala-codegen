package com.aktit.reflect.lib

/**
  * @author kostas.kougios
  *         26/07/2019 - 21:40
  */
case class Field[A, V](name: String, getter: A => V)
