package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.ViagemDAO;
import model.Viagem;
import spark.Request;
import spark.Response;

public class ViagemService
{

	private ViagemDAO viagemDAO = new ViagemDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_ORIGEM = 2;
	private final int FORM_ORDERBY_DESTINO = 2;

	public ViagemService() {
		makeForm();
	}

	public void makeForm() {
		makeForm(FORM_INSERT, new Viagem(), FORM_ORDERBY_ORIGEM);
	}

	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Viagem(), orderBy);
	}

	public void makeForm(int tipo, Viagem viagem, int orderBy) {
		String nomeArquivo = "index.html";
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

		String umaViagem = "";
		if (tipo != FORM_INSERT) {
			umaViagem += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umaViagem += "\t\t<tr>";
			umaViagem += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/viagem/list/1\">Nova Viagem</a></b></font></td>";
			umaViagem += "\t\t</tr>";
			umaViagem += "\t</table>";
			umaViagem += "\t<br>";
		}

		if (tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/viagem/";
			String name, buttonLabel;
			if (tipo == FORM_INSERT) {
				action += "insert";
				name = "Inserir Viagem";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + viagem.getId();
				name = "Atualizar Viagem (ID " + viagem.getId() + ")";
				buttonLabel = "Atualizar";
			}
			umaViagem += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umaViagem += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umaViagem += "\t\t<tr>";
			umaViagem += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name
					+ "</b></font></td>";
			umaViagem += "\t\t</tr>";
			umaViagem += "\t\t<tr>";
			umaViagem += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umaViagem += "\t\t</tr>";
			umaViagem += "\t\t<tr>";
			umaViagem += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nome\" value=\""
					+ name + "\"></td>";
			umaViagem += "\t\t\t<td>Origem: <input class=\"input--register\" type=\"text\" name=\"telefone\" value=\""
					+ viagem.getOrigem() + "\"></td>";
			umaViagem += "\t\t\t<td>Destino: <input class=\"input--register\" type=\"text\" name=\"email\" value=\""
					+ viagem.getDestino() + "\"></td>";
			umaViagem += "\t\t</tr>";
			umaViagem += "\t\t<tr>";
			umaViagem += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\"" + buttonLabel
					+ "\" class=\"input--main__style input--button\"></td>";
			umaViagem += "\t\t</tr>";
			umaViagem += "\t</table>";
			umaViagem += "\t</form>";
		} else if (tipo == FORM_DETAIL) {
			umaViagem += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umaViagem += "\t\t<tr>";
			umaViagem += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Cliente (ID "
					+ viagem.getId() + ")</b></font></td>";
			umaViagem += "\t\t</tr>";
			umaViagem += "\t\t<tr>";
			umaViagem += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umaViagem += "\t\t</tr>";
			umaViagem += "\t\t<tr>";
			umaViagem += "\t\t\t<td>&nbsp;Origem: " + viagem.getOrigem() + "</td>";
			umaViagem += "\t\t\t<td>Destino: " + viagem.getDestino() + "</td>";
			umaViagem += "\t\t</tr>";
			umaViagem += "\t\t<tr>";
			umaViagem += "\t\t\t<td>&nbsp;</td>";
			umaViagem += "\t\t</tr>";
			umaViagem += "\t</table>";
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UMA-VIAGEM>", umaViagem);

		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Viagens</b></font></td></tr>\n"
				+
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
				"\n<tr>\n" +
				"\t<td><a href=\"/viagem/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
				"\t<td><a href=\"/viagem/list/" + FORM_ORDERBY_ORIGEM + "\"><b>Origen</b></a></td>\n" +
				"\t<td><a href=\"/viagem/list/" + FORM_ORDERBY_DESTINO + "\"><b>Destino</b></a></td>\n" +
				"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
				"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
				"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
				"</tr>\n";

		List<Viagem> viagens;
		if (orderBy == FORM_ORDERBY_ID) {
			viagens = viagemDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_ORIGEM) {
			viagens = viagemDAO.getOrderByOrigem();
		} else if (orderBy == FORM_ORDERBY_DESTINO) {
			viagens = viagemDAO.getOrderByDestino();
		} else {
			viagens = viagemDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Viagem p : viagens)
		{
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\"" + bgcolor + "\">\n" +
					"\t<td>" + p.getId() + "</td>\n" +
					"\t<td>" + p.getOrigem() + "</td>\n" +
					"\t<td>" + p.getDestino() + "</td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"/viagem/" + p.getId()
					+ "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"/viagem/update/" + p.getId()
					+ "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteCliente('" + p.getId()
					+ "', '" + p.getOrigem() + "', '" + p.getDestino()
					+ "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"</tr>\n";
		}
		list += "</table>";
		form = form.replaceFirst("<LISTAR-VIAGEM>", list);
	}

	public Object insert(Request request, Response response) {
		String origem = request.queryParams("origem");
		String destino = request.queryParams("destino");

		String resp = "";

		Viagem viagem = new Viagem(-1 , origem , destino , null , null);

		if (viagemDAO.insert(viagem) == true) {
			resp = "Viagem (" + origem + ") inserido!";
			response.status(201); // 201 Created
		} else {
			resp = "Viagem (" + origem + ") não inserido!";
			response.status(404); // 404 Not found
		}

		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
				"<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Viagem viagem = (Viagem) viagemDAO.get(id);

		if (viagem != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, viagem, FORM_ORDERBY_ORIGEM);
		} else {
			response.status(404); // 404 Not found
			String resp = "Viagem " + id + " não encontrada.";
			makeForm();
			form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
					"<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
		}

		return form;
	}

	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Viagem viagem = (Viagem) viagemDAO.get(id);

		if (viagem != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, viagem, FORM_ORDERBY_DESTINO);
		} else {
			response.status(404); // 404 Not found
			String resp = "Viagem " + id + " não encontrada.";
			makeForm();
			form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
					"<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
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
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
				"<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
	}

	public Object delete(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Viagem viagem = viagemDAO.get(id);
		String resp = "";

		if (viagem != null) {
			viagemDAO.delete(id);
			response.status(200); // success
			resp = "VIAGEM (" + id + ") EXCLUIDA!";
		} else {
			response.status(404); // 404 Not found
			resp = "VIAGEM (" + id + ") não encontrada!";
		}
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
				"<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
	}
}