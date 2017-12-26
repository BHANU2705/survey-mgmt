var qCount = 0;
function addQuestionUsingCount() {
	qCount++;
	addQuestionDiv(qCount);
};

function addQuestionDiv(i) {
	var superDiv = document.getElementById('qMain');
	
	var questionParent = document.createElement('div');
	questionParent.id = 'div_accordion_q_' + i;
	questionParent.role = "tablist";
	
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
	a.setAttribute('data-toggle', 'collapse');
	a.setAttribute('href', '#'+collapseId);
	a.setAttribute('aria-expanded', 'true');
	a.setAttribute('aria-controls', collapseId);
	a.innerHTML = "Question# "+i;
	
	h5.appendChild(a);
	
	var closeButton = document.createElement('button');
	closeButton.type= "button";
	closeButton.className = "close";
	/*closeButton.setAttribute("data-dismiss", "tablist");*/
	closeButton.setAttribute("aria-label", "Close");
	closeButton.style = "align:right";
	closeButton.addEventListener("click", function(e) {
		console.log(this);
		console.log(i);
		var qParentId = e.path[5].id;
		var qMain = document.getElementById('qMain');
		qMain.removeChild(document.getElementById(qParentId));
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
	
	var qText = document.createElement('input');
	qText.type="text";
	qText.id="qText_" + i;
	qText.name="qText_" + i;
	qText.placeholder = "Enter Question";
	cardBody.appendChild(qText);
	questionCard.appendChild(collapse);
	
	superDiv.appendChild(questionParent);
}