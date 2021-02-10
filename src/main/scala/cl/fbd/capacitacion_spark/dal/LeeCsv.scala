package cl.fbd.capacitacion_spark

import org.apache.spark.sql.SparkSession
import cl.fbd.capacitacion_spark.domain.{Evento, Vuelta}
import cl.fbd.capacitacion_spark.bl.Validador

/*
 *
 */ 
object LeeCsv {
  def leeVueltas (nombre: String) (implicit spark: SparkSession) = {
    import spark.implicits._

    spark.read.text (nombre)
        .as[String]
        .map (fila => {
            val arr = fila.split (";")

            val eventos = arr (7)
                .split (":")
                .map (evento => {
                val arr2 = evento.split (",")
                Evento (
                    tipo      = arr2 (0),
                    lugar     = arr2 (1),
                    timestamp = arr2 (2).toLong
                )
                })
                .toSeq

            Vuelta (
                anho     = arr (0).toInt,
                mes      = arr (1).toInt,
                dia      = arr (2).toInt,
                turno    = arr (3),
                camion   = arr (4),
                operador = arr (5),
                tonelada = arr (6).toDouble,
                eventos  = eventos
            )
        })
        .filter (Validador.validaVuelta _)
    }
}
