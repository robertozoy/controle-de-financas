package app;

import Classes.Categoria;
import Classes.Conta;
import Classes.ContaCorrente;
import Classes.ContaPoupanca;
import Classes.TipoTransacao;
import Classes.Transacao;
import Classes.Usuario;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Organizador {
	private static Usuario usuarioLogado;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
    	
    	//scanner para de ler assim q o bloco try acaba
        try (Scanner scanner = new Scanner(System.in)) {
            inicializarAplicacao(scanner);

            //continua rodando enquanto a variável executando for verdadeira
            boolean executando = true;
            while (executando) {
                exibirMenuPrincipalEnxuto();
                System.out.print("Escolha uma opção: ");
                String opcao = scanner.nextLine().trim();

                //Para cada número ele chama o método correspondente que realiza a tarefa desejada
                try {
                    switch (opcao) {
                        case "1":
                            adicionarNovaConta(scanner);
                            break;
                        case "2":
                            adicionarNovaTransacao(scanner);
                            break;
                        case "3":
                            visualizarContasEExtratos(scanner);
                            break;
                        case "4":
                            usuarioLogado.exibirResumoFinanceiroGeral();
                            break;
                        case "0":
                            executando = false;
                            System.out.println("\nObrigado por usar o Organizador Financeiro. Até logo!");
                            break;
                        default:
                            System.out.println("Opção inválida. Tente novamente.");
                            break;
                    }
                //faz um tratamento de excessoes para quando um dado de entrada não é válido, e quando o programa está em um estado que não permite a ação
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.err.println("---------------------------------------------");
                    System.err.println("ERRO: " + e.getMessage());
                    System.err.println("---------------------------------------------");
                } catch (Exception e) {
                    System.err.println("---------------------------------------------");
                    System.err.println("Ocorreu um erro inesperado: " + e.getMessage());
                    System.err.println("---------------------------------------------");
                }

                if (executando) {
                    System.out.println("\nPressione Enter para retornar ao menu...");
                    scanner.nextLine();
                }
            }
        }
    }

    //metodo de inicializacao que da as boas vindas ao usuario.
    private static void inicializarAplicacao(Scanner scanner) {
        System.out.println("Bem-vindo ao Organizador Financeiro Pessoal!");
        System.out.print("Digite seu nome para começar: ");
        String nomeUser = "";
        while(nomeUser.trim().isEmpty()){
            nomeUser = scanner.nextLine();
            if(nomeUser.trim().isEmpty()) System.out.println("Nome não pode ser vazio. Digite novamente:");
        }
        usuarioLogado = new Usuario(nomeUser); // Usando construtor simplificado de Usuario
        System.out.println("\nOlá, " + usuarioLogado.getnome() + "! Seu organizador está pronto.");
    }

    //exibe o menu principal
    private static void exibirMenuPrincipalEnxuto() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║         ORGANIZADOR FINANCEIRO         ║");
        System.out.println("╠════════════════════════════════════════╣");
        System.out.println("║ 1. Adicionar Conta                     ║");
        System.out.println("║ 2. Adicionar Transação                 ║");
        System.out.println("║ 3. Visualizar Contas e Extratos        ║");
        System.out.println("║ 4. Visualizar Resumo Geral             ║");
        System.out.println("║ 0. Sair                                ║");
        System.out.println("╚════════════════════════════════════════╝");
    }

    //adiciona uma conta nova
    private static void adicionarNovaConta(Scanner scanner) {
        System.out.println("\n-- Adicionar Nova Conta --");
        String nomeConta = "";
        
        //while para validar o nome da conta como valido (nao nulo)
        while (nomeConta.trim().isEmpty()) {
            System.out.print("Nome da conta (ex: Banco X, Carteira): ");
            nomeConta = scanner.nextLine().trim();
            
            //remove espacos e verifica novamente se o nome é valido
            if (nomeConta.trim().isEmpty()) {
                System.out.println("Nome da conta não pode ser vazio.");
              
                //verifica se já existe uma conta com aquele nome logado.
            } else if (usuarioLogado.getContaPorNome(nomeConta).isPresent()){
                System.out.println("Já existe uma conta com este nome ('" + nomeConta + "'). Tente outro.");
                nomeConta = ""; // Força repetir o loop
            }
        }

        //ler o saldo inicial
        double saldoInicial = lerDouble(scanner, "Saldo inicial: R$ ", false, true);

        //opcoes de conta para escolher
        System.out.println("Tipos de Conta Disponíveis:");
        System.out.println("1. Conta Corrente");
        System.out.println("2. Conta Poupança");
        int tipoEscolhidoInt = lerInteiroEntre(scanner, "Escolha o tipo da conta (número): ", 1, 2); //garante que o numero escolhido seja 1 ou 2

        //com base no tipo escolhido ele pede informacoes adicionais
        Conta novaConta;
        if (tipoEscolhidoInt == 1) {
            double limite = lerDouble(scanner, "Limite do cheque especial (0 se não houver): R$ ", false, true);
            novaConta = new ContaCorrente(nomeConta, saldoInicial, limite);
        } else {
            double taxa = lerDouble(scanner, "Taxa de rendimento anual (ex: 6.5 para 6.5%): ", false, true);
            novaConta = new ContaPoupanca(nomeConta, saldoInicial, taxa);
        }
        
        //ao final a conta é adicionada a lista
        usuarioLogado.adicionarConta(novaConta);
        System.out.println("Conta '" + novaConta.getNome() + "' do tipo '" + novaConta.getTipoContaDescricao() + "' adicionada com sucesso!");
    }

    //registra seus movimentos financeiros 
    private static void adicionarNovaTransacao(Scanner scanner) {
        System.out.println("\n-- Adicionar Nova Transação --");
        Conta contaAlvo = selecionarConta(scanner, "Em qual conta deseja adicionar a transação?");
        if (contaAlvo == null) return; // Usuário cancelou a seleção

        String descricao = "";
        while (descricao.trim().isEmpty()) {
            System.out.print("Descrição da transação: ");
            descricao = scanner.nextLine().trim();
            if (descricao.trim().isEmpty()) System.out.println("Descrição não pode ser vazia.");
        }

        double valor = lerDouble(scanner, "Valor da transação: R$ ", true, false);

        LocalDate data = lerData(scanner, "Data da transação (dd/MM/yyyy) ou deixe em branco para hoje: ");
        
        //cancela a transacao se a categoria n for definida
        Categoria categoria = selecionarOuCriarCategoria(scanner);
        if (categoria == null) {
             System.out.println("Operação cancelada (categoria não definida).");
             return;
        }
        
        //bloco para escolher tipo de transacao
        System.out.println("Tipo da Transação: 1. Receita | 2. Despesa");
        int tipoInt = lerInteiroEntre(scanner, "Escolha o tipo (1 ou 2): ", 1, 2);
        TipoTransacao tipoTransacao = (tipoInt == 1) ? TipoTransacao.RECEITA : TipoTransacao.DESPESA;

        //so permite registrar metodo de pagamento se for uma despesa
        String metodoPagamento = null;
        if (tipoTransacao == TipoTransacao.DESPESA) {
            System.out.print("Método de pagamento (opcional): ");
            metodoPagamento = scanner.nextLine().trim();
        }

        //após informacoes coletadas a transacao é criada
        Transacao novaTransacao = new Transacao(descricao, valor, data, categoria, tipoTransacao, metodoPagamento);
        contaAlvo.adicionarTransacao(novaTransacao);
        System.out.println("Transação '" + novaTransacao.getDescricao() + "' adicionada com sucesso!");
    }

    // permite que o usuário escolha uma conta específica e veja seu histórico de transações
    private static void visualizarContasEExtratos(Scanner scanner) {
        System.out.println("\n-- Visualizar Contas e Extratos --");
        Conta contaSelecionada = selecionarConta(scanner, "Qual conta você deseja visualizar?");
        if (contaSelecionada != null) {
            contaSelecionada.exibirExtrato();
        }
    }
    
    // --- MÉTODOS UTILITÁRIOS (Helpers) ---
    //permite que o usuário escolha uma das suas contas cadastradas
    private static Conta selecionarConta(Scanner scanner, String prompt) {
        List<Conta> contas = usuarioLogado.getContas(); //obtém a lista de todas as contas associadas
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada. Adicione uma conta primeiro (Opção 1).");
            return null;
        }
        
        //exibindo cada uma com um número sequencial com o nome da conta, o tipo da conta e o saldo atual
        System.out.println("\n" + prompt);
        for (int i = 0; i < contas.size(); i++) {
            System.out.printf("%d. %s (%s - Saldo: R$ %.2f)%n", 
                i + 1, 
                contas.get(i).getNome(), 
                contas.get(i).getTipoContaDescricao(),
                contas.get(i).getSaldo()
            );
        }
        //opcao de saida facil caso nao queira ver nenhuma conta
        System.out.println("0. Cancelar");
        int escolha = lerInteiroEntre(scanner, "Número da conta: ", 0, contas.size());
        
        return (escolha == 0) ? null : contas.get(escolha - 1);
    }

    //permite que o usuário escolha uma categoria já existente para uma transação ou, caso precise, crie uma nova
    private static Categoria selecionarOuCriarCategoria(Scanner scanner) {
        System.out.println("\n-- Selecionar ou Criar Categoria --");
        List<Categoria> categoriasExistentes = usuarioLogado.getCategoriasPersonalizadas(); //exibe a lista de categorias personalizadas que o usuário já possui
        
        //listar as categorias existentes e duas opções adicionais são apresentadas: Criar nova categoria e Cancelar
        int i = 0;
        if (!categoriasExistentes.isEmpty()) {
            System.out.println("Categorias existentes:");
            for (i = 0; i < categoriasExistentes.size(); i++) {
                System.out.println((i + 1) + ". " + categoriasExistentes.get(i).getnome());
            }
        }
        System.out.println((i + 1) + ". Criar nova categoria");
        System.out.println("0. Cancelar");

        int escolha = lerInteiroEntre(scanner, "Escolha uma opção: ", 0, categoriasExistentes.size() + 1);

      //processamento da escolha
        if (escolha == 0) return null;
        if (escolha > 0 && escolha <= categoriasExistentes.size()) {
            return categoriasExistentes.get(escolha - 1);
        }
        
      //cria nova categoria e usa um loop para garantir que nao seja nula
        String nomeNovaCategoria = "";
        while(nomeNovaCategoria.trim().isEmpty()){
            System.out.print("Nome da nova categoria: ");
            nomeNovaCategoria = scanner.nextLine().trim();
            if(nomeNovaCategoria.trim().isEmpty()) {
                System.out.println("Nome não pode ser vazio.");
            } else if (usuarioLogado.getCategoriaPersonalizadaPorNome(nomeNovaCategoria).isPresent()) {
                System.out.println("Categoria com este nome já existe. Use a existente ou escolha outro nome.");
                nomeNovaCategoria = "";
            }
        }
        Categoria novaCat = new Categoria(nomeNovaCategoria);
        usuarioLogado.adicionarCategoriaPersonalizada(novaCat);
        System.out.println("Categoria '" + novaCat.getnome() + "' criada e selecionada.");
        return novaCat;
    }

 //---- METODOS AUXILIARES -----
   //Eles servem para tornar a interação com o usuário no console mais robusta, segura e eficiente
    
    //garante que o usuário insira um valor numérico válido
    private static double lerDouble(Scanner scanner, String prompt, boolean somentePositivoMaiorQueZero, boolean permiteZero) {
        double valor = 0;
        boolean valido = false;
        while (!valido) {
            System.out.print(prompt);
            try {
                valor = scanner.nextDouble();
                scanner.nextLine(); // Consumir \n
                if (somentePositivoMaiorQueZero && valor <= 0) {
                    System.out.println("Valor deve ser maior que zero.");
                } else if (!permiteZero && valor == 0) {
                     System.out.println("Valor não pode ser zero.");
                } else {
                    valido = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número (ex: 123.45).");
                scanner.nextLine();
            }
        }
        return valor;
    }
    
    //valida se um número é inteiro e se está dentro dos limites mínimo e máximo definidos.
    private static int lerInteiroEntre(Scanner scanner, String prompt, int min, int max) {
        int valor;
        while (true) {
            System.out.print(prompt);
            try {
                valor = scanner.nextInt();
                scanner.nextLine();
                if (valor >= min && valor <= max) {
                    break;
                } else {
                    System.out.printf("Opção inválida. Por favor, insira um número entre %d e %d.%n", min, max);
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, digite um número inteiro.");
                scanner.nextLine();
            }
        }
        return valor;
    }

    //garante que o formato de data seja sempre dia/mes/ano
    private static LocalDate lerData(Scanner scanner, String prompt) {
        LocalDate data = null;
        boolean valido = false;
        while (!valido) {
            System.out.print(prompt);
            String inputData = scanner.nextLine().trim();
            if (inputData.isEmpty()) {
                return LocalDate.now();
            }
            try {
                data = LocalDate.parse(inputData, DATE_FORMATTER);
                valido = true;
            } catch (DateTimeParseException e) {
                System.out.println("Formato de data inválido. Use dd/MM/yyyy (ex: 06/06/2025) ou deixe em branco para hoje.");
            }
        }
        return data;
    }
}
