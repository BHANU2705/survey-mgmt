function showCreateSurveyPage() {
	var surveyAllPage = document.getElementById("surveyAllPage");
	removeAllChild(surveyAllPage);
	$("#surveyAllPage").hide();
	var readSurveyPage = document.getElementById("readSurveyPage");
	removeAllChild(readSurveyPage);
	$("#readSurveyPage").show();
	readSurvey();
};

function readSurvey() {
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