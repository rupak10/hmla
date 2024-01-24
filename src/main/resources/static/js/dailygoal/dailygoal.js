$('.btnDailyGoalDel').click(function() {
	let dailyGoalJson = $(this).attr('data-dailygoal');
	const dailyGoal = convertStringToJsObject(dailyGoalJson);

	populateModal(dailyGoal);
	
	const dailyGoalModal = new bootstrap.Modal(document.getElementById("dailyGoalModal"), {'backdrop' :'static'});
	dailyGoalModal.show();
});


const convertStringToJsObject = (dataString) => {
	// Step 1: Remove the unnecessary prefixes
	const trimmedData = dataString.replace("DailyGoalDTO(", "").replace(")", "");
	// Step 2: Split the string into individual key-value pairs
	const keyValuePairs = trimmedData.split(", ");
	// Step 3: Create the JavaScript object using these key-value pairs
	const jsObject = {};
	keyValuePairs.forEach(pair => {
		const [key, value] = pair.split("=");
		jsObject[key] = value === "null" ? null : value;
	});
	return jsObject;
};


const populateModal = (dailyGoal) => {
	let deleteUrl = '/dailygoal/delete/' + dailyGoal.id;
		
	var $deleteLink = $('#delDailyGoalBtn');
	$deleteLink.attr('href', deleteUrl);
};


	