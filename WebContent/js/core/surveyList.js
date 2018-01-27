function onLoadSurvey() {
	var createSurveyPage = document.getElementById("createSurveyPage");
	removeAllChild(createSurveyPage);
	$("#createSurveyPage").hide();
	$("#subscriptionInfo").hide();
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
	
	var thead_th6 = document.createElement("th");
	thead_th6.innerText = "Actions";
	
	thead_tr.appendChild(thead_th1);
	thead_tr.appendChild(thead_th2);
	thead_tr.appendChild(thead_th3);
	thead_tr.appendChild(thead_th4);
	thead_tr.appendChild(thead_th5);
	thead_tr.appendChild(thead_th6);
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
        		var surveyId = data[i].id;
        		var sName = data[i].name;
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
				
				var pDiv = document.createElement("div");
				pDiv.className = "dropdown show";
				pDiv.style = "margin-left: 30px;margin-top: 15px;";
				pDiv.setAttribute("data-toggle", "tooltip");
				pDiv.setAttribute("data-placement", "left");
				pDiv.title = "Actions";
				
				var pDivA = document.createElement("a");
				pDivA.className = "btn btn-sm btn-secondary btn-create dropdown-toggle";
				pDivA.toolTip = "Actions";
				pDivA.style = "background-color: rgb(126, 145, 130);color: white;";
				pDivA.href = "#";
				pDivA.role = "button";
				var testId = "dropdownMenuLink_" + data[i].id;
				pDivA.id = testId;
				pDivA.setAttribute("data-toggle", "dropdown");
				pDivA.setAttribute("aria-haspopup", "true");
				pDivA.setAttribute("aria-expanded", "false");
				
				var pIcon = document.createElement("i");
				pIcon.className = "fas fa-edit";
				
				pDivA.appendChild(pIcon);
				pDiv.appendChild(pDivA);
				
				var dropDownMenuId = "dropDownMenuId_" + data[i].id;
				var dropDownMenuDiv = document.createElement("div");
				dropDownMenuDiv.id = dropDownMenuId;
				dropDownMenuDiv.className = "dropdown-menu";
				dropDownMenuDiv.setAttribute("aria-labelledby", testId);
				dropDownMenuDiv.style = "min-width: auto";
			    
				
				var dropdownItem1 = document.createElement("a");
				dropdownItem1.className = "dropdown-item";
				dropdownItem1.href = "#";
				
				var assignUsersIcon = document.createElement("i");
				assignUsersIcon.className = "fas fa-user-plus";
				
				dropdownItem1.appendChild(assignUsersIcon);
				dropdownItem1.setAttribute("data-toggle", "tooltip");
				dropdownItem1.setAttribute("data-placement", "left");
				dropdownItem1.title = "Assign Users";
				dropDownMenuDiv.appendChild(dropdownItem1);
				
				var dropdownItem2 = document.createElement("a");
				dropdownItem2.addEventListener("click", function() {
					deleteSurvey(surveyId, sName);
				});
				dropdownItem2.className = "dropdown-item";
				
				var deleteIcon = document.createElement("i");
				deleteIcon.className = "fas fa-trash-alt";
				
				dropdownItem2.appendChild(deleteIcon);
				dropdownItem2.setAttribute("data-toggle", "tooltip");
				dropdownItem2.setAttribute("data-placement", "left");
				dropdownItem2.title = "Delete Survey";
				dropDownMenuDiv.appendChild(dropdownItem2);
				pDiv.appendChild(dropDownMenuDiv);
				
				row.appendChild(index);
				row.appendChild(surveyName);
				row.appendChild(status);
				row.appendChild(createdOn);
				row.appendChild(responseCount);
				row.appendChild(pDiv);
				tBody.appendChild(row);
			}
        	surveyTable.appendChild(tBody);
        }
    }
};

function getResonseCount(surveyId) {
	return Math.floor(Math.random() * 101);
};

function deleteSurvey(surveyId, surveyName) {
	$.blockUI({ message: 'Deleting the Survey: ' +  surveyName});
	var httpRequest = new XMLHttpRequest();
	var url = "/Test/survey?id="+surveyId;
	httpRequest.open('DELETE', url);
	httpRequest.onload = function () {
		$.unblockUI();
		if (httpRequest.readyState == 4 && httpRequest.status == "204") {
			onLoadSurvey();
		} else {
			// error scenario
		}
	}
	httpRequest.send(null);
};
