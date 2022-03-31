function getPartyUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content");
	return baseUrl + "/api/parties";
}

function addParty() {
	var $form = $("#party-form");
	var json = toJson($form);
	var url = getPartyUrl();

	$.ajax({
		url: url,
		type: 'POST',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(data) {
			$('#party-modal').modal('toggle');
			$("#party-form").trigger("reset");
			showSuccess("Success");
			getPartyList();     //...
		},
		error: function(data) {
			var response = JSON.parse(data.responseText);
             showError("Error: " + response.message);
		}
	});

}

function getPartyList() {
	var url = getPartyUrl();
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
			//console.log("Employee data fetched");
			//console.log(data);
			displayPartyList(data);     //...
		},
		error: function(data) {
			var response = JSON.parse(data.responseText);
            			showError("Error: " + response.message);
		}
	});
}

function displayPartyList(data) {
	//console.log('Printing employee data');
	var $tbody = $('#party-table').find('tbody');
	$tbody.empty();
	var c = 1;
	for (var i in data) {
	    var e = data[i];
	    if(e.partyType === 'CLIENT'){
		//console.log(e);
		var row = '<tr>'
			+ '<td>' + e.partyId + '</td>'
			+ '<td>' + e.partyName + '</td>'
			+ '</tr>';
		$tbody.append(row);
		c++;
	}
	}
}

function showError(msg) {
	$('#EpicToast').html('<div class="d-flex">'
		+ '<div class="toast-body">'
		+ '' + msg + ''
		+ ' </div>'
		+ '<button type="button" class="close btn-close btn-close-white me-2 m-auto" data-dismiss="toast" aria-label="Close"></button>'
		+ '</div>'

	);


	var option = {
		animation: true,
		autohide: false
	};
	var t = document.getElementById("EpicToast");
	var tElement = new bootstrap.Toast(t, option);
	tElement.show();

}

function showSuccess(msg) {

	$('#EpicToast1').html('<div class="d-flex">'
		+ '<div class="toast-body">'
		+ '' + msg + ''
		+ ' </div>'
		+ '<button type="button" class="close btn-close btn-close-white me-2 m-auto" data-dismiss="toast" aria-label="Close"></button>'
		+ '</div>'

	);


	var option = {
		animation: true,
		delay: 2000
	};
	var t = document.getElementById("EpicToast1");
	var tElement = new bootstrap.Toast(t, option);
	tElement.show();

}



function toJson($form) {
	var serialized = $form.serializeArray();
	//console.log(serialized);
	var s = '';
	var data = {};
	for (s in serialized) {
		data[serialized[s]['name']] = serialized[s]['value']
	}
	var json = JSON.stringify(data);
	//console.log(json);
	return json;
}



function csv2Json(){
var json;
const uploadconfirm = document.getElementById("process-data").
addEventListener('click', () => {
Papa.parse(document.getElementById('employeeFile').files[0],
{
    download:true,
    header:true,
    skipEmptyLines: true,
    complete: function(results){
        json = JSON.stringify(results.data);
    }
});
});
return json;

}

function addClientFormValidation(){
    var name = $('#inputPartyName').val();
    console.log(name.length);
    if(name.length>255){
    showError("Client Name length should not exceed 255");
    return false;
    }
    addParty();

}

function init(){
    	$('#add-party').click(addClientFormValidation);

}
$(document).ready(init);
$(document).ready(getPartyList);


