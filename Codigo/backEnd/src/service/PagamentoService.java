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
	
	public Pagamento get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
        return pagamentoDAO.get(id);
    }

	public String update(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Pagamento pagamento = (Pagamento) pagamentoDAO.get(id);
		String resp = "";

		if (pagamento != null) {
			pagamento.setNomeCartao(request.queryParams("nome"));
			pagamento.setNumeroCartao(request.queryParams("numero_cartao"));
			pagamento.setDataValidade(request.queryParams("data_validade"));
			pagamento.setCvvCartao(request.queryParams("cvv"));
			pagamento.setTipoPlano(request.queryParams("tipo_cartao"));

			pagamentoDAO.update(pagamento);
			response.status(200); // success
			resp = "Pagamento (ID " + pagamento.getId() + ") atualizado!";
		} else {
			response.status(404); // 404 Not found
			resp = "Pagamento (ID \" + pagamento.getId() + \") não encontrado!";
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

