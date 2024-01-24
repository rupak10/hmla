$(document).ready(function() {

     $("#btn_search").click(function() {
          const selectedDate = $("#searchDate").val();
          if(selectedDate) {
                fetchFitnessData(selectedDate);
          }
          else{
                alert('Please select date !');
          }
     });

     const fetchFitnessData = function(searchDate) {
         //console.log('inside fetchFitnessData() method');
         //console.log('searchDate : '+searchDate);
        var postData = {
            searchDate: searchDate
        };

        // AJAX POST request
        $.ajax({
            type: "POST",
            url: "http://localhost:8181/api/data",
            data: JSON.stringify(postData),
            contentType: "application/json",
            success: function(response) {
                console.log("Success:", response);
                if(response){
                    populateCalorieCard(response.calorie);
                    populateExerciseCard(response.exercise);
                    populateSleepCard(response.sleep);
                    populateWaterCard(response.water);
                    populateDailyGoalInfoCard(response.dailyGoal);
                }
            },
            error: function(error) {
                console.error("Error:", error);
            }
        });
    };

    const populateCalorieCard = function(calorieInfo) {
        if(!calorieInfo){
            let htmlContent = '<p class="card-text">No calorie data found !</p>';
            $("#calorie_info_container").html('');
            $("#calorie_info_container").append(htmlContent);
            return;
        }

        let htmlContent = '<p class="card-text">' + '<span>Food Type : ' + calorieInfo.foodType + '</span>' +'</p>';
        htmlContent += '<p class="card-text">' + '<span>Amount of Food(grams): ' + calorieInfo.amountOfFood + '</span>' +'</p>';
        htmlContent += '<p class="card-text">' + '<span>Calories : ' + calorieInfo.calories + '</span>' +'</p>';
        $("#calorie_info_container").html('');
        $("#calorie_info_container").append(htmlContent);
    };

    const populateExerciseCard = function(exerciseInfo) {
        if(!exerciseInfo){
            let htmlContent = '<p class="card-text">No exercise data found !</p>';
            $("#exercise_info_container").html('');
            $("#exercise_info_container").append(htmlContent);
            return;
        }

        let htmlContent = '<p class="card-text">' + '<span>Exercise Name : ' + exerciseInfo.exerciseName + '</span>' +'</p>';
        htmlContent += '<p class="card-text">' + '<span>Total Minutes : ' + exerciseInfo.totalMinutes + '</span>' +'</p>';
        htmlContent += '<p class="card-text">' + '<span>Calories Burnt: ' + exerciseInfo.caloriesBurnt + '</span>' +'</p>';
        $("#exercise_info_container").html('');
        $("#exercise_info_container").append(htmlContent);
    };

    const populateSleepCard = function(sleepInfo) {
        if(!sleepInfo){
            let htmlContent = '<p class="card-text">No sleep data found !</p>';
            $("#sleep_info_container").html('');
            $("#sleep_info_container").append(htmlContent);
            return;
        }

        let htmlContent = '<p class="card-text">' + '<span>Total Sleep Time(hours) : ' + sleepInfo.totalSleepTime + '</span>' +'</p>';
        $("#sleep_info_container").html('');
        $("#sleep_info_container").append(htmlContent);
    };

    const populateWaterCard = function(waterInfo) {
        if(!waterInfo){
            let htmlContent = '<p class="card-text">No water data found !</p>';
            $("#water_info_container").html('');
            $("#water_info_container").append(htmlContent);
            return;
        }

        let htmlContent = '<p class="card-text">' + '<span>Water Amount : ' + waterInfo.amount + '</span>' +'</p>';
        $("#water_info_container").html('');
        $("#water_info_container").append(htmlContent);
    };

    const populateDailyGoalInfoCard = function(dailyGoalInfo) {
        if(!dailyGoalInfo){
            let htmlContent = '<p class="card-text">No daily goal data found !</p>';
            $("#dailyGoal_info_container").html('');
            $("#dailyGoal_info_container").append(htmlContent);
            return;
        }


        let htmlContent = '<p class="card-text">' + '<span>Calories : ' + dailyGoalInfo.calories + '</span>' +'</p>';
        htmlContent += '<p class="card-text">' + '<span>Exercise Minutes : ' + dailyGoalInfo.exerciseMinutes + '</span>' +'</p>';
        htmlContent += '<p class="card-text">' + '<span>Sleep: ' + dailyGoalInfo.sleep + '</span>' +'</p>';
        htmlContent += '<p class="card-text">' + '<span>Water: ' + dailyGoalInfo.water + '</span>' +'</p>';
        $("#dailyGoal_info_container").html('');
        $("#dailyGoal_info_container").append(htmlContent);
    };

    //initialization
    let currentDate = new Date().toISOString().slice(0, 10);
    $("#searchDate").val(currentDate);
    fetchFitnessData(currentDate);
});