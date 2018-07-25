function removeAllChild(node) {
	if (node && node.hasChildNodes()) {
		while (node.hasChildNodes()) {
			node.removeChild(node.firstChild);
		}
	}
};

function getModelUtility(id, title, okText, cancelText, okAction, cancelAction, body, validationFunction, errorText) {
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
	h5.innerText = title;

	var closeBtn = document.createElement('button');
	closeBtn.type = "button";
	closeBtn.className = "close";
	closeBtn.setAttribute("data-dismiss", "modal");
	closeBtn.setAttribute("aria-label", "Close");
	closeBtn.addEventListener("click", function() {
		if (cancelAction) {
			cancelAction();
		}
	});

	var span = document.createElement('span');
	span.setAttribute("aria-hidden", "true");
	span.innerHTML = "&times;";
	closeBtn.appendChild(span);

	modalHeader.appendChild(h5);
	modalHeader.appendChild(closeBtn);

	var modalBody = document.createElement('div');
	modalBody.className = "modal-body";

	modalBody.appendChild(getInfoAlertUtility(errorText));
	modalBody.appendChild(body);

	var modalFooter = document.createElement('div');
	modalFooter.className = "modal-footer";

	var proceed = document.createElement('button');
	proceed.type = "button";
	proceed.className = "btn btn-primary";
	proceed.innerText = okText;
	proceed.enabled = false;
	proceed.addEventListener("click", function() {
		if (validationFunction()) {
			okAction();
		} else {
			$("#warningAlert").show();
		}
	});
	
	modalFooter.appendChild(proceed);

	var cancel = document.createElement('button');
	cancel.type = "button";
	cancel.className = "btn btn-secondary";
	cancel.addEventListener("click", function() {
		cancelAction();
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

function getInfoAlertUtility(text) {
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


function fetchMyRoles() {
	var httpRequest = new XMLHttpRequest();
	var url = contextPath + "/roles";
	httpRequest.open('GET', url);
	/*httpRequest.onload = function () {
		if (httpRequest.readyState == 4 && httpRequest.status == "200") {
			var response = httpRequest.responseText;
        	var roles = JSON.parse(response);
        	return roles;
		} else {
			// error scenario
		}
	}*/
	httpRequest.send(null);
	return httpRequest;
}