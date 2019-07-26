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
	def isBornAfter(p: Person) = dob.isAfter(p.dob)
}
