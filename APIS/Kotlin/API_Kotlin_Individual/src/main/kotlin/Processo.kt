import java.time.LocalDateTime

class Processo {

    var idProcesso : Int = 0
    var PID : Int = 0
    var nome : String = ""
    var uso_cpu :Double = 0.0
    var uso_ram :Double = 0.0
    var total_processos : Int = 0
    var total_threads : Int = 0
    var dtHoraInsercao  = LocalDateTime.now()
    var fkMaquina: Int = 0
    var fkEmpresa: Int = 0
    var fkStatus: Int = 0
    var fkTipoMaquina: Int = 0

}