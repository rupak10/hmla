$('.btnRoleDel').click(function() {
	let roleJson = $(this).attr('data-role');
	const role = convertStringToJsObject(roleJson);
	console.log(role);
	
	clearModal();
	populateModal(role);
	
	const roleModal = new bootstrap.Modal(document.getElementById("roleModal"), {'backdrop' :'static'});
	roleModal.show();
});


const convertStringToJsObject = (dataString) => {
	// Step 1: Remove the unnecessary prefixes
	const trimmedData = dataString.replace("RoleDto(", "").replace(")", "");
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

const clearModal = () => {
	$('#roleName').val('');
	$('#roleDescription').val('');
};

const populateModal = (role) => {
	$('#roleName').val(role.roleName);
	$('#roleDescription').val(role.roleDescription);
	
	let deleteUrl = '/roles/delete/' + role.id;
		
	var $DeleteLink = $('#delRoleBtn');
    $DeleteLink.attr('href', deleteUrl);
};


	