var ANSWERED = "Answered";
var UN_ANSWERED = "Un-answered";

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
	thead_th4.innerText = "Status";
	var thead_th5 = document.createElement("th");
	thead_th5.innerText = "Actions";

	thead_tr.appendChild(thead_th1);
	thead_tr.appendChild(thead_th2);
	thead_tr.appendChild(thead_th3);
	thead_tr.appendChild(thead_th4);
	thead_tr.appendChild(thead_th5);
	thead.appendChild(thead_tr);
	assignedSurveyListTable.appendChild(thead);

	$.blockUI({
		message : 'Fetching Assigned Survey List...'
	});
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
				index.innerText = (i + 1);

				var surveyName = document.createElement("td");
				surveyName.innerText = data[i].name;

				var owner = document.createElement("td");
				owner.innerText = data[i].owner;
				
				var status = document.createElement("td");
				
				var para = document.createElement("p");
				para.style = "padding-right: 10px;";
				
				if (data[i].isResponded) {
					para.innerText = ANSWERED;
					para.className = "text-success font-weight-bold qStatus";
				} else {
					para.className = "text-danger font-weight-bold";
					para.innerText = UN_ANSWERED;
				}
				status.appendChild(para);

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
				pIcon.id = "edit_icon_" + 1;

				pDivA.appendChild(pIcon);
				pDiv.appendChild(pDivA);

				var dropDownMenuId = "dropDownMenuId_" + data[i].id;
				var dropDownMenuDiv = document.createElement("div");
				dropDownMenuDiv.id = dropDownMenuId;
				dropDownMenuDiv.className = "dropdown-menu";
				dropDownMenuDiv.setAttribute("aria-labelledby", testId);
				dropDownMenuDiv.style = "min-width: auto;";

				var dropdownItem1 = document.createElement("a");
				dropdownItem1.id = "respond_survey" + GLOBAL_SEPARATOR + data[i].id;
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
					dropdownItem1.addEventListener("click", viewSurveyResponse);
					dropdownItem1.title = "Already Replied, View Response.";
				}

				row.appendChild(index);
				row.appendChild(surveyName);
				row.appendChild(owner);
				row.appendChild(status);
				row.appendChild(pDiv);
				tBody.appendChild(row);
			}
			assignedSurveyListTable.appendChild(tBody);
		}
	}
};

function replySurvey(evt) {
	var targetId = evt.currentTarget.id.trim();
	var id = targetId.split(GLOBAL_SEPARATOR)[1];
	readAssignedSpecificSurvey(id);
};

function viewSurveyResponse(evt) {
	var targetId = evt.currentTarget.id.trim();
	var surveyId = targetId.split(GLOBAL_SEPARATOR)[1];
	readSurveyResponse(surveyId);
};

function readSurveyResponse(surveyId) {
	console.log("hello: " + surveyId);
};

function readAssignedSpecificSurvey(surveyId) {
	var httpRequest = new XMLHttpRequest();
	var url = contextPath + "/survey?id=" + surveyId;
	httpRequest.open('GET', url);
	httpRequest.onload = function() {
		// $.unblockUI();
		if (httpRequest.readyState == 4 && httpRequest.status == "200") {
			var response = httpRequest.responseText;
			var survey = JSON.parse(response);
			var card = getAssignedSurveyCard(survey);

			$("#assignedSurveyList").hide();
			var parent = document.getElementById("assignedSurveyList");
			removeAllChild(parent);
			parent.appendChild(card);
			$("#assignedSurveyList").show();
		} else {
			// error scenario
		}
	}
	httpRequest.send(null);
};

function getAssignedSurveyCard(survey) {
	var card = document.createElement('div');
	card.className = "card";
	surveyAllPage.appendChild(card);

	var pDiv = document.createElement('div');
	pDiv.className = "card-header";
	card.appendChild(pDiv);

	var row = document.createElement('div');
	row.className = "row";
	pDiv.appendChild(row);

	var col1 = document.createElement('div');
	col1.className = "col text-left";
	col1.style = "padding-top: 9px;";
	var surveyTitle = document.createElement('h5');
	surveyTitle.id = "sTitle_" + survey.id;
	surveyTitle.innerText = survey.name;
	surveyTitle.style = "text-align: left;";
	col1.appendChild(surveyTitle);
	row.appendChild(col1);

	var col2 = document.createElement("div");
	col2.className = "col text-right";
	var btn = document.createElement('button');
	btn.className = "btn btn-md btn-primary btn-create";
	btn.innerHTML = "Back to Survey Listing";
	btn.style = "background-color: #03ab22;color: white;";

	btn.addEventListener("click", function() {
		onLoadAssignedSurveyList();
	});

	col2.appendChild(btn);
	row.appendChild(col2);

	var cardBody = document.createElement('div');
	cardBody.className = "card-body";

	loadQuestions(cardBody, survey);
	card.appendChild(cardBody);

	var cardFooter = document.createElement('div');
	cardFooter.className = "card-footer text-right";

	var submitResponse = document.createElement('button');
	submitResponse.className = "btn btn-md btn-primary btn-create";
	submitResponse.innerText = "Submit";
	submitResponse.id = "submit";
	submitResponse.style = "background-color: #03ab22;color: white;";
	submitResponse.setAttribute("data-toggle", "tooltip");
	submitResponse.setAttribute("disabled", true);
	submitResponse.setAttribute("data-toggle", "tooltip");
	submitResponse.setAttribute("data-placement", "left");
	if (submitResponse.getAttribute("disabled")) {
		submitResponse.title = "Answer all the questions to enable Submit button.";
	}
	submitResponse.addEventListener("click", function() {
		var formData = collectAnswers(survey);

		$.ajax({
			url: contextPath + '/response',
			data: formData,
			processData: false,
			type: 'POST',
			contentType: false, 
			mimeType: 'multipart/form-data',
			success: function (data) {
				alert("Response submitted successfully.");
				onLoadAssignedSurveyList();
			},
			error: function(data) {
				alert(data.responseText);
			}
		});
	});
	cardFooter.appendChild(submitResponse);
	card.appendChild(cardFooter);

	return card;
};

function loadQuestions(cardBody, survey) {
	if (survey && survey.questions && survey.questions.length > 0) {
		for (var i = 0; i < survey.questions.length; i++) {
			var question = survey.questions[i];
			var questionDiv = getEachQuestionDiv(i, question, survey.id);
			cardBody.appendChild(questionDiv);
		}
	}
};

function getEachQuestionDiv(i, question, surveyId) {
	var questionParent = document.createElement('div');
	questionParent.id = 'div_accordion_q_' + i;
	questionParent.role = "tablist";

	var questionCard = document.createElement('div');
	questionCard.className = "card";
	questionCard.style = "margin-bottom: 15px;";

	var cardHeader = document.createElement('div');
	cardHeader.className = "card-header";
	cardHeader.role = "tab";
	cardHeader.id = "heading_" + i;

	var h5 = document.createElement('h5');
	h5.className = "mb-0 text-left";

	var collapseId = 'collapse_' + i;

	var a = document.createElement('a');
	a.id = "anchor_" + i;
	a.setAttribute('data-toggle', 'collapse');
	a.setAttribute('href', '#' + collapseId);
	a.setAttribute('aria-expanded', 'true');
	a.setAttribute('aria-controls', collapseId);
	a.innerHTML = (i + 1) + ". " + question.text;
	a.className = "text-left";

	h5.appendChild(a);

	var questioRow = document.createElement('div');
	questioRow.className = "row";

	var questioRow_Col1 = document.createElement("div");
	questioRow_Col1.className = "col text-left";

	questioRow_Col1.appendChild(h5);
	questioRow.appendChild(questioRow_Col1);

	var questioRow_Col2 = document.createElement("div");
	questioRow_Col1.className = "col text-right";

	var para = document.createElement("p");
	para.id = "para_" + question.id;
	para.style = "padding-right: 10px;";
	para.className = "text-danger font-weight-bold qStatus";
	para.innerText = UN_ANSWERED;
	questioRow_Col2.appendChild(para);

	questioRow.appendChild(questioRow_Col2);

	cardHeader.appendChild(questioRow);
	questionCard.appendChild(cardHeader);

	var collapse = document.createElement('div');
	collapse.id = collapseId;
	collapse.className = "collapse show";
	collapse.role = "tabpanel";
	collapse.setAttribute('aria-labelledby', cardHeader.id);
	collapse.setAttribute('data-parent', '#' + questionParent.id);

	var cardBody = document.createElement('div');
	cardBody.className = "card-body";

	cardBody.appendChild(getOptionsDiv(question, surveyId));

	collapse.appendChild(cardBody);
	questionCard.appendChild(collapse);

	questionParent.appendChild(questionCard);
	return questionParent;
};

function getOptionsDiv(question, surveyId) {
	var qType = question.type;
	if (qType === "Radio") {
		return getRadioButtonDiv(question, surveyId);
	} else if (qType === "Dropdown") {
		return getDropdownDiv(question, surveyId);
	} else if (qType === "TextField") {
		return getTextFieldDiv(question, surveyId);
	} else if (qType === "CheckBox") {
		return getCheckBoxDiv(question, surveyId);
	} else if (qType === "Gender") {
		return getGenderDiv(question, surveyId);
	} else if (qType === "YesNo") {
		return getYesNoDiv(question, surveyId);
	} else if (qType === "Date") {
		return getDatePickerDiv(question, surveyId);
	} else if (qType === "Image") {
		return getImageUploadDiv(question, surveyId);
	}/* else if (qType === "Geocode") {

	}*/
	return document.createElement('div');
};

function getRadioButtonDiv(question, surveyId) {
	var optionsParent = document.createElement('div');

	for (var i = 0; i < question.options.length; i++) {
		var option = document.createElement('div');
		option.className = "form-check";

		var absoluteId = question.type + GLOBAL_SEPARATOR + surveyId + GLOBAL_SEPARATOR + question.id
		+ GLOBAL_SEPARATOR + question.options[i].id;

		var input = document.createElement('input');
		input.className = "form-check-input";
		input.type = "radio";
		input.name = question.id;
		input.id = absoluteId;
		input.value = question.options[i].id;
		input.addEventListener("click", function(e) {
			var paraId = "para_" + question.id;
			setToAnswered(paraId);
		});

		var label = document.createElement('label');
		label.className = "form-check-label";
		label.setAttribute("for", absoluteId);
		label.innerText = question.options[i].text;

		option.appendChild(input);
		option.appendChild(label);
		optionsParent.appendChild(option);
	}
	return optionsParent;
};

function getDropdownDiv(question, surveyId) {
	var optionsParent = document.createElement('div');
	var absoluteId = question.type + GLOBAL_SEPARATOR + surveyId + GLOBAL_SEPARATOR + question.id;

	var select = document.createElement('select');
	select.className = "custom-select";
	select.id = question.type + GLOBAL_SEPARATOR + surveyId + GLOBAL_SEPARATOR + question.id;

	var opt = document.createElement('option');
	opt.setAttribute("selected", true);
	opt.setAttribute("value", "choose");
	opt.innerText = "Choose...";
	select.appendChild(opt);

	for (var i = 0; i < question.options.length; i++) {
		var opt = document.createElement('option');
		opt.setAttribute("value", question.options[i].id);
		opt.innerText = question.options[i].text;
		select.appendChild(opt);
	}

	select.addEventListener("change", function(e) {
		var e1 = document.getElementById(e.currentTarget.id);
		var value = e1.options[e1.selectedIndex].value;
		if (value) {
			var paraId = "para_" + question.id;
			if (value === "choose") {
				resetToUnAnswered(paraId);
			} else {
				setToAnswered(paraId);
			}
		}
	});

	optionsParent.appendChild(select);
	return optionsParent;
};

function getTextFieldDiv(question, surveyId) {
	var optionsParent = document.createElement('div');
	var input = document.createElement('input');
	input.className = "form-control";
	var absoluteId = question.type + GLOBAL_SEPARATOR + surveyId + GLOBAL_SEPARATOR + question.id;
	input.id = absoluteId;
	input.type = "text";
	optionsParent.appendChild(input);
	input.addEventListener("change", function(e) {
		var paraId = "para_" + question.id;
		if (input.value.trim()) {
			setToAnswered(paraId);
		} else {
			resetToUnAnswered(paraId);
		}
	});
	return optionsParent;
};

function getCheckBoxDiv(question, surveyId) {
	var optionsParent = document.createElement('div');
	for (var i = 0; i < question.options.length; i++) {
		var option = document.createElement('div');
		option.className = "form-check";

		var absoluteId = question.type + GLOBAL_SEPARATOR + surveyId + GLOBAL_SEPARATOR + question.id
		+ GLOBAL_SEPARATOR + question.options[i].id;

		var input = document.createElement('input');
		input.className = "form-check-input";
		input.type = "checkbox";
		input.id = absoluteId;
		input.value = question.options[i].id;
		input.addEventListener("click", function(e) {
			var paraId = "para_" + question.id;
			var isChecked = $('input:checkbox').is(':checked')
			if (isChecked) {
				setToAnswered(paraId);
			} else {
				resetToUnAnswered(paraId);
			}
		});

		var label = document.createElement('label');
		label.className = "form-check-label";
		label.setAttribute("for", absoluteId);
		label.innerText = question.options[i].text;

		option.appendChild(input);
		option.appendChild(label);
		optionsParent.appendChild(option);
	}
	return optionsParent;
};

function getGenderDiv(question, surveyId) {
	var optionsParent = document.createElement('div');
	optionsParent
	.appendChild(getGenericRadioOption(question, surveyId, "Male"));
	optionsParent.appendChild(getGenericRadioOption(question, surveyId,
	"Female"));
	optionsParent
	.appendChild(getGenericRadioOption(question, surveyId, "LGBT"));
	return optionsParent;
};

function getYesNoDiv(question, surveyId) {
	var optionsParent = document.createElement('div');
	optionsParent.appendChild(getGenericRadioOption(question, surveyId, "Yes"));
	optionsParent.appendChild(getGenericRadioOption(question, surveyId, "No"));
	return optionsParent;
};

function getDatePickerDiv(question, surveyId) {
	$(function() {
		$('[data-toggle="datepicker"]').datepicker({
			autoHide : true,
			zIndex : 2048,
			format : 'dd-mm-yyyy'
		}).on('pick.datepicker', function(e) {
			var paraId = "para_" + question.id;
			if (e.date) {
				setToAnswered(paraId);
			} else {
				resetToUnAnswered(paraId);
			}
		});
	});

	var optionsParent = document.createElement('div');
	var absoluteId = question.type + GLOBAL_SEPARATOR + surveyId + GLOBAL_SEPARATOR + question.id;

	var datePicker = document.createElement('input');
	datePicker.setAttribute("data-toggle", "datepicker");
	datePicker.setAttribute("readonly", true);
	datePicker.type = "text";
	datePicker.className = "form-control";
	datePicker.style = "max-width: fit-content;";
	datePicker.id = absoluteId;
	optionsParent.appendChild(datePicker);

	return optionsParent;
};

function getImageUploadDiv(question, surveyId) {

	var absoluteId = question.type + GLOBAL_SEPARATOR + surveyId + GLOBAL_SEPARATOR + question.id;
//	var absoluteId = "bhanuImage";

	var optionsParent = document.createElement('div');
	optionsParent.className = "input-group mb-3";

	var customFileDiv = document.createElement('div');
	customFileDiv.className = "custom-file";

	var input = document.createElement('input');
	input.type = "file";
	var customCss = "img_" + question.id;
	input.className = "custom-file-input " + customCss;
	input.id = absoluteId;
	input.name = absoluteId;
	input.setAttribute("accept", "image/*");


	var label = document.createElement('label');
	var customLabelCss = "lbl_" + question.id;
	label.className = "custom-file-label " + customLabelCss;
	label.setAttribute("for", absoluteId);
	label.innerText = "Choose file";

	customFileDiv.appendChild(input);
	customFileDiv.appendChild(label);
	optionsParent.appendChild(customFileDiv);

	$(function() {
		var css = ".img_" + question.id;
		$(css).change(function(e) {
			if (e && e.target && e.target.files && e.target.files[0] && e.target.files[0].name) {
				var fileName = e.target.files[0].name;
				var lblCss = ".lbl_" + question.id;
				$(lblCss).html(fileName);
				var paraId = "para_" + question.id;
				if (fileName) {
					setToAnswered(paraId);
				} else {
					resetToUnAnswered(paraId);
				}
			}
		});
	});

	return optionsParent;
};

function getGenericRadioOption(question, surveyId, val) {
	var option = document.createElement('div');
	option.className = "form-check";

	var absoluteId = question.type + GLOBAL_SEPARATOR + surveyId + GLOBAL_SEPARATOR + question.id
	+ GLOBAL_SEPARATOR + val;

	var input = document.createElement('input');
	input.className = "form-check-input";
	input.type = "radio";
	input.name = question.id;
	input.id = absoluteId;
	input.value = val;
	input.addEventListener("click", function(e) {
		var paraId = "para_" + question.id;
		setToAnswered(paraId);
	});

	var label = document.createElement('label');
	label.className = "form-check-label";
	label.setAttribute("for", absoluteId);
	label.innerText = val;

	option.appendChild(input);
	option.appendChild(label);
	return option;
};

function setToAnswered(paraId) {
	var para = document.getElementById(paraId);
	para.innerText = ANSWERED;
	para.className = "text-success font-weight-bold qStatus";
	enableDisableSubmitButton();
};

function resetToUnAnswered(paraId) {
	var para = document.getElementById(paraId);
	para.className = "text-danger font-weight-bold qStatus";
	para.innerText = UN_ANSWERED;
	enableDisableSubmitButton();
};

function enableDisableSubmitButton() {
	var flags = document.querySelectorAll("p.qStatus");
	var submitBtn = document.getElementById("submit");
	submitBtn.setAttribute("disabled", true);
	if (submitBtn.getAttribute("disabled")) {
		submitBtn.title = "Answer all the questions to enable Submit button.";
	}
	var status = [];
	if(flags) {
		for(var i = 0; i < flags.length; i++) {
			status.push(isAnswered(flags[i].innerText.trim()));
		}
		if(status) {
			var finalResult = true;
			for(var i = 0; i < status.length; i++) {
				finalResult = finalResult && status[i];
			}
		}
		if(finalResult) {
			submitBtn.removeAttribute("disabled");
			submitBtn.title = "Submit Survey";
		}
	}
};

function isAnswered(text) {
	if(text === ANSWERED) {
		return true;
	}
	return false;
};

function collectAnswers(survey) {
	var formData = new FormData();
	var answersJson = {};
	answersJson.surveyId = survey.id;
	answersJson.userId = loggedInUserEmail;
	answersJson.answers = [];

	if(survey && survey.questions && survey.questions.length > 0) {
		for (var k = 0; k < survey.questions.length; k++) {
			var question = survey.questions[k];
			var answer = {};
			answer.questionId = question.id;
			answer.questionType = question.type;
			answer.responses = [];
			var qType = question.type;
			var id = question.type + GLOBAL_SEPARATOR + survey.id + GLOBAL_SEPARATOR + question.id;

			if (qType === "Radio" || qType === "CheckBox") {
				for (var j = 0; j < question.options.length; j++) {
					var option = question.options[j];
					var tempId = id + GLOBAL_SEPARATOR + option.id;
					var element = document.getElementById(tempId);
					if (element && element.checked) {
						var valueJson = {};
						valueJson.value = option.id; // actual answer or option id(s);
						answer.responses.push(valueJson);
					}
				}
			} else if (qType === "Dropdown" || qType === "TextField" || qType === "Date") {
				var element = document.getElementById(id);
				if(element) {
					var valueJson = {};
					valueJson.value = element.value;
					answer.responses.push(valueJson);
				}
			} else if (qType === "Gender") {
				var ids = [];
				ids.push(id + GLOBAL_SEPARATOR + "Male");
				ids.push(id + GLOBAL_SEPARATOR + "Female");
				ids.push(id + GLOBAL_SEPARATOR + "LGBT");
				setAnswersForGenderAndYesNoQuestions(ids, answer);
			} else if (qType === "YesNo") {
				var ids = [];
				ids.push(id + GLOBAL_SEPARATOR + "Yes");
				ids.push(id + GLOBAL_SEPARATOR + "No");
				setAnswersForGenderAndYesNoQuestions(ids, answer);
			} else if (qType === "Image") {
				var image = document.getElementById(id);
				formData.append(question.id, image.files[0]);
			}
			answersJson.answers.push(answer);
		}
	}
	
	formData.append("answers", JSON.stringify(answersJson));
	return formData;
};

function setAnswersForGenderAndYesNoQuestions(ids, answer) {
	if (ids && ids.length > 0) {
		for (var m = 0; m < ids.length; m++) {
			var tmp = ids[m];
			var element = document.getElementById(tmp);
			if (element && element.checked) {
				var valueJson = {};
				var array = tmp.split(GLOBAL_SEPARATOR);
				valueJson.value = array[array.length-1];
				answer.responses.push(valueJson);
			}
		}
	}
};
