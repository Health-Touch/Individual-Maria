import com.github.britooo.looca.api.core.Looca
import org.springframework.jdbc.core.JdbcTemplate
import java.time.LocalDateTime
import javax.swing.JOptionPane
import org.springframework.dao.EmptyResultDataAccessException
class Repositorio {

    lateinit var jdbcTemplate: JdbcTemplate

    val looca = Looca()
    val grupoProcesssos = looca.grupoDeProcessos

    fun iniciar() {
        jdbcTemplate = Conexao.jdbcTemplate!!
    }


    fun cadastrarProcesso(processos: Processo){




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


            jdbcTemplate.update("""
            insert into processo ( PID, nome,uso_cpu, uso_ram, total_processos, total_threads, dtHoraInsercao, fkMaquina, fkEmpresa, fkTipoMaquina, fkStatusMaquina) values
           ( ${processos.PID},'${processos.nome}', ${processos.uso_cpu},  ${processos.uso_ram}, ${processos.total_processos},  ${processos.total_threads} , '${processos.dtHoraInsercao}', ${processos.fkMaquina},${processos.fkEmpresa}, ${processos.fkTipoMaquina}, ${processos.fkStatus})
        """.trimIndent())




        }


    }

    fun pesquisarProcesso(){

        val pesquisa = JOptionPane.showInputDialog("Qual processo quer achar?")
        val processosEncontrados = grupoProcesssos.processos.filter{
            it.nome.contains(pesquisa)}

        if (processosEncontrados.isEmpty()) {
            println("Nenhum processo '$pesquisa' encontrado")
        } else {
            println("${processosEncontrados.size} processos '$pesquisa' encontrados:")
            println()

            processosEncontrados.forEach {
                println("Processo:\r\r$it")
            }
        }


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
    fun buscarfkPlanoEmpresa(ip: Int, maquina: Maquina){



        val fkPlanoEmpresa=  jdbcTemplate.queryForObject(
            """
                 select fkPlanoEmpresa from maquina where IP = ${ip};
                """, Int::class.java
        );

        if (fkPlanoEmpresa != null) {
            maquina.fkPlanoEmpresa = fkPlanoEmpresa
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
        var colaborador: Int? = 0
       try {
           colaborador = jdbcTemplate.queryForObject(
               """
                  select count(idColaborador) from Colaborador where email = '${email}' and senha = '${senha}';
                """, Int::class.java
           );
       }      catch (e: EmptyResultDataAccessException) { colaborador = 0 }

      return colaborador
    }


    fun buscarNome(email: String, senha: String, colaborador: Colaborador) {
        val nome = jdbcTemplate.queryForObject(
            """
                   select nome from Colaborador where email = '${email}' and senha = '${senha}';
                """, String::class.java
        );

        if (nome != null) {
           println(nome)
            colaborador.nome = nome
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
        var maquina : Int? = 0
        try {

         maquina=  jdbcTemplate.queryForObject(
            """
                 select count(idMaquina) from maquina where IP = '${ip}';
                """, Int::class.java
        );
        } catch (e: EmptyResultDataAccessException) { maquina = 0 }

     return maquina

    }

}
