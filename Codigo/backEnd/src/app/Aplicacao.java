package app;

import static spark.Spark.*;
import service.ClienteService;
import service.PagamentoService;
import service.ViagemService;


public class Aplicacao {
	
	private static ClienteService clienteService = new ClienteService();
	private static ViagemService viagemService = new ViagemService();
	private static PagamentoService pagamentoService = new PagamentoService();
                    
    public static void main(String[] args) {
        port(4568);
        
        staticFiles.location("/public");
        
        post("/cliente/cadastrar", (request, response) -> clienteService.insert(request, response));

        get("/cliente/:id", (request, response) -> clienteService.get(request, response));
        
        get("/cliente/list/:orderby", (request, response) -> clienteService.getAll(request, response));

        get("/cliente/update/:id", (request, response) -> clienteService.getToUpdate(request, response));
        
        post("/cliente/update/:id", (request, response) -> clienteService.update(request, response));
           
        get("/cliente/delete/:id", (request, response) -> clienteService.delete(request, response));

             
        
        post("/viagem/cadastrar", (request, response) -> viagemService.insert(request, response));

        get("/viagem/:id", (request, response) -> viagemService.get(request, response));
        
        get("/viagem/list/:orderby", (request, response) -> viagemService.getAll(request, response));

        get("/viagem/update/:id", (request, response) -> viagemService.getToUpdate(request, response));
        
        post("/viagem/update/:id", (request, response) -> viagemService.update(request, response));
           
        get("/viagem/delete/:id", (request, response) -> viagemService.delete(request, response));
        
        
        
        post("/pagamento/cadastrar", (request, response) -> pagamentoService.insert(request, response));

        get("/pagamento/:id", (request, response) -> pagamentoService.get(request, response));
        
        get("/pagamento/list/:orderby", (request, response) -> pagamentoService.getAll(request, response));

        get("/pagamento/update/:id", (request, response) -> pagamentoService.getToUpdate(request, response));
        
        post("/pagamento/update/:id", (request, response) -> pagamentoService.update(request, response));
           
        get("/pagamento/delete/:id", (request, response) -> pagamentoService.delete(request, response));
    }
}