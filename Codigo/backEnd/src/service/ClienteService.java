package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.ClienteDAO;
import model.Cliente;
import spark.Request;
import spark.Response;


public class ClienteService {

	private ClienteDAO clienteDAO = new ClienteDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_EMAIL = 3;
	
	
	public ClienteService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Cliente(), FORM_ORDERBY_NOME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Cliente(), orderBy);
	}

	
	public void makeForm(int tipo, Cliente cliente, int orderBy) {
		String nomeArquivo = "cadastreSe.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umCliente = "";
		if(tipo != FORM_INSERT) {
			umCliente += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/cliente/list/1\">Novo Cliente</a></b></font></td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t</table>";
			umCliente += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/cliente/";
			String name, nome, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				name = "Inserir Cliente";
				nome = "Arthur, Laura, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + cliente.getId();
				name = "Atualizar Cliente (ID " + cliente.getId() + ")";
				nome = cliente.getNome();
				buttonLabel = "Atualizar";
			}
			umCliente += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umCliente += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""+ nome +"\"></td>";
			umCliente += "\t\t\t<td>Telefone: <input class=\"input--register\" type=\"text\" name=\"telefone\" value=\""+ cliente.getTelefone() +"\"></td>";
			umCliente += "\t\t\t<td>Email: <input class=\"input--register\" type=\"text\" name=\"email\" value=\""+ cliente.getEmail() +"\"></td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t</table>";
			umCliente += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umCliente += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Cliente (ID " + cliente.getId() + ")</b></font></td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td>&nbsp;Nome: "+ cliente.getNome() +"</td>";
			umCliente += "\t\t\t<td>Telefone: "+ cliente.getTelefone() +"</td>";
			umCliente += "\t\t\t<td>Email: "+ cliente.getEmail() +"</td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td>&nbsp;</td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-CLIENTE>", umCliente);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Clientes</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/cliente/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/cliente/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/cliente/list/" + FORM_ORDERBY_EMAIL + "\"><b>Email</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Cliente> clientes;
		if (orderBy == FORM_ORDERBY_ID) {                 	clientes = clienteDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_NOME) {		clientes = clienteDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_EMAIL) {			clientes = clienteDAO.getOrderByEmail();
		} else {											clientes = clienteDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Cliente p : clientes) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getId() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getEmail() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/cliente/" + p.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/cliente/update/" + p.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteCliente('" + p.getId() + "', '" + p.getNome() + "', '" + p.getEmail() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-CLIENTE>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
        String email = request.queryParams("email");
        int telefone = Integer.parseInt(request.queryParams("telefone"));
        String senha = request.queryParams("senha");
		
		String resp = "";
		
		Cliente cliente = new Cliente(-1, nome, email, telefone, senha);
		
		if(clienteDAO.insert(cliente) == true) {
            resp = "Cliente (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Cliente (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Cliente cliente = (Cliente) clienteDAO.get(id);
		
		if (cliente != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, cliente, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Cliente " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Cliente cliente = (Cliente) clienteDAO.get(id);
		
		if (cliente != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, cliente, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Cliente " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Cliente cliente = (Cliente) clienteDAO.get(id);
        String resp = "";       

        if (cliente != null) {
        	cliente.setNome(request.queryParams("nome"));
            cliente.setEmail(request.queryParams("email"));
        	cliente.setTelefone(Integer.parseInt(request.queryParams("telefone")));
            cliente.setSenha(request.queryParams("senha"));
            
        	clienteDAO.update(cliente);
        	response.status(200); // success
            resp = "Cliente (ID " + cliente.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Cliente (ID \" + cliente.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Cliente cliente = clienteDAO.get(id);
        String resp = "";       

        if (cliente != null) {
            clienteDAO.delete(id);
            response.status(200); // success
            resp = "cLIENTE (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "cLIENTE (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}


/*package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.ClienteDAO;
import model.Cliente;
import spark.Request;
import spark.Response;


public class ClienteService {

	private ClienteDAO clienteDAO = new ClienteDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NOME = 2;
	private final int FORM_ORDERBY_EMAIL = 3;
	
	
	public ClienteService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Cliente(), FORM_ORDERBY_NOME);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Cliente(), orderBy);
	}

	
	public void makeForm(int tipo, Cliente cliente, int orderBy) {
		String nomeArquivo = "cadastreSe.html";
		form = "";
		try {
			Scanner entrada = new Scanner(new File(nomeArquivo));
			while (entrada.hasNext()) {
				form += (entrada.nextLine() + "\n");
			}
			entrada.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
		String umCliente = "";
		if (tipo != FORM_INSERT) {
			umCliente += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/cliente/list/1\">Novo Cliente</a></b></font></td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t</table>";
			umCliente += "\t<br>";
		}
	
		if (tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/cliente/";
			String name, email, buttonLabel;
			if (tipo == FORM_INSERT) {
				action += "insert";
				name = "arthur teste";
				email = "aa@gmail.com";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + cliente.getId();
				name = cliente.getNome();
				email = cliente.getEmail();
				buttonLabel = "Atualizar";
			}
			umCliente += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umCliente += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\"" + name + "\"></td>";
			umCliente += "\t\t\t<td>&nbsp;Email: <input class=\"input--register\" type=\"email\" name=\"email\" value=\"" + email + "\"></td>";
			umCliente += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\"" + buttonLabel + "\" class=\"input--main__style input--button\"></td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t</table>";
			umCliente += "\t</form>";
		} else if (tipo == FORM_DETAIL) {
			umCliente += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Cliente (ID " + cliente.getId() + ")</b></font></td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t\t<tr>";
			umCliente += "\t\t\t<td>&nbsp;Nome: " + cliente.getNome() + "</td>";
			umCliente += "\t\t\t<td>&nbsp;Email: " + cliente.getEmail() + "</td>";
			umCliente += "\t\t</tr>";
			umCliente += "\t</table>";
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-CLIENTE>", umCliente);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Clientes</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/cliente/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
        		"\t<td><a href=\"/cliente/list/" + FORM_ORDERBY_NOME + "\"><b>Nome</b></a></td>\n" +
        		"\t<td><a href=\"/cliente/list/" + FORM_ORDERBY_EMAIL + "\"><b>Email</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Cliente> clientes;
		if (orderBy == FORM_ORDERBY_ID) {                 	clientes = clienteDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_NOME) {			clientes = clienteDAO.getOrderByNome();
		} else if (orderBy == FORM_ORDERBY_EMAIL) {			clientes = clienteDAO.getOrderByEmail();
		} else {											clientes = clienteDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Cliente p : clientes) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getId() + "</td>\n" +
            		  "\t<td>" + p.getNome() + "</td>\n" +
            		  "\t<td>" + p.getEmail() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/cliente/" + p.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/cliente/update/" + p.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteCliente('" + p.getId() + "', '" + p.getNome() + "', '" + p.getEmail() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-CLIENTE>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		String nome = request.queryParams("nome");
		String email = request.queryParams("email");
		int telefone = Integer.parseInt(request.queryParams("telefone"));
		String senha = request.queryParams("senha");
		
		String resp = "";
		
		Cliente cliente = new Cliente(-1, nome, email, telefone, senha);
		
		if(clienteDAO.insert(cliente) == true) {
            resp = "Cliente (" + nome + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Cliente (" + nome + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Cliente cliente = (Cliente) clienteDAO.get(id);
		
		if (cliente != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, cliente, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Cliente " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));		
		Cliente cliente = (Cliente) clienteDAO.get(id);
		
		if (cliente != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, cliente, FORM_ORDERBY_NOME);
        } else {
            response.status(404); // 404 Not found
            String resp = "Cliente " + id + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
		Cliente cliente = clienteDAO.get(id);
        String resp = "";       

        if (cliente != null) {
        	cliente.setNome(request.queryParams("nome"));
			cliente.setEmail(request.queryParams("email"));
        	cliente.setTelefone(Integer.parseInt(request.queryParams("telefone")));			
			cliente.setEmail(request.queryParams("email"));

        	clienteDAO.update(cliente);

        	response.status(200); // success
            resp = "Cliente (ID " + cliente.getId() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Cliente (ID \" + cliente.getId() + \") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Cliente cliente = clienteDAO.get(id);
        String resp = "";       

        if (cliente != null) {
            clienteDAO.delete(id);
            response.status(200); // success
            resp = "Cliente (" + id + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Cliente (" + id + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}

*/







////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/*package service;

//import java.io.IOException;
import com.google.gson.Gson;
import dao.ClienteDAO;
import model.Cliente;
import spark.Request;
import spark.Response;

public class ClienteService {

  //private ClienteDAO clienteDAO;
  private ClienteDAO clienteDAO = new ClienteDAO();

  public ClienteService() {
      /*try {
          clienteDAO = new ClienteDAO("cliente.dat");
      } catch (IOException e) {
          System.out.println(e.getMessage());
      }
  }

  
  
  public Object insert(Request request, Response response) {
      // Inicializa o Gson
      Gson gson = new Gson();

      try {
          // Converte o corpo JSON da requisição para um objeto Cliente
          Cliente cliente = gson.fromJson(request.body(), Cliente.class);

          // Validação dos dados do cliente (adapte conforme suas necessidades)
          if (cliente.getNome() == null || cliente.getEmail() == null || cliente.getTelefone() == 0
                  || cliente.getSenha() == null) {
              response.status(400); // 400 Bad Request
              return "Campos inválidos no formulário.";
          }

          // Lógica para inserir o cliente no banco de dados ou realizar outras operações
          int id = clienteDAO.getMaxId() + 1;
          cliente.setId(id);
          //clienteDAO.insert(cliente);
          Cliente cliente2 = new Cliente(1, "arthur", "arthur@gmail.com", 1111, "aaa");
          clienteDAO.insert(cliente2);
          System.out.println(cliente2);

          response.status(201); // 201 Created
          return id;
      } catch (Exception e) {
          response.status(500); // 500 Internal Server Error
          return "Erro ao processar a requisição: " + e.getMessage();
      }
  }

  
  
  public Object get(Request request, Response response) {
      int id = Integer.parseInt(request.params(":id"));

      Cliente cliente = (Cliente) clienteDAO.get(id);

      if (cliente != null) {
          response.header("Content-Type", "application/xml");
          response.header("Content-Encoding", "UTF-8");

          return "<cliente>\n" +
                  "\t<id>" + cliente.getId() + "</id>\n" +
                  "\t<nome>" + cliente.getNome() + "</nome>\n" +
                  "\t<email>" + cliente.getEmail() + "</email>\n" +
                  "\t<telefone>" + cliente.getTelefone() + "</telefone>\n" +
                  "\t<senha>" + cliente.getSenha() + "</senha>\n" +
                  "</cliente>\n";
      } else {
          response.status(404); // 404 Not found
          return "Cliente " + id + " não encontrado.";
      }

  }

  
  
  public Object getToUpdate(Request request, Response response) {
      int id = Integer.parseInt(request.params(":id"));

      Cliente cliente = (Cliente) clienteDAO.get(id);

      if (cliente != null) {
          cliente.setNome(request.queryParams("nome"));
          cliente.setEmail(request.queryParams("email"));
          cliente.setTelefone(Integer.parseInt(request.queryParams("telefone")));
          cliente.setSenha(request.queryParams("senha"));

          clienteDAO.update(cliente);

          return id;
      } else {
          response.status(404); // 404 Not found
          return "Cliente não encontrado.";
      }

  }

  
  
  public Object delete(Request request, Response response) {
      int id = Integer.parseInt(request.params(":id"));

      Cliente cliente = (Cliente) clienteDAO.get(id);

      if (cliente != null) {

          clienteDAO.delete(cliente.getId());

          response.status(200); // success
          return id;
      } else {
          response.status(404); // 404 Not found
          return "Cliente não encontrado.";
      }
  }

  /*public Object getAll(Request request, Response response) {
      StringBuffer returnValue = new StringBuffer("<cliente type=\"array\">");
      for (Cliente cliente : clienteDAO.getAll()) {
          returnValue.append("\n<cliente>\n" +
                  "\t<id>" + cliente.getId() + "</id>\n" +
                  "\t<nome>" + cliente.getNome() + "</nome>\n" +
                  "\t<email>" + cliente.getEmail() + "</email>\n" +
                  "\t<telefone>" + cliente.getTelefone() + "</telefone>\n" +
                  "\t<senha>" + cliente.getSenha() + "</quantidade>\n" +
                  "</cliente>\n");
      }
      returnValue.append("</cliente>");
      response.header("Content-Type", "application/xml");
      response.header("Content-Encoding", "UTF-8");
      return returnValue.toString();
  }
}
*/
