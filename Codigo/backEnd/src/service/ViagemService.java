package service;

import java.time.LocalDate;

import dao.ViagemDAO;
import model.Viagem;
import spark.Request;
import spark.Response;


public class ViagemService {
	
	private ViagemDAO viagemDAO;
	
	public ViagemService() {
        this.viagemDAO = new ViagemDAO();
    }

	
	public String insert(Request request, Response response) {
		int id = 0;  //0 pois nao vai ser necessario inserir no BD, pois ele é incrementado automaticamente do postgres
		String origem = request.queryParams("origin");
        String destino = request.queryParams("destination");
        
        String partidaString = request.queryParams("departureDate");
        String voltaString = request.queryParams("returnDate");
        // Convertendo as strings de data em objetos LocalDate
        LocalDate partida = LocalDate.parse(partidaString);
        LocalDate volta = LocalDate.parse(voltaString);
 
        Viagem viagem = new Viagem(id, origem, destino, partida, volta);
        viagemDAO.insert(viagem);

        String respostaJS = "https://www.clickbus.com.br/onibus/" + origem + "/" + destino + "?departureDate=" + partida + "&returnDate=" + volta;
        response.redirect(respostaJS);
        return respostaJS;
        
    }
	
	
	public Object get(Request request, Response response) {
	    int id = Integer.parseInt(request.params("id"));
	    Viagem viagem = viagemDAO.get(id);

	    if (viagem != null) {
	        response.type("text/html;charset=utf-8");
	        String htmlResponse = "<h3>Dados da Viagem</h3>" +
	                "<p>ID: " + viagem.getId() + "</p>" +
	                "<p>Origem: " + viagem.getOrigem() + "</p>" +
	                "<p>Destino: " + viagem.getDestino() + "</p>" +
	                "<p>Partida: " + viagem.getPartida() + "</p>" +
	                "<p>Volta: " + viagem.getVolta() + "</p>";
	        return htmlResponse;
	        
	    } else {	        
	        response.type("text/html;charset=utf-8");
	        String htmlResponse = "<h3>Viagem com ID desejado não existe.</h3>";
	        return htmlResponse;
	    }
	}





    public String update(Request request, Response response) {
    	int id = Integer.parseInt(request.params(":id"));
		Viagem viagem = (Viagem) viagemDAO.get(id);
		String resp = "";

		if (viagem != null) {
			viagem.setOrigem(request.queryParams("origem"));
			viagem.setDestino(request.queryParams("destino"));
			viagem.setPartida(LocalDate.parse(request.queryParams("partida")));
			viagem.setVolta(LocalDate.parse(request.queryParams("volta")));


			viagemDAO.update(viagem);
			response.status(200); // success
			resp = "Viagem (ID " + viagem.getId() + ") atualizada!";
		} else {
			response.status(404); // 404 Not found
			resp = "Viagem (ID \" + viagem.getId() + \") não encontrada!";
		}
		
		return resp;
    }
    
    public String delete(Request request, Response response) {
    	int id = Integer.parseInt(request.params(":id"));
        if (viagemDAO.delete(id)) {
            return "Viagem excluída com sucesso!";
        } else {
            return "Viagem não encontrada!";
        }
    }
}

