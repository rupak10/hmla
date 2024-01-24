$('.btnExerciseDel').click(function() {
	let exerciseJson = $(this).attr('data-exercise');
	const exercise = convertStringToJsObject(exerciseJson);
	console.log(exercise);

	populateModal(exercise);
	
	const exerciseModal = new bootstrap.Modal(document.getElementById("exerciseModal"), {'backdrop' :'static'});
	exerciseModal.show();
});


const convertStringToJsObject = (dataString) => {
	// Step 1: Remove the unnecessary prefixes
	const trimmedData = dataString.replace("ExerciseDTO(", "").replace(")", "");
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


const populateModal = (exercise) => {
	
	let deleteUrl = '/exercise/delete/' + exercise.id;
		
	var $DeleteLink = $('#delExerciseBtn');
    $DeleteLink.attr('href', deleteUrl);
};


	