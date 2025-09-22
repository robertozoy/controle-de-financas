package Classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Transacao {
	private String descricao;
	private double valor; 
	private LocalDate data;
	private Categoria categoria;
	private TipoTransacao tipo;
	private String metodoPagamento;
	
	public Transacao(String descricao, double valor, LocalDate data, Categoria categoria, TipoTransacao tipo, String metodoPagamento) {	 
		 if (descricao == null || descricao.trim().isEmpty()) {
	         throw new IllegalArgumentException("A descrição da transação não pode ser nula ou vazia.");
	     }
	     if (valor <= 0) {
	    	 throw new IllegalArgumentException("O valor da transação deve ser positivo e maior que zero.");
	     }
	     if (data == null) {
           throw new IllegalArgumentException("A data da transação não pode ser nula.");
	     }
	     if (categoria == null) {
	         throw new IllegalArgumentException("A categoria da transação não pode ser nula.");
	     }
	     if (tipo == null) {
	          throw new IllegalArgumentException("O tipo da transação não pode ser nulo.");
       }
	
	     this.descricao = descricao.trim();
	     this.valor = valor;
	     this.data = data;
	     this.categoria = categoria;
	     this.tipo = tipo;
	     this.metodoPagamento = (tipo == TipoTransacao.DESPESA && metodoPagamento != null) ? metodoPagamento.trim() : null;	 
	 }
	
	public Transacao(String descricao, double valor, LocalDate data, Categoria categoria, TipoTransacao tipo) {
		 this(descricao, valor, data, categoria, tipo, null);
	 }
	 
	 public String getDescricao() {
		 return descricao;
	 }

	 public double getValor() {
		 return valor;
   }

	 public LocalDate getData() {
		 return data;
	 }

	 public Categoria getCategoria() {
		 return categoria;
	 }

	 public TipoTransacao getTipo() {
		 return tipo;
	 }

	 public String getMetodoPagamento() {
		 return metodoPagamento;
	 }
	 
	 @Override
	 public String toString() {
	     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	     String dataFormatada = data.format(dtf);

	     String desc = descricao.length() > 20 ? descricao.substring(0, 17) + "..." : descricao;
	     String simbolo = (tipo == TipoTransacao.RECEITA) ? "R$" : "D$";

	     String nomeCategoria = categoria.getnome();
	     String cat = nomeCategoria.length() > 15 ? nomeCategoria.substring(0, 12) + "..." : nomeCategoria;

	     String detalhes = String.format("%s | %-20s | %s %9.2f | Cat: %-15s",
	             dataFormatada, desc, simbolo, valor, cat);

	     if (tipo == TipoTransacao.DESPESA && metodoPagamento != null && !metodoPagamento.isEmpty()) {
	         detalhes += " | Pago com: " + metodoPagamento;
	     }

	     return detalhes;
	 }

	 @Override
	 public boolean equals(Object o) {
	     if (this == o) return true;
	     if (!(o instanceof Transacao)) return false;

	     Transacao that = (Transacao) o;

	     return valor == that.valor &&
	            Objects.equals(descricao, that.descricao) &&
	            Objects.equals(data, that.data) &&
	            Objects.equals(categoria, that.categoria) &&
	            tipo == that.tipo &&
	            Objects.equals(metodoPagamento, that.metodoPagamento);
	 }
	 
	 @Override
	 public int hashCode() {
	     return Objects.hash(valor, descricao, data, categoria, tipo, metodoPagamento);
	 } 
}
