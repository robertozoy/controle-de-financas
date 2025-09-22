package Classes;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import Classes.Conta;

public abstract class Conta {
	protected String nome;
    protected double saldo;
    protected List<Transacao> transacoes;
    
    
    //contrutor + tratamento de erro para conta vazia e nula.
    public Conta(String nome, double saldoInicial) {
    	if (nome == null || nome.trim().isEmpty()) {
    		throw new IllegalArgumentException("O nome da conta não pode ser nulo ou vazio.");
    	}
    	this.nome = nome.trim();
    	this.saldo = saldoInicial;
    	this.transacoes = new ArrayList<>();
    }
    
    //adiciona transacoes e chega se é nula
    public void adicionarTransacao(Transacao transacao) {
        if (transacao == null) {
            throw new IllegalArgumentException("Transação não pode ser nula.");
        }
        this.transacoes.add(transacao);
        
        // ajusta o saldo financeiro de acordo com o tipo de transacao
        if (transacao.getTipo() == TipoTransacao.RECEITA) { //verifica se o tipo da transacao é RECEITA
            this.saldo += transacao.getValor();
        } else { // DESPESA, ou seja dinheiro sai
            this.saldo -= transacao.getValor();
        }
    }
    
    public String getNome() {
        return nome;
    }

    public double getSaldo() {
        return saldo;
    }
    
    public List<Transacao> getTransacoes() {
        return new ArrayList<>(transacoes); 
    }
    
    public abstract String getTipoContaDescricao();
    
    //formata a data para formato dia/mes/ano e printa extrato e saldo
    public void exibirExtrato() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        System.out.println("\n--- Extrato da Conta: " + nome + " (" + getTipoContaDescricao() + ") ---");
        System.out.printf("Saldo Atual: R$ %.2f%n", saldo);

        //checa se há transacao registrada
        if (transacoes.isEmpty()) {
            System.out.println("Nenhuma transação registrada.");
        } else { // impressão do cabeçalho da tabela (se não estiver vazia):
            System.out.println("Transações:");
            System.out.println("-------------------------------------------------------------------------------------------------");
            System.out.printf("%-10s | %-20s | %-12s | %12s | %-25s%n", "Data", "Descrição", "Tipo", "Valor", "Categoria (Método Pgto.)");
            System.out.println("-------------------------------------------------------------------------------------------------");
            for (Transacao t : transacoes) { //imprime de acordo com a transacao feita
                String tipoStr = t.getTipo() == TipoTransacao.RECEITA ? "Receita" : "Despesa";
                String valorStr = String.format("R$ %.2f", t.getValor());
                String categoriaDetalhe = t.getCategoria().getnome();
                if (t.getTipo() == TipoTransacao.DESPESA && t.getMetodoPagamento() != null && !t.getMetodoPagamento().isEmpty()) {
                    categoriaDetalhe += " (" + t.getMetodoPagamento() + ")";
                }

                System.out.printf("%-10s | %-20s | %-12s | %12s | %-25s%n",
                        t.getData().format(dtf),
                        t.getDescricao().length() > 20 ? t.getDescricao().substring(0,17)+"..." : t.getDescricao(),
                        tipoStr,
                        valorStr,
                        categoriaDetalhe.length() > 25 ? categoriaDetalhe.substring(0,22)+"..." : categoriaDetalhe
                        );
            }
            System.out.println("-------------------------------------------------------------------------------------------------");
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return nome.equals(conta.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }

	}