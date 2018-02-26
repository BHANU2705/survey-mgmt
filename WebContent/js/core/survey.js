var qCount = 0;
var qSerialNumber = 0;

function showCreateSurveyPage() {
	var surveyAllPage = document.getElementById("surveyAllPage");
	removeAllChild(surveyAllPage);
	$("#surveyAllPage").show();
	showCreateSurveyModal();
};

function showCreateSurveyModal() {
	var modal = getModel('myId');
	var surveyAllPage = document.getElementById('surveyAllPage');
	surveyAllPage.appendChild(modal);
	$('#myId').modal({
		keyboard: false,
		show: true,
		focus: true,
		backdrop: "static"
	});
	document.getElementById("surveyName").focus();
};

function createSurvey() {
	$('#myId').modal('toggle');
	qCount = 0;
	qSerialNumber = 0;
	var surveyAllPage = document.getElementById('surveyAllPage');
	var card = document.createElement('div');
	card.id = "createSurveyCard";
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
		h5.innerHTML = document.getElementById("surveyName").value;
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

function onSave() {
	$.blockUI({ message: 'Saving the survey...' });
	var data = getData();
	if(data == null) {
		alert('Please choose the question type properly.');
		$.unblockUI();
	} else {
		var payload =  JSON.stringify(data);
		var httpRequest = new XMLHttpRequest();
		var url = "/Test/survey";
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
				onLoadSurvey();
			} else {
				// error scenario
			}
		}
		httpRequest.send(payload);
	}
};

function getData() {
	var survey = {};
	var surveyNameElement = document.getElementById('surveyName');
	if(surveyNameElement && surveyNameElement.value) {
		survey.name = surveyNameElement.value;
	}
	survey.status = "Draft";
	var questions = [];
	for (var i = 1; i <= qCount; i++) {
		var element = document.getElementById('qText_'+i);
		if(element && element.value) {
			var question = {};
			question.text = element.value;
			question.options = [];
			var qType = document.getElementById('inputGroupSelect_'+i);
			if(qType && qType.value) {
				var type = qType.value;
				if (type === 'Choose') {
					return null;
				}
				question.type = type;
				if(type === 'Radio' || type === 'Dropdown' || type === 'CheckBox') {
					var opt = null;
					for(var cnt = 1; cnt <=5; cnt++) {
						opt = document.getElementById('q_'+i+'_option_'+cnt);
						if(opt && opt.value){
							var t = {};
							t.text = opt.value;
							question.options.push(t);
						}
					}
				}
			}
			questions.push(question);
		}
	}

	survey.questions = questions;
	return survey;
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

	cardBody.appendChild(getQuestionSet(i));
	questionCard.appendChild(collapse);
};

function getQuestionSet(i) {
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
	qText.className = "form-control";
	qText.rows = 1;
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

	row2.appendChild(row2Col1);
	parent.appendChild(row2);

	return parent;
};

function attachOptionsDiv(e, optionType, questionIndex) {
	var container = e.path[4].childNodes[1].childNodes[0];
	removeAllChild(container);
	var optionDiv = null;
	switch (optionType) {
	case "Radio":
		optionDiv = getRadioOptionDiv(questionIndex);
		break;
	case "Dropdown":
		optionDiv = getDropdownOptionDiv(questionIndex);
		break;
	case "TextField":
		optionDiv = getTextFieldOptionDiv(questionIndex);
		break;
	case "CheckBox":
		optionDiv = getCheckBoxOptionDiv(questionIndex);
		break;
	case "Gender":
		optionDiv = getGenderOptionDiv(questionIndex);
		break;
	case "YesNo":
		optionDiv = getYesNoOptionDiv(questionIndex);
		break;
	case "Date":
		optionDiv = getDateOptionDiv(questionIndex);
		break;
	case "Image":
		optionDiv = getImageOptionDiv(questionIndex);
		break;
	case "Geocode":
		optionDiv = getGeocodeOptionDiv(questionIndex);
		break;
	default:
		break;
	}
	if (optionDiv) {
		container.appendChild(optionDiv);
	}
};

function getFiveOptionsDiv(questionIndex) {
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
		divParent.appendChild(qText);
	}
	return divParent;
};

function getRadioOptionDiv(questionIndex) {
	return getFiveOptionsDiv(questionIndex);
};

function getDropdownOptionDiv(questionIndex) {
	return getFiveOptionsDiv(questionIndex);
};

function getTextFieldOptionDiv(questionIndex) {
	return null;
};

function getCheckBoxOptionDiv(questionIndex) {
	return getFiveOptionsDiv(questionIndex);
};

function getGenderOptionDiv(questionIndex) {
	return null;
};

function getYesNoOptionDiv(questionIndex) {
	return null;
};

function getDateOptionDiv(questionIndex) {
	return null;
};

function getImageOptionDiv(questionIndex) {
	return null;
};

function getGeocodeOptionDiv(questionIndex) {
	return null;
};

function getQTypeOption(value, text, isSelected) {
	var option = document.createElement('option');
	option.value = value;
	option.innerText = text;
	if(isSelected) {
		option.setAttribute("selected", true);
	}
	return option;
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

function getSurveyData(surveyId) {
	var survey = {};
	survey.id = "survey_1";
	survey.name = "Survey Name";
	survey.createdOn = Date();
	survey.createdBy = "Bhanu";
	survey.questions = [];
	for(var i = 0; i < 3; i++) {
		survey.questions[i] = {};
		survey.questions[i].id = "question_" + (i+1);
		survey.questions[i].text = "This is question# " + (i+1);
		survey.questions[i].type = "Radio";
		survey.questions[i].surveyId = survey.id;
		survey.questions[i].options = [];
		for(var j = 0; j < 5; j++) {
			survey.questions[i].options[j] = {};
			survey.questions[i].options[j].optionId = "option_" + (i+1) + "_" + (j+1);
			survey.questions[i].options[j].optionText = "This is option# " + (j+1) + " for question# " + (i+1);
		}
	}
	return survey;
};

function readSurvey(data) {

};

function editSurvey(data) {

};