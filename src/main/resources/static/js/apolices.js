var selectApolice = null;

function apagarApoliceFormulario() {
    $('#apolices-form')[0].reset();
}

function novaApolice() {
    selectApolice = null;
    apagarApoliceFormulario()
    $('#nif_lista').empty();
    $('#perfil_risco_lista').empty();
    $("#numero_apolice_div").hide();

    fetch('/api/users/')
            .then(function (resp) {
                return resp.json();
            })
            .then(function (data) {
                $('#nif_lista').append(new Option('', '')).prop('selected', true);
                $.each(data, function (index, value) {
                    $('#nif_lista').append(new Option(value.nif + ' - ' + value.nome, value.nif));
                })
            })
    fetch('/api/perfis_risco/')
            .then(function (resp) {
                return resp.json();
            })
            .then(function (data) {
                $('#perfil_risco_lista').append(new Option('', '')).prop('selected', true);
                $.each(data, function (index, value) {
                    $('#perfil_risco_lista').append(new Option(value.perfil_risco, value.perfil_risco));
                })
            })
    $('#nif_lista').prop('disabled', false);
    $('#perfil_risco_lista').prop('disabled', false);
    $("#matricula").prop("readOnly", false);
    $("#data_subscricao").prop("readOnly", false);
    $('#apolice-remover-botao').hide();
    $('#apolice-dlg .modal-title').text("Nova Apólice");
    $('#apolice-dlg').modal();
}

function mostrarApolice(apolice) {
    selectApolice = apolice;
    apagarApoliceFormulario()
    $('#nif_lista').empty();
    $('#perfil_risco_lista').empty();
    $("#numero_apolice_div").show();
    fetch('/api/apolices/' + apolice)
            .then(function (resp) {
                return resp.json();
            })
            .then(function (data) {
                $("#numero_apolice").val(data.numero_apolice);
                $("#numero_apolice").prop("readOnly", true);
                $('#nif_lista').append(new Option(data.nif, data.nif));
                $('#nif_lista').prop('disabled', 'disabled');
                $('#perfil_risco_lista').append(new Option(data.perfilRisco, data.perfilRisco));
                $('#perfil_risco_lista').prop('disabled', 'disabled');
                $("#seguradora").val(data.seguradora);
                $("#matricula").val(data.matricula);
                $("#matricula").prop("readOnly", true);
                $("#data_subscricao").val(data.dataSubscricao);
                $("#data_subscricao").prop("readOnly", true);
                $("#data_termino").val(data.dataTermino);
                $("#premio").val(data.premio);
                if (data.estado == "ativa") {
                    $("#estado_ativa").prop('checked', true);
                } else {
                    $("#estado_desativa").prop('checked', true);
                }
                $('#apolice-remover-botao').show();
                $('#apolice-dlg .modal-title').text("Editar Apólice");
                $("#apolice-dlg").modal();
            });
}

function listarApolices() {

    fetch('/api/apolices/')
            .then(function (resp) {
                return resp.json();
            })
            .then(function (data) {
                var table = $('#apolices-table').find('tbody')[0];
                table.innerHTML = "";
                $.each(data, function (index, value) {
                    var newRow = table.insertRow(table.rows.length);
                    newRow.insertCell(0).appendChild(document.createTextNode(value.numero_apolice));
                    newRow.insertCell(1).appendChild(document.createTextNode(value.nif));
                    newRow.insertCell(2).appendChild(document.createTextNode(value.perfilRisco));
                    newRow.insertCell(3).appendChild(document.createTextNode(value.seguradora));
                    newRow.insertCell(4).appendChild(document.createTextNode(value.matricula));
                    newRow.insertCell(5).appendChild(document.createTextNode(value.dataSubscricao));
                    newRow.insertCell(6).appendChild(document.createTextNode(value.dataTermino));
                    newRow.insertCell(7).appendChild(document.createTextNode(value.estado));
                    newRow.insertCell(8).appendChild(document.createTextNode(value.premio));
                });
            })
}
;

function guardarApolice() {
    let url = '/api/apolices/';
    let method = 'post';
    let apoliceData = {
        nif: $("#nif_lista").val(),
        perfilRisco: $("#perfil_risco_lista").val(),
        seguradora: $("#seguradora").val(),
        matricula: $("#matricula").val(),
        dataSubscricao: $("#data_subscricao").val(),
        dataTermino: $("#data_termino").val(),
        premio: $("#premio").val(),
        estado:  $("input[name='estado']:checked").val(),
    }

    if (selectApolice) {
        url = url + selectApolice;
        method = 'put';
    } else {
        apoliceData['numero_apolice'] = $("#numero_apolice").val()
    }

    fetch(url, {
        method: method,
        body: JSON.stringify(apoliceData),
        headers: {
            'Content-Type': 'application/json'
        }
    })
            .then(function (resp) {
                if (resp.ok) {
                    listarApolices();
                    $("#apolice-dlg").modal('hide');
                    return;
                } else {
                    return resp.json();
                }
            })
            /*.then(function (data) {
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

function removerApolice() {
    $.confirm({
        title: 'Remover Apolice',
        content: `Tem a certeza que quer remover a apolice '${selectApolice}' ?`,
        icon: 'fa fa-question',
        theme: 'bootstrap',
        closeIcon: true,
        animation: 'scale',
        type: 'red',
        buttons: {
            Confirm: function () {
                fetch(`api/apolices/${selectApolice}`, {
                    method: 'delete'
                })
                        .then(function () {
                            listarApolices();
                            $('#apolice-dlg').modal('hide');
                        })
            },
            Cancel: function () {}
        }
    });
}

$(document).ready(function () {

    listarApolices();

    $('#refresh-botao').click(function () {
        listarApolices();
    });

    $('#apolice-nova-botao').click(function () {
        novaApolice();
    });

    $('#apolice-guardar-botao').click(function () {
        guardarApolice();
    });

    $('#apolices-table').delegate('tr td:first-child', 'click', function () {
        var numero_apolice = $(this).text();
        mostrarApolice(numero_apolice)
    });

    $('#apolice-remover-botao').click(function () {
        removerApolice();
    });

});