function onLoadSurvey() {
	var createSurveyPage = document.getElementById("createSurveyPage");
	removeAllChild(createSurveyPage);
	$("#createSurveyPage").hide();
	$("#subscriptionInfo").show();
	var surveyAllPage = document.getElementById("surveyAllPage");
	removeAllChild(surveyAllPage);
	var card = getSurveyList();
	surveyAllPage.appendChild(card);
	$("#surveyAllPage").show();
};

function getSurveyList() {
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
	h5.innerHTML = "Surveys";
	col1.appendChild(h5);
	row.appendChild(col1);
	
	var col2 = document.createElement("div");
	col2.className = "col text-right";
	var btn = document.createElement('button');
	btn.className = "btn btn-md btn-primary btn-create";
	btn.innerHTML = "Create";
	btn.style = "background-color: #03ab22;color: white;";
	
	btn.addEventListener("click", function() {
		showCreateSurveyPage();
	});
	
	col2.appendChild(btn);
	row.appendChild(col2);
	pDiv.appendChild(row);
	card.appendChild(pDiv);

	var cardBody = document.createElement('div');
	cardBody.className = "card-body";
	
	var surveyTable = document.createElement("table");
	surveyTable.id = "surveyTable";
	surveyTable.className = "table table-striped table-bordered";
	surveyTable.setAttribute("cellspacing", 0);
	surveyTable.width = "100%";

	var thead = document.createElement("thead");
	var thead_tr = document.createElement("tr");
	var thead_th1 = document.createElement("th");
	thead_th1.innerText = "Survey Name";
	var thead_th2 = document.createElement("th");
	thead_th2.innerText = "Other Details";
	var thead_th3 = document.createElement("th");
	thead_th3.innerText = "#";
	thead_tr.appendChild(thead_th3);
	thead_tr.appendChild(thead_th1);
	thead_tr.appendChild(thead_th2);
	thead.appendChild(thead_tr);
	surveyTable.appendChild(thead);
	
	var tBody = document.createElement("tbody");
	
	for (var i = 1; i <= 20; i++) {
		var tbody_tr1 = document.createElement("tr");
		var tbody_tr1_td0 = document.createElement("td");
		tbody_tr1_td0.innerText = i;
		var tbody_tr1_td1 = document.createElement("td");
		var a = document.createElement('a');
		a.className = "nav-link active";
		a.style = "color: #01ab21;";
		a.href = "#";
		a.innerText = " Survey# " + i;
		tbody_tr1_td1.appendChild(a);
		var tbody_tr1_td2 = document.createElement("td");
		tbody_tr1_td2.innerText = "Details# " + i;
		tbody_tr1.appendChild(tbody_tr1_td0);
		tbody_tr1.appendChild(tbody_tr1_td1);
		tbody_tr1.appendChild(tbody_tr1_td2);
		tBody.appendChild(tbody_tr1);
	}
	
	
	surveyTable.appendChild(tBody);
	cardBody.appendChild(surveyTable);
	card.appendChild(cardBody);
	
	return card;
};

