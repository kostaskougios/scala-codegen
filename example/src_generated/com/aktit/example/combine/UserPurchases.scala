package com.aktit.example.combine

import java.sql.Timestamp

case class UserPurchases(id: Int, name: String, userId: Int, item: String, price: BigDecimal, time: Timestamp)

object UserPurchases
{
	def apply(user: User, purchase: Purchase): UserPurchases = UserPurchases(user.id, user.name, purchase.userId, purchase.item, purchase.price, purchase.time)
}