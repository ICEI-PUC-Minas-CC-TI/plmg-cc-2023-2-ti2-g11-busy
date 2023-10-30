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
	
	
	public Viagem get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
        return viagemDAO.get(id);
    }

    public String update(Request request, Response response) {
    	int id = Integer.parseInt(request.params(":id"));
		Viagem viagem = (Viagem) viagemDAO.get(id);
		String resp = "";

		if (viagem != null) {
			viagem.setOrigem(request.queryParams("origem"));
			viagem.setOrigem(request.queryParams("destino"));
			viagem.setOrigem(request.queryParams("partida"));
			viagem.setOrigem(request.queryParams("volta"));

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

