package Classes;

public class ContaCorrente extends Conta {
	private double limiteChequeEspecial;
	
	//checagem do limite do cheque especial e o construtor 
	public ContaCorrente(String nome, double saldoInicial, double limiteChequeEspecial) {
		super(nome, saldoInicial);
		if (limiteChequeEspecial < 0) {
            throw new IllegalArgumentException("O limite do cheque especial não pode ser negativo.");
        }
        this.limiteChequeEspecial = limiteChequeEspecial;
	}
	
	//getters e setters
	public double getLimiteChequeEspecial() {
        return limiteChequeEspecial;
    }
 
	//define o valor máximo do limite do cheque especial
	public void setLimiteChequeEspecial(double limiteChequeEspecial) {
       if (limiteChequeEspecial < 0) {
        throw new IllegalArgumentException("O limite do cheque especial não pode ser negativo.");
        }
        this.limiteChequeEspecial = limiteChequeEspecial;
    }
 
 	public double getSaldoDisponivel() {
        return super.getSaldo() + this.limiteChequeEspecial;
    }
 
 	@Override
    public String getTipoContaDescricao() {
        return "Conta Corrente";
    }
 	
 	//printa o limite e saldo disponivel.
    @Override
    public void exibirExtrato() {
        super.exibirExtrato(); 
        System.out.printf("Limite Cheque Especial: R$ %.2f%n", this.limiteChequeEspecial);
        System.out.printf("Saldo Disponível (com limite): R$ %.2f%n", getSaldoDisponivel());
        System.out.println("-------------------------------------------------------------------------------------------------");
    }

	
}
