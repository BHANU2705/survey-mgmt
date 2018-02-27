function displaySurveyPage(survey, mode) {
	var surveyAllPage = document.getElementById("surveyAllPage");
	removeAllChild(surveyAllPage);
	$("#surveyAllPage").show();
	displaySurveyUI(survey, mode);
};

function displaySurveyUI(survey, mode) {
	qCount = 0;
	qSerialNumber = 0;
	var surveyAllPage = document.getElementById('surveyAllPage');
	var card = document.createElement('div');
	card.className = "card";
	surveyAllPage.appendChild(card);

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
	var surveyTitle = document.createElement('input');
	surveyTitle.id = "sTitle_" + survey.id;
	surveyTitle.setAttribute("readonly", true);
	surveyTitle.value = survey.name;
	surveyTitle.type = "text";
	surveyTitle.className = "form-control-plaintext";
	surveyTitle.style = "text-align: center;font-weight: bold;";
	col2.appendChild(surveyTitle);
	row.appendChild(col2);

	var col3 = document.createElement("div");
	col3.className = "col text-right";
	
	var col3_row = document.createElement("div");
	col3_row.className = "row";
	col3_row.id = "col3_row";
	
	var col3_dummy1 = document.createElement("div");
	col3_dummy1.className = "col text-right";
	col3_row.appendChild(col3_dummy1);
	
	var col3_dummy2 = document.createElement("div");
	col3_dummy2.className = "col text-right";
	col3_row.appendChild(col3_dummy2);
	
	var col3_dummy3 = document.createElement("div");
	col3_dummy3.className = "col text-right";
	col3_row.appendChild(col3_dummy3);

	var col3_dummy4 = document.createElement("div");
	col3_dummy4.className = "col text-right";
	col3_row.appendChild(col3_dummy4);
	
	var col3_edit = document.createElement("div");
	col3_edit.className = "col text-right";
	var editSurvey = document.createElement('button');
	editSurvey.id = "editSurveyBtn_" + survey.id;
	editSurvey.className = "btn btn-md btn-primary btn-create";
	editSurvey.innerText = "Edit";
	editSurvey.style = "background-color: #03ab22;color: white;";
	editSurvey.setAttribute("currentAction","editSurveyAction");

	editSurvey.addEventListener("click", function(evt) {
		console.log("edit survey name");
		var currentAction = evt.currentTarget.getAttribute("currentaction");
		var surveyId = evt.currentTarget.id.split("editSurveyBtn_")[1];
		if(currentAction && currentAction === "editSurveyAction") {
			evt.currentTarget.setAttribute("currentAction","saveSurveyAction");
			evt.currentTarget.innerText = "Save";
			
			var col3_cancel = document.createElement("div");
			col3_cancel.className = "col text-right";
			col3_cancel.id = "col3_cancel";
			var cancelEditSurveyName = document.createElement('button');
			cancelEditSurveyName.className = "btn btn-md btn-primary btn-create";
			cancelEditSurveyName.innerText = "Cancel";
			cancelEditSurveyName.style = "background-color: #03ab22;color: white;";

			cancelEditSurveyName.addEventListener("click", function(evt1) {
				var col3_row = document.getElementById("col3_row");
				var col3_cancel = document.getElementById("col3_cancel");
				col3_row.removeChild(col3_cancel);
				
				var sTitle = document.getElementById("sTitle_" + surveyId);
				sTitle.setAttribute("readonly", true);
				sTitle.value = "original value";
				sTitle.className = "form-control-plaintext";
				surveyTitle.style = "text-align: center;font-weight: bold;";
				
				var editBtn = document.getElementById("editSurveyBtn_" + surveyId);
				editBtn.innerText = "Edit";
				editBtn.setAttribute("currentAction","editSurveyAction");
				
			});
			col3_cancel.appendChild(cancelEditSurveyName);
			var col3_row = document.getElementById("col3_row");
			col3_row.appendChild(col3_cancel);
			
			var sTitle = document.getElementById("sTitle_" + surveyId);
			sTitle.removeAttribute("readonly");
//			sTitle.style = "form-control-plaintext background-color: white;";
			sTitle.style = "form-control";
			surveyTitle.style = "text-align: center;font-weight: bold; background-color: white;";
			sTitle.focus();
		}
	});
	
	col3_edit.appendChild(editSurvey);
	col3_row.appendChild(col3_edit);
	//col3_edit.appendChild(editSurvey);
	
	
	
//	col3.appendChild(col3_edit);
	col3.appendChild(col3_row);
	row.appendChild(col3);

	var cardBody = document.createElement('div');
	cardBody.className = "card-body";

	var qMain = document.createElement('div');
	qMain.id = "qMain";

	cardBody.appendChild(qMain);
	loadExistingQuestions(cardBody, survey, mode);
	card.appendChild(cardBody);

	if (document.getElementById("surveyName") && document.getElementById("surveyName").value) {
		dataModel.surveyName = document.getElementById("surveyName").value;
		h5.innerHTML = dataModel.surveyName;
	}

	var cardFooter =  document.createElement('div');
	cardFooter.className = "card-footer text-right";
	var saveSurvey = document.createElement('button');
	saveSurvey.className = "btn btn-md btn-primary btn-create";
	saveSurvey.innerText = "Save Survey";
	saveSurvey.style = "background-color: #03ab22;color: white;";
	saveSurvey.addEventListener("click", function() {
		if(qMain && qMain.childElementCount <= 0) {
			alert("Warning: No question added to the survey. So, nothing to save. Kindly add atleast one question to the survey.");
		} else {
			onSave();
		}
	});
	cardFooter.appendChild(saveSurvey);
	card.appendChild(cardFooter);
};

function loadExistingQuestions(cardBody, survey, mode) {
	if(survey && survey.questions && survey.questions.length > 0) {
		for (var i = 0; i < survey.questions.length; i++) {
			var question = survey.questions[i];
			var questionCard = getExistingQuestionDiv(i, survey, mode);
			cardBody.appendChild(questionCard);
		}
	}
};

function getExistingQuestionDiv(i, survey, mode) {
//	var superDiv = document.getElementById('qMain');
	var questionParent = document.createElement('div');
	questionParent.id = 'div_accordion_q_' + i;
	questionParent.role = "tablist";

//	superDiv.appendChild(questionParent);
	qSerialNumber++;

	var questionCard = document.createElement('div');
	questionCard.className = "card";
	questionCard.style = "margin-bottom: 15px;";

	var cardHeader = document.createElement('div');
	cardHeader.className = "card-header";
	cardHeader.role = "tab";
	cardHeader.id = "heading_" + i;

	var h5 = document.createElement('h5');
	h5.className = "mb-0";

	var collapseId = 'collapse_'+i;

	var a = document.createElement('a');
	a.id = "anchor_"+i;
	a.setAttribute('data-toggle', 'collapse');
	a.setAttribute('href', '#'+collapseId);
	a.setAttribute('aria-expanded', 'true');
	a.setAttribute('aria-controls', collapseId);
	a.innerHTML = "Question# "+ (i + 1);

	h5.appendChild(a);

	if(mode && mode === "edit") {
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
	}

//	cardHeader.appendChild(h5);
	
	var editQuestion = document.createElement('button');
	editQuestion.id = "editQuestionBtn_" + survey.questions[i].id;
	editQuestion.className = "btn btn-md btn-primary btn-create";
	editQuestion.innerText = "Edit Question";
	editQuestion.style = "background-color: #03ab22;color: white;";

	editQuestion.addEventListener("click", function() {
//		addQuestionUsingCount();
	});
	cardHeader.appendChild(editQuestion);
	
	var questioRow = document.createElement('div');
	questioRow.className = "row";
	
	var questioRow_Col1 = document.createElement("div");
	questioRow_Col1.className = "col text-left";
	var btn = document.createElement('button');
	btn.className = "btn btn-md btn-primary btn-create";
	btn.innerHTML = "Back to Survey Listing";
	btn.style = "background-color: #03ab22;color: white;";

	btn.addEventListener("click", function() {
		onLoadSurvey();
	});

	questioRow_Col1.appendChild(h5);
	questioRow.appendChild(questioRow_Col1);

	var questioRow_Col2 = document.createElement("div");
	questioRow_Col2.className = "col text-right";
	questioRow_Col2.appendChild(editQuestion);
	questioRow.appendChild(questioRow_Col2);
	
	cardHeader.appendChild(questioRow);
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

	cardBody.appendChild(getExistingQuestionSet(i, survey.questions[i]), mode);
	questionCard.appendChild(collapse);
	return questionCard;
};

function getExistingQuestionSet(i, question, mode) {
	var parent = document.createElement('div');

	var row1 = document.createElement('div');
	row1.className = "row";
	row1.id = "row1";

	var col1 = document.createElement('div');
	col1.className = "col-8";
	var qText = document.createElement('textarea');
	qText.type="text";
	qText.id="qText_" + i;
	qText.name="qText_" + i;
	qText.placeholder = "Enter Question";
	qText.className = "form-control-plaintext";
	qText.rows = 1;
	qText.value = question.text;
	qText.setAttribute("readonly", true);
	col1.appendChild(qText);

	var col2 = document.createElement('div');
	col2.className = "col-4";

	var qType = document.createElement('div');
	qType.className = "input-group mb-3";

	var inputGroupPrepend = document.createElement('div');
	inputGroupPrepend.className = "input-group-prepend";
	var inputGroupLabel = document.createElement('label');
	inputGroupLabel.className = "input-group-text";
	inputGroupLabel.setAttribute("for", "inputGroupSelect_" + i);
	inputGroupLabel.innerText = "Question Type";
	inputGroupPrepend.appendChild(inputGroupLabel);

	var select = document.createElement('select');
	select.className = "custom-select";
	select.id = "inputGroupSelect_" + i;
	select.setAttribute("disabled", true);
	var x = select.id;
	var that = this;
	select.addEventListener("change", function(e) {
		var e1 = document.getElementById(e.currentTarget.id);
		var value = e1.options[e1.selectedIndex].value;
		var i = parseInt(e1.id.charAt(e1.id.length-1));
		attachOptionsDiv(e, value, i);
	});
	select.addEventListener("focus", function(e) {
		var e1 = document.getElementById(e.currentTarget.id);
		var value = e1.options[e1.selectedIndex].value;
		var i = parseInt(e1.id.charAt(e1.id.length-1));
		attachOptionsDiv(e, value, i);
	}, {once: true});

	var option0 = getQTypeOption("Choose", "Choose...", true);
	var option1 = getQTypeOption("Radio", "Radio Button", false);
	var option2 = getQTypeOption("Dropdown", "Dropdown List", false);
	var option3 = getQTypeOption("TextField", "Text Field", false);
	var option4 = getQTypeOption("CheckBox", "Check Box", false);
	var option5 = getQTypeOption("Gender", "Gender", false);
	var option6 = getQTypeOption("YesNo", "Yes/ No", false);
	var option7 = getQTypeOption("Date", "Date", false);
	var option8 = getQTypeOption("Image", "Image Upload", false);
	var option9 = getQTypeOption("Geocode", "Geocode", false);

	select.appendChild(option0);
	select.appendChild(option1);
	select.appendChild(option2);
	select.appendChild(option3);
	select.appendChild(option4);
	select.appendChild(option5);
	select.appendChild(option6);
	select.appendChild(option7);
	select.appendChild(option8);
	select.appendChild(option9);
	
	select.value = question.type;

	qType.appendChild(inputGroupPrepend);
	qType.appendChild(select);

	col2.appendChild(qType);
	row1.appendChild(col1);
	row1.appendChild(col2);

	parent.appendChild(row1);

	
	var row2 = document.createElement('div');
	row2.className = "row";
	var row2Col1 = document.createElement('div');
	row2Col1.className = "col";
	row2Col1.id = "row2Col1_" + i;
	if (question.type && (question.type === "Radio" || 
			question.type === "Dropdown" || 
			question.type === "CheckBox")) {
		if (question.options && question.options.length > 0) {
			for (var i = 0; i < 5; i++) {
				var qText = document.createElement("input");
				qText.type="text";
				qText.id = question.options[i].id;
				qText.name = qText.id;
				qText.placeholder = "Enter an answer choice";
				qText.className = "form-control";
				qText.style = "margin-bottom: 8px; margin-top: 5px;";
				qText.value = question.options[i].text;
				qText.setAttribute("readonly", true);
				row2Col1.appendChild(qText);
			}
		}
	}

	row2.appendChild(row2Col1);
	
	/*attachExistingOptionsDiv()*/
	
	parent.appendChild(row2);

	return parent;
};

function attachExistingOptionsDiv(e, optionType, questionIndex) {
	var container = e.path[4].childNodes[1].childNodes[0];
	removeAllChild(container);
	var optionDiv = null;
	switch (optionType) {
	case "Radio":
		optionDiv = getExistingRadioOptionDiv(questionIndex);
		break;
	case "Dropdown":
		optionDiv = getExistingDropdownOptionDiv(questionIndex);
		break;
	case "TextField":
		optionDiv = getExistingTextFieldOptionDiv(questionIndex);
		break;
	case "CheckBox":
		optionDiv = getExistingCheckBoxOptionDiv(questionIndex);
		break;
	case "Gender":
		optionDiv = getExistingGenderOptionDiv(questionIndex);
		break;
	case "YesNo":
		optionDiv = getExistingYesNoOptionDiv(questionIndex);
		break;
	case "Date":
		optionDiv = getExistingDateOptionDiv(questionIndex);
		break;
	case "Image":
		optionDiv = getExistingImageOptionDiv(questionIndex);
		break;
	case "Geocode":
		optionDiv = getExistingGeocodeOptionDiv(questionIndex);
		break;
	default:
		break;
	}
	if (optionDiv) {
		container.appendChild(optionDiv);
	}
};

function getExistingFiveOptionsDiv(questionIndex, question) {
	var divParent = document.createElement("div");
	divParent.id = "div_option_parent_q_" + questionIndex;
	for (var i = 1; i < 6; i++) {
		var qText = document.createElement("input");
		qText.type="text";
		qText.id="q_" + questionIndex + "_option_" + i;
		qText.name = qText.id;
		qText.placeholder = "Enter an answer choice";
		qText.className = "form-control";
		qText.style = "margin-bottom: 8px; margin-top: 5px;";
		qText.value = question
		divParent.appendChild(qText);
	}
	return divParent;
};

function getExistingRadioOptionDiv(questionIndex) {
	return getFiveOptionsDiv(questionIndex);
};

function getExistingDropdownOptionDiv(questionIndex) {
	return getFiveOptionsDiv(questionIndex);
};

function getExistingTextFieldOptionDiv(questionIndex) {
	return null;
};

function getExistingCheckBoxOptionDiv(questionIndex) {
	return getFiveOptionsDiv(questionIndex);
};

function getExistingGenderOptionDiv(questionIndex) {
	return null;
};

function getExistingYesNoOptionDiv(questionIndex) {
	return null;
};

function getExistingDateOptionDiv(questionIndex) {
	return null;
};

function getExistingImageOptionDiv(questionIndex) {
	return null;
};

function getExistingGeocodeOptionDiv(questionIndex) {
	return null;
};

