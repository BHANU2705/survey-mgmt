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
	thead_th1.innerText = "#";
	var thead_th2 = document.createElement("th");
	thead_th2.innerText = "Survey Name";
	var thead_th3 = document.createElement("th");
	thead_th3.innerText = "Status";
	var thead_th4 = document.createElement("th");
	thead_th4.innerText = "Created On";
	var thead_th5 = document.createElement("th");
	thead_th5.innerText = "Responses #";
	
	thead_tr.appendChild(thead_th1);
	thead_tr.appendChild(thead_th2);
	thead_tr.appendChild(thead_th3);
	thead_tr.appendChild(thead_th4);
	thead_tr.appendChild(thead_th5);
	thead.appendChild(thead_tr);
	surveyTable.appendChild(thead);
	
	$.blockUI({ message: 'Fetching Survey List...' });
	var httpRequest = new XMLHttpRequest();
	var url = "/Test/survey";
	httpRequest.open('GET', url);
	httpRequest.setRequestHeader('Cache-Control', 'no-cache');
	var that = this;
	httpRequest.onreadystatechange = function() {
		setData(surveyTable, httpRequest);
	};
	httpRequest.send(null);
//	var tBody = document.createElement("tbody");
	/*var status = [];
	status.push("Live");
	status.push("Draft");
	
	for (var i = 1; i <= 10; i++) {
		var tbody_tr1 = document.createElement("tr");
		var tbody_tr1_td1 = document.createElement("td");
		tbody_tr1_td1.innerText = i;

		var tbody_tr1_td2 = document.createElement("td");
		var a = document.createElement('a');
		a.className = "nav-link active";
		a.style = "color: #01ab21;";
		a.href = "#";
		a.innerText = " Survey# " + i;
		tbody_tr1_td2.appendChild(a);
		
		var tbody_tr1_td3 = document.createElement("td");
		tbody_tr1_td3.innerText = status[i%2];
		
		var tbody_tr1_td4 = document.createElement("td");
		tbody_tr1_td4.innerText = Date();
		
		var tbody_tr1_td5 = document.createElement("td");
		tbody_tr1_td5.innerText = Math.floor(Math.random() * 101);
		
		tbody_tr1.appendChild(tbody_tr1_td1);
		tbody_tr1.appendChild(tbody_tr1_td2);
		tbody_tr1.appendChild(tbody_tr1_td3);
		tbody_tr1.appendChild(tbody_tr1_td4);
		tbody_tr1.appendChild(tbody_tr1_td5);
		tBody.appendChild(tbody_tr1);
	}*/
	
	
//	surveyTable.appendChild(tBody);
	cardBody.appendChild(surveyTable);
	card.appendChild(cardBody);
	
	return card;
};

function setData(surveyTable, httpRequest) {
	if (httpRequest.readyState === 4) {
    	$.unblockUI();
        if (httpRequest.status === 200) {
        	var response = httpRequest.responseText;
        	var data = JSON.parse(response);
        	var tBody = document.createElement("tbody");
        	for (var i = 0; i < data.length; i++) {
				var row = document.createElement("tr");

				var index = document.createElement("td");
				index.innerText = (i+1);
				
				var surveyName = document.createElement("td");
				var a = document.createElement('a');
				a.className = "nav-link active";
				a.style = "color: #01ab21;";
				a.href = "#";
				a.innerText = data[i].name;
				surveyName.appendChild(a);

				var status = document.createElement("td");
				status.innerText = data[i].status;

				var createdOn = document.createElement("td");
				var d = data[i].lifeCycle.updatedOn;
				var date = new Date(d.year, d.month, d.dayOfMonth, d.hourOfDay, d.minute, d.second, 0);
				createdOn.innerText = date;

				var responseCount = document.createElement("td");
				responseCount.innerText = getResonseCount(data[i].id);
				
				row.appendChild(index);
				row.appendChild(surveyName);
				row.appendChild(status);
				row.appendChild(createdOn);
				row.appendChild(responseCount);
				tBody.appendChild(row);
			}
        	surveyTable.appendChild(tBody);
        }
    }
};

function getResonseCount(surveyId) {
	return Math.floor(Math.random() * 101);
};
