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
        	var tBody = document.createElement("tbody");
        	for (var i = 0; i < data.length; i++) {
				var row = document.createElement("tr");

				var index = document.createElement("td");
				index.innerText = (i+1);
				
				var surveyName = document.createElement("td");
				surveyName.innerText = data[i].name;
				/*var a = document.createElement('a');
				a.id = "surveyLink_#@_" + data[i].id;
				a.className = "nav-link active";
				a.style = "color: #01ab21;cursor: pointer;";
				a.addEventListener("click", function(evt) {
					var targetId = evt.currentTarget.id.trim();
					var id = targetId.split("_#@_")[1].split(":")[0].trim();
					readSpecificSurvey(id);
				});
				a.innerText = data[i].name;
				surveyName.appendChild(a);*/

				var owner = document.createElement("td");
				owner.innerText = data[i].owner;

				var pDiv = document.createElement("div");
				pDiv.className = "dropdown show";
				pDiv.style = "margin-left: 30px;margin-top: 15px;margin-bottom: 15px;";
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
				pIcon.id = "edit_icon_"+1;
				
				pDivA.appendChild(pIcon);
				pDiv.appendChild(pDivA);
				
				var dropDownMenuId = "dropDownMenuId_" + data[i].id;
				var dropDownMenuDiv = document.createElement("div");
				dropDownMenuDiv.id = dropDownMenuId;
				dropDownMenuDiv.className = "dropdown-menu";
				dropDownMenuDiv.setAttribute("aria-labelledby", testId);
				dropDownMenuDiv.style = "min-width: auto;";
			    
				var dropdownItem1 = document.createElement("a");
				dropdownItem1.id = "respond_survey_#@_" + data[i].id;
				dropdownItem1.addEventListener("click", replySurvey);
				dropdownItem1.className = "dropdown-item";
				
				var replyIcon = document.createElement("i");
				replyIcon.className = "fas fa-reply";
				
				dropdownItem1.appendChild(replyIcon);
				dropdownItem1.setAttribute("data-toggle", "tooltip");
				dropdownItem1.setAttribute("data-placement", "left");
				dropdownItem1.title = "Reply";
				dropdownItem1.style = "cursor: pointer";
				dropDownMenuDiv.appendChild(dropdownItem1);
				pDiv.appendChild(dropDownMenuDiv);
				
				if (data[i].isResponded) {
					dropdownItem1.removeEventListener("click", replySurvey);
					dropdownItem1.title = "Already Replied";
				}
				
				row.appendChild(index);
				row.appendChild(surveyName);
				row.appendChild(owner);
				row.appendChild(pDiv);
				tBody.appendChild(row);
			}
        	assignedSurveyListTable.appendChild(tBody);
    	}
	}
};

function replySurvey(evt) {
	var targetId = evt.currentTarget.id.trim();
	var id = targetId.split("_#@_")[1];
	readAssignedSpecificSurvey(id);
}

function readAssignedSpecificSurvey(surveyId) {
	var httpRequest = new XMLHttpRequest();
	var url = contextPath + "/survey?id="+surveyId;
	httpRequest.open('GET', url);
	httpRequest.onload = function () {
//		$.unblockUI();
		if (httpRequest.readyState == 4 && httpRequest.status == "200") {
			var response = httpRequest.responseText;
        	var survey = JSON.parse(response);
        	console.log(survey);
//			displaySurveyPage(survey, "read");
		} else {
			// error scenario
		}
	}
	httpRequest.send(null);
};
