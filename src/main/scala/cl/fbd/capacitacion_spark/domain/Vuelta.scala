package cl.fbd.capacitacion_spark.domain

case class Vuelta (
    anho: Int, 
    mes: Int, 
    dia: Int, 
    turno: String, 
    camion: String, 
    operador: String, 
    tonelada: Double, 
    eventos: Seq [Evento]
)