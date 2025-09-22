package Classes;

import java.time.LocalDate;


public class ContaPoupanca extends Conta {
	private double taxaRendimentoAnual;
	
	//construtor e checagem se a taxa é menor que zero
	public ContaPoupanca(String nome, double saldoInicial, double taxaRendimentoAnualPercentual) {
        super(nome, saldoInicial);
        if (taxaRendimentoAnualPercentual < 0) {
            throw new IllegalArgumentException("A taxa de rendimento não pode ser negativa.");
        }
        this.taxaRendimentoAnual = taxaRendimentoAnualPercentual / 100.0;
    }
	
	//getters e setters
	public double getTaxaRendimentoAnual() {
        return taxaRendimentoAnual * 100; 
    }
 
	//define a taxa de rendimento anual
	public void setTaxaRendimentoAnual(double taxaRendimentoAnualPercentual) {
	 if (taxaRendimentoAnualPercentual < 0) {
		 throw new IllegalArgumentException("A taxa de rendimento não pode ser negativa.");
        }
     	this.taxaRendimentoAnual = taxaRendimentoAnualPercentual / 100.0;
    }
 
	// calcula e adiciona o rendimento mensal a conta poupanca
	public void aplicarRendimentoMensal() {
        if (this.taxaRendimentoAnual > 0 && super.getSaldo() > 0) {
            double rendimentoMensalValor = (this.taxaRendimentoAnual / 12.0) * super.getSaldo();
            if (rendimentoMensalValor > 0.001) { 
                Categoria catRendimento = new Categoria("Rendimentos Poupança");
                Transacao rendimentoTransacao = new Transacao(
                    "Rendimento mensal poupança",
                    rendimentoMensalValor,
                    LocalDate.now(),
                    catRendimento,
                    TipoTransacao.RECEITA
                );
                super.adicionarTransacao(rendimentoTransacao);
                System.out.printf("Rendimento de R$ %.2f aplicado à conta %s.%n", rendimentoMensalValor, super.getNome());
            } else {
                System.out.println("Rendimento mensal muito baixo para ser aplicado na conta " + super.getNome());
            	}
        	} else {
            System.out.println("Não há rendimento a aplicar ou taxa não definida para a conta " + super.getNome());
        }
    }
 
	@Override
    public String getTipoContaDescricao() {
        return "Conta Poupança";
    }

    @Override
    public void exibirExtrato() {
        super.exibirExtrato(); // Chama o método da classe mãe
        System.out.printf("Taxa de Rendimento Anual Configurada: %.2f%%%n", getTaxaRendimentoAnual());
        System.out.println("-------------------------------------------------------------------------------------------------");
    }
	
}
