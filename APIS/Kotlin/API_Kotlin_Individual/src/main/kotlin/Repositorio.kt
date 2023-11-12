import com.github.britooo.looca.api.core.Looca
import org.springframework.jdbc.core.JdbcTemplate
import java.time.LocalDateTime
import javax.swing.JOptionPane

class Repositorio {

    lateinit var jdbcTemplate: JdbcTemplate
    var colaborador1 = Colaborador()
    val processo = Processo()

    fun iniciar() {
        jdbcTemplate = Conexao.jdbcTemplate!!
    }


    fun completarProcesso(processos: Processo){

        val looca = Looca()
        val grupoProcesssos = looca.grupoDeProcessos







        processos.total_processos = grupoProcesssos.totalProcessos
        processos.total_threads = grupoProcesssos.totalThreads

        grupoProcesssos.processos.forEachIndexed { p, processo ->
            processos.nome = processo.nome
            processos.PID = processo.pid
            processos.uso_cpu = processo.usoCpu
            processos.uso_ram = processo.usoMemoria
            processos.dtHoraInsercao = LocalDateTime.now()


            println( """
                            
                       Nome: ${processos.nome}
                       PID: ${processos.PID}
                       Uso de memória (GB): ${processos.uso_ram}
                       Uso de CPU (%): ${processos.uso_cpu}
                      
                        """.trimIndent()
            )

            cadastrarProcessos(processos)

        }


    }
 private fun cadastrarProcessos(processo: Processo){


        jdbcTemplate.update("""
            insert into processo ( PID, nome,uso_cpu, uso_ram, total_processos, total_threads, dtHoraInsercao, fkMaquina, fkEmpresa, fkTipoMaquina, fkStatusMaquina) values
           ( ${processo.PID},'${processo.nome}', ${processo.uso_cpu},  ${processo.uso_ram}, ${processo.total_processos},  ${processo.total_threads} , '${processo.dtHoraInsercao}', ${processo.fkMaquina},${processo.fkEmpresa}, ${processo.fkTipoMaquina}, ${processo.fkStatus})
        """.trimIndent())

     completarProcesso(processo)

 }

    fun buscaridMaquina(ip: Int, processo: Processo){


        val idMaquina=  jdbcTemplate.queryForObject(
            """
                 select idMaquina from maquina where IP = ${ip};
                """, Int::class.java
        );

        if (idMaquina != null) {
            processo.fkMaquina =idMaquina
        }


    }

    fun buscarfkEmpresa(ip: Int, processo: Processo){



        val fkEmpresa=  jdbcTemplate.queryForObject(
            """
                 select fkEmpresa from maquina where IP = ${ip};
                """, Int::class.java
        );

        if (fkEmpresa != null) {
            processo.fkEmpresa =fkEmpresa
        }
    }

    fun buscarfkTipoMaquina(ip: Int, processo: Processo){


        val fkTipoMaquina=  jdbcTemplate.queryForObject(
            """
                 select fkTipoMaquina from maquina where IP = ${ip};
                """, Int::class.java
        );

        if (fkTipoMaquina != null) {
            processo.fkTipoMaquina = fkTipoMaquina
        }
    }
    fun buscarfkStatusMaquina(ip: Int, processo: Processo){



        val  fkStatusMaquina=  jdbcTemplate.queryForObject(
            """
                 select  fkStatusMaquina from maquina where IP = ${ip};
                """, Int::class.java
        );

        if ( fkStatusMaquina != null) {
            processo.fkStatus =  fkStatusMaquina
        }
    }







    fun verificarColaborador(email: String, senha: String) : Int?{

        val colaborador = jdbcTemplate.queryForObject(
            """
                  select count(idColaborador) from Colaborador where email = '${email}' and senha = '${senha}';
                """, Int::class.java
        );

      return colaborador
    }


    fun buscarNome(email: String, senha: String){
        val nome = jdbcTemplate.queryForObject(
            """
                   select nome from Colaborador where email = '${email}' and senha = '${senha}';
                """, String::class.java
        );

        if (nome != null) {
            colaborador1.nome = nome
        }
    }

    fun verificarProcesso() : Int?{

        val processo = jdbcTemplate.queryForObject(
            """
                  select count(idProcesso) from Processo;
                """, Int::class.java
        );

        return processo
    }



    fun validarMaquina(ip: Int): Int? {



        val maquina=  jdbcTemplate.queryForObject(
            """
                 select count(idMaquina) from maquina where IP = '${ip}';
                """, Int::class.java
        );

     return maquina

    }

}
