var selectApolice = null;

function limparCampos() {
    $('#novo_pedido')[0].reset();
}

function guardarPedidoApolice() {
    let url = '/api/pedidoapolices/'; ///necessário retificar se caso
    let method = 'post';
    let today = new Date();
    var dd = today.getDate();
    var mm = today.getMonth()+1; //January is 0!
    var yyyy = today.getFullYear();
    
    if(dd<10) {
      dd = '0'+dd
    } 

    if(mm<10) {
      mm = '0'+mm
    } 

    today = yyyy + '/' + mm + '/' + dd;

    let pedidoData = {
        nif: $("#nif").val(),
        matricula: $("#matricula").val(),
        tipo_seguro: $("#tipo_seguro").val(),
        detalhes: $("#detalhes").val(),
        data_pedido: today
    };

    // pedidoData['numero_apolice'] = $("#numero_apolice").val()

    fetch(url, {
        method: method,
        body: JSON.stringify(pedidoData),
        headers: {
            'Content-Type': 'application/json'
        }
    })
    .then(function (resp) {
        if (resp.ok) {
            limparCampos() ;
            return;
        } else {
            return resp.json();
       }
    })
    /*then(function (data) {
        if (typeof data.error !== "undefined") {
            $.alert({
            title: 'Dados inválidos!',
            content: data.error.data[0].msg,
            icon: 'fa fa-exclamation',
            theme: 'bootstrap',
            closeIcon: true,
            animation: 'scale',
            type: 'red'
            });
        }
    })*/
}


$(document).ready(function () {

    $('#refresh-botao').click(function () {
        limparCampos();
    });

    $('#guardar-apolice-botao').click(function () {
        guardarPedidoApolice();
    });

});