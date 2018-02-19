function loadAdminUserPage() {
	var parent = document.getElementById('user');
	removeAllChild(parent);
	parent.appendChild(getUserList());
};

function getUserList() {
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
	h5.innerHTML = "Users";
	col1.appendChild(h5);
	row.appendChild(col1);
	
	var col2 = document.createElement("div");
	col2.className = "col text-right";
	var btn = document.createElement('button');
	btn.className = "btn btn-md btn-primary btn-create";
	btn.innerHTML = "Create";
	btn.style = "background-color: #03ab22;color: white;";
	
	btn.addEventListener("click", function() {
		showCreateUserPage();
	});
	
	col2.appendChild(btn);
	row.appendChild(col2);
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
	thead_th2.innerText = "User Name";
	var thead_th3 = document.createElement("th");
	thead_th3.innerText = "Email";
	var thead_th4 = document.createElement("th");
	thead_th4.innerText = "Attached Survey #";
	var thead_th5 = document.createElement("th");
	thead_th5.innerText = "Actions";
	
	thead_tr.appendChild(thead_th1);
	thead_tr.appendChild(thead_th2);
	thead_tr.appendChild(thead_th3);
	thead_tr.appendChild(thead_th4);
	thead_tr.appendChild(thead_th5);
	thead.appendChild(thead_tr);
	userTable.appendChild(thead);
	
	$.blockUI({ message: 'Fetching User List...' });
	var httpRequest = new XMLHttpRequest();
	if (!httpRequest) {
		alert('Giving up :( Cannot create an XMLHTTP instance');
		return false;
	}
	var url = "/Test/cuser";
	httpRequest.open('GET', url);
	httpRequest.setRequestHeader('Cache-Control', 'no-cache');
	httpRequest.onload = function () {
		if (httpRequest.readyState == 4 && httpRequest.status == "200") {
			setUserData(userTable, httpRequest);
		} else {
			// error scenario
		}
	}
	httpRequest.send(null);
	cardBody.appendChild(userTable);
	card.appendChild(cardBody);
	
	return card;
};

function setUserData(userTable, httpRequest) {
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
				
				var userNameTd = document.createElement("td");
				var a = document.createElement('a');
				a.className = "nav-link active";
				a.style = "color: #01ab21;cursor: pointer;";
				a.addEventListener("click", function() {
					readSpecificUser(email);
				});
				a.innerText = data[i].name;
				userNameTd.appendChild(a);

				var emailTd = document.createElement("td");
				emailTd.innerText = data[i].email;

				var attachedSurveyCountTd = document.createElement("td");
				attachedSurveyCountTd.innerText = data[i].assignedSurveyCount;

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
				dropdownItem1.id = "assign_survey_#@_" + data[i].email;
				
				var assignSurveyIcon = document.createElement("i");
				assignSurveyIcon.className = "fas fa-chart-line";
				
				dropdownItem1.appendChild(assignSurveyIcon);
				dropdownItem1.setAttribute("data-toggle", "tooltip");
				dropdownItem1.setAttribute("data-placement", "left");
				dropdownItem1.title = "Assign Survey";
				dropdownItem1.style = "cursor: pointer";
				dropdownItem1.addEventListener("click", function(evt) {
					var targetId = evt.currentTarget.id;
					var cuEmail = targetId.split("_#@_")[1].trim();
					
					var modal = getModelUtility("assignSurveyModal", "Assign Survey", "Save", "Cancel", 
							assignSurveyToUser, cancelModelAssignSurvey, assignSurveyToUserModalBody(cuEmail),
							validateModalData, "Erroneous Data");
					var parent = document.getElementById('user');
					parent.appendChild(modal);
					$('#assignSurveyModal').modal({
						keyboard: false,
						show: true,
						focus: true,
						backdrop: "static"
					});
				});
				dropDownMenuDiv.appendChild(dropdownItem1);
				
				var dropdownItem2 = document.createElement("a");
				dropdownItem2.id = "delete_user_#@_" + data[i].email + ":" + data[i].name;
				dropdownItem2.addEventListener("click", function(evt) {
					var targetId = evt.currentTarget.id.trim();
					var email = targetId.split("_#@_")[1].split(":")[0].trim();
					var name = targetId.split("_#@_")[1].split(":")[1].trim();
					deleteUser(email, name);
				});
				dropdownItem2.className = "dropdown-item";
				
				var deleteIcon = document.createElement("i");
				deleteIcon.className = "fas fa-trash-alt";
				
				dropdownItem2.appendChild(deleteIcon);
				dropdownItem2.setAttribute("data-toggle", "tooltip");
				dropdownItem2.setAttribute("data-placement", "left");
				dropdownItem2.title = "Delete User";
				dropdownItem2.style = "cursor: pointer";
				dropDownMenuDiv.appendChild(dropdownItem2);
				pDiv.appendChild(dropDownMenuDiv);
				
				row.appendChild(index);
				row.appendChild(userNameTd);
				row.appendChild(emailTd);
				row.appendChild(attachedSurveyCountTd);
				row.appendChild(pDiv);
				tBody.appendChild(row);
			}
        	userTable.appendChild(tBody);
        }
	}
};

function cancelModelAssignSurvey() {
	console.log("Cancel model for Assign Survey to User");
};

function assignSurveyToUserModalBody(clientUserEmail) {
	var body = document.createElement("div");
	body.id = "assignSurveyToClientUser" + clientUserEmail;
	
	var mutedText = document.createElement("h6");
	mutedText.className = "card-subtitle mb-2 text-muted";
	mutedText.innerText = "Following is the list of unassigned surveys to this client user.";
	body.appendChild(mutedText);
	var httpRequest = new XMLHttpRequest();
	if (!httpRequest) {
		alert('Giving up :( Cannot create an XMLHTTP instance');
		return false;
	}
	var url = "/Test/su?clientUserEmail=" + clientUserEmail + "&qType=getMyUnassignedSurveys";
	httpRequest.open('GET', url);
	httpRequest.setRequestHeader('Cache-Control', 'no-cache');
	httpRequest.onload = function () {
		if (httpRequest.readyState == 4 && httpRequest.status == "200") {
			var response = httpRequest.responseText;
        	var data = JSON.parse(response);
        	for (var i = 0; i < data.length; i++) {
        		var checkBoxId = "checkBox_#_" + data[i].surveyId;
        		var text = data[i].surveyName;
        		body.appendChild(getCheckBox(data[i].surveyId, text, clientUserEmail));
			}
		} else {
			// error scenario
		}
	}
	httpRequest.send(null);
	return body;
};

function assignSurveyToUser() {
	var jsonPayload = [];
	var surveys = document.getElementsByClassName("unassignedSurveys");
	for (var i = 0; i < surveys.length; i++) {
		var survey = surveys[i];
		if (survey.children[0].checked) {
			var data = survey.children[2].value;
			var d = data.split("_#_");
			var surveyId = d[0].split(":")[1].trim();
			var clientUserEmail = d[1].split(":")[1].trim();
			var eachSurvey = {};
			eachSurvey.surveyId = surveyId;
			eachSurvey.clientUserEmail = clientUserEmail;
			jsonPayload.push(eachSurvey);
		}
	}
	var payload =  JSON.stringify(jsonPayload);
	var httpRequest = new XMLHttpRequest();
	var url = "/Test/su";
	if (!httpRequest) {
		alert('Giving up :( Cannot create an XMLHTTP instance');
		return false;
	}
	httpRequest.open('POST', url);
	httpRequest.setRequestHeader('Content-Type', 'application/json');
	httpRequest.setRequestHeader('Cache-Control', 'no-cache');
	httpRequest.onload = function () {
		if (httpRequest.readyState == 4 && httpRequest.status == "201") {
			alert('Surveys linked to user successfully.');
			$('#assignSurveyModal').modal('toggle');
		} else {
			// error scenario
		}
	}
	httpRequest.send(payload);
//	$('#assignSurveyModal').modal('toggle');
};

function getCheckBox(id, text, clientUserEmail) {
	var parent = document.createElement("div");
	parent.className = "form-check unassignedSurveys";
	var customId = "checkBox_#_"+ id;
	
	var input = document.createElement("input");
	input.className = "form-check-input";
	input.type = "checkbox";
	input.id = customId

	var label = document.createElement("label");
	label.className = "form-check-label";
	label.setAttribute("for", customId);
	label.innerText = text;
	
	var d = document.createElement("input");
	d.className = "form-check-input";
	d.type = "hidden";
	d.value = "id:" + id + "_#_" + "clientUserEmail:" + clientUserEmail;

	parent.appendChild(input);
	parent.appendChild(label);
	parent.appendChild(d);

	return parent;
}

function validateModalData() {
	console.log("validate modal data");
	return true;
};

function deleteUser(email, userName) {
	var msg = "Deleting the User: " + userName + " (" + email + ")";
	$.blockUI({ message: msg});
	var httpRequest = new XMLHttpRequest();
	var url = "/Test/cuser?email="+email;
	httpRequest.open('DELETE', url);
	httpRequest.onload = function () {
		$.unblockUI();
		if (httpRequest.readyState == 4 && httpRequest.status == "204") {
			loadAdminUserPage();
		} else {
			// error scenario
		}
	}
	httpRequest.send(null);
};

function showCreateUserPage() {
	var parent = document.getElementById('user');
	removeAllChild(parent);
	parent.appendChild(getCreateUserPage());
};

function getCreateUserPage() {
	var card = document.createElement('div');
	card.className = "card";

	var pDiv = document.createElement('div');
	pDiv.className = "card-header";
	card.appendChild(pDiv);

	var row = document.createElement('div');
	row.className = "row";
	pDiv.appendChild(row);

	var col1 = document.createElement("div");
	col1.className = "col text-left";
	var btn = document.createElement('button');
	btn.className = "btn btn-md btn-primary btn-create";
	btn.innerHTML = "Back to User Listing";
	btn.style = "background-color: #03ab22;color: white;";

	btn.addEventListener("click", function() {
		loadAdminUserPage();
	});

	col1.appendChild(btn);
	row.appendChild(col1);


	var col2 = document.createElement('div');
	col2.className = "col text-center";
	col2.style = "padding-top: 9px;";
	var h5 = document.createElement('h5');
	h5.innerText = "Create User"
	col2.appendChild(h5);
	row.appendChild(col2);

	var col3 = document.createElement("div");
	col3.className = "col text-right";
	row.appendChild(col3);

	card.appendChild(getCreateUserFormCard());
	
	var cardFooter =  document.createElement('div');
	cardFooter.className = "card-footer text-right";
	var saveUser = document.createElement('button');
	saveUser.className = "btn btn-md btn-primary btn-create";
	saveUser.innerText = "Create User";
	saveUser.style = "background-color: #03ab22;color: white;";
	saveUser.addEventListener("click", function() {
		onSaveUser();
	});
	cardFooter.appendChild(saveUser);
	
	var resetUser = document.createElement('button');
	resetUser.className = "btn btn-md btn-primary btn-create";
	resetUser.innerText = "Reset";
	resetUser.style = "background-color: #03ab22;color: white; margin-left: 10px;";
	resetUser.addEventListener("click", function() {
		onResetUser();
	});
	cardFooter.appendChild(resetUser);
	
	card.appendChild(cardFooter);
	return card;
};

function onSaveUser() {
	$.blockUI({ message: 'Creating the user...' });
	var name = document.getElementById('createUser_Name').value;
	var email = document.getElementById('createUser_Email').value;
	var cuser = {};
	cuser.name = name;
	cuser.email = email;
	var payload =  JSON.stringify(cuser);
	
	var httpRequest = new XMLHttpRequest();
	var url = "/Test/cuser";
	if (!httpRequest) {
		alert('Giving up :( Cannot create an XMLHTTP instance');
		return false;
	}
	httpRequest.open('POST', url);
	httpRequest.setRequestHeader('Content-Type', 'application/json');
	httpRequest.setRequestHeader('Cache-Control', 'no-cache');
	httpRequest.onload = function () {
		$.unblockUI();
		if (httpRequest.readyState == 4 && httpRequest.status == "201") {
			alert('success');
			loadAdminUserPage();
		} else {
			// error scenario
		}
	}
	httpRequest.send(payload);
};

function onResetUser() {
	var name = document.getElementById('createUser_Name');
	name.value = null;
	var email = document.getElementById('createUser_Email');
	email.value = null;
};

function getCreateUserFormCard() {
	var cardBody = document.createElement('div');
	cardBody.className = "card-body";
	cardBody.style = "width: fit-content;";
	
	var nameDiv = document.createElement('div');
	nameDiv.className = "form-group";
	
	var nameLabel = document.createElement('label');
	nameLabel.setAttribute("for", "name");
	nameLabel.innerText = "Name";
	nameDiv.appendChild(nameLabel);
	
	var nameInput = document.createElement('input');
	nameInput.type = "text";
	nameInput.name = "createUser_Name";
	nameInput.id = "createUser_Name";
	nameInput.className = "form-control";
	nameInput.setAttribute("placeholder", "Enter name");
	nameDiv.appendChild(nameInput);
	cardBody.appendChild(nameDiv);
	
	var emailDiv = document.createElement('div');
	emailDiv.className = "form-group";
	
	var emailLabel = document.createElement('label');
	emailLabel.setAttribute("for", "email");
	emailLabel.innerText = "Email Address";
	emailDiv.appendChild(emailLabel);
	
	var emailInput = document.createElement('input');
	emailInput.setAttribute("type", "email");
	emailInput.className = "form-control";
	emailInput.id = "createUser_Email";
	emailInput.name = "createUser_Email";
	emailInput.setAttribute("aria-describedby", "emailHelp");
	emailInput.setAttribute("placeholder", "Enter email");
	emailDiv.appendChild(emailInput);
	
	var emailSmall = document.createElement('small');
	emailSmall.className = "form-text text-muted";
	emailSmall.id = "emailHelp";
	emailSmall.innerText = "We'll never share the email with anyone else.";
	emailDiv.appendChild(emailSmall);
	cardBody.appendChild(emailDiv);
	
	var note = document.createElement('medium');
	note.className = "form-text text-muted";
	note.id = "emailHelp";
	note.innerText = "Note: User's credentials will be automatically sent to his email id (mentioned above).";
	note.style = "margin-bottom: 20px;";
	cardBody.appendChild(note);
	
	var hiddenAction = document.createElement('input');
	hiddenAction.type = "hidden";
	hiddenAction.name = "action";
	hiddenAction.value = "createClientUser";
	cardBody.appendChild(hiddenAction);
	
	return cardBody;
};

