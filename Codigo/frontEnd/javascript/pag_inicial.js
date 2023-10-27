//REDIRECIONAMENTO PARA CLICKBUS
function removeAccents(str) {
            return str.normalize("NFD").replace(/[\u0300-\u036f]/g, "");
        }

        document.getElementById("buscarRotas").addEventListener("click", function() {
            var origin = removeAccents(document.getElementById("origin").value.toLowerCase().replace(/ /g, '-'));
            var destination = removeAccents(document.getElementById("destination").value.toLowerCase().replace(/ /g, '-'));
            var departureDate = document.getElementById("departureDate").value;
            var returnDate = document.getElementById("returnDate").value;
            
            // Verifica se a cidade de destino é "São Paulo - SP" e adiciona "todos" se necessário.
            if (destination === "sao-paulo-sp") {
                destination = "sao-paulo-sp-todos";
            }
            
            var searchUrl = "https://www.clickbus.com.br/onibus/" + origin + "/" + destination + "?departureDate=" + departureDate + "&returnDate=" + returnDate;
            window.location.href = searchUrl;
        });

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