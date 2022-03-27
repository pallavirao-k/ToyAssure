
function getOrderUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orders";
}

function getPartyUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/parties";
}

function getProductUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/products";
}

function getInvoiceUrl() {
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/invoice";
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
            cuId = $("#inputCustomerId").val();
            cOID = $("#inputChannelOrderId").val();
            const data = {clientId:cId, customerId:cuId, channelOrderId:cOID, formList:results.data};
            var jsonData = JSON.stringify(data);
            addEmployee(jsonData);
    }
});
});

}
function addEmployee(jsonData) {
	//Set the values to update
	var url = getOrderUrl()+"/upload";

	$.ajax({
		url: url,
		type: 'POST',
		data: jsonData,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(data, textStatus, xhr) {
			$('#upload-order-modal').modal('toggle');
			$("#upload-order-form").trigger("reset");
			showSuccess("Success");
		},
		error: function(data) {

		    var response = JSON.parse(data.responseText);
			showError("Error: " + response.message);
		}
	});

	return false;
}

function searchOrder(event) {
	//Set the values to update
	var $form = $("#order-search-form");
	var json = toJson($form);
	var url = getOrderUrl()+"/search";

	$.ajax({
		url: url,
		type: 'POST',
		data: json,
		headers: {
			'Content-Type': 'application/json'
		},
		success: function(data) {
			showSuccess("Success");
            displayEmployeeList(data);
		},
		error: function(data, textStatus, xhr) {
			showError("Error: " + data.responseText);
		}
	});

	return false;
}

function displayEmployeeList(data) {
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	for (var i in data) {
		var e = data[i];
		var buttonHtml = ' <button type="button" class="btn btn-outline-info border-0" data-toggle="tooltip" title="More Info" onclick="getOrderItems(' + e.id + ')">More Info</button>';
		if(e.orderStatus === "CREATED"){
		    buttonHtml+= ' <button type="button" class="btn btn-outline-secondary border-0" data-toggle="tooltip" title="Allocate" onclick="allocateOrder(' + e.id + ')">Allocate</button>';
		}
		else if(e.orderStatus === "ALLOCATED"){
		    buttonHtml+= ' <button type="button" class="btn btn-outline-primary border-0" data-toggle="tooltip" title="Generate Invoice" onclick="getInvoice(' + e.id + ')">Generate Invoice</button>';
		}
		else{
		    buttonHtml+= ' <button type="button" class="btn btn-outline-primary border-0" data-toggle="tooltip" title="Get Invoice" onclick="getInvoice(' + e.id + ')">Get Invoice</button>';
		}

		var row = '<tr>'
			+ '<td>' + e.clientId + '</td>'
			+ '<td>' + e.customerId + '</td>'
			+ '<td>' + e.channelId + '</td>'
			+ '<td>' + e.channelOrderId + '</td>'
			+ '<td>' + buttonHtml + '</td>'
			+ '</tr>';
		$tbody.append(row);

	}
}

function getOrderItems(id){
    	var url = getOrderUrl()+"/order-items/"+id;
    	$.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	        displayOrderItemList(data);
    	   		     //...
    	   },
    	   error: function(data){
    	   		 var response = JSON.parse(data.responseText);
                 showError("Error: " + response.message);
    	   }
    	});
    }


function getInvoice(id){
    var url = getInvoiceUrl()+"?orderId="+id;
        	$.ajax({
        	   url: url,
        	   type: 'GET',
        	   success: function(data) {
        	        searchOrder();
        	   		     //...
        	   },
        	   error: function(data){
        	   		 var response = JSON.parse(data.responseText);
                     showError("Error: " + response.message);
        	   }
        	});
        }



function displayOrderItemList(data){
	//console.log('Printing orderitem data');
	var $tbody = $('#order-item-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var url = getProductUrl()+"/"+e.globalSkuId;
              $.ajax({
              url: url,
              type: 'GET',
              success: function(successData) {
                    var row = '<tr>'
                    		+ '<td>' + successData.productName + '</td>'
                    		+ '<td>' + successData.brandId + '</td>'
                    		+ '<td>' + e.orderedQty + '</td>'
                    		+ '<td>' + e.fulfilledQty + '</td>'
                    		+ '<td>' + e.sellingPricePerUnit + '</td>'
                    		+ '<td>' + e.sellingPricePerUnit*e.fulfilledQty + '</td>'
                    		+ '</tr>';
                            $tbody.append(row);

              },
              error: function(errorData){
                var response = JSON.parse(errorData.responseText);
                 showError("Error: " + response.message);
                 }
             });
	}
	var span = document.getElementById("spanB");
    span.innerHTML = "Order ID: " + data[0].orderId;
	$('#order-item-modal').modal('toggle');
}

function allocateOrder(id){
        var url = getOrderUrl()+"/allocate?id="+id;
    	$.ajax({
    	   url: url,
    	   type: 'PUT',
    	   success: function(data) {
    	        searchOrder();
    	   		     //...
    	   },
    	   error: function(data){
    	   		 var response = JSON.parse(data.responseText);
                 showError("Error: " + response.message);
    	   }
    	});
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




function init(){
    	csv2Json();
    	$('#search-orders').click(searchOrder);

}
$(document).ready(init);
$(document).ready(show);
$(document).ready(dropdown1);
$(document).ready(dropdown2);

function show(){
	$("#datepicker1").datepicker({
  format: 'yyyy-mm-dd',
  maxDate: '0' // change format here

  });
  $("#datepicker2").datepicker({
  format: 'yyyy-mm-dd',
  maxDate: '0'// change format here

  });
}

function dropdown1(){

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

function dropdown2(){

	var url = getPartyUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
		var select = document.getElementById('inputCustomerId');
		for(var i in data){
		var e = data[i];
		if(e.partyType === 'CUSTOMER'){
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