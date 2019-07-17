package com.aktit.example.combine

import java.sql.Timestamp

/**
  * @author kostas.kougios
  *         17/07/19 - 18:03
  */
case class Purchase(userId: String, item: String, price: BigDecimal, time: Timestamp)