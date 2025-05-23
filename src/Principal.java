import java.util.Scanner;
public class Principal { 
    
    public static void main (String [] args ) {
        Scanner scanner = new Scanner(System.in);

        System.out.println (" Digite 1 para acessar o Modo aluno (Normal e Especial)");
        System.out.println (" Digite 2 para acessar o Modo disciplina/turma");
        System.out.println (" Digite 3 para acessar o Modo avaliação/frequência");

        
        int opcao = scanner.nextInt();
                


        while (opcao == 0){
        System.out.println (" Digite 1 para acessar o Modo aluno (Normal e Especial)");
        System.out.println (" Digite 2 para acessar o Modo disciplina/turma");
        System.out.println (" Digite 3 para acessar o Modo avaliação/frequência");

        }

        while (opcao == 1 ){
            System.out.print ( "Caro aluno (a). Seja bem vindo! Escolha uma das opções para dar continuidado ao que você deseja:")
            System.out.println ( " Digite 1 para se cadastrar ou editar seu cadadastro");
            System.out.println ( " Digite 2 para listar os alunos cadastrados");
            System.out.println ( " Digite 3 para se matricular em uma disciplina");
            System.out.println ( " Digite 4 para trancar uma disciplina ou semestre");

                int opcao2 = scanner.nextInt();

            while (opcao2 == 0){
                System.out.println ( "Caro aluno (a). Seja bem vindo! Escolha uma das opções para dar continuidade ao que você deseja:");
                System.out.println ( " Digite 1 para se cadastrar ou editar seu cadadastro");
                System.out.println ( " Digite 2 para listar os alunos cadastrados");
                System.out.println ( " Digite 3 para se matricular em uma disciplina");
                System.out.println ( " Digite 4 para trancar uma disciplina ou semestre");


            }
        
            while (opcao2 == 1 ){
                System.out.println ("Você escolheu a opção 1");
                System.out.println ("Digite o seu nome:");
                String nome = scanner.nextLine();
                System.out.println ("Digite o seu RA:");
                String ra = scanner.nextLine();
                System.out.println ("Digite o seu curso de graduação:");
                String cursoGraduacao = scanner.nextLine();

                Aluno aluno = new Aluno(nome, ra, cursoGraduacao);
                System.out.println ("Cadastro realizado com sucesso!");
                
            }

            while (opcao2 == 2 ){
              
                
            }

            while (opcao2 == 3 ){
        
                
            }
        }

        while(opcao > 3 ){
            System.out.println ("Está opção é inválida, digite novamente");
            opcao = scanner.nextInt();            
        } 
    }


}