
function novaApolice() {
    selectApolice = null;
    apagarApoliceFormulario();
    $('#nif_lista').empty();
    $('#perfil_risco_lista').empty();

    fetch('/api/users/')
            .then(function (resp) {
                return resp.json();
            })
            .then(function (data) {
                $('#nif_lista').append(new Option('por favor selecione...', '')).prop('selected', true);
                $.each(data, function (index, value) {
                    $('#nif_lista').append(new Option(value.nif + ' - ' + value.nome, value.nif));
                });
            });
    fetch('/api/perfis_risco/')
            .then(function (resp) {
                return resp.json();
            })
            .then(function (data) {
                $('#perfil_risco_lista').append(new Option('por favor selecione...', '')).prop('selected', true);
                $.each(data, function (index, value) {
                    $('#perfil_risco_lista').append(new Option(value.perfilRisco, value.perfilRisco));
                });
            });

    $('#numero_apolice').prop("readOnly", false);
    $('#nif_lista').prop('disabled', false);
    $('#perfil_risco_lista').prop('disabled', false);
    $("#matricula").prop("readOnly", false);
    $("#data_subscricao").prop("readOnly", false);
    $('#apolice-dlg .modal-title').text("Nova Apólice");
    $('#apolice-dlg').modal();
}

function novoImportar() {
    $('#apolice-dlg .modal-title').text("Importar .CCCSV");
    $('#importar-dlg').modal();
}

function mostrarApolice(apolice) {
    selectApolice = apolice;
    apagarApoliceFormulario();
    $('#nif_lista').empty();
    $('#perfil_risco_lista').empty();

    fetch('/api/apolices/' + apolice)
            .then(function (resp) {
                return resp.json();
            })
            .then(function (data) {
                $("#numero_apolice").val(data.n_apolice);
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
                if (data.estado === "ativa") {
                    $("#estado_ativa").prop('checked', true);
                } else {
                    $("#estado_desativa").prop('checked', true);
                }
                $('#apolice-dlg .modal-title').text("Editar Apólice");
                $("#apolice-dlg").modal();
            });

}

function listarApolices() {

    var table = $('#apolices-table').DataTable({
        "dom": 'Bfrtip',
        "buttons": [
            'csv', 'excel', 'pdf'
        ],
        "scrollX": true,
        "scrollY": true,
        "columnDefs": [
            {"width": "100%"},
            {
                "data": null,
                "defaultContent": "<button class='w3-btn fas fa-cogs table-icon details-control' style='font-size:25px;color:orange'></button>",
                "targets": -2,
                "orderable": false
            },
            {
                "data": null,
                "defaultContent": "<button class='w3-btn fas fa-trash table-icon remove-control' style='font-size:25px;color:orange'></button>",
                "targets": -1,
                "orderable": false,
            }
        ]
    });

    if ($('#tipoUtilizador').val() == "guest" || $('#tipoUtilizador').val() == "tomadorseguro") {
        table.column(8).visible(false);
        table.column(9).visible(false);
    }

    table.clear();
    if ($('#tipoUtilizador').val() == "guest" || $('#tipoUtilizador').val() == "tomadorseguro") {
        fetch('/api/apolices/utilizador/' + $('#nifuser').val())
                .then(function (resp) {
                    return resp.json();
                })
                .then(function (data) {
                    $.each(data, function (index, value) {
                        table.row.add([
                            value.n_apolice, value.nif, value.perfilRisco, value.seguradora, value.dataSubscricao, value.dataTermino, value.estado, value.premio]);
                    });
                    table.draw();
                });
    } else {
        fetch('/api/apolices/')
                .then(function (resp) {
                    return resp.json();
                })
                .then(function (data) {
                    $.each(data, function (index, value) {
                        table.row.add([
                            value.n_apolice, value.nif, value.perfilRisco, value.seguradora, value.dataSubscricao, value.dataTermino, value.estado, value.premio, , ]);
                    });
                    table.draw();
                });
    }
}

function guardarApolice() {
    let url = '/api/apolices/';
    let method = 'post';
    let apoliceData = {
        n_apolice: $("#numero_apolice").val(),
        nif: $("#nif_lista").val(),
        perfilRisco: $("#perfil_risco_lista").val(),
        seguradora: $("#seguradora").val(),
        matricula: $("#matricula").val(),
        dataSubscricao: $("#data_subscricao").val(),
        dataTermino: $("#data_termino").val(),
        premio: $("#premio").val(),
        estado: $("input[name='estado']:checked").val()
    };
    if (selectApolice) {
        url = url + selectApolice;
        method = 'put';
    } else {
        apoliceData['n_apolice'] = $("#numero_apolice").val();
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
                    fetch('/api/users/' + $("#nif_lista").val())
                            .then(function (resp) {
                                return resp.json();
                            })
                            .then(function (data) {
                                if (data.tipoUtilizador === "guest") {
                                    utilizadorData = {
                                        nif: data.nif,
                                        nome: data.nome,
                                        sexo: data.sexo,
                                        dataNascimento: data.dataNascimento,
                                        email: data.email,
                                        telemovel: data.telemovel,
                                        morada: data.morada,
                                        distrito: data.distrito,
                                        password: data.password,
                                        tipoUtilizador: "tomadorseguro"
                                    };
                                    fetch('api/users/' + $("#nif_lista").val(), {
                                        method: 'put',
                                        body: JSON.stringify(utilizadorData),
                                        headers: {
                                            'Content-Type': 'application/json'
                                        }
                                    });
                                }
                            });
                    $("#apolice-dlg").modal('hide');
                    location.reload();
                    return;
                } else {
                    return resp.json();
                }
            }).then(function (data) {
        Array.prototype.push.apply(keys, Object.keys(data));
        Array.prototype.push.apply(msg, Object.values(data));
        displayErrorMessage(keys, msg)
        keys = [];
        msg = [];
    });
}

function removerApolice(apolice) {
    $.confirm({
        title: 'Remover Apolice',
        content: `Tem a certeza que quer remover a apolice '${apolice}' ?`,
        icon: 'fa fa-question',
        theme: 'bootstrap',
        closeIcon: true,
        animation: 'scale',
        type: 'red',
        buttons: {
            Confirm: function () {
                fetch(`api/apolices/${apolice}`, {
                    method: 'delete'
                })
                        .then(function () {
                            $('#apolice-dlg').modal('hide');
                            location.reload();
                        });
            },
            Cancel: function () {}
        }
    });
}

function handleFiles(files) {
    // Check for the various File API support.
    if (window.FileReader) {
        // FileReader are supported.
        getAsText(files[0]);
    } else {
        alert('FileReader are not supported in this browser.');
    }
}

function getAsText(fileToRead) {
    var reader = new FileReader();
    // Handle errors load
    reader.onload = loadHandler;
    reader.onerror = errorHandler;
    // Read file into memory as UTF-8      
    reader.readAsText(fileToRead);
}

function loadHandler(event) {
    var csv = event.target.result;
    processDataAsObj(csv);
}

//if your csv file contains the column names as the first line
function processDataAsObj(csv) {
    var allTextLines = csv.split(/\r\n|\n/);
    var lines = [];

    //first line of csv
    var keys = allTextLines.shift().split(';');
    while (allTextLines.length - 1) {
        var arr = allTextLines.shift().replace(new RegExp("/", 'g'), '-').split(';');
        var obj = {};
        for (var i = 0; i < keys.length; i++) {
            obj[keys[i]] = arr[i];
        }
        lines.push(obj);
    }
    drawOutputAsObj(lines);
    $('#importar-guardar-botao').click(function () {
        importarApolice(lines);
    });
}
//lines e um array de objetos, em que cada objeto e uma apolice, cujos dados sao do tipo key:value"
function importarApolice(lines) {
    let url = '/api/apolices/';
    let method = 'post';

    $.each(lines, function (index, value) {
        var data_subscricao = value["data_subscricao"].split('-');
        var datasubscricao = new String(data_subscricao[2] + '-' + data_subscricao[1] + '-' + data_subscricao[0]);
        var data_termino = value["data_termino"].split('-');
        var datatermino = new String(data_termino[2] + '-' + data_termino[1] + '-' + data_termino[0]);
        let apoliceCsv = {
            n_apolice: value["n_apolice"],
            nif: value["nif"],
            perfilRisco: value["perfil_risco"],
            seguradora: value["seguradora"],
            matricula: value["matricula"],
            dataSubscricao: datasubscricao,
            dataTermino: datatermino,
            premio: value["premio"],
            estado: value["estado"]
        };
        fetch(url, {
            method: method,
            body: JSON.stringify(apoliceCsv),
            headers: {
                'Content-Type': 'application/json'
            }
        })
                .then(function (resp) {
                    if (resp.ok) {
                        $("#importar-dlg").modal('hide');
                        location.reload();
                        return;
                    } else {
                        return resp.json();
                    }
                });
    });
}

function errorHandler(evt) {
    if (evt.target.error.name == "NotReadableError") {
        alert("Canno't read file !");
    }
}

//draw the table, if first line contains heading
function drawOutputAsObj(lines) {
    //Clear previous data
    document.getElementById("output").innerHTML = "";
    var table = document.createElement("table");
    table.style = "width:750px";

    //for the table headings
    var tableHeader = table.insertRow(-1);
    Object.keys(lines[0]).forEach(function (key) {
        var el = document.createElement("TH");
        el.innerHTML = key;
        tableHeader.appendChild(el);
    });

    //the data
    for (var i = 0; i < lines.length; i++) {
        var row = table.insertRow(-1);
        Object.keys(lines[0]).forEach(function (key) {
            var data = row.insertCell(-1);
            data.appendChild(document.createTextNode(lines[i][key]));
        });
    }
    document.getElementById("output").appendChild(table);
}

$(document).ready(function () {
    listarApolices();

    $('#refresh-botao').click(function () {
        listarApolices();
    });

    $('#apolice-nova-botao').click(function () {
        novaApolice();
    });

    $('#importar-nova-botao').click(function () {
        novoImportar();
    });

    $('#apolice-guardar-botao').click(function () {
        event.preventDefault();
        guardarApolice();
    });

    $('#apolices-table tbody').on('click', '.details-control', function () {
        var currentRow = $(this).closest("tr");
        selectApolice = currentRow.find("td:eq(0)").text();
        mostrarApolice(selectApolice);
    });

    $('#apolices-table tbody').on('click', '.remove-control', function () {
        var currentRow = $(this).closest("tr");
        var apolice = currentRow.find("td:eq(0)").text();
        removerApolice(apolice);
    });

    /*   var options = {
     translation: {
     'A': {pattern: /[aA-zZ]/}
     }
     }; 
     
     $("#matricula").mask("00-AA-00" , options, {reverse:true}),
     
     $("#premio").mask("999990.00", {reverse: true}),
     
     $("#numero_apolice").mask("99999900", {reverse: true}),*/

    /*     $( "#apolices-form" ).validate( {
     
     rules: {
     numero_apolice: {  
     required: true,
     number: true,
     numeroPositivo: true,
     min:1,
     inteiro:true,        
     },
     nifPt: {  
     required: true,
     },
     perfil_risco: {  
     required: true,
     },
     seguradora: {  
     required: true,
     minlength: 4
     },
     matricula: {  
     required: true,
     matriculaValPt: true,
     },
     data_subscricao: {  
     required: true,
     },
     data_termino: {  
     required: true,
     },
     premio: {
     required: true,
     number: true,
     numeroPositivo:true,
     },
     estado: {
     required: true,
     },
     
     },
     messages: {
     numero_apolice: {
     required: "O número de apólice é um campo obrigatório.",
     number: "Por favor insira um número.",
     numeroPositivo: "Não são aceites números negativos",
     min: "Por favor insira um número inteiro diferente de 0.",
     inteiro: "Por favor insira um número inteiro",
     },
     nifPt: {
     required: "Por favor seleccione um Nif.",
     },
     perfil_risco: {  
     required: "Por favor selecione um perfil de risco.",
     },
     seguradora: {
     required: "O nome da seguradora é um campo obrigatório..",
     minlength: "O nome da seguradora deve conter no minimo 4 caracteres"
     },
     matricula: {
     required: "A matrícula é um campo obrigatório.",
     matriculaValPt: "A matrícula deve ser composta por 6 caracteres"
     },
     data_subscricao: {  
     required: "Por favor identifique a data de subscrição da apólice.",
     },
     data_termino: {  
     required: "Por favor identifique a data de termino da apólice",
     },
     premio: {
     required: "O valor do prémio é um campo obrigatório.",
     number: "Por favor insira um número.",
     numeroPositivo:"O prémio não pode ser negativo",
     },
     estado: {
     required: "Por favor seleccione estado da apolice.",
     },
     },
     
     errorElement: "em",
     errorPlacement: function ( error, element ) {
     // Add the `invalid-feedback` class to the error element
     error.addClass( "invalid-feedback" );
     
     if ( element.prop( "type" ) === "checkbox" ) {
     error.insertAfter( element.next( "label" ) );
     } else {
     error.insertAfter( element );
     }
     
     if ( element.is(":radio") ) 
     {
     error.appendTo( element.parents('.container') );
     }
     else 
     { // This is the default behavior 
     error.insertAfter( element );
     }
     
     },
     
     
     highlight: function ( element, errorClass, validClass ) {
     $( element ).addClass( "is-invalid" ).removeClass( "is-valid" );
     },
     unhighlight: function (element, errorClass, validClass) {
     $( element ).addClass( "is-valid" ).removeClass( "is-invalid" );
     },
     submitHandler: function(form){
     swal({
     title: "Bom trabalho!",
     text: "Formulário Submetido!",
     icon: "success",
     button: "Fechar",
     }) ;
     
     
     
     // alert("Formulário enviado com sucesso!");
     //   form.submit(); 
     }        
     
     }); */
});
