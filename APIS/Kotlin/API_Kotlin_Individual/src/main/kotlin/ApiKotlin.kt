import com.github.britooo.looca.api.core.Looca
import javax.swing.JOptionPane

fun main() {

    val repositorio = Repositorio ()
    repositorio.iniciar()
    val colaborador = Colaborador()
    val locca = Looca()
    val maquina = Maquina ()
    val processo = Processo()






    val processos = Processo()
    val grupoProcesssos = locca.grupoDeProcessos

    JOptionPane.showMessageDialog(null, """
        Bem Vindo a Health Touch!!!
        Para verificar se você está no Sistema da Health Touch iremos fazer um login.
    """.trimIndent())


    colaborador.email = JOptionPane.showInputDialog("""
        Insira seu email:
    """.trimIndent())
    colaborador.senha  = JOptionPane.showInputDialog("""
        Insira sua senha :
    """.trimIndent())


    val vColaborador : Int? = repositorio.verificarColaborador(colaborador.email, colaborador.senha)
    repositorio.buscarNome(colaborador.email, colaborador.senha, colaborador)


    if (vColaborador != 0){
        if (vColaborador != null){
            JOptionPane.showMessageDialog(null, """
                Bem vindo ${colaborador.nome}!!!
                Você está dentro do Sistema da Health Touch.
            """.trimIndent())


            while (true){

                val opcao = JOptionPane.showInputDialog("""
                        Agora ${colaborador.nome },
                        Escolha uma das opções abaixo : 

                        1 - Capturar Processos
                        2 - Listar Processos
                        3 - Ver Total de Processos Capturados
                        4 - Ver Total de Threads Capturados
                        5 - Pesquisar Processo
                        6 - Sair


                    """.trimIndent()).toInt()


                when(opcao){
                    1 -> {
                      val ip =  JOptionPane.showInputDialog("""
                            Qual é o IP da máquina que você quer capturar ?
                        """.trimIndent()).toInt()

                      val vmaquina =   repositorio.validarMaquina(ip)

                        if (vmaquina != null) {
                            if (vmaquina != 0) {


                                JOptionPane.showMessageDialog(
                                    null, """
                                Você está capturando!!
                            """.trimIndent()
                                )


                                repositorio.buscaridMaquina(ip, processo)
                                repositorio.buscarfkEmpresa(ip, processo)
                                repositorio.buscarfkTipoMaquina(ip, processo)
                                repositorio.buscarfkStatusMaquina(ip, processo)

                                repositorio.cadastrarProcesso(processo)



                            }
                        }

                        else{
                            JOptionPane.showMessageDialog(null, """
                                IP errado você não está capturando!!
                            """.trimIndent())
                        }





                    }
                    2 -> {
                        println( """
                            
                       Nome: ${processo.nome}
                       PID: ${processo.PID}
                       Uso de memória (GB): ${processo.uso_ram}
                       Uso de CPU (%): ${processo.uso_cpu}
                       Total de processos: ${grupoProcesssos.totalProcessos}
                       Total de threads: ${grupoProcesssos.totalThreads}
                        """.trimIndent()
                        )
                    }
                   3-> {
                      val vProcessos = repositorio.verificarProcesso()
                       if (vProcessos != null){
                           if (vProcessos != 0) {
                               processos.total_processos = grupoProcesssos.totalProcessos
                               JOptionPane.showMessageDialog(null, """
                                   Total de Processos Capturados é de:
                                   ${processos.total_processos}
                               """.trimIndent())
                           }
                       }
                   }
                    4-> {
                        val vProcessos = repositorio.verificarProcesso()
                        if (vProcessos != null){
                            if (vProcessos != 0) {
                                processos.total_threads = grupoProcesssos.totalThreads
                                JOptionPane.showMessageDialog(null, """
                                   Total de Threads Capturados é de:
                                   ${processos.total_threads}
                               """.trimIndent())
                            }
                        }
                    }
                    5-> repositorio.pesquisarProcesso()
                    else -> break
                }
            }





        }
    }
    else {
        JOptionPane.showMessageDialog(null, """
            Tente Novamente!!!
            Você não está dentro do Sistema da Health Touch.
        """.trimIndent())
    }
}