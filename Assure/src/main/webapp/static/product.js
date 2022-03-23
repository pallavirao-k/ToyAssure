
function getEmployeeUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return "http://localhost:9090/Assure/api/products";
}

function getPartyUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return "http://localhost:9090/Assure/api/parties";
}

function csv2Json(){
const uploadconfirm = document.getElementById("process-data").
addEventListener('click', () => {
Papa.parse(document.getElementById('employeeFile').files[0],
{
    download:true,
    header:true,
    skipEmptyLines: true,
    complete: function(results){
            cId = $("#inputClientId").val();
            const data = {clientId:cId, formList:results.data};
            console.log(data);
            var jsonData = JSON.stringify(data);
            addEmployee(jsonData);
    }
});
});

}

function addEmployee(jsonData) {
	//Set the values to update
	var url = getEmployeeUrl();

	$.ajax({
		url: url,
		type: 'POST',
		data: jsonData,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(data, textStatus, xhr) {
			$('#brand-modal').modal('toggle');
			$("#product-form").trigger("reset");
			showSuccess("Product added");
			getEmployeeList();     //...
		},
		error: function(data) {
		    var response = JSON.parse(data.responseText);
			showError("Error: " + response.message);
		}
	});

	return false;
}

function updateEmployee(event) {
	$('#edit-product-modal').modal('toggle');
	//Get the ID
	var id = $("#product-edit-form input[name=globalSkuId]").val();
	var url = getEmployeeUrl() + "?id=" + id;

	//Set the values to update
	var $form = $("#product-edit-form");
	var json = toJson($form);

	$.ajax({
		url: url,
		type: 'PUT',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(data, textStatus, xhr) {
			showSuccess("Product updated");
			getEmployeeList();     //...
		},
		error: function(data) {
		    var response = JSON.parse(data.responseText);
			showError("Error: " + response.message);
		}
	});

	return false;
}


function getEmployeeList() {
	var url = getEmployeeUrl();
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
			//console.log("Employee data fetched");
			//console.log(data);
			displayEmployeeList(data);     //...
		},
		error: function(data) {
			showError("Error: "+data.responseText);
		}
	});
}

function displayEmployeeList(data) {
	//console.log('Printing employee data');
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	var c = 1;
	for (var i in data) {
		var e = data[i];
        var buttonHtml = ' <button type="button" class="btn btn-outline-primary border-0" data-toggle="tooltip" title="Edit" onclick="displayEditEmployee(' + e.globalSkuId + ')"><i class="bi bi-pencil-fill"></i></button>';
		var row = '<tr>'
			+ '<td>' + c + '</td>'
			+ '<td>' + e.productName + '</td>'
			+ '<td>' + e.clientId + '</td>'
			+ '<td>' + e.clientSkuId + '</td>'
			+ '<td>' + e.brandId + '</td>'
			+ '<td>' + e.productMrp + '</td>'
			+ '<td>' + e.description + '</td>'
			+ '<td>' + buttonHtml + '</td>'
			+ '</tr>';
		$tbody.append(row);
		c++
	}
}

function displayEditEmployee(id) {
	var url = getEmployeeUrl() + "/" + id;
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
			//console.log("Employee data fetched");
			//console.log(data);
			displayEmployee(data);     //...
		},
		error: function() {
			showError("An error has occurred");
		}
	});
}

function displayEmployee(data) {
	$("#product-edit-form input[name=productName]").val(data.productName);
	$("#product-edit-form input[name=brandId]").val(data.brandId);
	$("#product-edit-form input[name=description]").val(data.description);
	$("#product-edit-form input[name=productMrp]").val(data.productMrp);
	$("#product-edit-form input[name=globalSkuId]").val(data.globalSkuId);
	$('#edit-product-modal').modal('toggle');
}


//HELPER METHOD
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


function init(){
    	csv2Json();
    	$('#update-product').click(updateEmployee);

}
$(document).ready(init);
$(document).ready(getEmployeeList);
$(document).ready(dropdown);

function dropdown(){

	var url = getPartyUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		var select = document.getElementById('inputClientId');
		for(var i in data){
		var e = data[i];
		if(e.partyType === 'CLIENT'){
		var opt = document.createElement('option');
		opt.value = e.partyId;
		opt.innerHTML = e.partyName;
		select.appendChild(opt);
		}
	}

	   },
	   error: function(){
	   		showError("An error has occurred");
	   }
	});


}