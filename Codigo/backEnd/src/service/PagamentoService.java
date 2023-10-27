package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.PagamentoDAO;
import model.Pagamento;
import spark.Request;
import spark.Response;

public class PagamentoService {

	private PagamentoDAO pagamentoDAO = new PagamentoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_ID = 1;
	private final int FORM_ORDERBY_NOMECARTAO = 2;
	private final int FORM_ORDERBY_TIPOPLANO = 3;

	public PagamentoService() {
		makeForm();
	}

	public void makeForm() {
		makeForm(FORM_INSERT, new Pagamento(), FORM_ORDERBY_NOMECARTAO);
	}

	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Pagamento(), orderBy);
	}

	public void makeForm(int tipo, Pagamento pagamento, int orderBy) {
		String nomeArquivo = "telaCompra.html";
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

		String umPagamento = "";
		if (tipo != FORM_INSERT) {
			umPagamento += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPagamento += "\t\t<tr>";
			umPagamento += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/pagamento/list/1\">Novo Pagamento</a></b></font></td>";
			umPagamento += "\t\t</tr>";
			umPagamento += "\t</table>";
			umPagamento += "\t<br>";
		}

		if (tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/pagamento/";
			String name, buttonLabel;
			if (tipo == FORM_INSERT) {
				action += "insert";
				name = "Inserir Pagamento";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + pagamento.getId();
				name = "Atualizar Pagamento (ID " + pagamento.getId() + ")";
				buttonLabel = "Atualizar";
			}
			umPagamento += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umPagamento += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPagamento += "\t\t<tr>";
			umPagamento += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name
					+ "</b></font></td>";
			umPagamento += "\t\t</tr>";
			umPagamento += "\t\t<tr>";
			umPagamento += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPagamento += "\t\t</tr>";
			umPagamento += "\t\t<tr>"; 
			umPagamento += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"nomeCartao\" value=\""
					+ pagamento.getNomeCartao() + "\"></td>";
			umPagamento += "\t\t\t<td>Telefone: <input class=\"input--register\" type=\"text\" name=\"numeroCartao\" value=\""
					+ pagamento.getNumeroCartao() + "\"></td>";
			umPagamento += "\t\t\t<td>Email: <input class=\"input--register\" type=\"text\" name=\"tipoPlano\" value=\""
					+ pagamento.getTipoPlano() + "\"></td>";
			umPagamento += "\t\t</tr>";
			umPagamento += "\t\t<tr>";
			umPagamento += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\"" + buttonLabel
					+ "\" class=\"input--main__style input--button\"></td>";
			umPagamento += "\t\t</tr>";
			umPagamento += "\t</table>";
			umPagamento += "\t</form>";
		} else if (tipo == FORM_DETAIL) {
			umPagamento += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umPagamento += "\t\t<tr>";
			umPagamento += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Pagamento (ID "
					+ pagamento.getId() + ")</b></font></td>";
			umPagamento += "\t\t</tr>";
			umPagamento += "\t\t<tr>";
			umPagamento += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umPagamento += "\t\t</tr>";
			umPagamento += "\t\t<tr>";
			umPagamento += "\t\t\t<td>&nbsp;Nome: " + pagamento.getNomeCartao() + "</td>";
			umPagamento += "\t\t\t<td>Telefone: " + pagamento.getNumeroCartao() + "</td>";
			umPagamento += "\t\t\t<td>Email: " + pagamento.getTipoPlano() + "</td>";
			umPagamento += "\t\t</tr>";
			umPagamento += "\t\t<tr>";
			umPagamento += "\t\t\t<td>&nbsp;</td>";
			umPagamento += "\t\t</tr>";
			umPagamento += "\t</table>";
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-Pagamento>", umPagamento);

		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Pagamentos</b></font></td></tr>\n"
				+
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
				"\n<tr>\n" +
				"\t<td><a href=\"/pagamento/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
				"\t<td><a href=\"/pagamento/list/" + FORM_ORDERBY_NOMECARTAO + "\"><b>Nome</b></a></td>\n" +
				"\t<td><a href=\"/pagamento/list/" + FORM_ORDERBY_TIPOPLANO + "\"><b>Email</b></a></td>\n" +
				"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
				"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
				"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
				"</tr>\n";

		List<Pagamento> pagamentos;
		if (orderBy == FORM_ORDERBY_ID) {
			pagamentos = pagamentoDAO.getOrderByID();
		} else if (orderBy == FORM_ORDERBY_NOMECARTAO) {
			pagamentos = pagamentoDAO.getOrderByNomeCartao();
		} else if (orderBy == FORM_ORDERBY_TIPOPLANO) {
			pagamentos = pagamentoDAO.getOrderByTipoPlano();
		} else {
			pagamentos = pagamentoDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Pagamento p : pagamentos) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\"" + bgcolor + "\">\n" +
					"\t<td>" + p.getId() + "</td>\n" +
					"\t<td>" + p.getNomeCartao() + "</td>\n" +
					"\t<td>" + p.getTipoPlano() + "</td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"/pagamento/" + p.getId()
					+ "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"/pagamento/update/" + p.getId()
					+ "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeletePagamento('" + p.getId()
					+ "', '" + p.getNomeCartao() + "', '" + p.getTipoPlano()
					+ "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
					"</tr>\n";
		}
		list += "</table>";
		form = form.replaceFirst("<LISTAR-PAGAMENTO>", list);
	}

	public Object insert(Request request, Response response) {
		String nomeCartao = request.queryParams("nomeCartao");
		String numeroCartao = request.queryParams("numeroCartao");
		int cvvCartao = Integer.parseInt(request.queryParams("cvvCartao"));
		String tipoPlano = request.queryParams("tipoPlano");
		String dataValidadeCartao = request.queryParams("dataValidadeCartao");

		String resp = "";

		Pagamento pagamento = new Pagamento(-1, nomeCartao, numeroCartao, dataValidadeCartao, cvvCartao, tipoPlano);

		if (pagamentoDAO.insert(pagamento) == true) {
			resp = "Pagamento (" + nomeCartao + ") inserido!";
			response.status(201); // 201 Created
		} else {
			resp = "Pagamento (" + nomeCartao + ") não inserido!";
			response.status(404); // 404 Not found
		}

		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
				"<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
	}

	public Object get(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Pagamento pagamento = (Pagamento) pagamentoDAO.get(id);

		if (pagamento != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, pagamento, FORM_ORDERBY_NOMECARTAO);
		} else {
			response.status(404); // 404 Not found
			String resp = "Pagamento " + id + " não encontrado.";
			makeForm();
			form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
					"<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
		}

		return form;
	}

	public Object getToUpdate(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Pagamento pagamento = (Pagamento) pagamentoDAO.get(id);

		if (pagamento != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, pagamento, FORM_ORDERBY_NOMECARTAO);
		} else {
			response.status(404); // 404 Not found
			String resp = "Pagamento " + id + " não encontrado.";
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
		Pagamento pagamento = (Pagamento) pagamentoDAO.get(id);
		String resp = "";

		if (pagamento != null) {
			pagamento.setNomeCartao(request.queryParams("nomeCartao"));
			pagamento.setNumeroCartao(request.queryParams("numeroCartao"));
			pagamento.setCvvCartao(Integer.parseInt(request.queryParams("cvvCartao")));
			pagamento.setDataValidadeCartao(request.queryParams("dataValidadeCartao"));
			pagamento.setTipoPlano(request.queryParams("tipoPlano"));

			pagamentoDAO.update(pagamento);
			response.status(200); // success
			resp = "Pagamento (ID " + pagamento.getId() + ") atualizado!";
		} else {
			response.status(404); // 404 Not found
			resp = "Pagamento (ID \" + pagamento.getId() + \") não encontrado!";
		}
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
				"<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
	}

	public Object delete(Request request, Response response) {
		int id = Integer.parseInt(request.params(":id"));
		Pagamento pagamento = pagamentoDAO.get(id);
		String resp = "";

		if (pagamento != null) {
			pagamentoDAO.delete(id);
			response.status(200); // success
			resp = "Pagamento (" + id + ") excluído!";
		} else {
			response.status(404); // 404 Not found
			resp = "Pagamento (" + id + ") não encontrado!";
		}
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">",
				"<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
	}
}