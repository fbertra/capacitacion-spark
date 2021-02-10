package cl.fbd.capacitacion_spark

import org.scalatest.flatspec.AnyFlatSpec

import cl.fbd.capacitacion_spark.domain.{Evento, Vuelta}
import cl.fbd.capacitacion_spark.bl.Validador

class ValidadVueltaTest extends AnyFlatSpec {
  val eventoEC = Evento (
      tipo = "EC", 
      lugar = "xy",
      timestamp = 1L
  )

  val eventoSC = Evento (
      tipo = "SC", 
      lugar = "xy",
      timestamp = 2L
  )

  val eventoED = Evento (
      tipo = "ED", 
      lugar = "ab",
      timestamp = 3L
  )

  val eventoSD= Evento (
      tipo = "SD", 
      lugar = "ab",
      timestamp = 3L
  )

  val vueltaOK = Vuelta (
      anho = 2020,
      mes = 12,
      dia = 1,
      turno = "A",
      camion = "C1",
      operador = "O1",
      tonelada = 1000.0,
      eventos = Seq (eventoEC, eventoSC, eventoED, eventoSD)
  )

  "eventos EC,SC,ED,SD" should "estar OK" in {
      val ret = Validador.validaVuelta (vueltaOK)

      assert (ret == true)
  }

  "eventos SC,EC,ED,SD" should "fallar por timestamp" in {
      val eventoEC_error = eventoEC.copy (timestamp = 10L)
      val vueltaError = vueltaOK.copy (eventos = Seq (eventoEC_error, eventoSC, eventoED, eventoSD))

      assertThrows [RuntimeException] {
          Validador.validaVuelta (vueltaError)
      }
  }

  "eventos SC,EC,SD" should "fallar por no tener ED" in {
      val vueltaError = vueltaOK.copy (eventos = Seq (eventoEC, eventoSC, eventoSD))

      assertThrows [java.util.NoSuchElementException] {
          Validador.validaVuelta (vueltaError)
      }
  }

}
