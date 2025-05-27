import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa {
    
    
    private boolean especial;
    private List<String> disciplinasMatriculadas;

    public Aluno(String nome, String matricula, String curso, boolean especial) {
        setNome(nome);
        setMatricula(matricula);
        setCurso(curso);
        this.especial = especial;
        this.disciplinasMatriculadas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getCurso() {
        return curso;
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

    public void adicionarDisciplina(String disciplina) {
        if (!disciplinasMatriculadas.contains(disciplina)) {
            disciplinasMatriculadas.add(disciplina);
        }
    }

    public void trancarDisciplina(String disciplina) {
        disciplinasMatriculadas.remove(disciplina);
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

    @Override
    public String toString() {
        return getNome() + " - " + matricula + " - " + curso + (especial ? " (Especial)" : "");
    }
}
