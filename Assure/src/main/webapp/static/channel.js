function getChannelUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/channels";
}

function getChannelListingUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/channel-listings";
}

function getPartyUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/parties";
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
            channel = $("#inputChannelId").val();
            const data = {clientId:cId, channelId:channel, channelListings:results.data};
            var jsonData = JSON.stringify(data);
            addEmployee(jsonData);
    }
});
});

}

function addEmployee(jsonData) {
	//Set the values to update
	var url = getChannelListingUrl();

	$.ajax({
		url: url,
		type: 'POST',
		data: jsonData,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(data) {
		var headers = {
		                id: 'id'.replace(/,/g, ''),
        				channelId: 'channelId'.replace(/,/g, ''),
        				channelSkuId: "channelSkuId".replace(/,/g, ''),
        				clientId: 'clientId'.replace(/,/g, ''),
                        globalSkuId: 'globalSkuId'
        			};

        			var itemsFormatted = [];


        			data.forEach((item) => {
        				itemsFormatted.push({
        				    id: item.id.toString().replace(/,/g, ''),
        					channelId: item.channelId.toString().replace(/,/g, ''),
        					channelSkuId: item.channelSkuId.replace(/,/g, ''),
        					clientId: item.clientId.toString().replace(/,/g, ''),
        					globalSkuId: item.globalSkuId
        				});
        			});
        			var z = convertToCSV(data);
        			var date = new Date();
        			var fileTitle = 'Channel-Listings' + date + '.csv';
        			exportCSVFile(headers, itemsFormatted, fileTitle);

			$('#upload-channel-listing-modal').modal('toggle');
			$("#upload-channel-listing-form").trigger("reset");
			showSuccess("Channel Listings added");
		},
		error: function(data) {

		    var response = JSON.parse(data.responseText);
			showError("Error: " + response.message);
		}
	});

	return false;
}

function addChannel() {
	var $form = $("#channel-form");
    var json = toJson($form);

    var url = getChannelUrl();

	$.ajax({
		url: url,
		type: 'POST',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(data, textStatus, xhr) {

			$('#add-channel-modal').modal('toggle');
			$("#channel-form").trigger("reset");
			showSuccess("Channel added");
			getEmployeeList();

		},
		error: function(data) {
		    var response = JSON.parse(data.responseText);
			showError("Error: " + response.message);
		}
	});

	return false;
}

function getEmployeeList() {
	var url = getChannelUrl();
	$.ajax({
		url: url,
		type: 'GET',
		success: function(data) {
			displayEmployeeList(data);     //...
		},
		error: function(data) {
			var response = JSON.parse(data.responseText);
            showError("Error: " + response.message);
		}
	});
}

function displayEmployeeList(data) {
	var $tbody = $('#channel-table').find('tbody');
	$tbody.empty();
	var c = 1;
	for (var i in data) {
		var e = data[i];
		var row = '<tr>'
			+ '<td>' + c + '</td>'
			+ '<td>' + e.channelName + '</td>'
			+ '<td>' + e.invoiceType + '</td>'
			+ '</tr>';
		$tbody.append(row);
		c++
	}
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
//	console.log(jsonObject);
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
//	console.log("Done");
}




function init(){
    	csv2Json();
    	$('#add-channel').click(addChannel);

}
$(document).ready(init);
$(document).ready(getEmployeeList);
$(document).ready(dropdownClient);
$(document).ready(dropdownChannel);

function dropdownClient(){

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

function dropdownChannel(){

	var url = getChannelUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		var select = document.getElementById('inputChannelId');
		for(var i in data){
		var e = data[i];
		var opt = document.createElement('option');
		opt.value = e.id;
		opt.innerHTML = e.channelName;
		select.appendChild(opt);
	}

	   },
	   error: function(){
	   		showError("An error has occurred");
	   }
	});


}