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
	
	var userTable = document.createElement("table");
	userTable.id = "userTable";
	userTable.className = "table table-striped table-bordered";
	userTable.setAttribute("cellspacing", 0);
	userTable.width = "100%";

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
	userTable.appendChild(thead);
	
	cardBody.appendChild(userTable);
	card.appendChild(cardBody);
	
	return card;
};
