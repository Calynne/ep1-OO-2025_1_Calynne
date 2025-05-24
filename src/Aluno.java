import java.io.Serializable;
import java.util.ArrayList;

public class Aluno implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String matricula;
    private String cursoGraduacao;
    private ArrayList<String> disciplinasMatriculadas;

    public Aluno(String nome, String matricula, String cursoGraduacao) {
        this.nome = nome;
        this.matricula = matricula;
        this.cursoGraduacao = cursoGraduacao;
        this.disciplinasMatriculadas = new ArrayList<>();
    }

    public void adicionarDisciplina(String nomeDisciplina) {
        disciplinasMatriculadas.add(nomeDisciplina);
    }

    public boolean possuiDisciplina(String nomeDisciplina) {
        return disciplinasMatriculadas.contains(nomeDisciplina);
    }

   
    public String getNome() {
        return nome;
    }

    public String getCurso() {
        return cursoGraduacao;
    }

    public String getMatricula() {
        return matricula;
    }

    @Override
    public String toString() {
        return nome + " - " + matricula + " - " + cursoGraduacao;
    }
}
