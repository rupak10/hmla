$('.btnSleepDel').click(function() {
	let sleepJson = $(this).attr('data-sleep');
	const sleep = convertStringToJsObject(sleepJson);

	populateModal(sleep);
	
	const sleepModal = new bootstrap.Modal(document.getElementById("sleepModal"), {'backdrop' :'static'});
	sleepModal.show();
});


const convertStringToJsObject = (dataString) => {
	// Step 1: Remove the unnecessary prefixes
	const trimmedData = dataString.replace("SleepDTO(", "").replace(")", "");
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


const populateModal = (sleep) => {
	
	let deleteUrl = '/sleep/delete/' + sleep.id;
		
	var $DeleteLink = $('#delSleepBtn');
    $DeleteLink.attr('href', deleteUrl);
};


	