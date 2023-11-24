import java.io.File

class API_Python() {

    lateinit var arquivo_python: Process

    var sair = "True"

    fun chamarApiPython(maquina: Maquina, processo: Processo) {
       val codigoPython = """
import psutil
import time
import platform
import datetime
from mysql.connector import connect

# Conectando com o Workbench para fazer os selects
# Coloque suas credenciais do banco
conn = connect(
    host='localhost',
    user='root',
    password='181004Mp.',
    database='HealthTouch'
)

print("Bem Vindo à Aplicação Health Touch")

cursor = conn.cursor()

i = 0

# Rodando o monitoramento
while i == 0:
    if True == $sair:
        uso_cpu = round(psutil.cpu_percent(interval=1), 2)
        uso_memoria = round(psutil.virtual_memory().percent, 2)
        data = datetime.datetime.now()

        query = '''
                insert into Monitoramento(porcentagem, dataHora, fkComponente, fkMaquina, fkPlanoEmpresa, fkTipoMaquina, fkEmpresaMaquina)
                VALUES (%s, %s, %s, %s, %s, %s, %s)
                '''

        insert = [
            uso_cpu, data, 1,${processo.fkMaquina}, ${maquina.fkPlanoEmpresa}, ${processo.fkTipoMaquina}, ${processo.fkEmpresa}
        ]

        cursor.execute(query, insert)
        conn.commit()

        insert = [
            uso_memoria, data, 3, ${processo.fkMaquina}, ${maquina.fkPlanoEmpresa}, ${processo.fkTipoMaquina}, ${processo.fkEmpresa}
        ]

        cursor.execute(query, insert)
        conn.commit()

        time.sleep(1)
        print(f"Uso da CPU: {uso_cpu}%")
        print(f"Uso da Memória: {uso_memoria}%\r\n")

       
    else:
        print("saiiiiii")
        i = 1
        cursor.close()        
                
"""

        val nomeArquivoPyDefault = "Api_Python.py"

        File(nomeArquivoPyDefault).writeText(codigoPython)
        arquivo_python = Runtime.getRuntime().exec("python $nomeArquivoPyDefault")




    }

fun encerrarPython() {
    arquivo_python.destroy()
}
}
