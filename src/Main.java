import dao.AlunoDAO;
import dao.CursoDAO;
import dao.InstrutorDAO;
import dao.MatriculaDAO;
import model.Aluno;
import model.Curso;
import model.Instrutor;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        AlunoDAO alunoDAO = new AlunoDAO();
        CursoDAO cursoDAO = new CursoDAO();
        InstrutorDAO instrutorDAO = new InstrutorDAO();
        MatriculaDAO matriculaDAO = new MatriculaDAO();
        Scanner scanner = new Scanner(System.in);

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("=================================================");
            System.out.println("     SISTEMA ACADÊMICO - GESTÃO DE MATRÍCULAS    ");
            System.out.println("=================================================");
            System.out.println(" 1 - Cadastrar Aluno");
            System.out.println(" 2 - Listar Todos os Alunos");
            System.out.println(" 3 - Matricular Aluno em um Curso (Escolher Curso)");
            System.out.println(" 4 - Cancelar Matrícula existente");
            System.out.println(" 5 - Puxar Todas as MatrículasAtivas");
            System.out.println(" 6 - Cadastrar Novo Instrutor");
            System.out.println(" 7 - Deletar um Instrutor");
            System.out.println(" 8 - Listar Todos os Instrutores");
            System.out.println(" 9 - Escolher qual Curso o Instrutor irá dar Aula");
            System.out.println(" 10- Listar Cursos e Professores Atuais");
            System.out.println(" 0 - Sair do Programa");
            System.out.println("=================================================");
            System.out.print("Selecione a ação desejada: ");

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[Erro] Insira um número válido!");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Nome do Aluno: ");
                    String nomeA = scanner.nextLine();
                    System.out.print("E-mail: ");
                    String emailA = scanner.nextLine();
                    System.out.print("CPF: ");
                    String cpfA = scanner.nextLine();
                    alunoDAO.cadastrar(new Aluno(nomeA, emailA, cpfA));
                    break;

                case 2:
                    System.out.println("--- ALUNOS NO SISTEMA ---");
                    for (Aluno a : alunoDAO.listarTodos()) {
                        System.out.println("ID: " + a.getIdAluno() + " | Nome: " + a.getNome());
                    }
                    break;

                case 3:
                    System.out.println("--- REALIZAR MATRÍCULA ---");
                    System.out.print("Digite o ID do Aluno: ");
                    int idA = Integer.parseInt(scanner.nextLine());

                    System.out.println("Cursos disponíveis para escolha:");
                    for (Curso c : cursoDAO.listarTodos()) {
                        System.out.println("ID Curso: " + c.getIdCurso() + " -> " + c.getTitulo());
                    }
                    System.out.print("Digite o ID do Curso escolhido: ");
                    int idC = Integer.parseInt(scanner.nextLine());

                    matriculaDAO.matricular(idA, idC);
                    break;

                case 4:
                    System.out.println("--- CANCELAR MATRÍCULA ---");
                    matriculaDAO.puxarMatriculas();
                    System.out.print("Digite o ID da Matrícula que deseja cancelar: ");
                    int idM = Integer.parseInt(scanner.nextLine());
                    matriculaDAO.cancelarMatricula(idM);
                    break;

                case 5:
                    System.out.println("--- PUXANDO REGISTROS DE MATRÍCULAS ---");
                    matriculaDAO.puxarMatriculas();
                    break;

                case 6:
                    System.out.print("\nNome do Instrutor: ");
                    String nomeI = scanner.nextLine();
                    System.out.print("Especialidade: ");
                    String espI = scanner.nextLine();
                    System.out.print("E-mail do Instrutor: ");
                    String emailI = scanner.nextLine();
                    instrutorDAO.cadastrar(new Instrutor(nomeI, espI, emailI));
                    break;

                case 7:
                    System.out.println("--- DELETAR INSTRUTOR ---");
                    System.out.print("Digite o ID do Instrutor para remover: ");
                    int idInstDel = Integer.parseInt(scanner.nextLine());
                    instrutorDAO.excluir(idInstDel);
                    break;

                case 8:
                    System.out.println("--- INSTRUTORES CADASTRADOS ---");
                    for (Instrutor inst : instrutorDAO.listarTodos()) {
                        System.out.println("ID: " + inst.getIdInstrutor() + " | Prof: " + inst.getNome() + " | Área: " + inst.getEspecialidade());
                    }
                    break;

                case 9:
                    System.out.println("--- ATRIBUIR CURSO AO INSTRUTOR ---");
                    System.out.print("Digite o ID do Instrutor: ");
                    int idInstAtrib = Integer.parseInt(scanner.nextLine());

                    System.out.println("Cursos cadastrados:");
                    for (Curso c : cursoDAO.listarTodos()) {
                        System.out.println("ID Curso: " + c.getIdCurso() + " -> " + c.getTitulo());
                    }
                    System.out.print("Digite o ID do Curso que ele assumirá: ");
                    int idCursoAtrib = Integer.parseInt(scanner.nextLine());

                    cursoDAO.atribuirInstrutor(idCursoAtrib, idInstAtrib);
                    break;

                case 10:
                    System.out.println("--- MAPA DE CURSOS E SEUS INSTRUTORES ---");
                    for (Curso c : cursoDAO.listarTodos()) {
                        System.out.println("Curso ID: " + c.getIdCurso() + " | " + c.getTitulo() + " (ID do Professor Responsável: " + c.getIdInstrutor() + ")");
                    }
                    break;

                case 0:
                    System.out.println("Encerrando e salvando alterações no PostgreSQL. Até logo!");
                    break;

                default:
                    System.out.println("Opção incorreta!");
                    break;
            }
        }
        scanner.close();
    }
}