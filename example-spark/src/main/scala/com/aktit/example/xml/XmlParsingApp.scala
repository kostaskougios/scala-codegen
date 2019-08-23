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
		import spark.implicits._
		val df = spark.read
			.option("rowTag", "user")
			.xml("xml-files/test.xml")

		df.show(false)
		df.printSchema()

		println(df.as[User].collect.mkString("\n"))
	} finally spark.close()
}

case class User(name: String, country: String, projects: Projects)

case class Projects(project: Seq[Project])

case class Project(name: String)