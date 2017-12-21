function handleLeftPane(caller) {
	if (caller) {
		if (caller === 'overview') {
			$("#overview").show();
			$("#survey").hide();
			$("#user").hide();
			$("#analytics").hide();
			$("#subscription").hide();

			$("#a_overview").addClass("active");
			$("#li_home").addClass("active");
			$("#a_survey").removeClass("active");
			$("#a_user").removeClass("active");
			$("#a_analytics").removeClass("active");
			$("#a_subscription").removeClass("active");
			$("#li_user").removeClass("active");
		} else if (caller === 'survey') {
			$("#overview").hide();
			$("#survey").show();
			$("#user").hide();
			$("#analytics").hide();
			$("#subscription").hide();

			$("#a_overview").removeClass("active");
			$("#li_home").removeClass("active");
			$("#a_survey").addClass("active");
			$("#a_user").removeClass("active");
			$("#a_analytics").removeClass("active");
			$("#a_subscription").removeClass("active");
			$("#li_user").removeClass("active");
		} else if (caller === 'user') {
			$("#overview").hide();
			$("#survey").hide();
			$("#user").show();
			$("#analytics").hide();
			$("#subscription").hide();

			$("#a_overview").removeClass("active");
			$("#li_home").removeClass("active");
			$("#a_survey").removeClass("active");
			$("#a_user").addClass("active");
			$("#a_analytics").removeClass("active");
			$("#a_subscription").removeClass("active");
			$("#li_user").removeClass("active");
		} else if (caller === 'analytics') {
			$("#overview").hide();
			$("#survey").hide();
			$("#user").hide();
			$("#analytics").show();
			$("#subscription").hide();

			$("#a_overview").removeClass("active");
			$("#li_home").removeClass("active");
			$("#a_survey").removeClass("active");
			$("#a_user").removeClass("active");
			$("#a_analytics").addClass("active");
			$("#a_subscription").removeClass("active");
			$("#li_user").removeClass("active");
		} else if (caller === 'subscription') {
			$("#overview").hide();
			$("#survey").hide();
			$("#user").hide();
			$("#analytics").hide();
			$("#subscription").show();

			$("#a_overview").removeClass("active");
			$("#li_home").removeClass("active");
			$("#a_survey").removeClass("active");
			$("#a_user").removeClass("active");
			$("#a_analytics").removeClass("active");
			$("#a_subscription").addClass("active");
			$("#li_user").removeClass("active");
		}
	}
};

function handleSurveyTab(caller) {
	if (caller) {
		if (caller === 'survey_new') {
			$("#a_survey_new").addClass("active");
			$("#a_survey_existing").removeClass("active");
			$("#div_survey_new").show();
		} else if (caller === 'survey_existing') {
			$("#a_survey_new").removeClass("active");
			$("#a_survey_existing").addClass("active");
		}
	}
};

function navigateToSubscription() {
	$("#a_subscription").click();
};

function handleMyProfile() {
	$("#li_user").addClass("active");
	$("#li_home").removeClass("active");
	$("#a_my_profile").addClass("active");
	$("#a_overview").removeClass("active");
	$("#overview").hide();
	$("#survey").hide();
	$("#user").hide();
	$("#analytics").hide();
	$("#subscription").hide();
	$("#myProfile").show();
	$("#div_general").show();
	$("#div_account").hide();
};

function handleProfileTab(caller) {
	if (caller) {
		$("#li_home").removeClass("active");
		$("#li_user").addClass("active");
		$("#a_overview").removeClass("active");
		$("#li_home").removeClass("active");
		$("#a_survey").removeClass("active");
		$("#a_user").removeClass("active");
		$("#a_analytics").removeClass("active");
		$("#a_subscription").removeClass("active");
		$("#a_profile_general").addClass("active");
		$("#div_general").show();
		$("#div_account").hide();
		if (caller === 'general') {
			$("#div_general").show();
			$("#div_account").hide();
			$("#a_profile_general").addClass("active");
			$("#a_profile_account").removeClass("active");
		} else if (caller === 'account') {
			$("#div_general").hide();
			$("#div_account").show();
			$("#a_profile_account").addClass("active");
			$("#a_profile_general").removeClass("active");
		}
	}
};

var ctx1 = $("#myChart1");
var ctx2 = $("#myChart2");
var myChart1 = new Chart(ctx1, {
	type : 'bar',
	data : {
		labels : [ "Survey 1", "Survey 2", "Survey 3", "Survey 4", "Survey 5",
				"Survey 6" ],
		datasets : [ {
			label : '# of End-Users',
			data : [ 12, 19, 3, 5, 2, 3 ],
			backgroundColor : [ 'rgba(255, 99, 132, 0.2)',
					'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)',
					'rgba(75, 192, 192, 0.2)', 'rgba(153, 102, 255, 0.2)',
					'rgba(255, 159, 64, 0.2)' ],
			borderColor : [ 'rgba(255,99,132,1)', 'rgba(54, 162, 235, 1)',
					'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)',
					'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)' ],
			borderWidth : 1
		} ]
	},
	options : {
		responsive : true,
		scales : {
			yAxes : [ {
				ticks : {
					beginAtZero : true
				}
			} ]
		}
	}
});

var myChart2 = new Chart(ctx2, {
	type : 'bar',
	data : {
		labels : [ "Survey 1", "Survey 2", "Survey 3", "Survey 4", "Survey 5",
				"Survey 6" ],
		datasets : [ {
			label : '# of Responses',
			data : [ 65, 9, 33, 47, 21, 7 ],
			backgroundColor : [ 'rgba(255, 99, 132, 0.2)',
					'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)',
					'rgba(75, 192, 192, 0.2)', 'rgba(153, 102, 255, 0.2)',
					'rgba(255, 159, 64, 0.2)' ],
			borderColor : [ 'rgba(255,99,132,1)', 'rgba(54, 162, 235, 1)',
					'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)',
					'rgba(153, 102, 255, 1)', 'rgba(255, 159, 64, 1)' ],
			borderWidth : 1
		} ]
	},
	options : {
		responsive : true,
		scales : {
			yAxes : [ {
				ticks : {
					beginAtZero : true
				}
			} ]
		}
	}
});