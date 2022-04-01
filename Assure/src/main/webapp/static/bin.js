function getBinUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/bins";
}

function getBinSkuUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/bins/skus";
}

function getPartyUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/parties";
}

function getEmployeeUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/products";
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
            if(results.data.length>5000){
                showError("Error: CSV rows must be less than 5000");
                return false;
                }
            cId = $("#inputClientId").val();
            const data = {clientId:cId, formList:results.data};
            var jsonData = JSON.stringify(data);
            addEmployee(jsonData);
    }
});
});

}

function addEmployee(jsonData) {
	//Set the values to update
	var url = getBinSkuUrl();

	$.ajax({
		url: url,
		type: 'POST',
		data: jsonData,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(data, textStatus, xhr) {
			$('#upload-bin-sku-modal').modal('toggle');
			$("#upload-bin-sku-form").trigger("reset");
			showSuccess("Success");
			getEmployeeList();     //...
		},
		error: function(data) {

		    var response = JSON.parse(data.responseText);
			showError(response.message);
		}
	});

	return false;
}

function addBin() {
	//Set the values to update
	var qty = $("#bin-form input[name=binCount]").val();
	var url = getBinUrl() + "?qty=" + qty;;

	$.ajax({
		url: url,
		type: 'POST',
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(data, textStatus, xhr) {
		    var headers = {
            				binId: 'binId'
            				};

            			var itemsFormatted = [];


            			data.forEach((item) => {
            				itemsFormatted.push({
            					binId: item.binId
            				});
            			});
            			var z = convertToCSV(data);
            			var today = new Date();
            			var date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
            			var time = today.getHours() + "-" + today.getMinutes() + "-" + today.getSeconds();
            			var dateTime = date + ' ' + time;
            			var fileTitle = 'Bins @' + dateTime;
            			exportCSVFile(headers, itemsFormatted, fileTitle);


			$('#upload-bin-modal').modal('toggle');
			$("#upload-bin-form").trigger("reset");
			showSuccess("Success");

		},
		error: function(data) {
		    var response = JSON.parse(data.responseText);
			showError(response.message);
		}
	});

	return false;
}


function updateEmployee(event) {
	$('#edit-bin-sku-modal').modal('toggle');
	//Get the ID
	var id = $("#bin-sku-edit-form input[name=id]").val();
	var url = getBinSkuUrl() + "?id=" + id;

	//Set the values to update
	var $form = $("#bin-sku-edit-form");
	var json = toJson($form);

	$.ajax({
		url: url,
		type: 'PUT',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(data, textStatus, xhr) {
			showSuccess("Success");
			getEmployeeList();     //...
		},
		error: function(data) {
		    var response = JSON.parse(data.responseText);
			showError(response.message);
		}
	});

	return false;
}

function getEmployeeList() {
    var bin = $("#inputBinIdSearch").val();
    var prod = $("#inputGlobalSkuIdSearch").val();
    const obj = {binId: bin, globalSkuId:prod};
    var json = JSON.stringify(obj);
	var url = getBinSkuUrl()+"/search";
	$.ajax({
		url: url,
		type: 'POST',
		data: json,
        headers: {
        	'Content-Type': 'application/json'
        },
		success: function(data) {
			displayEmployeeList(data);     //...
		},
		error: function(data) {
			var response = JSON.parse(data.responseText);
            showError(response.message);
		}
	});
}

function displayEmployeeList(data) {
    if(data.length>5){
    document.getElementById('footer').style.display = "";
    }
	var $tbody = $('#bin-sku-table').find('tbody');
	$tbody.empty();
	var c = 1;
	for (var i in data) {
		var e = data[i];
        var buttonHtml = ' <button type="button" class="btn btn-outline-primary border-0" data-toggle="tooltip" title="Edit" onclick="displayEditEmployee(' + e.id + ')"><i class="bi bi-pencil-fill"></i></button>';
		var row = '<tr>'
			+ '<td>' + e.binId + '</td>'
			+ '<td>' + e.globalSkuId + '</td>'
			+ '<td>' + e.qty + '</td>'
			+ '<td>' + buttonHtml + '</td>'
			+ '</tr>';
		$tbody.append(row);
		c++
	}
}

function displayEditEmployee(id) {
	var url = getBinSkuUrl() + "/" + id;
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
	$("#bin-sku-edit-form input[name=qty]").val(data.qty);
	$("#bin-sku-edit-form input[name=id]").val(data.id);
	$('#edit-bin-sku-modal').modal('toggle');
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
		+ '<div class="toast-body ">'
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


//convert to CSV

function convertToCSV(objArray) {
	var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
	var str = '';

	for (var i = 0; i < array.length; i++) {
		var line = '';
		for (var index in array[i]) {
			if (line != '') line += ','

			line += array[i][index];
		}

		str += line + '\r\n';
	}

	return str;
}
function exportCSVFile(headers, items, fileTitle) {
	if (headers) {
		items.unshift(headers);
	}

	// Convert Object to JSON
	var jsonObject = JSON.stringify(items);
	var csv = this.convertToCSV(jsonObject);

	var exportedFilenmae = fileTitle + '.csv' || 'export.csv';

	var blob = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
	if (navigator.msSaveBlob) { // IE 10+
		navigator.msSaveBlob(blob, exportedFilenmae);
	} else {
		var link = document.createElement("a");
		if (link.download !== undefined) { // feature detection
			// Browsers that support HTML5 download attribute
			var url = URL.createObjectURL(blob);
			link.setAttribute("href", url);
			link.setAttribute("download", exportedFilenmae);
			link.style.visibility = 'hidden';
			document.body.appendChild(link);
			link.click();
			document.body.removeChild(link);
		}
	}
}



function init(){
    	csv2Json();
    	$('#update-bin-sku').click(updateEmployee);
    	$('#add-bin').click(addBin);
        $('#search').click(getEmployeeList);
}
$(document).ready(init);
$(document).ready(dropdown);
$(document).ready(dropdown1);
$(document).ready(dropdown2);

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

function dropdown1(){

	var url = getBinUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		var select = document.getElementById('inputBinIdSearch');
		for(var i in data){
		var e = data[i];
		var opt = document.createElement('option');
		opt.value = e.binId;
		opt.innerHTML = e.binId;
		select.appendChild(opt);
	}
	var opt = document.createElement('option');
        		opt.value = null;
        		opt.innerHTML = 'None';
        		select.appendChild(opt);

	   },
	   error: function(){
	   		showError("An error has occurred");
	   }
	});


}

function dropdown2(){

	var url = getEmployeeUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		var select = document.getElementById('inputGlobalSkuIdSearch');
		for(var i in data){
		var e = data[i];
		var opt = document.createElement('option');
		opt.value = e.globalSkuId;
		opt.innerHTML = e.globalSkuId;
		select.appendChild(opt);
	}
	var opt = document.createElement('option');
    		opt.value = null;
    		opt.innerHTML = "None";
    		select.appendChild(opt);



	   },
	   error: function(){
	   		showError("An error has occurred");
	   }
	});


}