function buscarLogin() {
  var email = $("#idEmail").val();
  var senha = $("#idSenha").val();

  // Realizar requisição AJAX para obter os dados do cliente
  $.ajax({
    url: "/cliente/" + email,
    type: "POST",  // Alterado para POST
    contentType: "application/json; charset=utf-8",  // Adicionado tipo de conteúdo
    data: JSON.stringify({ senha: senha }),  // Passa a senha como JSON no corpo da requisição
    success: function (resultado) {
      var parsedResult = JSON.parse(resultado);

      if (parsedResult.success) {
        // Redirecionar para meuPlano.html se o resultado for true
        window.location.href = "planoLuxo.html";
      } else {
        // Exibir pop-up com a mensagem de erro do servidor
        exibirPopup("Erro: " + parsedResult.message);
      }
    },
    error: function (xhr, status, error) {
      console.error("Erro na requisição:", status, error);
      exibirPopup("Erro na requisição. Tente novamente mais tarde.");
    }
  });
}

function exibirPopup(mensagem) {
  // Exibir pop-up com a mensagem de erro
  alert(mensagem);
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////
//BTN LOGIN
const btnLogin = document.getElementById("login");

btnLogin.addEventListener('click', function() {

  window.location.href = 'login.html';

});

//BTN CADASTRO
const btnCadastro = document.getElementById("cadastro");

btnCadastro.addEventListener('click', function() {

  window.location.href = 'cadastreSe.html';

});

//BTN QUEM SOMOS
const btnQuemSomos = document.getElementById("quem_somos");

btnQuemSomos.addEventListener('click', function() {

  window.location.href = 'quemSomos.html';

});

//BTN MEU PLANO
const btnMeuPlano = document.getElementById("meu_plano");

btnMeuPlano.addEventListener('click', function() {

  window.location.href = 'planoStandard.html';                 ////////////DEPENDE DO PLANO DA PESSOA///////////////////

});

//BTN MENU DE OPCOES
const btnMenu = document.getElementById("menu_opcoes");

btnMenu.addEventListener('click', function() {

  window.location.href = 'index.html';

});

//BTN NOSSOS PLANOS
const btnPlanos = document.getElementById("nossosPlanos");

btnPlanos.addEventListener('click', function() {

  window.location.href = 'nossosPlanos.html';

});

//BTN POLITICAS
const btnPoliticas = document.getElementById("politicas");

btnPoliticas.addEventListener('click', function() {

  window.location.href = 'politicas.html';

});

//BTN INFOS GERAIS
const btnInfos = document.getElementById("infos");

btnInfos.addEventListener('click', function() {

  window.location.href = 'infoGerais.html';

});
