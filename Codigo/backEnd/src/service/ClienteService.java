package service;


import java.math.BigInteger;
import java.security.MessageDigest;
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
        //System.out.println("Texto Original: " + senha);
        //System.out.println("Texto Criptografado: " + senhaCriptografada);
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
	
	public Cliente get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
        return clienteDAO.get(id);
    }

    public String update(Request request, Response response) {
    	int id = Integer.parseInt(request.params(":id"));
		Cliente cliente = (Cliente) clienteDAO.get(id);
        String resp = "";       

        if (cliente != null) {
        	cliente.setNome(request.queryParams("nome"));
            cliente.setEmail(request.queryParams("email"));
        	cliente.setTelefone(request.queryParams("telefone"));
            cliente.setSenha(request.queryParams("senha"));
            
        	clienteDAO.update(cliente);
        	response.status(200); // success
            resp = "Cliente (ID " + cliente.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Cliente (ID \" + cliente.getId() + \") não encontrado!";
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

