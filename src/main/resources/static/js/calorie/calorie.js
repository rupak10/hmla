$('.btnCalorieDel').click(function() {
	let calorieJson = $(this).attr('data-calorie');
	const calorie = convertStringToJsObject(calorieJson);
	console.log(calorie);

	populateModal(calorie);
	
	const calorieModal = new bootstrap.Modal(document.getElementById("calorieModal"), {'backdrop' :'static'});
	calorieModal.show();
});


const convertStringToJsObject = (dataString) => {
	// Step 1: Remove the unnecessary prefixes
	const trimmedData = dataString.replace("CalorieDTO(", "").replace(")", "");
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


const populateModal = (calorie) => {
	
	let deleteUrl = '/calories/delete/' + calorie.id;
		
	var $DeleteLink = $('#delCalorieBtn');
    $DeleteLink.attr('href', deleteUrl);
};


	