var selectPedidoApolice = null;

var singleUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
//var singleFileUploadError = document.querySelector('#singleFileUploadError');
//var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');

function apagarPedidoApoliceFormulario() {
    $('#pedido-apolices-form')[0].reset();
}

function novoPedidoApolice() {
    selectPedidoApolice = null;
    apagarPedidoApoliceFormulario()
    $('#nif').empty();
    $('#matricula').empty();
    $("#nif").prop("readOnly", false);
    $("#matricula").prop("readOnly", false);
    $("#tipo_seguro").prop("readOnly", false);
    $("#data-div").hide();
    $("#detalhes").prop("readOnly", false);
    $("#proposta-guardar-botao").hide();
    $("#pedido-guardar-botao").show();
    $('#apolice-remover-botao').hide();
    $('#pedido-apolice-dlg .modal-title').text("Novo Pedido Apólice");
    $('#pedido-apolice-dlg').modal();

    fetch('/api/users/')
            .then(function (resp) {
                return resp.json();
            })
            .then(function (data) {
                $('#nif').append(new Option('', '')).prop('selected', true);
                $.each(data, function (index, value) {
                    $('#nif').append(new Option(value.nif + ' - ' + value.nome, value.nif));
                })
            })
} 

function mostrarPedidoApolice(pedidoApolice) {
    selectPedidoApolice = pedidoApolice;
    apagarPedidoApoliceFormulario();
    fetch('/api/pedidoapolices/' + pedidoApolice)
            .then(function (resp) {
                return resp.json();
            })
            .then(function (data) {
                $('#numero_pedido').val(data.numero_pedido);
                $("#nif").val(data.nif);
                $("#nif").prop("disabled", true);
                $("#matricula").val(data.matricula);
                $("#matricula").prop("readOnly", true);
                $("#tipo_seguro").val(data.tipo_seguro);
                $("#tipo_seguro").prop("readOnly", true);
                $("#data-div").show();
                $("#data_pedido").val(data.data_pedido);
                $("#data_pedido").prop("readOnly", true);
                $("#detalhes").val(data.detalhes);
                $("#detalhes").prop("readOnly", true);
                $("#pedido-guardar-botao").hide();
                $("#proposta-guardar-botao").show();
                $('#apolice-remover-botao').show();
                $('#pedido-apolice-dlg .modal-title').text("Consultar Pedido Apólice");
                $("#pedido-apolice-dlg").modal();
            });
}

function listarPedidoApolices() {
    fetch('/api/pedidoapolices/')
        .then(function (resp) {
            return resp.json();
        }) 
        .then(function (data) {
            var table = $('#pedido-apolices-table').find('tbody')[0];
            table.innerHTML = "";
                $.each(data, function (index, value) {
                    var newRow = table.insertRow(table.rows.length);
                    newRow.insertCell(0).appendChild(document.createTextNode(value.numero_pedido));
                    newRow.insertCell(1).appendChild(document.createTextNode(value.nif));
                    newRow.insertCell(2).appendChild(document.createTextNode(value.matricula));
                    newRow.insertCell(3).appendChild(document.createTextNode(value.tipo_seguro));
                    newRow.insertCell(4).appendChild(document.createTextNode(value.data_pedido));
                });
         });
}

function uploadSingleFile(file) {
    var formData = new FormData();
    formData.append("file", file);
    let url = '/uploadFile';
    let method = 'post';
    
    fetch(url, {
        method: method,
        body: formData 
    })
    .then(function(resp) {
        if(resp.ok) {
            fetch('/api/topfile/')
            .then(function(resp) {
                return resp.json();
            })
            .then(function(data) {
                var id = data.id;
                let url = '/api/propostaapolices/';
                let method = 'post';
                let propostaData = {
                    descricao: $("#detalhes").val(),
                    numero_pedido: $("#numero_pedido").val(),
                    id_ficheiro: id
                };

                fetch(url, {
                    method: method,
                    body: JSON.stringify(propostaData),
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                .then(function (resp) {
                    if (resp.ok) {
                        listarPedidoApolices();
                        return;
                    } else {
                        return resp.json();
                    }
                });
            });
        } else {
            return resp.json();
        }
    });
    /*
            .then(function (resp) {
        if (resp.ok) {
            singleFileUploadError.style.display = "none";
            singleFileUploadSuccess.innerHTML = "<p>File Uploaded Successfully.";
            singleFileUploadSuccess.style.display = "block";                    
            return;
                } else {
            singleFileUploadSuccess.style.display = "none";
            singleFileUploadError.innerHTML = (resp && resp.message) || "Some Error Occurred";                }
            }) */ 
}
    
function criarPedidoApolice() {
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
            listarPedidoApolices();   
            $("#pedido-apolice-dlg").modal('hide');
            return;
        } else {
            return resp.json();
       }
    })
    .then(function (data) {
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
    })
}

function removerPedidoApolice() {
    $.confirm({
        title: 'Remover Pedido de  Apolice',
        content: `Tem a certeza que quer remover o Pedido de Apolice '${selectPedidoApolice}' ?`,
        icon: 'fa fa-question',
        theme: 'bootstrap',
        closeIcon: true,
        animation: 'scale',
        type: 'red',
        buttons: {
            Confirm: function () {
                fetch(`api/pedidoapolices/${selectPedidoApolice}`, {
                    method: 'delete'
                })
                        .then(function () {
                            listarPedidoApolices();
                            $('#pedido-apolice-dlg').modal('hide');
                        })
            },
            Cancel: function () {}
        }
    });
}

$(document).ready(function () {

    listarPedidoApolices();
    
    $("#singleFileUploadButton").click(function() {
        var files = singleFileUploadInput.files;
        /*
        if(files.length === 0) {
        singleFileUploadError.innerHTML = "Please select a file";
        singleFileUploadError.style.display = "block";
        }
        */
        uploadSingleFile(files[0]);
        event.preventDefault();
    }); 
    
    
    $('#pedir-botao').click(function () {
        novoPedidoApolice();
    });
    
    $('#refresh-botao').click(function () {
        listarPedidoApolices();
    });
    
    $('#pedido-guardar-botao').click(function () {
        criarPedidoApolice();
    });
    
    $('#proposta-guardar-botao').click(function () {
        $("#pedido-apolice-dlg").modal('hide');
    });
    

    $('#pedido-apolices-table').delegate('tr td:first-child', 'click', function () {
        var numero_pedido = $(this).text();
        mostrarPedidoApolice(numero_pedido)
    });

    $('#pedido-remover-botao').click(function () {
        removerPedidoApolice();
    });

});