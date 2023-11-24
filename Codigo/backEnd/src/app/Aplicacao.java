package app;

import static spark.Spark.*;
import service.ClienteService;
import service.ViagemService;
import service.PagamentoService;

public class Aplicacao {
	
	private static ClienteService clienteService = new ClienteService();
	private static ViagemService viagemService = new ViagemService();
	private static PagamentoService pagamentoService = new PagamentoService();
	
    public static void main(String[] args) {
        port(4568);
        
        
        staticFiles.location("/public");
        
        post("/cadastrarCliente", (request, response) -> clienteService.insert(request, response));
        get("/cliente/:id", (request, response) -> clienteService.get(request, response));
        post("/cliente/:email", (request, response) -> clienteService.getByEmail(request, response));
        post("/cliente/update/:id", (request, response) -> clienteService.update(request, response));
        delete("/cliente/delete/:id", (request, response) -> clienteService.delete(request, response));

        
        post("/cadastrarViagem", (request, response) -> viagemService.insert(request, response));
        get("/viagem/:id", (request, response) -> viagemService.get(request, response));
        post("/viagem/update/:id", (request, response) -> viagemService.update(request, response));
        delete("/viagem/delete/:id", (request, response) -> viagemService.delete(request, response));
        
        
        post("/cadastrarPagamento", (request, response) -> pagamentoService.insert(request, response));
        get("/pagamento/:id", (request, response) -> pagamentoService.get(request, response));
        post("/pagamento/update/:id", (request, response) -> pagamentoService.update(request, response));
        delete("/pagamento/delete/:id", (request, response) -> pagamentoService.delete(request, response));
    }
}

