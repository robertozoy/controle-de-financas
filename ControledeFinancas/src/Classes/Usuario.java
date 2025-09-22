package Classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Usuario {
	private String nome;
    private List<Conta> contas;
    private List<Categoria> categoriasPersonalizadas;

    //construtor e validacao do nome n ser vazio
    public Usuario (String nome) { 
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário não pode ser nulo ou vazio.");
        }
        this.nome = nome.trim();
        this.contas = new ArrayList<>();
        this.categoriasPersonalizadas = new ArrayList<>();
        inicializarCategoriasPadrao();
    }
    
    //inicializa categorias personalizadas
    private void inicializarCategoriasPadrao() {
    
        adicionarCategoriaPersonalizada(new Categoria("Alimentação"));
        adicionarCategoriaPersonalizada(new Categoria("Transporte"));
        adicionarCategoriaPersonalizada(new Categoria("Moradia"));
        adicionarCategoriaPersonalizada(new Categoria("Saúde"));
        adicionarCategoriaPersonalizada(new Categoria("Lazer"));
        adicionarCategoriaPersonalizada(new Categoria("Educação"));
        adicionarCategoriaPersonalizada(new Categoria("Salário"));
        adicionarCategoriaPersonalizada(new Categoria("Investimentos"));
        adicionarCategoriaPersonalizada(new Categoria("Presentes"));
        adicionarCategoriaPersonalizada(new Categoria("Outras Receitas"));
        adicionarCategoriaPersonalizada(new Categoria("Outras Despesas"));
    }
    
    //getters
    public String getnome() {
        return nome;
    }
    
    public List<Conta> getContas() {
        return new ArrayList<>(contas); 
    }
    
    //busca uma conta pelo nome em uma coleção
    public Optional<Conta> getContaPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return Optional.empty();
        }
        return contas.stream().filter(c -> c.getNome().equalsIgnoreCase(nome.trim())).findFirst();
    }
    
    public Optional<Categoria> getCategoriaPersonalizadaPorNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return Optional.empty();
        }
        return categoriasPersonalizadas.stream().filter(c -> c.getnome().equalsIgnoreCase(nome.trim())).findFirst();
    }

    public List<Categoria> getCategoriasPersonalizadas() {
        return new ArrayList<>(categoriasPersonalizadas);
    }
    
    
    //metodo pra adicionar conta validando se ela é nula ou nao
    public void adicionarConta(Conta conta) {
        if (conta == null) {
            throw new IllegalArgumentException("Conta não pode ser nula.");
        }
        //verifica se a conta existe se n adiciona a conta
        if (this.contas.stream().anyMatch(c -> c.getNome().equalsIgnoreCase(conta.getNome()))) {
            throw new IllegalArgumentException("Conta com nome '" + conta.getNome() + "' já existe.");
        }
        this.contas.add(conta);
    }
   
 
    //adiciona uma nova categoria à lista de categorias do usuário
    public void adicionarCategoriaPersonalizada(Categoria categoria) {
        if (categoria != null && categoriasPersonalizadas.stream().noneMatch(c -> c.getnome().equalsIgnoreCase(categoria.getnome()))) {
            this.categoriasPersonalizadas.add(categoria);
        }
    }
    
    //exibe um panorama financeiro completo do usuário, incluindo o saldo de todas as suas contas e um resumo dos seus orcamentos
    public void exibirResumoFinanceiroGeral() {
        System.out.println("\n=============== RESUMO FINANCEIRO GERAL DE " + nome.toUpperCase() + " ================");
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
        } else {
            System.out.println("\n--- SALDO DAS CONTAS ---");
            double saldoTotal = 0;
            for (Conta conta : contas) {
                System.out.printf("Conta: %-20s | Saldo: R$ %.2f (%s)%n",
                        conta.getNome(), conta.getSaldo(), conta.getTipoContaDescricao());
                saldoTotal += conta.getSaldo();
            }
            System.out.printf("----------------------------------------------------%n");
            System.out.printf("SALDO TOTAL EM TODAS AS CONTAS: R$ %.2f%n", saldoTotal);
        }


        System.out.println("==========================================================================");
    }
}
