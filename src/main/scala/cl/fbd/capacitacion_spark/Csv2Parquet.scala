package cl.fbd.capacitacion_spark

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.SaveMode

import cl.fbd.capacitacion_spark.dal.LeeCsv

object Csv2ParquetMain {
  def main (args: Array [String]): Unit = {
      println ("Capacitacion Spark")

      if (args.length < 2) {
        println ("Uso <nombre CSV> <nombre parquet>")
        System.exit (1)
      }

      val nombreCSV = args (0)
      val nombreParquet = args (1)

      implicit val spark = SparkSession.builder.master ("local[2]").appName ("Lee y valida Vueltas en formato CSV y guarda en formato Parquet").getOrCreate()
      
      println (s"Lee y valida archivos de vualtas CSV ${nombreCSV}")
      println (s"El proceso falla por completo si existe una vuelta con error")
      println (s"Si no hay vueltas con error, Guarda la salida en ${nombreParquet}")

      val vueltas = LeeCsv.leeVueltas (nombreCSV)
      vueltas.write.mode (SaveMode.Overwrite).parquet (nombreParquet)

      spark.close 

      ()
  }
}