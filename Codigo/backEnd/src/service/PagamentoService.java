package service;


import java.math.BigInteger;
import java.security.MessageDigest;
import dao.PagamentoDAO;
import model.Pagamento;
import spark.Request;
import spark.Response;


public class PagamentoService {
	
	private PagamentoDAO pagamentoDAO;
	
	public PagamentoService() {
        this.pagamentoDAO = new PagamentoDAO();
    }

	
	public static String codificar(String item) throws Exception {
		
    	MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(item.getBytes(), 0, item.length());
        String itemCriptografado = new BigInteger(1, m.digest()).toString(16);
        
        return itemCriptografado;
	}
	    
	
	public String insert(Request request, Response response) {
		int id = 0;  //0 pois nao vai ser necessario inserir no BD, pois ele é incrementado automaticamente do postgres
		String nomeCartao = request.queryParams("nome");
        String numeroCartao;
        String dataValidade; 
        String cvvCartao;
        String tipoPlano = request.queryParams("tipo_cartao");
        
        try {
            numeroCartao = codificar(request.queryParams("numero_cartao"));
        } catch (Exception e) {
            return "ERRO AO CODIFICAR";
        }
        try {
        	dataValidade = codificar(request.queryParams("data_validade"));
        } catch (Exception e) {
            return "ERRO AO CODIFICAR";
        }
        try {
        	cvvCartao = codificar(request.queryParams("cvv"));
        } catch (Exception e) {
            return "ERRO AO CODIFICAR";
        }
        
        Pagamento pagamento = new Pagamento(id, nomeCartao, numeroCartao, dataValidade, cvvCartao, tipoPlano);
        pagamentoDAO.insert(pagamento);

        String respostaJS = "<script>alert('Pagamento cadastrado com sucesso!'); window.location.href='/compraFinalizada.html';</script>";
        return respostaJS;
    }
	
	public Object get(Request request, Response response) {
	    int id = Integer.parseInt(request.params("id"));
	    Pagamento pagamento = pagamentoDAO.get(id);

	    if (pagamento != null) {
	        response.type("text/html;charset=utf-8");
	        String htmlResponse = "<h3>Dados do Pagamento</h3>" +
	                "<p>ID: " + pagamento.getId() + "</p>" +
	                "<p>Nome no cartão: " + pagamento.getNomeCartao() + "</p>" +
	                "<p>Numero do cartão criptografado: " + pagamento.getNumeroCartao() + "</p>" +
	                "<p>Data de validade do cartão criptografada: " + pagamento.getDataValidade() + "</p>" +
	                "<p>CVV do cartão criptografado: " + pagamento.getCvvCartao() + "</p>" +
	                "<p>Tipo do Plano: " + pagamento.getTipoPlano() + "</p>";
	        return htmlResponse;
	        
	    } else {	        
	        response.type("text/html;charset=utf-8");
	        String htmlResponse = "<h3>Pagamento com ID desejado não existe.</h3>";
	        return htmlResponse;
	    }
	}

	public String update(Request request, Response response) throws Exception {
	    int id = Integer.parseInt(request.params(":id"));
	    Pagamento pagamento = pagamentoDAO.get(id);
	    String resp = "";

	    if (pagamento != null) {
	    	pagamento.setNomeCartao(request.queryParams("nomecartao"));
	    	pagamento.setNumeroCartao(codificar(request.queryParams("numerocartao")));
	    	pagamento.setDataValidade(codificar(request.queryParams("datavalidadecartao")));
	    	pagamento.setCvvCartao(codificar(request.queryParams("cvvcartao")));
	    	pagamento.setTipoPlano(request.queryParams("tipoplano"));

	    	pagamentoDAO.update(pagamento);
	        response.status(200); // success
	        resp = "Pagamento (ID " + pagamento.getId() + ") atualizado!";
	    } else {
	        response.status(404); // 404 Not found
	        resp = "Pagamento (ID " + id + ") não encontrado!";
	    }

	    return resp;
	}

    
	public String delete(Request request, Response response) {
    	int id = Integer.parseInt(request.params(":id"));
        if (pagamentoDAO.delete(id)) {
            return "Pagamento excluído com sucesso!";
        } else {
            return "Pagamento não encontrado!";
        }
    }
}

