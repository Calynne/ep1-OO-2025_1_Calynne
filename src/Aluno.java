import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Aluno implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String matricula;
    private String cursoGraduacao;
    private boolean especial;
    private List<String> disciplinasMatriculadas;

    public Aluno(String nome, String matricula, String cursoGraduacao, boolean especial) {
        this.nome = nome;
        this.matricula = matricula;
        this.cursoGraduacao = cursoGraduacao;
        this.especial = especial;
        this.disciplinasMatriculadas = new ArrayList<>();
    }

    public void listarDisciplinas() {
        if (disciplinasMatriculadas.isEmpty()) {
            System.out.println("Você não está matriculado em nenhuma disciplina.");
        } else {
            System.out.println("Disciplinas matriculadas:");
            for (String d : disciplinasMatriculadas) {
                System.out.println("- " + d);
            }
        }
    }

    public void adicionarDisciplina(String nomeDisciplina) {
        if (especial && disciplinasMatriculadas.size() >= 2) {
            System.out.println("Limite de 2 disciplinas para alunos especiais.");
            return;
        }
        if (disciplinasMatriculadas.contains(nomeDisciplina)) {
            System.out.println("Você já está matriculado nessa disciplina.");
            return;
        }
        disciplinasMatriculadas.add(nomeDisciplina);
        System.out.println("Disciplina adicionada com sucesso.");
    }

    public void trancarDisciplina(String nomeDisciplina) {
        if (disciplinasMatriculadas.remove(nomeDisciplina)) {
            System.out.println("Disciplina '" + nomeDisciplina + "' trancada com sucesso.");
        } else {
            System.out.println("Você não está matriculado nessa disciplina.");
        }
    }

    public boolean possuiDisciplina(String nomeDisciplina) {
        return disciplinasMatriculadas.contains(nomeDisciplina);
    }

    public boolean pdMatri() {
        return especial && disciplinasMatriculadas.size() >= 2;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return cursoGraduacao;
    }

    public boolean isEspecial() {
        return especial;
    }

    public List<String> getDisciplinasMatriculadas() {
        return disciplinasMatriculadas;
    }

    public void setDisciplinasMatriculadas(List<String> disciplinas) {
        this.disciplinasMatriculadas = disciplinas;
    }

    public String toString() {
        return nome + " - " + matricula + " - " + cursoGraduacao +
               (especial ? " (Especial)" : "");
    }
}
