package service;


import java.math.BigInteger;
import java.security.MessageDigest;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dao.ClienteDAO;
import model.Cliente;
import spark.Request;
import spark.Response;


public class ClienteService {
	
	private ClienteDAO clienteDAO;
	
	public ClienteService() {
        this.clienteDAO = new ClienteDAO();
    }

	
	public static String codificarSenha(String senha) throws Exception {
		
    	MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(senha.getBytes(), 0, senha.length());
        String senhaCriptografada = new BigInteger(1, m.digest()).toString(16);
        
        return senhaCriptografada;
	}
	    
	
	public String insert(Request request, Response response) {
		int id = 0;  //0 pois nao vai ser necessario inserir no BD, pois ele é incrementado automaticamente do postgres
		String nome = request.queryParams("nome");
        String email = request.queryParams("email");
        String telefone = request.queryParams("telefone");
        String senha;
        
        try {
            senha = codificarSenha(request.queryParams("senha"));
        } catch (Exception e) {
            return "ERRO AO CODIFICAR SENHA";
        }
      
        Cliente cliente = new Cliente(id, nome, email, telefone, senha);
        clienteDAO.insert(cliente);

        String respostaJS = "<script>alert('Cadastro realizado com sucesso!'); window.location.href='/login.html';</script>";
        return respostaJS;
    }
	
	public Object get(Request request, Response response) {
	    int id = Integer.parseInt(request.params("id"));
	    Cliente cliente = clienteDAO.get(id);
	    
	    if (cliente != null) {
	        response.type("text/html;charset=utf-8");
	        String htmlResponse = "<h3>Dados do Cliente</h3>" +
	                "<p>ID: " + cliente.getId() + "</p>" +
	                "<p>Nome: " + cliente.getNome() + "</p>" +
	                "<p>Email: " + cliente.getEmail() + "</p>" +
	                "<p>Telefone: " + cliente.getTelefone() + "</p>" +
	                "<p>Senha criptografada: " + cliente.getSenha() + "</p>";
	        return htmlResponse;
	        
	    } else {	        
	        response.type("text/html;charset=utf-8");
	        String htmlResponse = "<h3>Cliente com ID desejado não existe.</h3>";
	        return htmlResponse;
	    }
	}
	
	public Object getByEmail(Request request, Response response) throws Exception {
	    String email = request.params("email");

	    // Obtem o corpo da requisição e extrai a senha
	    String body = request.body();
	    JsonObject json = JsonParser.parseString(body).getAsJsonObject();
	    String senhaDigitada = json.get("senha").getAsString();

	    Cliente cliente = clienteDAO.getByEmail(email);

	    if (cliente != null) {
	        String senhaDoBanco = cliente.getSenha();
	        if (verificarSenha(senhaDigitada, senhaDoBanco)) {
	            return "{ \"success\": true }";  // Senha válida
	        } else {
	            return "{ \"success\": false, \"message\": \"Senha inválida\" }"; // Senha inválida
	        }
	    } else {
	        return "{ \"success\": false, \"message\": \"Cliente não encontrado\" }";  // Cliente não encontrado
	    }
	}

	private boolean verificarSenha(String senhaDigitada, String senhaDoBanco) throws Exception {
        String senhaDigitadaCodificada = codificarSenha(senhaDigitada);

        return senhaDigitadaCodificada.equals(senhaDoBanco);
    }

	public String update(Request request, Response response) throws Exception {
	    int id = Integer.parseInt(request.params(":id"));
	    Cliente cliente = clienteDAO.get(id);
	    String resp = "";

	    if (cliente != null) {
	        cliente.setNome(request.queryParams("nome"));
	        cliente.setEmail(request.queryParams("email"));
	        cliente.setTelefone(request.queryParams("telefone"));
	        cliente.setSenha(codificarSenha(request.queryParams("senha")));

	        clienteDAO.update(cliente);
	        response.status(200); // success
	        resp = "Cliente (ID " + cliente.getId() + ") atualizado!";
	    } else {
	        response.status(404); // 404 Not found
	        resp = "Cliente (ID " + id + ") não encontrado!";
	    }

	    return resp;
	}

    
    public String delete(Request request, Response response) {
    	int id = Integer.parseInt(request.params(":id"));
        if (clienteDAO.delete(id)) {
            return "Cliente excluído com sucesso!";
        } else {
            return "Cliente não encontrado!";
        }
    }
}

