package com.aktit.example.combine

import java.sql.Timestamp

/**
  * @author kostas.kougios
  *         26/07/2019 - 21:22
  */
object Example extends App
{
	val u = User(5, "Kostas")
	val p = Purchase(5, "Phone", 5.5, new Timestamp(System.currentTimeMillis))

	val up = UserPurchases(u, p)
	println(up)
}
