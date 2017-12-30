var qCount = 0;
var qSerialNumber = 0;
var dataModel = {};

function showCreateSurveyPage() {
	var surveyAllPage = document.getElementById("surveyAllPage");
	removeAllChild(surveyAllPage);
	$("#surveyAllPage").hide();
	var createSurveyPage = document.getElementById("createSurveyPage");
	removeAllChild(createSurveyPage);
	$("#createSurveyPage").show();
	showCreateSurveyModal();
};

function showCreateSurveyModal() {
	var modal = getModel('myId');
	var createSurveyPage = document.getElementById('createSurveyPage');
	createSurveyPage.appendChild(modal);
	$('#myId').modal({
		keyboard: false,
		show: true,
		focus: true,
		backdrop: "static"
	});
	document.getElementById("surveyName").focus();
};

function createSurvey() {
	dataModel = {};
	$('#myId').modal('toggle');
	qCount = 0;
	qSerialNumber = 0;
	var createSurveyPage = document.getElementById('createSurveyPage');
	var card = document.createElement('div');
	card.className = "card";
	createSurveyPage.appendChild(card);

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
	btn.innerHTML = "Back to Survey Listing";
	btn.style = "background-color: #03ab22;color: white;";
	
	btn.addEventListener("click", function() {
		onLoadSurvey();
	});
	
	col1.appendChild(btn);
	row.appendChild(col1);
	
	
	var col2 = document.createElement('div');
	col2.className = "col text-center";
	col2.style = "padding-top: 9px;";
	var h5 = document.createElement('h5');
	h5.id = "surveyTitle";
	col2.appendChild(h5);
	row.appendChild(col2);
	
	var col3 = document.createElement("div");
	col3.className = "col text-right";
	var addQuestion = document.createElement('button');
	addQuestion.className = "btn btn-md btn-primary btn-create";
	addQuestion.innerText = "Add Question";
	addQuestion.style = "background-color: #03ab22;color: white;";
	
	addQuestion.addEventListener("click", function() {
		addQuestionUsingCount();
	});
	
	col3.appendChild(addQuestion);
	row.appendChild(col3);
	
	var cardBody = document.createElement('div');
	cardBody.className = "card-body";
	
	var qMain = document.createElement('div');
	qMain.id = "qMain";
	
	cardBody.appendChild(qMain);
	card.appendChild(cardBody);
	
	if (document.getElementById("surveyName") && document.getElementById("surveyName").value) {
		dataModel.surveyName = document.getElementById("surveyName").value;
		h5.innerHTML = dataModel.surveyName;
	}
	
};

function addQuestionUsingCount() {
	qCount++;
	addQuestionDiv(qCount);
};

function addQuestionDiv(i) {
	var superDiv = document.getElementById('qMain');
	var questionParent = document.createElement('div');
	questionParent.id = 'div_accordion_q_' + i;
	questionParent.role = "tablist";
	
	superDiv.appendChild(questionParent);
	qSerialNumber++;
	
	var questionCard = document.createElement('div');
	questionCard.className = "card";
	
	var cardHeader = document.createElement('div');
	cardHeader.className = "card-header";
	cardHeader.role = "tab";
	cardHeader.id = "heading_" + i;
	
	var h5 = document.createElement('h5');
	h5.className = "mb-0";
	
	var collapseId = 'collapse_'+i;
	var qText = document.createElement('input');
	
	var a = document.createElement('a');
	a.id = "anchor_"+i;
	a.setAttribute('data-toggle', 'collapse');
	a.setAttribute('href', '#'+collapseId);
	a.setAttribute('aria-expanded', 'true');
	a.setAttribute('aria-controls', collapseId);
	a.innerHTML = "Question# "+ qSerialNumber;
	
	h5.appendChild(a);
	
	var closeButton = document.createElement('button');
	closeButton.type= "button";
	closeButton.className = "close";
	closeButton.setAttribute("aria-label", "Close");
	closeButton.setAttribute("data-toggle", "tooltip");
	closeButton.setAttribute("data-placement", "top");
	closeButton.setAttribute("title", "Delete");
	closeButton.style = "align:right";
	closeButton.addEventListener("click", function(e) {
		var qParentId = e.path[5].id;
		var createSurveyPage = document.getElementById('qMain');
		createSurveyPage.removeChild(document.getElementById(qParentId));
		qSerialNumber--;
		resetQuestionSerialNo();
	});
	
	var span = document.createElement("span");
	span.setAttribute("aria-hidden", "true");
	span.innerHTML = "&times;";
	closeButton.appendChild(span);
	h5.appendChild(closeButton);
	
	cardHeader.appendChild(h5);
	questionCard.appendChild(cardHeader);
	questionParent.appendChild(questionCard);
	
	var collapse = document.createElement('div');
	collapse.id = collapseId;
	collapse.className = "collapse show";
	collapse.role = "tabpanel";
	collapse.setAttribute('aria-labelledby', cardHeader.id);
	collapse.setAttribute('data-parent', '#'+questionParent.id);
	
	var cardBody = document.createElement('div');
	cardBody.className = "card-body";
	collapse.appendChild(cardBody);
	
	
	qText.type="text";
	qText.id="qText_" + i;
	qText.name="qText_" + i;
	qText.placeholder = "Enter Question";
	cardBody.appendChild(qText);
	questionCard.appendChild(collapse);
};

function resetQuestionSerialNo() {
	var createSurveyPage = document.getElementById('qMain');
	for (var i = 0; i < createSurveyPage.children.length; i++) {
		var child = createSurveyPage.children[i];
		var id = child.id;
		var cnt = id.charAt(id.length-1);
		var anchor = document.getElementById('anchor_'+cnt);
		if (anchor) {
			anchor.innerHTML = "Question# "+ (i+1);
		}
	}
};

function getModel(id) {
	var parent = document.createElement('div');
	parent.id = id;
	parent.className = "modal";
	parent.setAttribute("tabindex", -1);
	parent.setAttribute("role", "dialog");
	
	var modalDialog = document.createElement('div');
	modalDialog.className = "modal-dialog";
	modalDialog.setAttribute("role", "document");
	
	var modalContent = document.createElement('div');
	modalContent.className = "modal-content";
	
	var modalHeader = document.createElement('div');
	modalHeader.className = "modal-header";
	
	var h5 = document.createElement('h5');
	h5.id = "modalTitle";
	h5.className = "modal-title";
	h5.innerText = "Survey Name";
	
	var closeBtn = document.createElement('button');
	closeBtn.type = "button";
	closeBtn.className = "close";
	closeBtn.setAttribute("data-dismiss", "modal");
	closeBtn.setAttribute("aria-label", "Close");
	closeBtn.addEventListener("click", function() {
		onLoadSurvey();
	});
	
	var span = document.createElement('span');
	span.setAttribute("aria-hidden", "true");
	span.innerHTML = "&times;";
	closeBtn.appendChild(span);
	
	modalHeader.appendChild(h5);
	modalHeader.appendChild(closeBtn);
	
	var modalBody = document.createElement('div');
	modalBody.className = "modal-body";
	
	modalBody.appendChild(getInfoAlert("<strong>Error!</strong> Missing Survey Name"));
	
	var surveyName = document.createElement('input');
	surveyName.type = "text";
	surveyName.id = "surveyName";
	surveyName.name = surveyName.id;
	surveyName.placeholder = "Enter Survey Name";
	surveyName.className = "form-control";
	modalBody.appendChild(surveyName);

	var modalFooter = document.createElement('div');
	modalFooter.className = "modal-footer";
	
	var proceed = document.createElement('button');
	proceed.type = "button";
	proceed.className = "btn btn-primary";
	proceed.addEventListener("click", function() {
		var surveyName = document.getElementById("surveyName");
		if (surveyName && surveyName.value.length > 0) {
			return createSurvey();
		} else {
			$("#warningAlert").show();
		}
		
	});
	proceed.innerText = "Proceed";
	proceed.enabled = false;
	modalFooter.appendChild(proceed);
	
	var cancel = document.createElement('button');
	cancel.type = "button";
	cancel.className = "btn btn-secondary";
	cancel.addEventListener("click", function() {
		onLoadSurvey();
	});
	cancel.innerText = "Cancel";
	cancel.setAttribute("data-dismiss", "modal");
	modalFooter.appendChild(cancel);
	
	modalContent.appendChild(modalHeader);
	modalContent.appendChild(modalBody);
	modalContent.appendChild(modalFooter);
	
	modalDialog.appendChild(modalContent);
	parent.appendChild(modalDialog);
	
	return parent;
};

function getInfoAlert(text) {
	var parent = document.createElement('div');
	parent.id = "warningAlert";
	parent.className = "alert alert-danger alert-dismissible fade show";
	parent.setAttribute("role", "alert");
	parent.style="display: none;"
	
	var p = document.createElement('span');
	p.innerHTML = text;
	
	var closeButton = document.createElement('button');
	closeButton.type= "button";
	closeButton.className = "close";
	closeButton.setAttribute("aria-label", "Close");
	closeButton.setAttribute("data-dismiss", "alert");
	
	var span = document.createElement("span");
	span.setAttribute("aria-hidden", "true");
	span.innerHTML = "&times;";
	closeButton.appendChild(span);
	
	parent.appendChild(p);
	parent.appendChild(closeButton);
	
	return parent;
};
