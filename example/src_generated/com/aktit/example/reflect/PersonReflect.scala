package com.aktit.example.reflect

import com.aktit.example.lib.Field

object PersonReflect
{
	val idField = Field[Person]("id", _.id)
	val nameField = Field[Person]("name", _.name)
	val dobField = Field[Person]("dob", _.dob)

	def allFields: Seq[Field[Person]] = Seq(idField, nameField, dobField)
}