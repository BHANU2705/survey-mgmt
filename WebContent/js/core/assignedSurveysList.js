function onLoadAssignedSurveyList() {
	$("#assignedSurveyList").hide();
	var parent = document.getElementById("assignedSurveyList");
	removeAllChild(parent);
	parent.appendChild(getAssignedSurveyList());
	$("#assignedSurveyList").show();
};

function getAssignedSurveyList() {
	var card = document.createElement('div');
	card.className = "card";

	var pDiv = document.createElement('div');
	pDiv.className = "card-header";
	
	var row = document.createElement('div');
	row.className = "row";
	
	var col1 = document.createElement('div');
	col1.className = "col";
	col1.style = "padding-top: 9px;";
	var h5 = document.createElement('h5');
	h5.innerHTML = "Assigned Surveys";
	col1.appendChild(h5);
	row.appendChild(col1);
	
	pDiv.appendChild(row);
	card.appendChild(pDiv);
	
	var cardBody = document.createElement('div');
	cardBody.className = "card-body";
	
	var assignedSurveyListTable = document.createElement("table");
	assignedSurveyListTable.id = "assignedSurveyListTable";
	assignedSurveyListTable.className = "table table-striped table-bordered";
	assignedSurveyListTable.setAttribute("cellspacing", 0);
	assignedSurveyListTable.width = "100%";

	var thead = document.createElement("thead");
	var thead_tr = document.createElement("tr");
	var thead_th1 = document.createElement("th");
	thead_th1.innerText = "#";
	var thead_th2 = document.createElement("th");
	thead_th2.innerText = "Survey Name";
	var thead_th3 = document.createElement("th");
	thead_th3.innerText = "Survey Owner";
	var thead_th4 = document.createElement("th");
	thead_th4.innerText = "Actions";
	
	thead_tr.appendChild(thead_th1);
	thead_tr.appendChild(thead_th2);
	thead_tr.appendChild(thead_th3);
	thead_tr.appendChild(thead_th4);
	thead.appendChild(thead_tr);
	assignedSurveyListTable.appendChild(thead);
	
	$.blockUI({ message: 'Fetching Assigned Survey List...' });
	var httpRequest = new XMLHttpRequest();
	var url = contextPath + "/csurvey";
	httpRequest.open('GET', url);
	httpRequest.setRequestHeader('Cache-Control', 'no-cache');
	httpRequest.onreadystatechange = function() {
		setAssignedSurveyData(assignedSurveyListTable, httpRequest);
	};
	httpRequest.send(null);
	
	cardBody.appendChild(assignedSurveyListTable);
	card.appendChild(cardBody);
	
	return card;
};

function setAssignedSurveyData(assignedSurveyListTable, httpRequest) {
	if (httpRequest.readyState === 4) {
    	$.unblockUI();
    	if (httpRequest.status === 200) {
        	var response = httpRequest.responseText;
        	var data = JSON.parse(response);
        	console.log(data);
    	}
	}
};
