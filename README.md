# Avaliação SpringBoot LinkedRH

## Descrição
Fazer uma API REST para atender operações básicas do controle de treinamento dos funcionários.

## Índice

- ***[Tecnologias](#tecnologias)***
- ***[Endpoints](#endpoints)***
- ***[Models](#entidades)***
- ***[Dtos](#dtos)***
- ***[Serviços](#servicos)***
- ***[Tabelas](#tabelas)***
- ***[Consultas](#consultas)***

## Tecnologias
- SpringBoot;
- Spring-boot-starter-jdbc;
- Spring-boot-starter-web;
- Spring-boot-devtools;
- Postgresql;
- Lombok;
- Spring-boot-starter-test;

## Endpoints
### Cursos

#### Listagem de Cursos
- ```@GetMapping("/curso")``` Recebe id do curso pela URL e retorna<br> 
{ "nome": String,<br> "descrição": String,<br> "duração": int };

#### Inclusão de cursos
- ```@PostMapping(/curso/cadastro)``` Recebe no corpo da requisição <br> { "nome": String,<br> "descrição": String, <br>"duração": int }, e cadastra o curso;

#### Atualização de cursos
- ```@PatchMapping("/curso/atualiza/{id}"``` Recebe pela URL o id do curso, e no corpo da requisição, <br> { "nome": String || null,<br> "descrição": String || null, <br>"duração": int || null };

#### Exclusão de turmas
- ```@DeleteMapping("/curso/delete/{id}")``` Recebe pela URL o id do curso e deleta o curso.

### Turmas

#### Busca de turmas por curso,
- ```@GetMapping("/curso/{id}/turma/{id_turma}")``` Recebe id do curso, e da turma, pela URL e retorna<br>
  { "curso":String,<br> "local":String,<br> "inicio":LocalDate,<br> "fim":LocalDate };

#### Inclusão de turmas
- ```@PostMapping(/curso/{id}/turma/cadastro)``` Recebe id do curso pela URL, e no corpo da requisição <br> { "curso":String,<br> "local":String,<br> "inicio":LocalDate,<br> "fim":LocalDate }, e cadastra a turma no curso indicado;

#### Atualização de turmas
- ```@PatchMapping("/curso/{id}/turma/{id_turma}/atualiza"``` Recebe pela URL o id do curso e da turma, e no corpo da requisição, <br> { "local":String || null,<br> "inicio":LocalDate || null,<br> "fim":LocalDate || null } e atualiza as informações passadas;

#### Exclusão de turmas
- ```@DeleteMapping("/curso/{id}/turma/{id_turma}/delete")``` Recebe pela URL o id do curso e da turma, e deleta a turma do curso indicado.

### Alunos
#### Listagem de Participantes
- ```@GetMapping("/curso/{id}/turma/{id_turma}/aluno")``` Recebe id do curso, e da turma, pela URL e retorna uma lista com os participantes<br>
  { "nome":String,<br> "cpf":String,<br> "nascimento":LocalDate,<br> "cargo":String,<br>"admiss":LocalDate,<br>"status": int };

#### Criação de Participantes
- ```@PostMapping(/curso/{id}/turma/{id_turma{id_turma}/aluno/cadastro)``` Recebe id do curso e id da turma, pela URL, e no corpo da requisição <br>{ "nome":String,<br> "cpf":String,<br> "nascimento":LocalDate,<br> "cargo":String,<br>"admiss":LocalDate,<br>"status": int }, e cadastra o participante na turma do curso indicado;

#### Exclusão de Participantes
- ```@DeleteMapping("/curso/{id}/turma/{id_turma}/aluno/{id_aluno}/delete")``` Recebe pela URL o id do curso e da turma, e deleta a turma do curso indicado.

## Models
### Curso
```
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CursoModel {
    private int codigo;
    private String nome;
    private String descricao;
    private int duracao;
}
```
### Funcionario
```
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FuncionarioModel {
    private int id;
    private String nome;
    private String cpf;
    private LocalDate dataNascimento;
    private String cargo;
    private LocalDate admissao;
    private int status;
}
```
### Turma
```
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurmaModel {
    private int id;
    private LocalDate inicio;
    private LocalDate fim;
    private String local;
    private int id_curso;
}
```
### Turma Participante
```
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurmaParticipanteModel {
    private int id;
    private int id_turma;
    private int id_funcionario;
}
```