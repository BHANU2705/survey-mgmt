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
	
//	$.blockUI({ message: 'Fetching User List...' });
	var httpRequest = new XMLHttpRequest();
	var url = "/Test/user";
	httpRequest.open('GET', url);
	httpRequest.setRequestHeader('Cache-Control', 'no-cache');
	var that = this;
	httpRequest.onreadystatechange = function() {
//		setData(userTable, httpRequest);
	};
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
	saveUser.innerText = "Save User";
	saveUser.style = "background-color: #03ab22;color: white;";
	saveUser.addEventListener("click", function() {
		onSaveUser();
	});
	cardFooter.appendChild(saveUser);
//	card.appendChild(cardFooter);
	return card;
};

function getCreateUserFormCard() {
	var cardBody = document.createElement('div');
	cardBody.className = "card-body";
	cardBody.style = "width: fit-content;";
	
	var form = document.createElement('form');
	form.action = "/Test/cuser";
	form.method = "post";
	
	var nameDiv = document.createElement('div');
	nameDiv.className = "form-group";
	
	var nameLabel = document.createElement('label');
	nameLabel.setAttribute("for", "name");
	nameLabel.innerText = "Name";
	nameDiv.appendChild(nameLabel);
	
	var nameInput = document.createElement('input');
	nameInput.type = "text";
	nameInput.name = "name";
	nameInput.id = "name";
	nameInput.className = "form-control";
	nameInput.setAttribute("placeholder", "Enter name");
	nameDiv.appendChild(nameInput);
	form.appendChild(nameDiv);
	
	var emailDiv = document.createElement('div');
	emailDiv.className = "form-group";
	
	var emailLabel = document.createElement('label');
	emailLabel.setAttribute("for", "email");
	emailLabel.innerText = "Email Address";
	emailDiv.appendChild(emailLabel);
	
	var emailInput = document.createElement('input');
	emailInput.setAttribute("type", "email");
	emailInput.className = "form-control";
	emailInput.id = "email";
	emailInput.name = "email";
	emailInput.setAttribute("aria-describedby", "emailHelp");
	emailInput.setAttribute("placeholder", "Enter email");
	emailDiv.appendChild(emailInput);
	
	var emailSmall = document.createElement('small');
	emailSmall.className = "form-text text-muted";
	emailSmall.id = "emailHelp";
	emailSmall.innerText = "We'll never share the email with anyone else.";
	emailDiv.appendChild(emailSmall);
	form.appendChild(emailDiv);
	
	var note = document.createElement('medium');
	note.className = "form-text text-muted";
	note.id = "emailHelp";
	note.innerText = "Note: User's credentials will be automatically sent to his email id (mentioned above).";
	note.style = "margin-bottom: 20px;";
	form.appendChild(note);
	
	var hiddenAction = document.createElement('input');
	hiddenAction.type = "hidden";
	hiddenAction.name = "action";
	hiddenAction.value = "createClientUser";
	form.appendChild(hiddenAction);
	
	var btnDiv = document.createElement('div');
	btnDiv.className = "row";
	
	var btnCol1 = document.createElement('div');
	btnCol1.className = "col";
	var submit = document.createElement('button');
	submit.className = "btn btn-md btn-primary btn-block";
	submit.type = "submit";
	submit.innerText = "Submit";
	btnCol1.appendChild(submit);
	btnDiv.appendChild(btnCol1);
	
	var btnCol2 = document.createElement('div');
	btnCol2.className = "col";
	var reset = document.createElement('button');
	reset.className = "btn btn-md btn-primary btn-block";
	reset.type = "reset";
	reset.innerText = "Reset";
	
	btnCol2.appendChild(reset);
	btnDiv.appendChild(btnCol2);
	
	form.appendChild(btnDiv);

	cardBody.appendChild(form);
	return cardBody;
};

