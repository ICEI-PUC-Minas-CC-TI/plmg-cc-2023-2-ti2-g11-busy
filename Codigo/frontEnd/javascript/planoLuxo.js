// Função para gerar um código alfanumérico de dez caracteres
function gerarCodigo() {
  const caracteres = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
  let codigo = '';

  for (let i = 0; i < 10; i++) {
    const indiceAleatorio = Math.floor(Math.random() * caracteres.length);
    codigo += caracteres.charAt(indiceAleatorio);
  }

  // Verifique se já se passaram quatro dias desde a última geração
  const dataUltimaGeracao = localStorage.getItem("dataUltimaGeracao");
  if (!dataUltimaGeracao || (new Date() - new Date(dataUltimaGeracao)) >= 4 * 24 * 60 * 60 * 1000) {
    // Se já se passaram quatro dias, permita a geração do novo código
    localStorage.setItem("dataUltimaGeracao", new Date());
    document.getElementById("codigoGerado").textContent = "Código gerado: " + codigo;
  } else {
    // Caso contrário, exiba uma mensagem informando que é necessário esperar
    document.getElementById("codigoGerado").textContent = "Espere quatro dias antes de gerar um novo código.";
  }
}

// Adiciona um evento de clique ao botão
document.getElementById("gerarCodigo").addEventListener("click", gerarCodigo); 




//BTN PRIORIDADE
const btnPrior = document.getElementById("btnPrioridade");

btnPrior.addEventListener('click', function() {

  window.location.href = 'prioridade.html';

}); 

//BTN MINHAS VIAGENS
const btnHistorico = document.getElementById("btnHistorico");

btnHistorico.addEventListener('click', function() {

  window.location.href = 'historico.html';

});

//BTN MILHAS E CASHBACKS
const btnMeC = document.getElementById("btnMeC");

btnMeC.addEventListener('click', function() {

  window.location.href = 'MilhasCashback.html';

}); 



///////////////////////////////////////////////////////////////////////////////////////////////////////////
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