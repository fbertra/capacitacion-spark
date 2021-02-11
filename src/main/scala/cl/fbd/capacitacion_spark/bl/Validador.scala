package cl.fbd.capacitacion_spark.bl // businees logic

import cl.fbd.capacitacion_spark.domain.Vuelta

object Validador {
    def validaVuelta (vuelta: Vuelta): Boolean = {
        def buscaEvento (tipo: String) = {
            val evento = vuelta.eventos.find (e => e.tipo == tipo)

            evento.get // lanzara una exception si no existe este tipo de evento
        }

        val entradaCarga = buscaEvento ("EC")
        val salidaCarga  = buscaEvento ("SC")
        val entradaDescarga = buscaEvento ("ED")
        val salidaDescarga  = buscaEvento ("SD")

        if (
            entradaCarga.timestamp > salidaCarga.timestamp
            ||
            salidaCarga.timestamp > entradaDescarga.timestamp
            ||
            entradaDescarga.timestamp > salidaDescarga.timestamp
        )
            throw new RuntimeException ("timestamp en eventos de " + vuelta.toString ())

        true
    }
}