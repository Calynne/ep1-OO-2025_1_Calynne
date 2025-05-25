import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Disciplina implements Serializable {
    private String nome;
    private String codigo;
    private int cargaHoraria;
    private List<String> preRequisitos;
    private List<Aluno> alunosMatriculados;
    private int maxAlunos;

    public Disciplina(String nome, String codigo, int cargaHoraria, List<String> preRequisitos) {
        this.nome = nome;
        this.codigo = codigo;
        this.cargaHoraria = cargaHoraria;
        this.preRequisitos = new ArrayList<>(preRequisitos);
        this.alunosMatriculados = new ArrayList<>();
        this.maxAlunos = 30; // valor padrão
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public List<String> getPreReq() {
        return preRequisitos;
    }

    public boolean temVaga() {
        return alunosMatriculados.size() < maxAlunos;
    }

    public boolean matricular(Aluno aluno) {
        if (!temVaga()) {
            return false;
        }
        alunosMatriculados.add(aluno);
        return true;
    }

    public void removerAluno(Aluno aluno) {
        alunosMatriculados.remove(aluno);
    }

    public String toString() {
        return "Nome: " + nome +
               ", Código: " + codigo +
               ", Carga horária: " + cargaHoraria +
               ", Pré-requisitos: " + (preRequisitos.isEmpty() ? "Nenhum" : preRequisitos.toString());
    }
}
