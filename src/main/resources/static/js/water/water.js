$('.btnWaterDel').click(function() {
	let waterJson = $(this).attr('data-water');
	const water = convertStringToJsObject(waterJson);

	populateModal(water);
	
	const waterModal = new bootstrap.Modal(document.getElementById("waterModal"), {'backdrop' :'static'});
	waterModal.show();
});


const convertStringToJsObject = (dataString) => {
	// Step 1: Remove the unnecessary prefixes
	const trimmedData = dataString.replace("WaterDTO(", "").replace(")", "");
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


const populateModal = (water) => {
	let deleteUrl = '/water/delete/' + water.id;
		
	var $DeleteLink = $('#delWaterBtn');
    $DeleteLink.attr('href', deleteUrl);
};


	