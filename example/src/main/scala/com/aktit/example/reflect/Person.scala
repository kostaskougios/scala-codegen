package com.aktit.example.reflect

import java.time.LocalDate

/**
 * @author kostas.kougios
 *         26/07/2019 - 21:33
 */
case class Person(
	id: Int,
	name: String,
	dob: LocalDate
)
{
	val idAndName = id + "/" + name
	def isBornAfter(p: Person) = dob.isAfter(p.dob)

	def tuples = PersonReflect.allFields.map(f => (f.name, f.getter(this)))

	def toMap = PersonReflect.toMap(this)
}

object TryPerson extends App
{
	val p1 = Person(5, "Kostas", LocalDate.of(1500, 1, 2))
	println(p1.tuples)
	println(p1.toMap)
}