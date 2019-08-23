package com.aktit.example.xml

import com.databricks.spark.xml._
import org.apache.spark.sql.SparkSession

/**
 * @author kostas.kougios
 *         23/08/2019 - 22:12
 */
object XmlParsingApp extends App
{
	val spark = new SparkSession.Builder().master("local[*]").getOrCreate

	try {
		val df = spark.read
			.option("rowTag", "user")
			.xml("xml-files/test.xml")

		df.show()
	} finally spark.close()
}
