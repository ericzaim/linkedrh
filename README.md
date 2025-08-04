# Avaliação SpringBoot LinkedRH

## Descrição
Fazer uma API REST para atender operações básicas do controle de treinamento dos funcionários.

## Índice

- ***[Tecnologias](#tecnologias)***
- ***[Endpoints](#endpoints)***
- ***[Models](#models)***
- ***[Dtos](#dtos)***
- ***[Serviços](#serviços)***

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
- ``` GET /curso``` Recebe id do curso pela URL e retorna<br> 
{ "nome": String,<br> "descrição": String,<br> "duração": int };

#### Inclusão de cursos
- ```POST /curso/cadastro``` Recebe no corpo da requisição <br> { "nome": String,<br> "descrição": String, <br>"duração": int }, e cadastra o curso;

#### Atualização de cursos
- ```PATCH /curso/atualiza/{id}``` Recebe pela URL o id do curso, e no corpo da requisição, <br> { "nome": String || null,<br> "descrição": String || null, <br>"duração": int || null };

#### Exclusão de Cursos
- ```DELETE /curso/delete/{id}``` Recebe pela URL o id do curso e deleta o curso.

### Turmas

#### Busca de turmas por curso,
- ```GET /curso/{id}/turma/``` Recebe id do curso, pela URL e retorna uma lista com objetos da seguinte maneira,<br>
  { "curso":String,<br> "local":String,<br> "inicio":LocalDate,<br> "fim":LocalDate };

#### Busca participantes de uma turma
```GET /curso/{id}/turma/{inicio}/{fim}/funcionario``` Recebe as datas de inicio e fim da turma e retorna os funcionarios da turma
{"nome":String, <br>"cpf": String,<br> "nascimento": LocalDate,<br> "cargo": String, <br>"admissao":LocalDate,<br> "status":boolean }

#### Inclusão de turmas
- ```POST /curso/{id}/turma/cadastro``` Recebe id do curso pela URL, e no corpo da requisição <br> { "curso":String,<br> "local":String,<br> "inicio":LocalDate,<br> "fim":LocalDate }, e cadastra a turma no curso indicado;

#### Atualização de turmas
- ```PATCH /curso/{id}/turma/{id_turma}/atualiza``` Recebe pela URL o id do curso e da turma, e no corpo da requisição, <br> { "local":String || null,<br> "inicio":LocalDate || null,<br> "fim":LocalDate || null } e atualiza as informações passadas;

#### Exclusão de turmas
- ```DELETE /curso/{id}/turma/{id_turma}/delete``` Recebe pela URL o id do curso e da turma, e deleta a turma do curso indicado.

#### Inclusão de Participantes
- ```POST /curso/turma/{id_turma}/participante/{id_funcionario}/incluir``` Recebe id da turma, pela URL, e no corpo da requisição <br>{ "nome":String,<br> "cpf":String,<br> "nascimento":LocalDate,<br> "cargo":String,<br>"admiss":LocalDate,<br>"status": int }, e cadastra o participante na turma indicada;

#### Exclusão de Participantes
- ```@DeleteMapping("/curso/turma/{id_turma}/participante/{id_aluno}/delete")``` Recebe pela URL o id da turma e do aluno, e deleta o aluno da turma indicada.

## Models
### Curso
```java
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
```java
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FuncionarioModel {
    private int codigo;
    private String nome;
    private String cpf;
    private LocalDate nascimento;
    private String cargo;
    private LocalDate admissao;
    private boolean status;

}
```
### Turma
```java
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurmaModel {
    private int codigo;
    private LocalDate inicio;
    private LocalDate fim;
    private String local;
    private int Curso;

}
```
## Dtos
### Curso
```java
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CursoDto {
    private String nome;
    private String descricao;
    private int duracao;
}
```
### Funcionario
```java
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FuncionarioDto {
    private String nome;
    private String cpf;
    private LocalDate nascimento;
    private String cargo;
    private LocalDate admissao;
    private boolean status;
}
```
### Turma
```java
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurmaDto {
    private LocalDate inicio;
    private LocalDate fim;
    private String local;
    private int curso;
    private int participantes;
}
```
### TurmaPostDto
```java
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TurmaPostDto {
    private LocalDate inicio;
    private LocalDate fim;
    private String local;
}
```
## Serviços
## Curso Service
### Buscar Cursos
#### Retorna a lista de todos os cursos cadastrados convertidos para CursoDto.
```java
    public List<CursoDto> getCursos(){
        try {
            List<CursoModel> cursos = cursoDao.findAll();
            if(cursos.isEmpty()){
                throw new RuntimeException("Ainda não há cursos cadastrados");
            }
            return cursos.stream()
                    .map(this::toDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao buscar cursos",e);
        }
    }
```
### Criar Cursos
#### Cria um novo curso a partir dos dados do CursoDto informado.
```java
    public String createCurso(CursoDto curso){
        try {
            this.cursoDao.createCurso(curso);
            return String.format("Curso %s cadastrado",curso.getNome());
        } catch (RuntimeException e) {
            throw new RuntimeException("Erro ao criar usuário");
        }
    }
```
### Atualizar Curso
Atualiza um curso existente com os dados fornecidos no `CursoDto`.

```java
public void updateCurso(CursoDto curso, int id){
    Map<String, Object> update = new HashMap<>();

    if (curso.getNome() != null) {
        update.put("nome", curso.getNome());
    }
    if (curso.getDescricao() != null) {
        update.put("descricao", curso.getDescricao());
    }
    if (curso.getDuracao() != 0) {
        update.put("duracao", curso.getDuracao());
    }
    try {
        this.cursoDao.updateCurso(id, update);
    } catch (RuntimeException e){
        throw new RuntimeException("Erro ao atualizar curso", e);
    }
}
```
### Deletar Curso
Deleta um curso existente com base no identificador informado.

```java
public void deleteCurso(int id){
    try{
        this.cursoDao.deleteCurso(id);
    }catch (RuntimeException e){
        throw new RuntimeException("Erro ao deletar curso",e);
    }
}
```
### Converter CursoModel para CursoDto
Converte um objeto `CursoModel` para `CursoDto`.

```java
/**
 * Converte um objeto CursoModel para CursoDto.
 *
 * @param curso CursoModel a ser convertido
 * @return Objeto CursoDto com dados convertidos
 */
public CursoDto toDto(CursoModel curso){
    return new CursoDto(curso.getNome(), curso.getDescricao(), curso.getDuracao());
}
```
## Turma Service
### Buscar Turmas por Curso
Retorna a lista de turmas associadas a um curso específico.

```java
public List<TurmaDto> getTurmas(int id_curso){
    try {
        return turmaDao.findTurmaByCurso(id_curso);
    } catch (RuntimeException e) {
        throw new RuntimeException("Não foi possível encontrar curso: " + id_curso + e);
    }
}
```
### Buscar Funcionários por Turma
Retorna a lista de funcionários associados a uma turma específica, identificada por curso, data de início e data de fim.

```java
public List<FuncionarioDto> getFuncionariosByTurma(int id_curso, LocalDate inicio, LocalDate fim){
    try {
        List<FuncionarioModel> funcionario = turmaDao.findByTurma(id_curso, inicio, fim);

        if (funcionario.isEmpty()) {
            throw new RuntimeException("Ainda não há Funcionarios cadastrados para essa turma");
        }

        return funcionario.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    } catch (InternalError e) {
        throw new InternalError("Erro ao buscar funcionarios ", e);
    }
}
```
### Incluir Aluno em Turma
Inclui um participante (aluno) em uma turma específica.

```java
public void incluirAluno(int id_turma, int id_funcionario) {
    try {
        this.turmaDao.adicionarParticipantes(id_turma, id_funcionario);
    } catch (RuntimeException e) {
        throw new RuntimeException("Erro ao incluir aluno ", e);
    }
}
```
### Excluir Aluno de Turma
Remove um participante (aluno) de uma turma específica.

```java
public void excluirAluno(int id, int id_turma) {
    try {
        this.turmaDao.removerParticipantes(id, id_turma);
    } catch (RuntimeException e) {
        throw new RuntimeException("Erro ao excluir aluno ", e);
    }
}
```
### Criar Turma  
Cria uma nova turma associada a um curso específico.

```java
/**
 * Cria uma nova turma associada a um curso.
 *
 * @param id_curso Identificador do curso
 * @param turma Dados da turma a ser criada
 * @throws RuntimeException em caso de erro na criação da turma
 */
public void createTurma(int id_curso, TurmaPostDto turma){
    try {
        this.turmaDao.createTurma(id_curso, turma);
    } catch (RuntimeException e) {
        throw new RuntimeException("Erro ao criar turma");
    }
}
```
### Atualizar Turma
Atualiza os dados de uma turma existente.

```java
public void updateTurma(int id_turma, TurmaPostDto turma){
    Map<String, Object> update = new HashMap<>();
    if (turma.getInicio() != null){
        update.put("inicio", turma.getInicio());
    }
    if (turma.getFim() != null){
        update.put("fim", turma.getFim());
    }
    if (turma.getLocal() != null) {
        update.put("local", turma.getLocal());
    }
    try{
        this.turmaDao.updateTurma(id_turma, update);
    }catch (Exception e){
        throw new RuntimeException("Erro ao atualizar turma", e);
    }
}
```
### Deletar Turma
Deleta uma turma existente.

```java
/**
 * Deleta uma turma existente.
 *
 * @param id_turma Identificador da turma a ser deletada
 */
public void deleteTurma(int id_turma) {
    try {
        this.turmaDao.deleteTurma(id_turma);
    } catch (RuntimeException e) {
        throw new RuntimeException("Erro ao deletar turma", e);
    }
}
```
### Converter FuncionarioModel para FuncionarioDto

```java
public FuncionarioDto toDto(FuncionarioModel funcionario){
    return new FuncionarioDto(
        funcionario.getNome(),
        funcionario.getCpf(),
        funcionario.getNascimento(),
        funcionario.getCargo(),
        funcionario.getAdmissao(),
        funcionario.isStatus()
    );
}
```
## Consultas
## Curso
### Buscar Todos os Cursos
```java
public List<CursoModel> findAll() {
    String sql =  "SELECT * FROM curso";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(CursoModel.class));
}
```
### Criar Curso
Insere um novo curso no banco de dados utilizando os dados fornecidos no objeto `CursoDto`.
```java
public void createCurso(CursoDto curso) {
    String sql = "INSERT INTO curso (nome, descricao, duracao) VALUES (?, ?, ?)";
    jdbcTemplate.update(sql, curso.getNome(), curso.getDescricao(), curso.getDuracao());
}
```
### Atualiza campos específicos de um curso existente no banco de dados

Atualiza campos específicos de um curso existente no banco de dados com base no identificador fornecido. Recebe um mapa contendo os pares campo-valor que serão atualizados.

```java
public void updateCurso(int id, Map<String, Object> updates){
    StringBuilder sql =  new StringBuilder("UPDATE curso SET ");
    List<Object> params = new ArrayList<>();

    updates.forEach((key,value)-> {
        sql.append(key).append(" = ?, ");
        params.add(value);
    });

    // Remove a última vírgula e espaço da query
    sql.setLength(sql.length()- 2);
    sql.append(" WHERE codigo = ?");
    params.add(id);

    jdbcTemplate.update(sql.toString(),params.toArray());
}
```
### Remove um curso do banco de dados, as turmas e participantes relacionadas a ele

Remove um curso do banco de dados, excluindo também todas as turmas e os participantes associados a essas turmas, com base no identificador do curso.

```java
public void deleteCurso(int id){
    jdbcTemplate.update("DELETE FROM turma_participante "+
                        "WHERE turma IN (SELECT codigo FROM turma WHERE curso = ?)", id);
    jdbcTemplate.update("DELETE FROM turma WHERE curso = ?", id);
    jdbcTemplate.update("DELETE FROM curso WHERE codigo = ?", id);
}
```
## Turma
### Busca uma lista de turmas associadas a um curso, incluindo contagem de participantes

Executa uma consulta SQL que retorna as turmas vinculadas a um curso específico, junto com a contagem de participantes de cada turma. Retorna uma lista de objetos TurmaDto contendo os dados das turmas.

```java
public List<TurmaDto> findTurmaByCurso(int id_curso){
  String  sql = "SELECT "+
          "t.*, "+
          "COUNT(tp.funcionario) AS participantes "+
          "FROM turma t "+
          "LEFT JOIN turma_participante tp ON t.codigo = tp.turma "+
          "WHERE t.curso = ? "+
          "GROUP BY t.codigo, t.inicio, t.fim, t.local, t.curso "+
          "ORDER BY t.inicio,t.fim";
  return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TurmaDto.class), id_curso);
}
```
### Busca a lista de funcionários participantes de uma turma específica

Executa uma consulta SQL para obter os funcionários que participam de uma turma específica, definida pela data de início, data de fim e o curso associado. Retorna uma lista de objetos FuncionarioModel correspondentes aos participantes.

```java
public List<FuncionarioModel> findByTurma(int id_curso, LocalDate inicio, LocalDate fim){
    String sql = "SELECT f.* FROM funcionario f " +
                 "JOIN turma_participante tp ON f.codigo = tp.funcionario " +
                 "JOIN turma t ON tp.turma = t.codigo " +
                 "WHERE t.inicio = ? AND t.fim = ? AND t.curso = ? " +
                 "ORDER BY f.nome";
    return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(FuncionarioModel.class), inicio, fim, id_curso);
}
```
### Insere uma nova turma no banco de dados associada a um curso

Recebe o identificador do curso e um objeto TurmaPostDto com os dados da turma, e insere uma nova turma na tabela `turma`.

```java
public void createTurma(int id_curso, TurmaPostDto turma) {
    String sql = "INSERT INTO turma (inicio, fim, local, curso) VALUES (?,?,?,?)";
    jdbcTemplate.update(sql, turma.getInicio(), turma.getFim(), turma.getLocal(), id_curso);
}
```
### Atualiza campos específicos de uma turma existente

Recebe o identificador da turma e um mapa com os pares campo-valor que serão atualizados. Constrói dinamicamente a query de update e executa no banco.

```java
public void updateTurma(int id_turma, Map<String, Object> updates){
    StringBuilder sql =  new StringBuilder("UPDATE turma SET ");
    List<Object> params = new ArrayList<>();

    updates.forEach((key,value)-> {
        sql.append(key).append(" = ?, ");
        params.add(value);
    });

    // Remove a última vírgula e espaço da query
    sql.setLength(sql.length()- 2);
    sql.append(" WHERE codigo = ?");
    params.add(id_turma);

    jdbcTemplate.update(sql.toString(),params.toArray());
}
```
### Adiciona um participante (funcionário) a uma turma

Recebe o identificador da turma e do funcionário, e insere um registro na tabela de associação entre turmas e participantes.

```java
public void adicionarParticipantes(int id_turma,int id_funcionario){
    String sql = "INSERT INTO turma_participante (turma, funcionario) VALUES (?,?)";
    jdbcTemplate.update(sql, id_turma, id_funcionario);
}
```
### Remove um participante (funcionário) de uma turma

Recebe o identificador do funcionário e da turma, e remove o registro correspondente da tabela de associação entre turmas e participantes.

```java
public void removerParticipantes(int id_funcionario,int id_turma){
    String sql = "DELETE FROM turma_participante WHERE turma = ? AND funcionario = ?";
    jdbcTemplate.update(sql, id_turma, id_funcionario);
}
```
### Remove uma turma e todos os seus participantes associados do banco de dados

Recebe o identificador da turma e remove todos os registros de participantes associados a essa turma, além da própria turma.

```java
public void deleteTurma(int id_turma) {
    jdbcTemplate.update("DELETE FROM turma_participante WHERE turma = ?", id_turma);
    jdbcTemplate.update("DELETE FROM turma WHERE codigo = ?", id_turma);
}
```