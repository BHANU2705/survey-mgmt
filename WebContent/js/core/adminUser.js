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
		$.unblockUI();
		if (httpRequest.readyState == 4 && httpRequest.status == "200") {
			console.log("success")
//			setUserData(userTable, httpRequest);
		} else {
			// error scenario
		}
	}
	httpRequest.send(null);
	cardBody.appendChild(userTable);
	card.appendChild(cardBody);
	
	return card;
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
//	httpRequest.setRequestHeader("Cookie", getJSessionCookie());
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

