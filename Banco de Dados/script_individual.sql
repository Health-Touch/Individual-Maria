create database if not exists HealthTouch;
drop database HealthTouch;

use HealthTouch;

-- Plano --

create table Plano (
idPlano Int primary key auto_increment,
tipoPlano varchar(45),
descricao varchar(45)
);

insert into Plano values
(null, 'Prime', '1 a 10 máquinas');

insert into Plano values
(null, 'Plus', '1 a 15 máquinas');

insert into Plano values
(null, 'Ultra', 'máquinas ilimitadas');

select * from plano;

-- Empresa --

create table Empresa (
idEmpresa int auto_increment,
NomeFantasia varchar(45),
CNPJ char(14),
inicioContrato date,
telFixo char(10),
fkPlano int, 
constraint fk_plano_empresa foreign key(fkPlano) references Plano(idPlano),
constraint pkComposta primary key (idEmpresa, fkPlano)
);

insert into Empresa values 
(null, 'Sus Santo André', '12345678912345', '2023-10-10', 1191234567, 3);

select * from Empresa;

-- Endereço --

create table Endereco (
idEndereco int auto_increment,
rua varchar(45),
num int,
estado varchar(45),
CEP char(8),
cidade varchar(45),
fkEmpresa int, 
constraint fk_empresa_endereco foreign key(fkEmpresa) references Empresa(idEmpresa),
constraint pk_composta_endereco primary key (idEndereco, fkEmpresa) 
);

insert into Endereco values
(null, "Rua Flores", 284, "São Paulo", 08121722, "São Paulo", 1);

select * from Endereco;

-- Nível de Acesso --

create table NivelAcesso (
idNivelAcesso int primary key auto_increment,
nivelAcesso char(2)
);

insert into NivelAcesso values
(null, 'RL');

insert into NivelAcesso values
(null, 'GT');

insert into NivelAcesso values
(null, 'ET');

select * from NivelAcesso;

-- Status Colaborador --

create table statusColaborador (
idStatusColaborador int primary key auto_increment,
statusColaborador varchar(45)
);

insert into statusColaborador values
(null, 'Ativo');

insert into statusColaborador values
(null, 'Desativo');

select * from statusColaborador;

-- Colaborador --

create table Colaborador (
idColaborador int auto_increment,
nome varchar(45),
email varchar(45),
senha varchar(45),
CPF char(14),
fkEmpresa int, 
constraint fk_empresa_colabrador foreign key(fkEmpresa) references Empresa(idEmpresa),
fkStatus int, 
constraint fk_status_colaborador foreign key(fkStatus) references statusColaborador(idStatusColaborador),
fkNivelAcesso int, 
constraint fk_nivel_acesso_colaborador foreign key(fkNivelAcesso) references NivelAcesso(idNivelAcesso),
constraint pk_composta_colaborador primary key (idColaborador, fkEmpresa, fkStatus, fkNivelAcesso)
);

insert into Colaborador values (null, 'Maria', 'maria@gmail.com', '123123', 37637602885, 1, 1, 2);

select * from Colaborador;

-- Telefone --

create table Telefone (
idTelefone int auto_increment,
TelCel char(11),
TelFixo char(10),
fkColaborador int,
constraint fk_colaborador_telefone foreign key(fkColaborador) references Colaborador (idColaborador),
constraint pk_composta_telefone primary key (idTelefone, fkColaborador)
);

insert into Telefone values
(null, 11912345678, 1109876543, 1);

select * from Telefone;

-- Setor --

create table setor (
idSetor int primary key auto_increment,
nome varchar(45));

insert into setor values
(null, 'Pediatria');

select * from setor;

-- Local Sala --

create table LocalSala (
idLocalSala int auto_increment,
sala int,
andar int,
fkSetor int,
constraint fk_setor_local_sala foreign key(fkSetor) references setor(idSetor),
constraint pk_composta_local_sala primary key (idLocalSala, fkSetor)
);

insert into LocalSala values
(null, 2, 3, 1);

select * from LocalSala;

-- Tipo de Máquina --

create table TipoMaquina (
idTipoMaquina int primary key auto_increment,
tipo varchar(45)
);

insert into TipoMaquina values 
(null, 'Computador');

insert into TipoMaquina values 
(null, 'Toten');

select * from tipoMaquina;

-- Status da Máquina --

create table statusMaquina (
idStatusMaquina int primary key auto_increment,
statusMaquina varchar(45)
);

insert into statusMaquina values
(null, 'Ativo');

insert into statusMaquina values
(null, 'Desativo');

select * from statusMaquina;

-- Máquina --

create table Maquina (
idMaquina int auto_increment,
SO varchar(45),
IP char(9),
fkEmpresa int, 
constraint fk_empresa_maquina foreign key(fkEmpresa) references Empresa(idEmpresa),
fkLocal int, 
constraint fk_local_sala_maquina  foreign key(fkLocal) references LocalSala(idLocalSala),
fkPlanoEmpresa int, 
constraint fk_plano_empresa_maquina  foreign key(fkPlanoEmpresa) references Plano(idPlano),
fkStatusMaquina int, 
constraint fk_status_maquina  foreign key(fkStatusMaquina) references statusMaquina(idStatusMaquina),
fkTipoMaquina int, 
constraint fk_tipo_maquina  foreign key(fkTipoMaquina) references TipoMaquina(idTipoMaquina),
constraint pk_composta_maquina primary key (idMaquina, fkEmpresa, fkPlanoEmpresa, fkStatusMaquina, fkTipoMaquina)
);

insert into Maquina values 
(null, "Windows", 123456789, 1, 1, 1, 1, 1);

 select count(idMaquina) from maquina where IP = 123456789;

select * from Maquina;
select idMaquina, fkEmpresa, fkStatusMaquina, fkTipoMaquina from maquina where IP = 123456789;
select fkEmpresa from maquina where IP = 123456789;
-- USB --

create table USB (
idUSB int auto_increment,
nomeUSB varchar(45),
dtHoraInserção datetime,
fkMaquina int, 
constraint fk_maquina_usb  foreign key(fkMaquina) references Maquina(idMaquina),
fkEmpresa int, 
constraint fk_empresa_usb foreign key(fkEmpresa) references Empresa(idEmpresa),
fkPlanoEmpresa int, 
constraint fk_plano_empresa_usb foreign key(fkPlanoEmpresa) references Plano(idPlano),
fkTipoMaquina int, 
constraint fk_tipo_maquina_usb foreign key(fkTipoMaquina) references TipoMaquina(idTipoMaquina),
constraint pk_composta_usb primary key (idUSB,fkMaquina, fkEmpresa,fkPlanoEmpresa,fkTipoMaquina)
);

insert into USB values
(null, "usbTeste", "2023-10-16 12:24:22.333", 1, 1, 3, 1);

select * from USB;

-- Análise de Toten --

create table analiseToten (
idAnaliseToten int auto_increment,
nomeBotao varchar(30),
dataHora date,
fkMaquina int, 
constraint fk_maquina_analise_toten foreign key(fkMaquina) references Maquina(idMaquina),
fkEmpresa int, 
constraint fk_empresa_analise_toten foreign key(fkEmpresa) references Empresa(idEmpresa),
fkPlanoEmpresa int, 
constraint fk_plano_empresa_analise_toten foreign key(fkPlanoEmpresa) references Plano(idPlano),
fkTipoMaquina int, 
constraint fk_tipo_maquina_analise_toten foreign key(fkTipoMaquina) references TipoMaquina(idTipoMaquina),
constraint pk_composta_analise_toten primary key (idAnaliseToten,fkMaquina, fkEmpresa,fkPlanoEmpresa,fkTipoMaquina)
);

select * from AnaliseToten;

-- Componetes --

create table Componente (
idComponente int auto_increment,
nome varchar(50),
capacidade varchar(50),
fkMaquina int, 
constraint fk_maquina_componente foreign key(fkMaquina) references Maquina(idMaquina),
fkEmpresaMaquina int, 
constraint fk_empresa_maquina_componente foreign key(fkEmpresaMaquina) references Maquina(idMaquina),
fkPlanoEmpresa int, 
constraint fk_plano_empresa_componente foreign key(fkPlanoEmpresa) references Plano(idPlano),
fkTipoMaquina int, 
constraint fk_tipo_maquina_componente foreign key(fkTipoMaquina) references TipoMaquina(idTipoMaquina),
constraint pk_composta_componete primary key (idComponente,fkMaquina, fkEmpresaMaquina,fkPlanoEmpresa,fkTipoMaquina)
);


insert into Componente values
(null, "CPU", "9 Núcleos", 1, 1, 1, 1);

insert into Componente values
(null, "DISCO", "500GB", 1, 1, 1, 1);

insert into Componente values
(null, "RAM", "16GB", 1, 1, 1, 1);

select * from Componente;

-- Monitoramento -- 

create table Monitoramento (
idMonitoramento int auto_increment,
porcentagem varchar (45),
dataHora datetime,
fkComponente int, 
constraint fk_componente_monitoramento foreign key(fkComponente) references Componente(idComponente),
fkMaquina int, 
constraint fk_maquina_monitoramento  foreign key(fkMaquina) references Maquina(idMaquina),
fkPlanoEmpresa int, 
constraint fk_plano_empresa_monitoramento  foreign key(fkPlanoEmpresa) references Plano(idPlano),
fkTipoMaquina int, 
constraint fk_tipo_maquina_monitoramento  foreign key(fkTipoMaquina) references TipoMaquina(idTipoMaquina),
fkEmpresaMaquina int, 
constraint fk_empresa_monitoramento  foreign key(fkEmpresaMaquina) references Maquina(idMaquina),
constraint pk_composta_monitoramnto primary key (idMonitoramento,fkComponente,fkMaquina,  fkPlanoEmpresa, fkTipoMaquina, fkEmpresaMaquina)
);

-- Processo --

drop table processo;
create table Processo (
idProcesso int  auto_increment,
nome varchar(45),
PID int,
uso_cpu double,
uso_ram double, 
total_processos int,
total_threads int, 
dtHoraInsercao datetime,
fkMaquina int, 
constraint fk_maquina_processo foreign key(fkMaquina) references Maquina(idMaquina),
fkEmpresa int, 
constraint fk_empresa_processo foreign key(fkEmpresa) references Empresa(idEmpresa),
fkTipoMaquina int, 
constraint fk_tipo_maquina_processo foreign key(fkTipoMaquina) references TipoMaquina(idTipoMaquina),
fkStatusMaquina int, 
constraint fk_status_maquina_processo foreign key(fkStatusMaquina) references StatusMaquina(idStatusMaquina),
constraint pk_composta_processo primary key (idProcesso, fkMaquina,  fkEmpresa, fkTipoMaquina, fkStatusMaquina)
);

select * from Processo;
truncate table processo;



-- Aviso --

create table aviso(
idAviso int  auto_increment,
dataHora datetime,
nivelAviso varchar (45),
cor varchar (45),
fkMonitoramento int,
 constraint foreign key(fkMonitoramento) references Monitoramento(idMonitoramento),
fkComponente int,
constraint fk_componete_aviso foreign key(fkComponente) references Componente(idComponente),
fkMaquina int,
constraint fk_maquina_aviso foreign key(fkMaquina) references Maquina(idMaquina),
fkEmpresa int,
constraint fk_empresa_aviso foreign key(fkEmpresa) references Empresa(idEmpresa),
fkPlanoEmpresa int,
constraint fk_plano_empresa_aviso foreign key(fkPlanoEmpresa) references Plano(idPlano),
fkTipoMaquina int,
constraint fk_tipo_maquina_aviso foreign key(fkTipoMaquina) references TipoMaquina(idTipoMaquina),
primary key (idAviso,fkMonitoramento,fkComponente, fkMaquina,fkEmpresa, fkPlanoEmpresa, fkTipoMaquina)
);


select * from aviso;

-- Parâmetro --

create table parametro (
idParametro int auto_increment ,
critico double,
alerta double, 
fkComponente int,
constraint fk_componete_parametro foreign key(fkComponente) references Componente(idComponente),
constraint pk_composta_parametro primary key (idParametro,fkComponente)
);


insert into parametro (idParametro,critico,alerta,fkComponente) values
(null, 30.0, 15.00 , 1),
(null, 50.0, 40.00 , 2),
(null, 70.0, 65.00 , 3);

-- Criando Trigger --


DELIMITER //
CREATE TRIGGER trigger_alerta AFTER INSERT ON Monitoramento FOR EACH ROW
BEGIN
    DECLARE v_id_parametro INT;
    DECLARE v_alerta DOUBLE;
    DECLARE v_critico DOUBLE;
    DECLARE v_porcentagem DOUBLE;

    SELECT NEW.porcentagem
    INTO v_porcentagem;
    
    SELECT p.idParametro, p.alerta, p.critico
    INTO v_id_parametro, v_alerta, v_critico
    FROM parametro as p
    WHERE p.idParametro = NEW.fkComponente;

    IF v_porcentagem >= v_critico THEN
      INSERT INTO aviso (dataHora,nivelAviso,cor, fkMonitoramento,fkComponente, fkMaquina,fkEmpresa, fkPlanoEmpresa, fkTipoMaquina)
        VALUES (NOW(), "Crítico", "Vermelho", NEW.idMonitoramento, NEW.fkComponente, NEW.fkMaquina, NEW.fkEmpresaMaquina, NEW.fkPlanoEmpresa, NEW.fkTipoMaquina);
    ELSEIF  v_porcentagem >= v_alerta THEN
     INSERT INTO aviso (dataHora,nivelAviso,cor,fkMonitoramento,fkComponente, fkMaquina,fkEmpresa, fkPlanoEmpresa, fkTipoMaquina)
        VALUES (NOW(), "Alerta", "Amarelo", NEW.idMonitoramento, NEW.fkComponente, NEW.fkMaquina, NEW.fkEmpresaMaquina, NEW.fkPlanoEmpresa, NEW.fkTipoMaquina);
    
    END IF;
END;
//
DELIMITER ;

drop trigger trigger_alerta;

insert into monitoramento values
(null, 15.6, now(), 1,1,1,1,1);

select nivelAviso, cor from aviso;
select * from monitoramento;